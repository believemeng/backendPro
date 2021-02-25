package com.dspread.august.util;

/**
 * Created by dsppc11 on 2019/2/25.
 */
//  deepcode ignore FormalParameterNamingConventions: <comment the reason here>
public class TMKKey extends Poskeys{
    public String getTMKKEY() {
        return TMKKEY;
    }

    public void setTMKKEY(String TMKKEY) {
        this.TMKKEY = TMKKEY;
    }

    private String TMKKEY = "0123456789ABCDEFFEDCBA9876543210";
}
