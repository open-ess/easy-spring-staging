package com.ess.core.model;

/**
 * 响应状态枚举类
 */

public enum RestStatus {

    OK(200,"OK"),
    BAD_REQUEST(400, "Bad Request"),
    UNAUTHORIZED(401, "Unauthorized"),
    FORBIDDEN(403, "Forbidden"),
    NOT_FOUND(404, "Not Found"),
    INTERNAL_SERVER_ERROR(500, "Internal Server Error")
    ;
    // 状态码
    private final Integer code;
    // 描述信息
    private final String message;
    RestStatus(Integer code, String message){
        this.code = code;
        this.message = message;
    }
    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
