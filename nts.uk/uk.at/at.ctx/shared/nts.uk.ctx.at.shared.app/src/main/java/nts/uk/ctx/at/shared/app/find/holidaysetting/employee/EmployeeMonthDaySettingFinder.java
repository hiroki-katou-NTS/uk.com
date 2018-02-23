/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.find.holidaysetting.employee;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.common.Year;
import nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.employee.EmployeeMonthDaySetting;
import nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.employee.EmployeeMonthDaySettingRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class EmployeeMonthDaySettingFinder.
 */
@Stateless
public class EmployeeMonthDaySettingFinder {
	
	/** The repository. */
	@Inject
	private EmployeeMonthDaySettingRepository repository;
	
	/**
	 * Gets the employee month day setting.
	 *
	 * @param sId the s id
	 * @param year the year
	 * @return the employee month day setting
	 */
	public EmployeeMonthDaySettingDto getEmployeeMonthDaySetting(String sId ,int year){
		String companyId = AppContexts.user().companyId();
		
		Optional<EmployeeMonthDaySetting> optional = this.repository.findByYear(new CompanyId(companyId), sId ,new Year(year));
		
		if(optional.isPresent()){
			EmployeeMonthDaySetting domain = optional.get();
			
			EmployeeMonthDaySettingDto dto = new EmployeeMonthDaySettingDto();
			domain.saveToMemento(dto);
			
			return dto;
		}
		return null;
	}
	
	/**
	 * Find all employee register.
	 *
	 * @return the list
	 */
	public List<String> findAllEmployeeRegister() {
		String companyId = AppContexts.user().companyId();
		
		List<String> lstEmployee = this.repository.findAllEmployeeRegister(new CompanyId(companyId));
		
		if (lstEmployee != null && !lstEmployee.isEmpty()) {
			return lstEmployee;
		}
		
		return new ArrayList<>();
	}
}
