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
import nts.uk.ctx.at.schedule.dom.shift.pattern.monthly.MonthlyPattern;
import nts.uk.ctx.at.schedule.dom.shift.pattern.monthly.MonthlyPatternRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.context.LoginUserContext;

/**
 * The Class MonthlyPatternFinder.
 */
@Stateless
public class MonthlyPatternFinder {
	
	/** The repository. */
	@Inject
	private MonthlyPatternRepository repository;

	/**
	 * Find all.
	 *
	 * @return the list
	 */
	public List<MonthlyPatternDto> findAll(){
		// get login user
		LoginUserContext loginUserContext = AppContexts.user();
		
		// get company id
		String companyId = loginUserContext.companyId();

		
		return this.repository.findAll(companyId).stream().map(monthlyPattern -> {
			MonthlyPatternDto dto = new MonthlyPatternDto();
			monthlyPattern.saveToMemento(dto);
			return dto;
		}).collect(Collectors.toList());
		
	}
	
	/**
	 * Find by id.
	 *
	 * @param monthlyPatternCode the monthly pattern code
	 * @return the monthly pattern dto
	 */
	public MonthlyPatternDto findById(String monthlyPatternCode){
		// get login user
		LoginUserContext loginUserContext = AppContexts.user();

		// get company id
		String companyId = loginUserContext.companyId();

		// find by id monthly pattern
		Optional<MonthlyPattern> monthlyPattern = this.repository.findById(companyId,
				monthlyPatternCode);
		MonthlyPatternDto dto = new MonthlyPatternDto();
		
		// check exist data
		if(monthlyPattern.isPresent()){
			monthlyPattern.get().saveToMemento(dto);
		}
		return dto;
	}
}
