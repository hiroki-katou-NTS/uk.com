/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.bs.employee.app.find.holidaysetting.workplace;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static java.util.stream.Collectors.toList;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.bs.employee.dom.common.CompanyId;
import nts.uk.ctx.bs.employee.dom.holidaysetting.common.Year;
import nts.uk.ctx.bs.employee.dom.holidaysetting.workplace.WorkplaceMonthDaySetting;
import nts.uk.ctx.bs.employee.dom.holidaysetting.workplace.WorkplaceMonthDaySettingRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class WorkplaceMonthDaySettingFinder.
 */
@Stateless
public class WorkplaceMonthDaySettingFinder {
	/** The repository. */
	@Inject
	private WorkplaceMonthDaySettingRepository repository;

	/**
	 * Gets the workplace month day setting.
	 *
	 * @param workplaceId the workplace id
	 * @param year the year
	 * @return the workplace month day setting
	 */
	public WorkplaceMonthDaySettingDto getWorkplaceMonthDaySetting(String workplaceId ,int year){
		String companyId = AppContexts.user().companyId();
		
		Optional<WorkplaceMonthDaySetting> optional = this.repository.findByYear(new CompanyId(companyId), workplaceId ,new Year(year));
		
		if(optional.isPresent()){
			WorkplaceMonthDaySetting domain = optional.get();
			
			WorkplaceMonthDaySettingDto dto = new WorkplaceMonthDaySettingDto();
			domain.saveToMemento(dto);
			
			return dto;
		}
		return null;
	}
	
	/**
	 * Find all by year.
	 *
	 * @param year the year
	 * @return the list
	 */
	public List<String> findAllByYear() {
		String companyId = AppContexts.user().companyId();
		
		List<String> lstWpk = this.repository.findWkpRegisterByYear(new CompanyId(companyId));
		if (lstWpk != null && !lstWpk.isEmpty()) {
			return lstWpk;
		} 
		return new ArrayList<>();
	}
}
