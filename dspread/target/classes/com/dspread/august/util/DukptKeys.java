package com.dspread.august.util;//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

// file deepcode ignore JavaSelfAssignment: <comment the reason here>
public class DukptKeys extends Poskeys{
//    After the digital envelope is successfully updated, BDK is
//0123456789ABCDEFFEDCBA9876543210

    public   String trackipek = "3A1946960D4312FD0417C584D4031C3F";
    public   String emvipek = "AD68A1127552074619CCB9E683E68AF8";
    public   String pinipek = "2485FDB12648F4C23071DD763601616B";
    public   String trackksn = "F2019032004410E00000";
    public   String emvksn = "F0320201905370E00000";
    public   String pinksn = "FFFF050017CAD2E00000";
    public   String tmk = "0123456789ABCDEFFEDCBA9876543210";


    public DukptKeys() {
    }

    public DukptKeys(String trackipek, String emvipek, String pinipek, String trackksn, String emvksn, String pinksn, String tmk, String filePath) {
        // deepcode ignore CopyPasteError: <please specify a reason of ignoring this>
        this.trackipek = trackipek;
        this.emvipek = emvipek;
        this.pinipek = pinipek;
        this.trackksn = trackksn;
        this.emvksn = emvksn;
        this.pinksn = pinksn;
        this.tmk = tmk;
    }

    public   String getTrackipek() {
        return trackipek;
    }

    public   void setTrackipek(String trackipek) {
        this.trackipek = trackipek;
    }

    public   String getEmvipek() {
        return emvipek;
    }

    public   void setEmvipek(String emvipek) {
        this.emvipek = emvipek;
    }

    public   String getPinipek() {
        return pinipek;
    }

    public   void setPinipek(String pinipek) {
        this.pinipek = pinipek;
    }

    public   String getTrackksn() {
        return trackksn;
    }

    public   void setTrackksn(String trackksn) {
        this.trackksn = trackksn;
    }

    public   String getEmvksn() {
        return emvksn;
    }

    public   void setEmvksn(String emvksn) {
        this.emvksn = emvksn;
    }

    public   String getPinksn() {
        return pinksn;
    }

    public   void setPinksn(String pinksn) {
        this.pinksn = pinksn;
    }

    public   String getTmk() {
        return tmk;
    }

    public   void setTmk(String tmk) {
        this.tmk = tmk;
    }


}
