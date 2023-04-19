package com.ess.demo.mvc.simple.modules.test.service;

import com.ess.core.executor.query.QueryPagePostExecutor;
import com.ess.core.pattern.mvc.simple.service.BaseService;
import com.ess.core.sercurity.AuthorizationUser;
import com.ess.demo.mvc.simple.modules.test.model.EmployeeInfo;

public interface EmployeeInfoService extends BaseService<String, EmployeeInfo> {
  String id = "id";
  String accountId = "account_id";
  String employeeName = "employee_name";
  String entryTime = "entry_time";
  QueryPagePostExecutor<String,EmployeeInfo> getQueryPageExecutor();
}


