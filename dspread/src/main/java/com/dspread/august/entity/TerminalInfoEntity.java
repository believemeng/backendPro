package com.dspread.august.entity;

import com.dspread.august.model.TerminalInfoModel;
import com.dspread.august.model.UserModel;
import org.springframework.beans.BeanUtils;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "terminal")
public class TerminalInfoEntity {
    /*
     * 主键
     * */
    @Id
    @Column(name = "terminal_id", unique = true, nullable = false)

    private String terminalId;

    @Column(name = "bootLoader_version")
    private String bootLoaderVersion;

    @Column(name = "firmware_version")
    private String firmwareVersion;

    @Column(name = "hardware_version")
    private String hardwareVersion;

    @Column(name = "PCI_firmwareVresion")
    private String PCIFirmwareVresion;

    @Column(name = "PCI_hardwareVersion")
    private String PCIHardwareVersion;

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

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }


    @Column(name = "merchant_id")
    private String merchantId;


    public static TerminalInfoEntity fromModel(TerminalInfoModel model) {
        if (model == null) {
            return null;
        }
        TerminalInfoEntity entity = new TerminalInfoEntity();
        BeanUtils.copyProperties(model, entity);
        return entity;
    }
}