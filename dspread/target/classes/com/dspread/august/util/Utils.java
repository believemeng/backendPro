package com.dspread.august.util;//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

// file deepcode ignore AvoidReassigningParameters: <comment the reason here>
public class Utils {
    private static String digits = "0123456789abcdef";

    public Utils() {
    }

    public static String toHex(byte[] data, int length) {
        StringBuilder buf = new StringBuilder();

        for(int i = 0; i != length; ++i) {
            int v = data[i] & 255;
            buf.append(digits.charAt(v >> 4));
            buf.append(digits.charAt(v & 15));
        }

        return buf.toString();
    }

    public static String toHex(byte[] data) {
        return toHex(data, data.length);
    }

    public static byte[] int2Byte(int intValue) {
        byte[] b = new byte[4];
        byte[] r = new byte[4];

        for(int i = 0; i < 4; ++i) {
            b[i] = (byte)(intValue >> 8 * (3 - i) & 255);
        }

        r[3] = b[0];
        r[2] = b[1];
        r[1] = b[2];
        r[0] = b[3];
        return r;
    }

    public static int byte2Int(byte[] b) {
        int intValue = 0;

        for(int i = 0; i < b.length; ++i) {
            intValue += (b[i] & 255) << 8 * (3 - i);
        }

        return intValue;
    }

    public static String bytesToHexString(byte[] src) {
        StringBuilder stringBuilder = new StringBuilder("");
        if (src != null && src.length > 0) {
            for (int i = 0; i < src.length; ++i) {
                int v = src[i] & 255;
                String hv = Integer.toHexString(v);
                if (hv.length() < 2) {
                    stringBuilder.append(0);
                }

                stringBuilder.append(hv);
            }

            return stringBuilder.toString();
        } else {
            return null;
        }
    }

    public static byte[] hexStringToBytes(String hexString) {
        if (hexString != null && !hexString.equals("")) {
            hexString = hexString.toUpperCase();
            int length = hexString.length() / 2;
            char[] hexChars = hexString.toCharArray();
            byte[] d = new byte[length];

            for (int i = 0; i < length; ++i) {
                int pos = i * 2;
                d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));
            }

            return d;
        } else {
            return null;
        }
    }

    private static byte charToByte(char c) {
        return (byte) "0123456789ABCDEF".indexOf(c);
    }
}
