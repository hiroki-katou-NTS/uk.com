package nts.uk.ctx.at.shared.app.command.calculation.holiday.roundingmonth;

import lombok.Data;
import nts.uk.ctx.at.shared.dom.monthly.roundingset.TimeRoundingOfExcessOutsideTime;

/**
 * Add exceed outside rounding data command
 * @author HoangNDH
 *
 */
@Data
public class AddExcoutRoundingCommand {
	
	// 丸め単位
	private int roundingUnit;
	// 端数処理
	private int roundingProcess;
	
	public TimeRoundingOfExcessOutsideTime toDomain(String companyId) {
		return TimeRoundingOfExcessOutsideTime.createFromJavaType(companyId, this.roundingUnit, this.roundingProcess);
	}
}
