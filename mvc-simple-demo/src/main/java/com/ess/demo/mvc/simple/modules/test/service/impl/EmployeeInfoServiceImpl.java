package com.ess.demo.mvc.simple.modules.test.service.impl;

import com.ess.core.pattern.simple.service.AbstractSimpleService;
import com.ess.core.repository.BaseDao;
import com.ess.core.sercurity.AuthorizationUser;
import com.ess.demo.mvc.simple.modules.test.model.EmployeeInfo;
import com.ess.demo.mvc.simple.modules.test.repository.EmployeeInfoDao;
import com.ess.demo.mvc.simple.modules.test.service.EmployeeInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
@Slf4j
public class EmployeeInfoServiceImpl extends AbstractSimpleService<BaseDao<String, EmployeeInfo>, String, EmployeeInfo> implements EmployeeInfoService {

  @Resource
  private EmployeeInfoDao employeeInfoDao;


  @Override
  public BaseDao<String, EmployeeInfo> getDao() {
    return this.employeeInfoDao;
  }
}


