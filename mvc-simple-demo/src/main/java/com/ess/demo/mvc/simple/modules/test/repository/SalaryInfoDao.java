package com.ess.demo.mvc.simple.modules.test.repository;

import com.ess.core.repository.rdb.simple.RdbDao;
import com.ess.demo.mvc.simple.modules.test.model.SalaryInfo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SalaryInfoDao extends RdbDao<String, SalaryInfo> {
}


