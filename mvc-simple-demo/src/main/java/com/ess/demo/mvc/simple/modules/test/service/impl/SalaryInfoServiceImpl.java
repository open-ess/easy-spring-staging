package com.ess.demo.mvc.simple.modules.test.service.impl;

import com.ess.core.pattern.simple.service.AbstractSimpleService;
import com.ess.core.repository.BaseDao;
import com.ess.core.sercurity.AuthorizationUser;
import com.ess.demo.mvc.simple.modules.test.model.SalaryInfo;
import com.ess.demo.mvc.simple.modules.test.repository.SalaryInfoDao;
import com.ess.demo.mvc.simple.modules.test.service.SalaryInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
@Slf4j
public class SalaryInfoServiceImpl extends AbstractSimpleService<BaseDao<String, SalaryInfo>, String, SalaryInfo> implements SalaryInfoService {

  @Resource
  private SalaryInfoDao salaryInfoDao;


  @Override
  public BaseDao<String, SalaryInfo> getDao() {
    return this.salaryInfoDao;
  }
}


