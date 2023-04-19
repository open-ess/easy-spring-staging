package com.ess.demo.mvc.simple.modules.test.service.impl;

import com.ess.core.executor.query.QueryPagePostExecutor;
import com.ess.core.pattern.mvc.simple.service.AbstractService;
import com.ess.core.pattern.mvc.simple.dao.BaseDao;
import com.ess.core.sercurity.AuthorizationUser;
import com.ess.demo.mvc.simple.modules.test.dao.AccountInfoDao;
import com.ess.demo.mvc.simple.modules.test.dao.SalaryInfoDao;
import com.ess.demo.mvc.simple.modules.test.model.AccountInfo;
import com.ess.demo.mvc.simple.modules.test.model.EmployeeInfo;
import com.ess.demo.mvc.simple.modules.test.dao.EmployeeInfoDao;
import com.ess.demo.mvc.simple.modules.test.model.SalaryInfo;
import com.ess.demo.mvc.simple.modules.test.service.AccountInfoService;
import com.ess.demo.mvc.simple.modules.test.service.EmployeeInfoService;
import com.ess.demo.mvc.simple.modules.test.service.SalaryInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.List;

@Service
@Slf4j
public class EmployeeInfoServiceImpl extends AbstractService<String, EmployeeInfo> implements EmployeeInfoService {

  @Resource
  private EmployeeInfoDao employeeInfoDao;


  @Resource
  private AccountInfoDao accountInfoDao;


  @Resource
  private SalaryInfoDao salaryInfoDao;

  private QueryPagePostExecutor<String,EmployeeInfo> queryPageExecutor;

  public QueryPagePostExecutor<String,EmployeeInfo> getQueryPageExecutor(){
    return queryPageExecutor;
  }


  @PostConstruct
  private void builderQueryPageExecutor(){
    this.queryPageExecutor = (u, q, p) -> {
      List<String> accountIds = getModelPropertyList(p, EmployeeInfo::getAccountId);
      if (accountIds != null) {
        List<AccountInfo> accountInfos = accountInfoDao.list(u, AccountInfoService.id, accountIds);
        joinOne(p.getItems(),accountInfos, EmployeeInfo::getAccountId, AccountInfo::getId, EmployeeInfo::setAccountInfo);
        List<SalaryInfo> salaryInfos = salaryInfoDao.list(u, SalaryInfoService.accountId, accountIds);
        joinMany(p.getItems(),salaryInfos, EmployeeInfo::getAccountId, SalaryInfo::getAccountId, EmployeeInfo::setSalaryInfos);
      }
    };
  }
  @Override
  public BaseDao<String, EmployeeInfo> getDao() {
    return this.employeeInfoDao;
  }
}


