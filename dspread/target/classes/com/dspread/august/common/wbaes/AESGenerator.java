package com.dspread.august.common.wbaes;


import com.dspread.august.common.wbaes.generator.ExternalBijections;
import com.dspread.august.common.wbaes.generator.Generator;
import com.dspread.august.util.Utils;

public class AESGenerator {


    public static AES generate(byte[] key){
        Generator gEnc = new Generator();

        ExternalBijections extc = new ExternalBijections();

        gEnc.generateExtEncoding(extc, Generator.WBAESGEN_EXTGEN_ID);
        gEnc.setUseIO04x04Identity(true);

        gEnc.setUseIO08x08Identity(true);
        gEnc.setUseMB08x08Identity(true);
        gEnc.setUseMB32x32Identity(true);


        gEnc.generate(false,  key, 16, extc);
        AES AESenc = gEnc.getAESi();

        return AESenc;
    }

    public static void main(String[] args) {
        AES generate = generate(Utils.hexStringToBytes("12345678123456781234567812345678"));
    }
}
