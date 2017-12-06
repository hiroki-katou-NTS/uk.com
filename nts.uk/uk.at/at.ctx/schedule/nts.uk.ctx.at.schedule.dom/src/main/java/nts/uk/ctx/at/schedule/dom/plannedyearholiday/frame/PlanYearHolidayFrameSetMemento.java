/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.dom.plannedyearholiday.frame;

import nts.uk.ctx.at.shared.dom.common.CompanyId;

/**
 * The Interface PlanYearHolidayFrameSetMemento.
 */
public interface PlanYearHolidayFrameSetMemento {
	
	/**
	 * Sets the company id.
	 *
	 * @param companyId the new company id
	 */
	public void setCompanyId(CompanyId companyId);

	/**
	 * Sets the plan year holiday frame no.
	 *
	 * @param planYearHolidayFrNo the new plan year holiday frame no
	 */
	public void setPlanYearHolidayFrameNo(PlanYearHolidayFrameNo planYearHolidayFrNo);
	
	/**
	 * Sets the use classification.
	 *
	 * @param useAtr the new use classification
	 */
	public void setUseClassification(NotUseAtr useAtr);
	
	/**
	 * Sets the plan year holiday frame name.
	 *
	 * @param planYearHolidayFrName the new plan year holiday frame name
	 */
	public void setPlanYearHolidayFrameName(PlanYearHolidayFrameName planYearHolidayFrName);
}
