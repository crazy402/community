package com.crazy.community.util;

import com.crazy.community.controller.UserController;
import org.apache.commons.lang3.CharUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName SensitiveFilter
 * @Description //TODO
 * @Author crazy402
 * @Date 2021/4/24 1:33
 * @Version 1.0
 */
@Component
public class SensitiveFilter {
    private static final Logger logger = LoggerFactory.getLogger(SensitiveFilter.class);

    // 替换符
    private static final String REPLACEMENT = "***";

    //根节点
    private TrieNode rootNode = new TrieNode();

  /**
   * 项目启动就调用这个初始化
   */
    @PostConstruct
    public void init() {
        try (InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("Ban-words.txt");
             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));){
            String keyword;
            //读取缓冲流每一行的敏感词
            while ((keyword = reader.readLine()) != null) {
                // 添加到前缀树
                this.addKeyword(keyword);
                
            }
        }catch (IOException e) {
            logger.error("加载敏感词文件失败: " + e.getMessage());
        }

    }

    private void addKeyword(String keyword) {
        TrieNode trieNode = rootNode;
        for (int i = 0; i < keyword.length(); i++) {
            char c = keyword.charAt(i);
            TrieNode subNode = trieNode.getSubNode(c);
            if (subNode == null) {
                //初始化节点
                subNode = new TrieNode();
                trieNode.addSubNode(c, subNode);

            }

            // 指向下一个节点
            trieNode = subNode;
            // 设置结束符
            if (i == keyword.length() - 1) {
                trieNode.setKeyWordEnd(true);
            }

        }
    }

    /**
     * @param text 带过滤的文本
     * @return
     */
    public String filter(String text) {
        if (StringUtils.isBlank(text)) {
            return null;
        }
        // 设置指针
        TrieNode tempNode = rootNode;
        int begin = 0;
        int position = 0;

        StringBuffer stringBuffer = new StringBuffer();
        while (position < text.length()) {
            char c = text.charAt(position);

            // 过滤字符防止干扰
            if (isSymbol(c)) {
                // 如果当前存在字符 则指针往下走一步
                if (tempNode == rootNode) {
                    stringBuffer.append(c);
                    begin ++;
                }
                position++;
                continue;
            }

            //检查下级节点
            tempNode = tempNode.getSubNode(c);
            if (tempNode == null) {
                // 以begin开头的字符串不是敏感词
                stringBuffer.append(text.charAt(begin));
                position = ++ begin;
                tempNode = rootNode;
            } else if (tempNode.isKeyWordEnd()) {
                stringBuffer.append(REPLACEMENT);
                begin = ++position;
                tempNode = rootNode;
            } else {
                position++;
            }
        }
        // 将最后一批字符计入结果
        stringBuffer.append(text.substring(begin));
        return stringBuffer.toString();
    }
    public boolean isSymbol(Character c) {

        return !CharUtils.isAsciiAlphanumeric(c) && (c < 0x2E80 || c > 0x9FF);
    }

    /**
     * 前缀树
     */
    public class TrieNode {
        // 关键词结束标语
        private boolean isKeyWordEnd = false;

        // 子节点(key 是下级字符， value是下级节点)
        private Map<Character, TrieNode> subNode = new HashMap<>();

        public boolean isKeyWordEnd() {
            return isKeyWordEnd;
        }

        public void setKeyWordEnd(boolean keyWordEnd) {
            isKeyWordEnd = keyWordEnd;
        }

        // 添加子节点
        public void addSubNode(Character c, TrieNode node) {
            subNode.put(c, node);
        }

        // 获取子节点
        public TrieNode getSubNode(Character c) {
            return subNode.get(c);
        }
    }
}
