package nts.uk.ctx.at.schedule.dom.shift.pattern.daily;

import nts.uk.ctx.at.schedule.dom.shift.pattern.WorkTypeCode;
import nts.uk.ctx.at.schedule.dom.shift.pattern.WorkingCode;

/**
 * The Interface DailyPatternValGetMemento.
 */
public interface DailyPatternValGetMemento {

	/**
	 * Gets the disp order.
	 *
	 * @return the disp order
	 */
	DispOrder getDispOrder();

	/**
	 * Gets the work type set cd.
	 *
	 * @return the work type set cd
	 */
	WorkTypeCode getWorkTypeSetCd();

	/**
	 * Gets the working hours cd.
	 *
	 * @return the working hours cd
	 */
	WorkingCode getWorkingHoursCd();

	/**
	 * Gets the days.
	 *
	 * @return the days
	 */
	Days getDays();
}
