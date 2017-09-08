/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.dom.shift.estimate.commonset;

import java.util.List;

import nts.uk.ctx.at.shared.dom.common.CompanyId;

/**
 * The Interface CommonGuidelineSettingGetMemento.
 */
public interface CommonGuidelineSettingGetMemento {

	/**
	 * Gets the company id.
	 *
	 * @return the company id
	 */
	CompanyId getCompanyId();

	/**
	 * Gets the alarm colors.
	 *
	 * @return the alarm colors
	 */
	List<EstimatedAlarmColor> getAlarmColors();

	/**
	 * Gets the estimate time.
	 *
	 * @return the estimate time
	 */
	ReferenceCondition getEstimateTime();

	/**
	 * Gets the estimate price.
	 *
	 * @return the estimate price
	 */
	ReferenceCondition getEstimatePrice();

	/**
	 * Gets the estimate number of days.
	 *
	 * @return the estimate number of days
	 */
	ReferenceCondition getEstimateNumberOfDays();

}
