package nts.uk.ctx.at.record.dom.workrecord.erroralarm;

import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.arc.layer.dom.AggregateRoot;
/**
 * 勤務実績の固定抽出項目
 * @author tutk
 *
 */
@Getter
@NoArgsConstructor
public class FixedConditionData extends AggregateRoot {
	/** No */
	private WorkRecordFixedCheckItem fixConWorkRecordNo;
	/** 名称 */
	private FixConWorkRecordName fixConWorkRecordName;
	/** 初期メッセージ*/
	private FixedConditionWorkRecordName message;
	/** 区分*/
	private String division;
	public FixedConditionData(WorkRecordFixedCheckItem fixConWorkRecordNo, FixConWorkRecordName fixConWorkRecordName,
			FixedConditionWorkRecordName message, String division) {
		super();
		this.fixConWorkRecordNo = fixConWorkRecordNo;
		this.fixConWorkRecordName = fixConWorkRecordName;
		this.message = message;
		this.division = division;
	}
	
	
	

}
