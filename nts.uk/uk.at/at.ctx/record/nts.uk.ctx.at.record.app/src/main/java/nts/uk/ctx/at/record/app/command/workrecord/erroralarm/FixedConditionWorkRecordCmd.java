package nts.uk.ctx.at.record.app.command.workrecord.erroralarm;

import lombok.Value;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.FixedConditionWorkRecord;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.FixedConditionWorkRecordName;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.WorkRecordFixedCheckItem;

@Value
public class FixedConditionWorkRecordCmd {
	/** 日次のアラームチェック条件ID */
	private String dailyAlarmConID;
	/** No */
	private int fixConWorkRecordNo;
	/** メッセージ */
	private String message;
	/** 使用区分 */
	private boolean useAtr;
	public FixedConditionWorkRecordCmd(String dailyAlarmConID, int fixConWorkRecordNo, String message, boolean useAtr) {
		super();
		this.dailyAlarmConID = dailyAlarmConID;
		this.fixConWorkRecordNo = fixConWorkRecordNo;
		this.message = message;
		this.useAtr = useAtr;
	}
	
	public FixedConditionWorkRecord fromDomain() {
		return new FixedConditionWorkRecord(
				this.dailyAlarmConID,
				EnumAdaptor.valueOf(this.fixConWorkRecordNo, WorkRecordFixedCheckItem.class),
				new FixedConditionWorkRecordName(this.message),
				this.useAtr
				);
	}
}

