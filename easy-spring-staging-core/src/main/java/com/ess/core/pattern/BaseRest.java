/**
 * Copyright(C) 2023 Company:深瞳大观科技有限公司
 */

package com.ess.core.pattern;

import java.util.Map;

/**
 * <p>基础rest接口</p> .
 *
 * @author caobaoyu
 * @date 2023/5/6 0:38
 */
public interface BaseRest {
  /**
   * 获取排序字段映射
   *
   * @return 字段映射
   * @author caobaoyu
   * @date 2020/5/16 23:53
   */
  Map<String, String> getColumnMap();
}
