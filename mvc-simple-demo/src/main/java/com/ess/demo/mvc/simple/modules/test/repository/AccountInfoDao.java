package com.ess.demo.mvc.simple.modules.test.repository;

import com.ess.core.repository.rdb.simple.RdbDao;
import com.ess.demo.mvc.simple.modules.test.model.AccountInfo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AccountInfoDao extends RdbDao<String, AccountInfo> {
}


