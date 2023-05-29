package com.ess.demo.mvc.simple.modules.test.service.impl;

import com.ess.core.executor.add.AddExecutor;
import com.ess.core.executor.add.AddPostExecutor;
import com.ess.core.executor.query.QueryPageExecutor;
import com.ess.core.executor.query.QueryPagePostExecutor;
import com.ess.core.model.Page;
import com.ess.core.model.Query;
import com.ess.core.pattern.simple.service.AbstractSimpleService;
import com.ess.core.repository.BaseDao;
import com.ess.core.sercurity.AuthorizationUser;
import com.ess.core.utils.JsonUtil;
import com.ess.demo.mvc.simple.modules.test.model.AccountInfo;
import com.ess.demo.mvc.simple.modules.test.model.EmployeeInfo;
import com.ess.demo.mvc.simple.modules.test.model.LogInfo;
import com.ess.demo.mvc.simple.modules.test.model.SalaryInfo;
import com.ess.demo.mvc.simple.modules.test.repository.AccountInfoDao;
import com.ess.demo.mvc.simple.modules.test.repository.EmployeeInfoDao;
import com.ess.demo.mvc.simple.modules.test.repository.LogInfoDao;
import com.ess.demo.mvc.simple.modules.test.repository.SalaryInfoDao;
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
public class EmployeeInfoServiceImpl extends AbstractSimpleService<BaseDao<String, EmployeeInfo>, String, EmployeeInfo> implements EmployeeInfoService {

  @Resource
  private EmployeeInfoDao employeeInfoDao;

  @Resource
  private AccountInfoDao accountInfoDao;

  @Resource
  private SalaryInfoDao salaryInfoDao;

  @Resource
  private LogInfoDao logInfoDao;

  private QueryPageExecutor<String, EmployeeInfo> accountInfoDetailsQueryPageExecutor;

  private QueryPageExecutor<String, EmployeeInfo> salaryInfoListQueryPageExecutor;

  private QueryPageExecutor<String, EmployeeInfo> logInfoAddQueryPageExecutor;

  private AddExecutor<String, EmployeeInfo> logAddAddExecutor;

  public QueryPageExecutor<String, EmployeeInfo> getAccountInfoDetailsQueryPageExecutor() {
    return accountInfoDetailsQueryPageExecutor;
  }

  public QueryPageExecutor<String, EmployeeInfo> getSalaryInfoListQueryPageExecutor() {
    return salaryInfoListQueryPageExecutor;
  }

  public QueryPageExecutor<String, EmployeeInfo> getLogInfoAddQueryPageExecutor() {
    return logInfoAddQueryPageExecutor;
  }

  public AddExecutor<String, EmployeeInfo> getLogAddAddExecutor() {
    return logAddAddExecutor;
  }

  @PostConstruct
  public void createAccountInfoDetailsQueryPageExecutor(){
    this.accountInfoDetailsQueryPageExecutor = new QueryPagePostExecutor<String, EmployeeInfo>(){
      @Override
      public void execute(AuthorizationUser<?, ?, ?, ?> u, Query q, Page<EmployeeInfo> p) throws Exception {
        List<String> accountIds = getPageProperties(p, EmployeeInfo::getAccountId);
        List<AccountInfo> accountInfos = accountInfoDao.list(u, AccountInfoService.id, accountIds);
        joinOne(p,accountInfos,EmployeeInfo::getAccountId,AccountInfo::getId,EmployeeInfo::setAccountInfo);
      }
    };
  }

  @PostConstruct
  public void createSalaryInfoListQueryPageExecutor(){
    this.salaryInfoListQueryPageExecutor = new QueryPagePostExecutor<String, EmployeeInfo>(){
      @Override
      public void execute(AuthorizationUser<?, ?, ?, ?> u, Query q, Page<EmployeeInfo> p) throws Exception {
        List<String> accountIds = getPageProperties(p, EmployeeInfo::getAccountId);
        List<SalaryInfo> salaryInfos = salaryInfoDao.list(u, SalaryInfoService.accountId,accountIds);
        joinMany(p,salaryInfos,EmployeeInfo::getAccountId,SalaryInfo::getAccountId,EmployeeInfo::setSalaryInfos);
      }
    };
  }

  @PostConstruct
  public void createLogInfoAddQueryPageExecutor(){
    this.logInfoAddQueryPageExecutor = new QueryPagePostExecutor<String, EmployeeInfo>(){
      @Override
      public void execute(AuthorizationUser<?, ?, ?, ?> u, Query q, Page<EmployeeInfo> p) throws Exception {
        LogInfo logInfo = LogInfo.builder()
                .content(JsonUtil.toJSONString(q))
                .build();
        logInfoDao.insert(logInfo);
      }
    };
  }

  @PostConstruct
  public void createLogAddAddExecutor(){
    this.logAddAddExecutor = new AddPostExecutor<String, EmployeeInfo>(){
      @Override
      public void execute(AuthorizationUser<?, ?, ?, ?> u, String s, EmployeeInfo employeeInfo) throws Exception {
        LogInfo logInfo = LogInfo.builder()
                .content(JsonUtil.toJSONString(employeeInfo))
                .build();
        logInfoDao.insert(logInfo);
      }
    };
  }

  @Override
  public BaseDao<String, EmployeeInfo> getDao() {
    return this.employeeInfoDao;
  }
}


