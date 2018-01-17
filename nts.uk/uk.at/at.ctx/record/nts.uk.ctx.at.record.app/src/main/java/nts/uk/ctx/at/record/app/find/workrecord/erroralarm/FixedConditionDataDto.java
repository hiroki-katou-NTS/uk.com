package nts.uk.ctx.at.record.app.find.workrecord.erroralarm;

import lombok.Data;
import lombok.NoArgsConstructor;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.FixConWorkRecordName;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.FixedConditionData;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.FixedConditionWorkRecordName;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.WorkRecordFixedCheckItem;

@Data
@NoArgsConstructor
public class FixedConditionDataDto {
	/** No */
	private int fixConWorkRecordNo;
	/** 名称 */
	private String fixConWorkRecordName;
	/** メッセージ */
	private String message;
	public FixedConditionDataDto(int fixConWorkRecordNo, String fixConWorkRecordName, String message) {
		super();
		this.fixConWorkRecordNo = fixConWorkRecordNo;
		this.fixConWorkRecordName = fixConWorkRecordName;
		this.message = message;
	}
	
	public FixedConditionData toDto() {
		return new  FixedConditionData(
				EnumAdaptor.valueOf(this.fixConWorkRecordNo, WorkRecordFixedCheckItem.class),
				new FixConWorkRecordName(this.fixConWorkRecordName),
				new FixedConditionWorkRecordName(this.message)
				);
	}
	
	public static FixedConditionDataDto fromDomain(FixedConditionData domain) {
		return new FixedConditionDataDto(
				domain.getFixConWorkRecordNo().value,
				domain.getFixConWorkRecordName().v(),
				domain.getMessage().v()
				);
	}
}
