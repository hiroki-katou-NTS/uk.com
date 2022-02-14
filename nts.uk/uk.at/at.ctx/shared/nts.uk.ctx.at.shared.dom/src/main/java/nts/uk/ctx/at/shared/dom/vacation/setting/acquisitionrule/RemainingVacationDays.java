package nts.uk.ctx.at.shared.dom.vacation.setting.acquisitionrule;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 休暇残日数
 * @author tutk
 *
 */
@Getter
@AllArgsConstructor
public class RemainingVacationDays {

	/**
	 * 代休残日数
	 */
	private double numberOfDayLeft;
	
	/**
	 * 振休残日数
	 */
	private double numberOfDayLeftForHoliday;
}
