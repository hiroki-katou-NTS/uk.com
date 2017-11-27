package nts.uk.ctx.at.schedule.app.plannedyearholiday.frame.find;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.schedule.dom.plannedyearholiday.frame.PlanYearHolidayFrame;
import nts.uk.ctx.at.schedule.dom.plannedyearholiday.frame.PlanYearHolidayFrameRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.context.LoginUserContext;

@Stateless
public class PlanYearHdFrameFinder {
	@Inject PlanYearHolidayFrameRepository repository;
	
	/** Find all.
	 *
	 * @return the list
	 */
	public List<PlanYearHdFrameFindDto> findAll(){
		
		// get login info
		LoginUserContext loginUserContext = AppContexts.user();
		
		// get company id
		String companyId = loginUserContext.companyId();

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
