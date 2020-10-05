package nts.uk.ctx.at.record.app.find.workrecord.erroralarm;

import lombok.Data;
import lombok.NoArgsConstructor;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.FixConWorkRecordName;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.FixedConditionData;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.FixedConditionWorkRecordName;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.WorkRecordFixedCheckItem;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.ErAlarmAtr;

@Data
@NoArgsConstructor
public class FixedConditionDataDto {
	/** No */
	private int fixConWorkRecordNo;
	/** 名称 */
	private String fixConWorkRecordName;
	/** メッセージ */
	private String message;
	/** 区分*/
	private Integer eralarmAtr;
	public FixedConditionDataDto(int fixConWorkRecordNo, String fixConWorkRecordName, String message, Integer eralarmAtr) {
		super();
		this.fixConWorkRecordNo = fixConWorkRecordNo;
		this.fixConWorkRecordName = fixConWorkRecordName;
		this.message = message;
		this.eralarmAtr = eralarmAtr;
	}
	
	public FixedConditionData toDto() {
		return new  FixedConditionData(
				EnumAdaptor.valueOf(this.fixConWorkRecordNo, WorkRecordFixedCheckItem.class),
				new FixConWorkRecordName(this.fixConWorkRecordName),
				new FixedConditionWorkRecordName(this.message),
				this.eralarmAtr!=null?ErAlarmAtr.values()[this.eralarmAtr]:ErAlarmAtr.OTHER
				);
	}
	
	public static FixedConditionDataDto fromDomain(FixedConditionData domain) {
		return new FixedConditionDataDto(
				domain.getFixConWorkRecordNo().value,
				domain.getFixConWorkRecordName().v(),
				domain.getMessage().v(),
				domain.getEralarmAtr()!=null?domain.getEralarmAtr().value:2
				);
	}
}
