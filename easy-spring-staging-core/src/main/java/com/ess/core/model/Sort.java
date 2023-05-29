/**
 * Copyright(C) 2023 Company:深瞳大观科技有限公司
 */

package com.ess.core.model;

import lombok.*;

/**
 * <p>排序模型</p> .
 *
 * @author caobaoyu
 * @date 2023/5/15 22:07
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = false)
public class Sort {

  // 升序符号
  public static final Integer SORT_TYPE_ASC = 1;
  // 将序符号
  public static final Integer SORT_TYPE_DESC = 0;


  // 模型属性名称
  private String fieldName;

  // 列名称
  private String columnName;

  // key名称
  private String keyName;

  // key名称
  private Integer sortType;


}
