package com.ess.demo.mvc.simple.modules.test.service.impl;

import com.ess.core.pattern.simple.service.AbstractSimpleService;
import com.ess.core.repository.BaseDao;
import com.ess.core.sercurity.AuthorizationUser;
import com.ess.demo.mvc.simple.modules.test.model.LogInfo;
import com.ess.demo.mvc.simple.modules.test.repository.LogInfoDao;
import com.ess.demo.mvc.simple.modules.test.service.LogInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
@Slf4j
public class LogInfoServiceImpl extends AbstractSimpleService<BaseDao<String, LogInfo>, String, LogInfo> implements LogInfoService {

  @Resource
  private LogInfoDao logInfoDao;


  @Override
  public BaseDao<String, LogInfo> getDao() {
    return this.logInfoDao;
  }
}


