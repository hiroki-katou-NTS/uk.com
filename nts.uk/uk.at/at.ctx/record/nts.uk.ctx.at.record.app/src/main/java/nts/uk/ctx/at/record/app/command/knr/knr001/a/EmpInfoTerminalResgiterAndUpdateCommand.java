package nts.uk.ctx.at.record.app.command.knr.knr001.a;

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
	
	/**
	 * 外出を応援に変換
	 */
	private int outSupport;
	
	/**
	 * 置換する
	 */
	private int replace;
	
	/**
	 * 外出理由
	 */
	private Integer goOutReason;
	
	/**
	 * 出退勤を入退門に変換
	 */
	private int entranceExit;
	
}
