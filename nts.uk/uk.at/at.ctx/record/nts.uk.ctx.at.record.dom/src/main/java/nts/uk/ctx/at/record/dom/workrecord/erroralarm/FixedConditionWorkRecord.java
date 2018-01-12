package nts.uk.ctx.at.record.dom.workrecord.erroralarm;

import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.AggregateRoot;
/**
 * 勤務実績の固定抽出条件
 * @author tutk
 *
 */
@Getter
@NoArgsConstructor
public class FixedConditionWorkRecord extends AggregateRoot {
	/** 日次のアラームチェック条件ID */
	private String errorAlarmID;
	/** No */
	private WorkRecordFixedCheckItem fixConWorkRecordNo;
	/** メッセージ */
	private FixedConditionWorkRecordName message;
	/** 使用区分 */
	private boolean useAtr;

	public FixedConditionWorkRecord(String errorAlarmID, WorkRecordFixedCheckItem fixConWorkRecordNo,
			FixedConditionWorkRecordName message, boolean useAtr) {
		super();
		this.errorAlarmID = errorAlarmID;
		this.fixConWorkRecordNo = fixConWorkRecordNo;
		this.message = message;
		this.useAtr = useAtr;
	}
	
	
}
