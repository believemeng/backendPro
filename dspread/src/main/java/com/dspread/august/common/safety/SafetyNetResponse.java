package com.dspread.august.common.safety;


import com.alibaba.fastjson.JSON;
import com.dspread.august.security.merchant.base.JwtUserModel;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.Base64;

/**
 * SafetyNet API payload Response (once unencoded from JSON Web token)
 * <p>
 * {
 * "nonce": "iBnt4sI4KCA5Vqh7yDxzUVJYxBYUIQG396Wgmu6lA/Y=",
 * "timestampMs": 1432658018093,
 * "apkPackageName": "com.scottyab.safetynet.sample",
 * "apkDigestSha256": "WN2ADq4LZvMsd0CFBIkGRl8bn3mRKIppCmnqsrJzUJg=",
 * "ctsProfileMatch": false,
 * "basicIntegrity": false,
 * "extension": "CY+oATrcJ6Cr",
 * "apkCertificateDigestSha256": ["Yao6w7Yy7/ab2bNEygMbXqN9+16j8mLKKTCsUcU3Mzw="]
 * "advice": "LOCK_BOOTLOADER,RESTORE_TO_FACTORY_ROM"
 * }
 * <p>
 */
public class SafetyNetResponse {

    private static final String TAG = SafetyNetResponse.class.getSimpleName();
    private String nonce;
    private long timestampMs;
    private String apkPackageName;
    private String[] apkCertificateDigestSha256;
    private String apkDigestSha256;
    private boolean ctsProfileMatch;
    private boolean basicIntegrity;
    private String advice;

    //forces the parse()
    private SafetyNetResponse() {
    }

    /**
     * @return BASE64 encoded
     */
    public String getNonce() {
        return nonce;
    }

    public long getTimestampMs() {
        return timestampMs;
    }

    /**
     * @return com.package.name.of.requesting.app
     */
    public String getApkPackageName() {
        return apkPackageName;
    }

    /**
     * SHA-256 hash of the certificate used to sign requesting app
     *
     * @return BASE64 encoded
     */
    public String[] getApkCertificateDigestSha256() {
        return apkCertificateDigestSha256;
    }

    /**
     * SHA-256 hash of the app's APK
     * <p>
     * Google Play since March 2018 adds a small amount of metadata to all apps which makes this apk validation less useful.
     *
     * @return BASE64 encoded
     */
    @Deprecated
    public String getApkDigestSha256() {
        return apkDigestSha256;
    }


    /**
     * If the value of "ctsProfileMatch" is true, then the profile of the device running your app matches the profile of a device that has passed Android compatibility testing.
     *
     * @return
     */
    public boolean isCtsProfileMatch() {
        return ctsProfileMatch;
    }

    /**
     * If the value of "basicIntegrity" is true, then the device running your app likely wasn't tampered with, but the device has not necessarily passed Android compatibility testing.
     *
     * @return
     */
    public boolean isBasicIntegrity() {
        return basicIntegrity;
    }

    /**
     * Advice for passing future checks
     *
     * @return
     */
    public String getAdvice() {
        return advice;
    }

    /**
     * Parse the JSON string into populated SafetyNetResponse object
     *
     * @param decodedJWTPayload JSON String (always a json string according to JWT spec)
     * @return populated SafetyNetResponse
     */
    @Nullable
    public static SafetyNetResponse parse(String decodedJWTPayload) {

        System.out.println("decodedJWTPayload json:" + decodedJWTPayload);

        SafetyNetResponse response = new SafetyNetResponse();

        SafetyNetResponse root = JSON.parseObject(decodedJWTPayload, SafetyNetResponse.class);
        return response;
    }


    @Override
    public String toString() {
        return "SafetyNetResponse{" +
                "nonce='" + nonce + '\'' +
                ", timestampMs=" + timestampMs +
                ", apkPackageName='" + apkPackageName + '\'' +
                ", apkCertificateDigestSha256=" + Arrays.toString(apkCertificateDigestSha256) +
                ", apkDigestSha256='" + apkDigestSha256 + '\'' +
                ", ctsProfileMatch=" + ctsProfileMatch +
                ", basicIntegrity=" + basicIntegrity +
                ", advice=" + advice +
                '}';
    }

