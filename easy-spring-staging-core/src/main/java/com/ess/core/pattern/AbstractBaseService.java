/**
 * Copyright(C) 2023 Company:深瞳大观科技有限公司
 */

package com.ess.core.pattern;

import com.ess.core.executor.add.AddExecutor;
import com.ess.core.executor.edit.EditAllExecutor;
import com.ess.core.executor.edit.EditExecutor;
import com.ess.core.executor.query.QueryDetailsExecutor;
import com.ess.core.executor.query.QueryPageExecutor;
import com.ess.core.executor.remove.RemoveExecutor;
import com.ess.core.executor.remove.RemoveMultiExecutor;
import com.ess.core.model.Model;
import com.ess.core.model.Page;
import com.ess.core.model.Query;
import com.ess.core.model.callback.GetCallback;
import com.ess.core.model.callback.SetCallback;
import com.ess.core.sercurity.AuthorizationUser;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * <p>基础抽象服务类</p> .
 *
 * @author caobaoyu
 * @date 2023/4/19 11:19
 */
public abstract class AbstractBaseService<K, M extends Model<K>> {

  /**
   * @param list         数据list
   * @param getCallback 获取属性回调
   * @param <R>         返回值类型
   * @param <M>         模型类型
   * @return 属性列表
   * @author caobaoyu
   * @date 2023/4/14 16:23
   */
  protected static <R, M extends Model<?>> List<R> getListProperties(List<M> list, GetCallback<M, R> getCallback) {
    List<R> keys = null;
    if (list!= null && list.size() > 0) {
      List<R> keyList = list.stream().map(getCallback::get).filter(Objects::nonNull).collect(Collectors.toList());
      if (keyList.size() > 0) {
        keys = keyList;
      }
    }
    return keys;
  }
  /**
   * @param p           分页模型
   * @param getCallback 获取属性回调
   * @param <R>         返回值类型
   * @param <M>         模型类型
   * @return 属性列表
   * @author caobaoyu
   * @date 2023/4/14 16:23
   */
  protected static <R, M extends Model<?>> List<R> getPageProperties(Page<M> p, GetCallback<M, R> getCallback) {
    List<R> keys = null;
    if(p!=null){
      keys = getListProperties(p.getItems(),getCallback);
    }
    return keys;
  }



  /**
   * 两个List一对一关联
   *
   * @param list1    list1
   * @param list2    list2
   * @param getCall1 获取list1的关联属性回调
   * @param getCall2 获取list2的关联属性回调
   * @param setCall  关联赋值回调
   * @param <P1>     list1关联属性的类型
   * @param <P2>     list2关联属性的类型
   * @param <M1>     list1元素类型
   * @param <M2>     list2元素类型
   * @author caobaoyu
   * @date 2023/4/14 16:42
   */
  protected static <P1, P2, M1 extends Model<?>, M2 extends Model<?>> void joinOne(List<M1> list1, List<M2> list2, GetCallback<M1, P1> getCall1, GetCallback<M2, P2> getCall2, SetCallback<M1, M2> setCall) {
    if (list1 != null && list2 != null) {
      Map<P2, M2> map2 = list2.stream().collect(Collectors.toMap(getCall2::get, t -> t));
      list1.forEach(e -> {
                P1 p = getCall1.get(e);
                M2 m2 = map2.get(p);
                if (m2 != null) {
                  setCall.set(e, m2);
                }
              }
      );
    }
  }

  /**
   * 两个List一对一关联
   *
   * @param page     page
   * @param list2    list2
   * @param getCall1 获取list1的关联属性回调
   * @param getCall2 获取list2的关联属性回调
   * @param setCall  关联赋值回调
   * @param <P1>     list1关联属性的类型
   * @param <P2>     list2关联属性的类型
   * @param <M1>     list1元素类型
   * @param <M2>     list2元素类型
   * @author caobaoyu
   * @date 2023/4/14 16:42
   */
  protected static <P1, P2, M1 extends Model<?>, M2 extends Model<?>> void joinOne(Page<M1> page, List<M2> list2, GetCallback<M1, P1> getCall1, GetCallback<M2, P2> getCall2, SetCallback<M1, M2> setCall) {
    if (page != null) {
      joinOne(page.getItems(), list2, getCall1, getCall2, setCall);
    }
  }

  /**
   * 两个ist一对多关联
   *
   * @param list1    list1
   * @param list2    list2
   * @param getCall1 获取list1的关联属性回调
   * @param getCall2 获取list2的关联属性回调
   * @param setCall  关联赋值回调
   * @param <P1>     list1关联属性的类型
   * @param <P2>     list2关联属性的类型
   * @param <M1>     list1元素类型
   * @param <M2>     list2元素类型
   * @author caobaoyu
   * @date 2023/4/14 17:00
   */
  protected static <P1, P2, M1 extends Model<?>, M2 extends Model<?>> void joinMany(List<M1> list1, List<M2> list2, GetCallback<M1, P1> getCall1, GetCallback<M2, P2> getCall2, SetCallback<M1, List<M2>> setCall) {
    if (list1 != null && list2 != null) {
      Map<P2, List<M2>> map2 = list2.stream().collect(Collectors.groupingBy(getCall2::get));
      list1.forEach(e -> {
                P1 p = getCall1.get(e);
                List<M2> m2List = map2.get(p);
                if (m2List != null) {
                  setCall.set(e, m2List);
                }
              }
      );
    }
  }


