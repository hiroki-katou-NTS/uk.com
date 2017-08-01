package nts.uk.ctx.at.schedule.dom.shift.pattern.daily;

import nts.uk.ctx.at.schedule.dom.shift.pattern.WorkTypeCode;
import nts.uk.ctx.at.schedule.dom.shift.pattern.WorkingCode;
import nts.uk.ctx.at.shared.dom.common.CompanyId;

/**
 * The Interface DailyPatternValSetMemento.
 */
public interface DailyPatternValSetMemento {

	/**
	 * Sets the company id.
	 *
	 * @param setCompanyId the new company id
	 */
	void  setCompanyId(CompanyId setCompanyId);

	/**
	 * Sets the pattern code.
	 *
	 * @param setPatternCode the new pattern code
	 */
	void  setPatternCode(PatternCode setPatternCode);

	/**
	 * Sets the disp order.
	 *
	 * @param setDispOrder the new disp order
	 */
	void  setDispOrder(DispOrder setDispOrder);

	/**
	 * Sets the work type codes.
	 *
	 * @param setWorkTypeCodes the new work type codes
	 */
	void  setWorkTypeCodes(WorkTypeCode setWorkTypeCodes);

	/**
	 * Sets the work house codes.
	 *
	 * @param setWorkHouseCodes the new work house codes
	 */
	void  setWorkHouseCodes(WorkingCode setWorkHouseCodes);

	/**
	 * Sets the days.
	 *
	 * @param setDays the new days
	 */
	void  setDays(Days setDays);

}
