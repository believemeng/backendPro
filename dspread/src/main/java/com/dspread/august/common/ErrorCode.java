package com.dspread.august.common;

public enum ErrorCode{
        ERROR_NO_USER(1001),
        ERROR_PASSWORD_ERROR(1002),
    ERROR_GENERATE_ENVELOP(1003),
    ERROR_USER_HASE_REGISTED(1004),
    ERROR_Attestation_COTS(1005),
    ERROR_GENERATE_RANDOM(1006),
    ERROR_RANDOM_VERIFY(1007),
    ERROR_VERIFY_SCRP(1008);



    public int getErrorCode() {
            return errorCode;
        }

        private int errorCode;
        ErrorCode(int i) {
            errorCode = i;
        }
    }