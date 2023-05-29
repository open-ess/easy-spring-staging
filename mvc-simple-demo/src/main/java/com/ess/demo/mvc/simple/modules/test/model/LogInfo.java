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
@ApiModel(value = "LogInfo", description = "日志信息-模型类")
public class LogInfo implements Model<String> {

  // 日志ID
  @ApiModelProperty(value = "日志ID")
  private String id;

  // 日志内容
  @ApiModelProperty(value = "日志内容")
  private String content;

  @Override
  public String getKey() {
    return this.id;
  }

  @Override
  public void setKey(String id) {
    this.id = id;
  }
}

