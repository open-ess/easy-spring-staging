package com.ess.demo.mvc.simple.modules.test.service.impl;

import com.ess.core.pattern.simple.service.AbstractSimpleService;
import com.ess.core.repository.BaseDao;
import com.ess.core.sercurity.AuthorizationUser;
import com.ess.demo.mvc.simple.modules.test.model.AccountInfo;
import com.ess.demo.mvc.simple.modules.test.repository.AccountInfoDao;
import com.ess.demo.mvc.simple.modules.test.service.AccountInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
@Slf4j
public class AccountInfoServiceImpl extends AbstractSimpleService<BaseDao<String, AccountInfo>, String, AccountInfo> implements AccountInfoService {

  @Resource
  private AccountInfoDao accountInfoDao;


  @Override
  public BaseDao<String, AccountInfo> getDao() {
    return this.accountInfoDao;
  }
}


