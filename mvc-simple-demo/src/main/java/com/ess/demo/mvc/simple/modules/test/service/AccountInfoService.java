package com.ess.demo.mvc.simple.modules.test.service;

import com.ess.core.pattern.simple.service.SimpleService;
import com.ess.core.sercurity.AuthorizationUser;
import com.ess.demo.mvc.simple.modules.test.model.AccountInfo;

public interface AccountInfoService extends SimpleService<String, AccountInfo> {
  String id = "id";
  String accountName = "account_name";
  String gender = "gender";
  String createTime = "create_time";
}


