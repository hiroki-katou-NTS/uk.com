/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.find.holidaysetting.company;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.common.Year;
import nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.company.CompanyMonthDaySetting;
import nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.company.CompanyMonthDaySettingRepository;
import nts.uk.shr.com.context.AppContexts;


/**
 * The Class CompanyMonthDaySettingFinder.
 */
@Stateless
public class CompanyMonthDaySettingFinder {
	
	/** The repository. */
	@Inject
	private CompanyMonthDaySettingRepository repository;
	
	/**
	 * Gets the company month day setting.
	 *
	 * @param year the year
	 * @return the company month day setting
	 */
	public CompanyMonthDaySettingDto getCompanyMonthDaySetting(int year){
		String companyId = AppContexts.user().companyId();
		
		Optional<CompanyMonthDaySetting> optional = this.repository.findByYear(new CompanyId(companyId), new Year(year));
		
		if(optional.isPresent()){
			CompanyMonthDaySetting domain = optional.get();
			
			CompanyMonthDaySettingDto dto = new CompanyMonthDaySettingDto();
			domain.saveToMemento(dto);
			
			return dto;
		}
		
		return null;
	}
}
