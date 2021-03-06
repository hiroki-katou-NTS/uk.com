package nts.uk.ctx.at.record.app.find.workrecord.erroralarm;

import lombok.Data;
import lombok.NoArgsConstructor;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.FixedConditionWorkRecord;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.FixedConditionWorkRecordName;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.WorkRecordFixedCheckItem;
@Data
@NoArgsConstructor
public class FixedConditionWorkRecordDto {

	/** 日次のアラームチェック条件ID */
	private String dailyAlarmConID;
	/** No */
	private int fixConWorkRecordNo;
	/** メッセージ */
	private String message;
	/** 使用区分 */
	private boolean useAtr;
	
	
	public FixedConditionWorkRecordDto(String dailyAlarmConID, int fixConWorkRecordNo, String message, boolean useAtr) {
		super();
		this.dailyAlarmConID = dailyAlarmConID;
		this.fixConWorkRecordNo = fixConWorkRecordNo;
		this.message = message;
		this.useAtr = useAtr;
	}


	public FixedConditionWorkRecord toDto() {
		return new FixedConditionWorkRecord(
				this.dailyAlarmConID,
				EnumAdaptor.valueOf(this.fixConWorkRecordNo, WorkRecordFixedCheckItem.class),
				new FixedConditionWorkRecordName(this.message),
				this.useAtr
				);
	}
	
	public static  FixedConditionWorkRecordDto fromDomain(FixedConditionWorkRecord domain) {
		return new FixedConditionWorkRecordDto(
				domain.getDailyAlarmConID(),
				domain.getFixConWorkRecordNo().value,
				domain.getMessage().v(),
				domain.isUseAtr()
				);
		
	}
}
