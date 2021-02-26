package com.dspread.august.common;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

// file deepcode ignore InsecureHash: <comment the reason here>
public class SecurityUtil {
    public static String SHA1(String s){
        MessageDigest md = null;
        String stnStr = null;
        try {
            md = MessageDigest.getInstance("SHA-1");
            byte[] digest = md.digest(s.getBytes());
            stnStr = getFormattedText(digest);
        } catch (NoSuchAlgorithmException e) {
//            e.printStackTrace();
        }
        return stnStr;
    }
    public static String MD5(String s) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] bytes = md.digest(s.getBytes("utf-8"));
            return toHex(bytes);
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static String toHex(byte[] data) {

        final char[] DIGITS_LOWER = "0123456789abcdef".toCharArray();

        char[] out = new char[data.length << 1];

        for (int i = 0, j = 0; i < data.length; i++) {
            out[j++] = DIGITS_LOWER[(0xF0 & data[i]) >>> 4];
            out[j++] = DIGITS_LOWER[0x0F & data[i]];
        }
        // deepcode ignore ReplaceBoxedConstructor: <please specify a reason of ignoring this>
        return new String(out);
    }
    private static String getFormattedText(byte[] bytes) {
        char[] HEX_DIGITS = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
        int len = bytes.length;
        StringBuilder buf = new StringBuilder(len * 2);
        for (int j = 0; j < len; j++) {
            buf.append(HEX_DIGITS[(bytes[j] >> 4) & 0x0f]);
            buf.append(HEX_DIGITS[bytes[j] & 0x0f]);
        }
        return buf.toString();
    }

    public static String getRandomString() {

        String string = "JGA0NAP11V7C6TNY7O1C72WB9FE4E1H8L6WT0KO00WKAOERIVGA452H7ABKPPOY1101CY8CF0NT8QVOKOEIB0A70AL1BEOO2BW1H085THEAH7O38KAH088";
        Random random = new Random();

        // deepcode ignore ApiMigration: <please specify a reason of ignoring this>
        StringBuffer sb = new StringBuffer();
        int length = 12;
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(string.length());
            sb.append(string.charAt(number));
        }
        return sb.toString();

    }

    public static String getRandomCode() {

        String string = "1234567890";
        Random random = new Random();

        // deepcode ignore ApiMigration: <please specify a reason of ignoring this>
        StringBuffer sb = new StringBuffer();
        int length = 6;
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(string.length());
            sb.append(string.charAt(number));
        }
        return sb.toString();

    }
}
