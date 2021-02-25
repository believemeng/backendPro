package com.dspread.august.model;


import com.dspread.august.entity.TerminalInfoEntity;

import org.springframework.beans.BeanUtils;


public class TerminalInfoModel {
    public String getTerminalId() {
        return terminalId;
    }

    public void setTerminalId(String terminalId) {
        this.terminalId = terminalId;
    }

    public String getBootLoaderVersion() {
        return bootLoaderVersion;
    }

    public void setBootLoaderVersion(String bootLoaderVersion) {
        this.bootLoaderVersion = bootLoaderVersion;
    }

    public String getFirmwareVersion() {
        return firmwareVersion;
    }

    public void setFirmwareVersion(String firmwareVersion) {
        this.firmwareVersion = firmwareVersion;
    }

    public String getHardwareVersion() {
        return hardwareVersion;
    }

    public void setHardwareVersion(String hardwareVersion) {
        this.hardwareVersion = hardwareVersion;
    }

    public String getPCIFirmwareVresion() {
        return PCIFirmwareVresion;
    }

    public void setPCIFirmwareVresion(String PCIFirmwareVresion) {
        this.PCIFirmwareVresion = PCIFirmwareVresion;
    }

    public String getPCIHardwareVersion() {
        return PCIHardwareVersion;
    }

    public void setPCIHardwareVersion(String PCIHardwareVersion) {
        this.PCIHardwareVersion = PCIHardwareVersion;
    }

    private String terminalId;

    private String bootLoaderVersion;

    private String firmwareVersion;

    private String hardwareVersion;

    private String PCIFirmwareVresion;

    private String PCIHardwareVersion;




    public static TerminalInfoModel fromEntity(TerminalInfoEntity entity) {
        if (entity == null) {
            return null;
        }
        TerminalInfoModel model = new TerminalInfoModel();
        BeanUtils.copyProperties(entity, model);
        return model;
    }

}
