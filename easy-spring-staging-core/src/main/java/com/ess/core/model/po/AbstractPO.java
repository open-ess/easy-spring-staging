package com.ess.core.model.po;


import com.ess.core.model.Model;

import java.lang.reflect.Field;
import java.util.Date;

/**
 * 抽象之久化模型类
 *
 * @param <K> 模型主键类型
 * @author caobaoyu
 * @date 2022/10/1 23:16
 */
public abstract class AbstractPO<K> implements Model<K> {

    // 创建时间字段名称
    public static final String CREATE_TIME_FIELD_NAME = "createTime";
    // 修改时间字段名称
    public static final String MODIFY_TIME_FIELD_NAME = "modifyTime";

    /**
     * 反射赋值模型属性值
     *
     * @param fieldName 属性字段名称
     * @param value     属性字段值
     * @throws NoSuchFieldException 异常
     * @throws IllegalAccessException 异常
     * @author caobaoyu
     * @date 2022/10/1 23:17
     */
    protected void assignment(String fieldName, Object value) throws NoSuchFieldException, IllegalAccessException {
        Field field = this.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(this, value);
    }

    /**
     * 设置创建时间默认值
     *
     * @author caobaoyu
     * @date 2022/10/1 23:17
     */
    public void setDefaultCreateTime() throws Exception {
        assignment(CREATE_TIME_FIELD_NAME, new Date());
    }

    /**
     * 设置修改时间默认值
     *
     * @author caobaoyu
     * @date 2022/10/1 23:17
     */
    public void setDefaultModifyTime() throws Exception {
        assignment(MODIFY_TIME_FIELD_NAME, new Date());

    }

    /**
     * 清除创建时间默认值
     *
     * @author caobaoyu
     * @date 2022/10/1 23:17
     */
    public void clearCreateTime() throws Exception {
        assignment(CREATE_TIME_FIELD_NAME, null);
    }

    /**
     * 清除修改时间默认值
     *
     * @author caobaoyu
     * @date 2022/10/1 23:17
     */
    public void clearModifyTime() throws Exception {
       assignment(MODIFY_TIME_FIELD_NAME, null);
    }
}
