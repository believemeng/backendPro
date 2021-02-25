package com.dspread.august.exception;

import com.dspread.august.common.ErrorCode;
import org.springframework.security.core.AuthenticationException;

public class CustomerException extends AuthenticationException {
    public ErrorCode getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }

    private ErrorCode errorCode;



    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    private String errorMsg;
    public CustomerException(ErrorCode errorCode, String errorMsg) {
        super(errorMsg);
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }


}
