package com.ess.demo.mvc.simple.modules.test.model;

import com.ess.core.model.Model;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.util.Date;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = false)
//@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(value = "SalaryInfo", description = "薪水-模型类")
public class SalaryInfo implements Model<String> {

  // 薪水ID
  @ApiModelProperty(value = "薪水ID")
  private String id;

  // 账号ID
  @ApiModelProperty(value = "账号ID")
  private String accountId;

  // 年份
  @ApiModelProperty(value = "年份")
  private Integer yearNumber;

  // 月份
  @ApiModelProperty(value = "月份")
  private Integer monthNumber;

  // 薪水（单位：元）
  @ApiModelProperty(value = "薪水（单位：元）")
  private Float salaryAmount;

  @Override
  public String getKey() {
    return this.id;
  }

  @Override
  public void setKey(String id) {
    this.id = id;
  }
}

