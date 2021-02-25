package com.dspread.august.model;

public class SafetyNetModel {
    private String nonce;

    private String jws;

    public String getNonce() {
        return nonce;
    }

    public void setNonce(String nonce) {
        this.nonce = nonce;
    }

    public String getJws() {
        return jws;
    }

    public void setJws(String jws) {
        this.jws = jws;
    }

    @Override
    public String toString() {
        return "SafetyNetEntity{" +
                "nonce='" + nonce + '\'' +
                ", jws='" + jws + '\'' +
                '}';
    }
}
