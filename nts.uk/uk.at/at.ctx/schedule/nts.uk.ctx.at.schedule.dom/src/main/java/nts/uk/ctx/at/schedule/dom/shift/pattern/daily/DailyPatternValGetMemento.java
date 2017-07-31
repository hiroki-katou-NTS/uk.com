package nts.uk.ctx.at.schedule.dom.shift.pattern.daily;

import nts.uk.ctx.at.shared.dom.common.CompanyId;

/**
 * The Interface DailyPatternValGetMemento.
 */
public interface DailyPatternValGetMemento {
	
	/**
	 * Gets the company id.
	 *
	 * @return the company id
	 */
	CompanyId getCompanyId();
	
	/**
	 * Gets the pattern code.
	 *
	 * @return the pattern code
	 */
	PatternCode getPatternCode();
	
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
