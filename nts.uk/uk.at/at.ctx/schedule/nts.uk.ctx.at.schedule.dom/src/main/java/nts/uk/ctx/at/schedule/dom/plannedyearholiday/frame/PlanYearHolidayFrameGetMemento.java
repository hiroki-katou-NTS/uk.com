/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.dom.plannedyearholiday.frame;

import nts.uk.ctx.at.shared.dom.common.CompanyId;

/**
 * The Interface PlanYearHolidayFrameGetMemento.
 */
public interface PlanYearHolidayFrameGetMemento {
	
	/**
	 * Gets the company id.
	 *
	 * @return the company id
	 */
	CompanyId getCompanyId();

	/**
	 * Gets the plan year holiday frame no.
	 *
	 * @return the plan year holiday frame no
	 */
	PlanYearHolidayFrameNo getPlanYearHolidayFrameNo();
	
	/**
	 * Gets the use classification.
	 *
	 * @return the use classification
	 */
	NotUseAtr getUseClassification();
	
	/**
	 * Gets the plan year holiday frame name.
	 *
	 * @return the plan year holiday frame name
	 */
	PlanYearHolidayFrameName getPlanYearHolidayFrameName();
}
