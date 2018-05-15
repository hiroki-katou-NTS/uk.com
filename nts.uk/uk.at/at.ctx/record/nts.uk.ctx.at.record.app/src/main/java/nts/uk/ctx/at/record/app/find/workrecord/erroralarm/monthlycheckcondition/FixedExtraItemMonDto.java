package nts.uk.ctx.at.record.app.find.workrecord.erroralarm.monthlycheckcondition;

import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.record.app.find.workrecord.erroralarm.FixedConditionDataDto;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.FixConWorkRecordName;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.FixedConditionWorkRecordName;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.monthlycheckcondition.FixedExtraItemMon;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.monthlycheckcondition.SysFixedMonPerEral;
@Data
@NoArgsConstructor
public class FixedExtraItemMonDto {
	/**NO*/
	private int fixedExtraItemMonNo;
	/**名称*/
	private String fixedExtraItemMonName;
	/**初期メッセージ*/
	private String message;
	public FixedExtraItemMonDto(int fixedExtraItemMonNo, String fixedExtraItemMonName, String message) {
		super();
		this.fixedExtraItemMonNo = fixedExtraItemMonNo;
		this.fixedExtraItemMonName = fixedExtraItemMonName;
		this.message = message;
	}
	
	public static FixedExtraItemMonDto fromDomain(FixedExtraItemMon domain) {
		return new FixedExtraItemMonDto(
				domain.getFixedExtraItemMonNo().value,
				domain.getFixedExtraItemMonName().v(),
				domain.getFixedExtraItemMonName().v()
				);
	}

}
