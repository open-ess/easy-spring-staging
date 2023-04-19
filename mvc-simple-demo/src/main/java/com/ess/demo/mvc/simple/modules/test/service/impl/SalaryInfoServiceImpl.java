package com.ess.demo.mvc.simple.modules.test.service.impl;

import com.ess.core.pattern.mvc.simple.service.AbstractService;
import com.ess.core.pattern.mvc.simple.dao.BaseDao;
import com.ess.core.sercurity.AuthorizationUser;
import com.ess.demo.mvc.simple.modules.test.model.SalaryInfo;
import com.ess.demo.mvc.simple.modules.test.dao.SalaryInfoDao;
import com.ess.demo.mvc.simple.modules.test.service.SalaryInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
@Slf4j
public class SalaryInfoServiceImpl extends AbstractService<String, SalaryInfo> implements SalaryInfoService {

  @Resource
  private SalaryInfoDao salaryInfoDao;


  @Override
  public BaseDao<String, SalaryInfo> getDao() {
    return this.salaryInfoDao;
  }
}


