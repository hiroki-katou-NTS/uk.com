/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.dom.shift.estimate.commonset;

import java.util.List;

import nts.uk.ctx.at.shared.dom.common.CompanyId;

/**
 * The Interface CommonGuidelineSettingSetMemento.
 */
public interface CommonGuidelineSettingSetMemento {

	/**
	 * Sets the company id.
	 *
	 * @param companyId the new company id
	 */
	void setCompanyId(CompanyId companyId) ;

	/**
	 * Sets the alarm colors.
	 *
	 * @param alarmColors the new alarm colors
	 */
	void setAlarmColors(List<EstimatedAlarmColor> alarmColors) ;

	/**
	 * Sets the estimate time.
	 *
	 * @param estimateTime the new estimate time
	 */
	void setEstimateTime(ReferenceCondition estimateTime) ;

	/**
	 * Sets the estimate price.
	 *
	 * @param estimatePrice the new estimate price
	 */
	void setEstimatePrice(ReferenceCondition estimatePrice) ;

	/**
	 * Sets the estimate number of days.
	 *
	 * @param estimateNumberOfDays the new estimate number of days
	 */
	void setEstimateNumberOfDays(ReferenceCondition estimateNumberOfDays);
}