  /**
   * 两个ist一对多关联
   *
   * @param page    page
   * @param list2    list2
   * @param getCall1 获取list1的关联属性回调
   * @param getCall2 获取list2的关联属性回调
   * @param setCall  关联赋值回调
   * @param <P1>     list1关联属性的类型
   * @param <P2>     list2关联属性的类型
   * @param <M1>     list1元素类型
   * @param <M2>     list2元素类型
   * @author caobaoyu
   * @date 2023/4/14 17:00
   */
  protected static <P1, P2, M1 extends Model<?>, M2 extends Model<?>> void joinMany(Page<M1> page, List<M2> list2, GetCallback<M1, P1> getCall1, GetCallback<M2, P2> getCall2, SetCallback<M1, List<M2>> setCall) {
    if (page != null) {
      joinMany(page.getItems(), list2, getCall1, getCall2, setCall);
    }
  }

  /**
   * 执行查询详情附加执行器
   *
   * @param u         用户
   * @param clazz     附加执行器类型
   * @param k         模型主键
   * @param m         模型
   * @param executors 附加执行器列表
   * @throws Exception 异常
   * @author caobaoyu
   * @date 2023/4/14 17:12
   */
  @SuppressWarnings("unchecked")
  protected void queryDetailsExecute(AuthorizationUser<?, ?, ?, ?> u, Class<? extends QueryDetailsExecutor> clazz, K k, M m, QueryDetailsExecutor<K, M>... executors) throws Exception {
    if (executors != null) {
      for (QueryDetailsExecutor<K, M> executor : executors) {
        if (clazz.isInstance(executor)) {
          executor.execute(u, k, m);
        }
      }
    }
  }


  /**
   * 执行查询列表附加执行器
   *
   * @param u         用户
   * @param clazz     附加执行器类型
   * @param q         查询参数模型
   * @param p         分页模型
   * @param executors 附加执行器列表
   * @throws Exception 异常
   * @author caobaoyu
   * @date 2023/4/14 17:36
   */
  @SuppressWarnings("unchecked")
  protected void queryPageExecute(AuthorizationUser<?, ?, ?, ?> u, Class<? extends QueryPageExecutor> clazz, Query q, Page<M> p, QueryPageExecutor<K, M>... executors) throws Exception {
    if (executors != null) {
      for (QueryPageExecutor<K, M> executor : executors) {
        if (clazz.isInstance(executor)) {
          executor.execute(u, q, p);
        }
      }
    }
  }

  /**
   * 执行添加附加执行器
   *
   * @param u         用户
   * @param clazz     附加执行器类型
   * @param k         模型主键
   * @param m         模型
   * @param executors 附加执行器
   * @throws Exception 异常
   * @author caobaoyu
   * @date 2023/4/14 18:02
   */
  protected void addExecute(AuthorizationUser<?, ?, ?, ?> u, Class<? extends AddExecutor> clazz, K k, M m, AddExecutor<K, M>... executors) throws Exception {
    if (executors != null) {
      for (AddExecutor<K, M> executor : executors) {
        if (clazz.isInstance(executor)) {
          executor.execute(u, k, m);
        }
      }
    }
  }


  /**
   * 执行删除附加执行器
   *
   * @param u         用户
   * @param clazz     执行器类型
   * @param k         模型主键
   * @param executors 执行器列表
   * @throws Exception 异常
   * @author caobaoyu
   * @date 2022/10/02 14:07
   */
  @SuppressWarnings("unchecked")
  protected void removeExecute(AuthorizationUser<?, ?, ?, ?> u, Class<? extends RemoveExecutor> clazz, K k, RemoveExecutor<K>... executors) throws Exception {
    if (executors != null) {
      for (RemoveExecutor<K> executor : executors) {
        if (clazz.isInstance(executor)) {
          executor.execute(u, k);
        }
      }
    }
  }


  /**
   * 执行多删除执行执行器
   *
   * @param clazz     执行器类型
   * @param ks        模型主键列表
   * @param u         用户
   * @param executors 执行器列表
   * @throws Exception 异常
   * @author caobaoyu
   * @date 2022/10/02 14:08
   */
  @SuppressWarnings("unchecked")
  protected void removeMultiExecute(AuthorizationUser<?, ?, ?, ?> u, Class<? extends RemoveMultiExecutor> clazz, List<K> ks, RemoveMultiExecutor<K>... executors) throws Exception {
    if (executors != null) {
      for (RemoveMultiExecutor<K> executor : executors) {
        if (clazz.isInstance(executor)) {
          executor.execute(u, ks);
        }
      }
    }
  }

  /**
   * 执行部分修改执行执行器
   *
   * @param clazz     执行器类型
   * @param k         模型主键
   * @param u         用户
   * @param m         模型
   * @param executors 执行器列表
   * @throws Exception 异常
   * @author caobaoyu
   * @date 2022/10/02 14:08
   */
  @SuppressWarnings("unchecked")
  protected void editExecute(AuthorizationUser<?, ?, ?, ?> u, Class<? extends EditExecutor> clazz, K k, M m, EditExecutor<K, M>... executors) throws Exception {
    if (executors != null) {
      for (EditExecutor<K, M> executor : executors) {
        if (clazz.isInstance(executor)) {
          executor.execute(u, k, m);
        }
      }
    }
  }


  /**
   * 执行全部修改执行执行器
   *
   * @param clazz     执行器类型
   * @param k         模型主键
   * @param u         用户
   * @param m         模型
   * @param executors 执行器列表
   * @throws Exception 异常
   * @author caobaoyu
   * @date 2022/10/02 14:09
   */
  @SuppressWarnings("unchecked")
  protected void editAllExecute(AuthorizationUser<?, ?, ?, ?> u, Class<? extends EditAllExecutor> clazz, K k, M m, EditAllExecutor<K, M>... executors) throws Exception {
    if (executors != null) {
      for (EditAllExecutor<K, M> executor : executors) {
        if (clazz.isInstance(executor)) {
          executor.execute(k, u, m);
        }
      }
    }
  }

}
