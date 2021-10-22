package nts.uk.ctx.at.record.app.command.knr.knr001.a;

import java.util.List;

import lombok.Value;

@Value
public class EmpInfoTerminalResgiterAndUpdateCommand {
	
	private String empInfoTerCode;
	
	private String empInfoTerName;
	
	private int modelEmpInfoTer;
	
	private String macAddress;
	
	private String ipAddress1;
	
	private String ipAddress2;
	
	private String ipAddress3;
	
	private String ipAddress4;
	
	private String terSerialNo;
	
	private String workLocationCode;
	
	private int intervalTime;
	
	private String memo;
	
	private String workplaceId;
	
	private List<MSConversionCommand> lstMSConversion;
	
	private NRConvertInfoCommand nrconvertInfo;
	
	public String getIpAddress() {
		return this.ipAddress1 + '.' + this.ipAddress2 + '.' + this.ipAddress3 + '.' + this.ipAddress4;
	}
}