    public static void main(String args[]){
        String s = "eyJhbGciOiJSUzI1NiIsIng1YyI6WyJNSUlGa3pDQ0JIdWdBd0lCQWdJUkFOY1NramRzNW42K0NBQUFBQUFwYTBjd0RRWUpLb1pJaHZjTkFRRUxCUUF3UWpFTE1Ba0dBMVVFQmhNQ1ZWTXhIakFjQmdOVkJBb1RGVWR2YjJkc1pTQlVjblZ6ZENCVFpYSjJhV05sY3pFVE1CRUdBMVVFQXhNS1IxUlRJRU5CSURGUE1UQWVGdzB5TURBeE1UTXhNVFF4TkRsYUZ3MHlNVEF4TVRFeE1UUXhORGxhTUd3eEN6QUpCZ05WQkFZVEFsVlRNUk13RVFZRFZRUUlFd3BEWVd4cFptOXlibWxoTVJZd0ZBWURWUVFIRXcxTmIzVnVkR0ZwYmlCV2FXVjNNUk13RVFZRFZRUUtFd3BIYjI5bmJHVWdURXhETVJzd0dRWURWUVFERXhKaGRIUmxjM1F1WVc1a2NtOXBaQzVqYjIwd2dnRWlNQTBHQ1NxR1NJYjNEUUVCQVFVQUE0SUJEd0F3Z2dFS0FvSUJBUUNXRXJCUVRHWkdOMWlaYk45ZWhSZ2lmV0J4cWkyUGRneHcwM1A3VHlKWmZNeGpwNUw3ajFHTmVQSzVIemRyVW9JZDF5Q0l5Qk15eHFnYXpxZ3RwWDVXcHNYVzRWZk1oSmJOMVkwOXF6cXA2SkQrMlBaZG9UVTFrRlJBTVdmTC9VdVp0azdwbVJYZ0dtNWpLRHJaOU54ZTA0dk1ZUXI4OE5xd1cva2ZaMWdUT05JVVQwV3NMVC80NTIyQlJXeGZ3eGMzUUUxK1RLV2tMQ3J2ZWs2V2xJcXlhQzUyVzdNRFI4TXBGZWJ5bVNLVHZ3Zk1Sd3lLUUxUMDNVTDR2dDQ4eUVjOHNwN3dUQUhNL1dEZzhRb3RhcmY4T0JIa25vWjkyWGl2aWFWNnRRcWhST0hDZmdtbkNYaXhmVzB3RVhDdnFpTFRiUXRVYkxzUy84SVJ0ZFhrcFFCOUFnTUJBQUdqZ2dKWU1JSUNWREFPQmdOVkhROEJBZjhFQkFNQ0JhQXdFd1lEVlIwbEJBd3dDZ1lJS3dZQkJRVUhBd0V3REFZRFZSMFRBUUgvQkFJd0FEQWRCZ05WSFE0RUZnUVU2REhCd3NBdmI1M2cvQzA3cHJUdnZ3TlFRTFl3SHdZRFZSMGpCQmd3Rm9BVW1OSDRiaERyejV2c1lKOFlrQnVnNjMwSi9Tc3daQVlJS3dZQkJRVUhBUUVFV0RCV01DY0dDQ3NHQVFVRkJ6QUJoaHRvZEhSd09pOHZiMk56Y0M1d2Eya3VaMjl2Wnk5bmRITXhiekV3S3dZSUt3WUJCUVVITUFLR0gyaDBkSEE2THk5d2Eya3VaMjl2Wnk5bmMzSXlMMGRVVXpGUE1TNWpjblF3SFFZRFZSMFJCQll3RklJU1lYUjBaWE4wTG1GdVpISnZhV1F1WTI5dE1DRUdBMVVkSUFRYU1CZ3dDQVlHWjRFTUFRSUNNQXdHQ2lzR0FRUUIxbmtDQlFNd0x3WURWUjBmQkNnd0pqQWtvQ0tnSUlZZWFIUjBjRG92TDJOeWJDNXdhMmt1WjI5dlp5OUhWRk14VHpFdVkzSnNNSUlCQkFZS0t3WUJCQUhXZVFJRUFnU0I5UVNCOGdEd0FIY0E5bHlVTDlGM01DSVVWQmdJTUpSV2p1Tk5FeGt6djk4TUx5QUx6RTd4Wk9NQUFBRnZudXkwWndBQUJBTUFTREJHQWlFQTdlLzBZUnUzd0FGbVdIMjdNMnZiVmNaL21ycCs0cmZZYy81SVBKMjlGNmdDSVFDbktDQ0FhY1ZOZVlaOENDZllkR3BCMkdzSHh1TU9Ia2EvTzQxaldlRit6Z0IxQUVTVVpTNnc3czZ2eEVBSDJLaitLTURhNW9LKzJNc3h0VC9UTTVhMXRvR29BQUFCYjU3c3RKTUFBQVFEQUVZd1JBSWdFWGJpb1BiSnA5cUMwRGoyNThERkdTUk1BVStaQjFFaVZFYmJiLzRVdk5FQ0lCaEhrQnQxOHZSbjl6RHZ5cmZ4eXVkY0hUT1NsM2dUYVlBLzd5VC9CaUg0TUEwR0NTcUdTSWIzRFFFQkN3VUFBNElCQVFESUFjUUJsbWQ4TUVnTGRycnJNYkJUQ3ZwTVhzdDUrd3gyRGxmYWpKTkpVUDRqWUZqWVVROUIzWDRFMnpmNDluWDNBeXVaRnhBcU9SbmJqLzVqa1k3YThxTUowajE5ekZPQitxZXJ4ZWMwbmhtOGdZbExiUW02c0tZN1AwZXhmcjdIdUszTWtQMXBlYzE0d0ZFVWFHcUR3VWJHZ2wvb2l6MzhGWENFK0NXOEUxUUFFVWZ2YlFQVFliS3hZait0Q05sc3MwYlRTb0wyWjJkL2ozQnBMM01GdzB5eFNLL1VUcXlrTHIyQS9NZGhKUW14aStHK01LUlNzUXI2MkFuWmF1OXE2WUZvaSs5QUVIK0E0OFh0SXlzaEx5Q1RVM0h0K2FLb2hHbnhBNXVsMVhSbXFwOEh2Y0F0MzlQOTVGWkdGSmUwdXZseWpPd0F6WHVNdTdNK1BXUmMiLCJNSUlFU2pDQ0F6S2dBd0lCQWdJTkFlTzBtcUdOaXFtQkpXbFF1REFOQmdrcWhraUc5dzBCQVFzRkFEQk1NU0F3SGdZRFZRUUxFeGRIYkc5aVlXeFRhV2R1SUZKdmIzUWdRMEVnTFNCU01qRVRNQkVHQTFVRUNoTUtSMnh2WW1Gc1UybG5iakVUTUJFR0ExVUVBeE1LUjJ4dlltRnNVMmxuYmpBZUZ3MHhOekEyTVRVd01EQXdOREphRncweU1URXlNVFV3TURBd05ESmFNRUl4Q3pBSkJnTlZCQVlUQWxWVE1SNHdIQVlEVlFRS0V4VkhiMjluYkdVZ1ZISjFjM1FnVTJWeWRtbGpaWE14RXpBUkJnTlZCQU1UQ2tkVVV5QkRRU0F4VHpFd2dnRWlNQTBHQ1NxR1NJYjNEUUVCQVFVQUE0SUJEd0F3Z2dFS0FvSUJBUURRR005RjFJdk4wNXprUU85K3ROMXBJUnZKenp5T1RIVzVEekVaaEQyZVBDbnZVQTBRazI4RmdJQ2ZLcUM5RWtzQzRUMmZXQllrL2pDZkMzUjNWWk1kUy9kTjRaS0NFUFpSckF6RHNpS1VEelJybUJCSjV3dWRnem5kSU1ZY0xlL1JHR0ZsNXlPRElLZ2pFdi9TSkgvVUwrZEVhbHROMTFCbXNLK2VRbU1GKytBY3hHTmhyNTlxTS85aWw3MUkyZE44RkdmY2Rkd3VhZWo0YlhocDBMY1FCYmp4TWNJN0pQMGFNM1Q0SStEc2F4bUtGc2JqemFUTkM5dXpwRmxnT0lnN3JSMjV4b3luVXh2OHZObWtxN3pkUEdIWGt4V1k3b0c5aitKa1J5QkFCazdYckpmb3VjQlpFcUZKSlNQazdYQTBMS1cwWTN6NW96MkQwYzF0Skt3SEFnTUJBQUdqZ2dFek1JSUJMekFPQmdOVkhROEJBZjhFQkFNQ0FZWXdIUVlEVlIwbEJCWXdGQVlJS3dZQkJRVUhBd0VHQ0NzR0FRVUZCd01DTUJJR0ExVWRFd0VCL3dRSU1BWUJBZjhDQVFBd0hRWURWUjBPQkJZRUZKalIrRzRRNjgrYjdHQ2ZHSkFib090OUNmMHJNQjhHQTFVZEl3UVlNQmFBRkp2aUIxZG5IQjdBYWdiZVdiU2FMZC9jR1lZdU1EVUdDQ3NHQVFVRkJ3RUJCQ2t3SnpBbEJnZ3JCZ0VGQlFjd0FZWVphSFIwY0RvdkwyOWpjM0F1Y0d0cExtZHZiMmN2WjNOeU1qQXlCZ05WSFI4RUt6QXBNQ2VnSmFBamhpRm9kSFJ3T2k4dlkzSnNMbkJyYVM1bmIyOW5MMmR6Y2pJdlozTnlNaTVqY213d1B3WURWUjBnQkRnd05qQTBCZ1puZ1F3QkFnSXdLakFvQmdnckJnRUZCUWNDQVJZY2FIUjBjSE02THk5d2Eya3VaMjl2Wnk5eVpYQnZjMmwwYjNKNUx6QU5CZ2txaGtpRzl3MEJBUXNGQUFPQ0FRRUFHb0ErTm5uNzh5NnBSamQ5WGxRV05hN0hUZ2laL3IzUk5Ha21VbVlIUFFxNlNjdGk5UEVhanZ3UlQyaVdUSFFyMDJmZXNxT3FCWTJFVFV3Z1pRK2xsdG9ORnZoc085dHZCQ09JYXpwc3dXQzlhSjl4anU0dFdEUUg4TlZVNllaWi9YdGVEU0dVOVl6SnFQalk4cTNNRHhyem1xZXBCQ2Y1bzhtdy93SjRhMkc2eHpVcjZGYjZUOE1jRE8yMlBMUkw2dTNNNFR6czNBMk0xajZieWtKWWk4d1dJUmRBdktMV1p1L2F4QlZielltcW13a201ekxTRFc1bklBSmJFTENRQ1p3TUg1NnQyRHZxb2Z4czZCQmNDRklaVVNweHU2eDZ0ZDBWN1N2SkNDb3NpclNtSWF0ai85ZFNTVkRRaWJldDhxLzdVSzR2NFpVTjgwYXRuWnoxeWc9PSJdfQ.eyJub25jZSI6IldDU2FRSHpjcFRFRmZVK05vam1NT3k2OFhTaTREUkd5cTVtdS9KOUc1Uk09IiwidGltZXN0YW1wTXMiOjE2MDI2NjA5NzQxNDksImFwa1BhY2thZ2VOYW1lIjoiY29tLnNjb3R0eWFiLnNhZmV0eW5ldC5zYW1wbGUuZGVidWciLCJhcGtEaWdlc3RTaGEyNTYiOiJsRTVhQ3VLTlo0T202NU9jNkJmVVIwYUFKeXVqcFdjcVBpTWhmVFB3OVZFPSIsImN0c1Byb2ZpbGVNYXRjaCI6dHJ1ZSwiYXBrQ2VydGlmaWNhdGVEaWdlc3RTaGEyNTYiOlsiWkVmWFMzUmlLVkE1Q25ZMzIxb2o4R1I1VkFlVjZHOHY3Z2ZOQnJmL2V0WT0iXSwiYmFzaWNJbnRlZ3JpdHkiOnRydWUsImV2YWx1YXRpb25UeXBlIjoiQkFTSUMsSEFSRFdBUkVfQkFDS0VEIn0.eDdZ-tp-nmR1rraABpNFrl26T0eOvhZPuJ8gVMsL1ct4-vUYceJ85uIRyRaO5_EfevWHrWxk9z3qmAMNEjMtsXNSByai3uh-pAAWSNFKvlsye_rPtemkQk6irtl2lKnL-oUgD4AMJKLq-C4bL-FsPm_XpT8KYJA3EhdLCD3b_4xSKlNgRJ3c3GbnmKTIEia75c9rdftzQJw4cFOAMDFMXMgBsQKm7zxc79ts2O_w1916JZPnJbcInPC0LxjfBJV_6dUxoANjXJCdaXadzDdvGOtwnWZHpMWYGNAX0t1hVqyCLYL8Y8kohuBB19A7TEaBXD_EyNo1C5fg_LW_5TUXuQ";
        try {
            SafetyNetResponse safetyNetResponse = parseJsonWebSignature(s);

        }catch (Exception e){
            e.printStackTrace();
            System.out.println("result = ");

        }
    }


    private static SafetyNetResponse parseJsonWebSignature(String jwsResult) {
        if (jwsResult == null) {
            return null;
        }
        //the JWT (JSON WEB TOKEN) is just a 3 base64 encoded parts concatenated by a . character
        final String[] jwtParts = jwsResult.split("\\.");

        if (jwtParts.length == 3) {
            //we're only really interested in the body/payload

            Base64.Decoder decoder = Base64.getDecoder();
            String decodedPayload = new String(decoder.decode(jwtParts[1]));
            System.out.println("result = "+decodedPayload);
            return SafetyNetResponse.parse(decodedPayload);
        } else {
            return null;
        }
    }

}
