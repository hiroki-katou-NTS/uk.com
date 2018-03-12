package nts.uk.ctx.at.record.app.command.workrecord.erroralarm;

import lombok.Value;

@Value
public class DeleteFixedConWorkRecordCmd {
	/** 日次のアラームチェック条件ID */
	private String dailyAlarmConID;
	
	private int fixConWorkRecordNo;
}
