/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.dom.plannedyearholiday.frame;

import java.util.List;
import java.util.Optional;

import nts.uk.ctx.at.shared.dom.common.CompanyId;

/**
 * The Interface PlanYearHolidayFrameRepository.
 */
public interface PlanYearHolidayFrameRepository {
	
	/**
	 * Find classification.
	 *
	 * @param companyId the company id
	 * @param planYearHdFrNo the plan year hd fr no
	 * @return the optional
	 */
	Optional<PlanYearHolidayFrame> findPlanYearHolidayFrame(CompanyId companyId, int planYearHdFrNo);
	
	/**
	 * Update.
	 *
	 * @param planYearHolidayFrame the plan year holiday frame
	 */
	void update(PlanYearHolidayFrame planYearHolidayFrame);
	
	
	/**
	 * Gets the all plan year holiday frame.
	 *
	 * @param companyId the company id
	 * @return the all plan year holiday frame
	 */
	List<PlanYearHolidayFrame> getAllPlanYearHolidayFrame(String companyId);
}
