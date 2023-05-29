package com.ess.demo.mvc.simple.modules.test.repository;

import com.ess.core.repository.rdb.simple.RdbDao;
import com.ess.demo.mvc.simple.modules.test.model.EmployeeInfo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface EmployeeInfoDao extends RdbDao<String, EmployeeInfo> {
}


