package nts.uk.ctx.at.function.dom.alarm.checkcondition;

import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.arc.layer.dom.DomainObject;
/**
 * 勤務実績の固定抽出条件
 * @author tutk
 *
 */
@Getter
@NoArgsConstructor
public class FixedConditionWorkRecord extends DomainObject {
	/** 日次のアラームチェック条件ID */
	private String dailyAlarmConID;
	/** No */
	private WorkRecordFixedCheckItem fixConWorkRecordNo;
	/** メッセージ */
	private FixedConditionWorkRecordName message;
	/** 使用区分 */
	private boolean useAtr;

	public FixedConditionWorkRecord(String dailyAlarmConID, WorkRecordFixedCheckItem fixConWorkRecordNo,
			FixedConditionWorkRecordName message, boolean useAtr) {
		super();
		this.dailyAlarmConID = dailyAlarmConID;
		this.fixConWorkRecordNo = fixConWorkRecordNo;
		this.message = message;
		this.useAtr = useAtr;
	}
	
}
