package nts.uk.ctx.at.record.app.command.workrecord.erroralarm;

import lombok.Value;

@Value
public class DeleteFixedConWorkRecordCmd {
	/** 日次のアラームチェック条件ID */
	private String dailyAlarmConID;
	/** No */
	private int fixConWorkRecordNo;
	
	public DeleteFixedConWorkRecordCmd(String dailyAlarmConID, int fixConWorkRecordNo) {
		super();
		this.dailyAlarmConID = dailyAlarmConID;
		this.fixConWorkRecordNo = fixConWorkRecordNo;
	}
	
	
}
