package com.ess.demo.mvc.simple.modules.test.repository;

import com.ess.core.repository.rdb.simple.RdbDao;
import com.ess.demo.mvc.simple.modules.test.model.LogInfo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface LogInfoDao extends RdbDao<String, LogInfo> {
}


