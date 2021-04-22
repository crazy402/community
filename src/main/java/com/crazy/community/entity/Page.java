package com.crazy.community.entity;

import lombok.Data;

/**
 * @ClassName Page
 * @Description //封装分页有关的信息
 * @Author crazy402
 * @Date 2021/4/22 23:54
 * @Version 1.0
 **/
@Data
public class Page {
  // 当前页
  private Integer current = 1;

  /** 显示当前页展示上限 */
  private Integer limit = 10;
  // 数据总数
  private Integer rows;
  // 查询路径
  private String path;

  public void setCurrent(Integer current) {
      if (current >= 1) {
          this.current = current;
      }
  }

  public void setLimit(Integer limit) {
      if (limit >= 1 && limit <= 100) {
          this.limit = limit;
      }
  }

  public void setRows(Integer rows) {
      if (rows > 0) {
          this.rows = rows;
      }
  }

  /**
   * @return
   * 获取起始页
   * */
  public Integer getOffset() {
      return (current - 1) * limit;
  }

  /**
   * 获取总页数
   * @return
   * */
  public Integer getTotal() {
      if (rows % limit == 0) {
          return rows/limit;
      }else {
          return rows/limit + 1;
      }
  }

  /**
   * 获取起始页码
   * @return */
  public Integer getFrom() {
      int from = current - 2;
      return from < 1 ? 1 : from;
  }

  /**
   * 获取结束页码
   * @return */
  public Integer getTo() {
      int to = current + 2;
      int total = getTotal();
      return to > total ? total : to;
  }
}
