package com.ess.demo.mvc.simple.modules.test.service;

import com.ess.core.pattern.mvc.simple.service.BaseService;
import com.ess.core.sercurity.AuthorizationUser;
import com.ess.demo.mvc.simple.modules.test.model.SalaryInfo;

public interface SalaryInfoService extends BaseService<String, SalaryInfo> {
  String id = "id";
  String accountId = "account_id";
  String yearNumber = "year_number";
  String monthNumber = "month_number";
  String salaryAmount = "salary_amount";
}


