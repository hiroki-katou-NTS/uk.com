package nts.uk.ctx.at.record.app.command.calculation.holiday.roundingmonth;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.ctx.at.shared.dom.calculation.holiday.roundingmonth.RoundingMonth;

/**
 * @author phongtq The class Add Rounding Month Command
 */
@Data
@AllArgsConstructor
public class AddRoundingMonthCommand {

	/** 勤怠項目ID */
	private String timeItemId;

	/** 丸め単位 */
	public int unit;

	/** 端数処理 */
	public int rounding;

	public RoundingMonth toDomain(String companyId) {
		return RoundingMonth.createFromJavaType(companyId, this.timeItemId, this.unit, this.rounding);
	}
}
