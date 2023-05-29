package com.ess.demo.mvc.simple.modules.test.service;

import com.ess.core.pattern.simple.service.SimpleService;
import com.ess.core.sercurity.AuthorizationUser;
import com.ess.demo.mvc.simple.modules.test.model.LogInfo;

public interface LogInfoService extends SimpleService<String, LogInfo> {
  String id = "id";
  String content = "content";
}


