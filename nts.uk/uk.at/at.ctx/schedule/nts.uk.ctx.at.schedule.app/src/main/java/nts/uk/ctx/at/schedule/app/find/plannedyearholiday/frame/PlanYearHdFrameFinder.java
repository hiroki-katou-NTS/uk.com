/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.app.find.plannedyearholiday.frame;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.schedule.dom.plannedyearholiday.frame.PlanYearHolidayFrame;
import nts.uk.ctx.at.schedule.dom.plannedyearholiday.frame.PlanYearHolidayFrameRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class PlanYearHdFrameFinder.
 */
@Stateless
public class PlanYearHdFrameFinder {
	
	/** The repository. */
	@Inject 
	private PlanYearHolidayFrameRepository repository;
	
	/**
	 * Find all.
	 *
	 * @return the list
	 */
	public List<PlanYearHdFrameFindDto> findAll(){
		// get company id
		String companyId = AppContexts.user().companyId();

		// get all
		List<PlanYearHolidayFrame> managementCategories = this.repository
			.getAllPlanYearHolidayFrame(companyId);
		
		// to domain
		return managementCategories.stream().map(category -> {
			PlanYearHdFrameFindDto dto = new PlanYearHdFrameFindDto();
			category.saveToMemento(dto);
			return dto;
		}).collect(Collectors.toList());
	}
}
