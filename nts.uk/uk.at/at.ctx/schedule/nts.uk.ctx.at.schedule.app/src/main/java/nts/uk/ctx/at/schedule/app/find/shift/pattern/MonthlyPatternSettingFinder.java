/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.app.find.shift.pattern;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.schedule.app.find.shift.pattern.dto.MonthlyPatternDto;
import nts.uk.ctx.at.schedule.app.find.shift.pattern.dto.MonthlyPatternSettingDto;
import nts.uk.ctx.at.schedule.dom.shift.pattern.MonthlyPattern;
import nts.uk.ctx.at.schedule.dom.shift.pattern.MonthlyPatternRepository;
import nts.uk.ctx.at.schedule.dom.shift.pattern.MonthlyPatternSetting;
import nts.uk.ctx.at.schedule.dom.shift.pattern.MonthlyPatternSettingRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.context.LoginUserContext;

/**
 * The Class MonthlyPatternSettingFinder.
 */
@Stateless
public class MonthlyPatternSettingFinder {

	/** The monthly pattern setting repository. */
	@Inject
	private MonthlyPatternSettingRepository monthlyPatternSettingRepository;
	
	/** The monthly pattern repository. */
	@Inject
	private MonthlyPatternRepository monthlyPatternRepository;
	
	/**
	 * Find by id.
	 *
	 * @param employeeId the employee id
	 * @return the monthly pattern setting dto
	 */
	public MonthlyPatternSettingDto findById(String employeeId){
		// get login user
		LoginUserContext loginUserContext = AppContexts.user();
		
		// get company id
		String companyId = loginUserContext.companyId();
		
		MonthlyPatternSettingDto dto = new MonthlyPatternSettingDto();
		
		dto.setSetting(false);
		//call repository find by employee id
		Optional<MonthlyPatternSetting> monthlyPatternSetting = this.monthlyPatternSettingRepository
				.findById(employeeId);
		
		// check exist data
		if(monthlyPatternSetting.isPresent()){
			Optional<MonthlyPattern> monthlyPattern = this.monthlyPatternRepository
					.findById(companyId, monthlyPatternSetting.get().getMonthlyPatternCode().v());
			if(monthlyPattern.isPresent()){
				dto.setSetting(true);
				MonthlyPatternDto info = new MonthlyPatternDto();
				monthlyPattern.get().saveToMemento(info);
				dto.setInfo(info);
			}
		}
		return dto;
	}
	
	/**
	 * Find all by employee id.
	 *
	 * @param employeeIds the employee ids
	 * @return the list
	 */
	public List<String> findAllByEmployeeId(List<String> employeeIds) {
		return this.monthlyPatternSettingRepository.findAllByEmployeeIds(employeeIds).stream()
				.map(domain -> domain.getEmployeeId()).collect(Collectors.toList());
	}
}
