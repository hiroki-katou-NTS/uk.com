package nts.uk.ctx.at.record.app.command.holiday.roundingmonth;

import lombok.Data;
import nts.uk.ctx.at.record.dom.monthly.roundingset.TimeRoundingOfExcessOutsideTime;

/**
 * Instantiates a new adds the excout rounding command.
 */
@Data
public class AddExcoutRoundingCommand {

	/** The rounding unit. */
	// 丸め単位
	private int roundingUnit;

	/** The rounding process. */
	// 端数処理
	private int roundingProcess;

	/**
	 * To domain.
	 *
	 * @param companyId
	 *            the company id
	 * @return the time rounding of excess outside time
	 */
	public TimeRoundingOfExcessOutsideTime toDomain(String companyId) {
		return TimeRoundingOfExcessOutsideTime.of(this.roundingUnit, this.roundingProcess);
	}
}
