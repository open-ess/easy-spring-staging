package com.ess.demo.mvc.simple.modules.test.service.impl;

import com.ess.core.pattern.mvc.simple.service.AbstractService;
import com.ess.core.pattern.mvc.simple.dao.BaseDao;
import com.ess.core.sercurity.AuthorizationUser;
import com.ess.demo.mvc.simple.modules.test.model.AccountInfo;
import com.ess.demo.mvc.simple.modules.test.dao.AccountInfoDao;
import com.ess.demo.mvc.simple.modules.test.service.AccountInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
@Slf4j
public class AccountInfoServiceImpl extends AbstractService<String, AccountInfo> implements AccountInfoService {

  @Resource
  private AccountInfoDao accountInfoDao;


  @Override
  public BaseDao<String, AccountInfo> getDao() {
    return this.accountInfoDao;
  }
}


