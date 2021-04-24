package com.crazy.community.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;

import java.util.Map;
import java.util.UUID;

/**
 * @ClassName CommunityUtil
 * @Description //TODO
 * @Author crazy402
 * @Date 2021/4/23 15:39
 * @Version 1.0
 **/
public class CommunityUtil {
    /**
     * 生成随机字符串
     *
     * @return
     */

    public static String generateUUID() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    /**
     * md5加密
     *
     * @param key
     * @return
     */
    public static String md5(String key) {
        if (StringUtils.isEmpty(key)) {
            return null;
        }
        return DigestUtils.md5DigestAsHex(key.getBytes());
    }

    public static String getJsonString(int code, String msg, Map<String, Object> map) {
        JSONObject json = new JSONObject();
        json.put("code", code);
        json.put("msg", map);
        if (map != null) {
            for (String key : map.keySet()) {
                json.put(key, map.get(key));
            }
        }
        return json.toJSONString();
    }

    public static String getJsonString(int code) {
        return getJsonString(code, null, null);

    }
    public static String getJsonString(int code, String msg) {
        return getJsonString(code, msg, null);

    }



}
