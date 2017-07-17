/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.app.find.shift.pattern;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;

import nts.uk.ctx.at.schedule.app.find.shift.pattern.dto.MonthlyPatternDto;

/**
 * The Class MonthlyPatternFinder.
 */
@Stateless
public class MonthlyPatternFinder {

	/**
	 * Find all.
	 *
	 * @param companyId the company id
	 * @return the list
	 */
	public List<MonthlyPatternDto> findAll(){
		// get login user
		//LoginUserContext loginUserContext = AppContexts.user();
		
		// get company id
		//String comanyId = loginUserContext.companyId();
		
		List<MonthlyPatternDto> mothlyPatterns = new ArrayList<>();
		MonthlyPatternDto monthlyPattern001 = new MonthlyPatternDto();
		monthlyPattern001.setCode("001");
		monthlyPattern001.setName("001");
		mothlyPatterns.add(monthlyPattern001);
		MonthlyPatternDto monthlyPattern002 = new MonthlyPatternDto();
		monthlyPattern002.setCode("002");
		monthlyPattern002.setName("002");
		mothlyPatterns.add(monthlyPattern002);
		MonthlyPatternDto monthlyPattern003 = new MonthlyPatternDto();
		monthlyPattern003.setCode("003");
		monthlyPattern003.setName("003");
		mothlyPatterns.add(monthlyPattern003);
		MonthlyPatternDto monthlyPattern004 = new MonthlyPatternDto();
		monthlyPattern004.setCode("004");
		monthlyPattern004.setName("004");
		mothlyPatterns.add(monthlyPattern004);
		MonthlyPatternDto monthlyPattern005 = new MonthlyPatternDto();
		monthlyPattern005.setCode("005");
		monthlyPattern005.setName("005");
		mothlyPatterns.add(monthlyPattern005);
		MonthlyPatternDto monthlyPattern006 = new MonthlyPatternDto();
		monthlyPattern006.setCode("006");
		monthlyPattern006.setName("006");
		mothlyPatterns.add(monthlyPattern006);
		MonthlyPatternDto monthlyPattern007 = new MonthlyPatternDto();
		monthlyPattern007.setCode("007");
		monthlyPattern007.setName("007");
		mothlyPatterns.add(monthlyPattern007);
		MonthlyPatternDto monthlyPattern008 = new MonthlyPatternDto();
		monthlyPattern008.setCode("008");
		monthlyPattern008.setName("008");
		mothlyPatterns.add(monthlyPattern008);
		MonthlyPatternDto monthlyPattern009 = new MonthlyPatternDto();
		monthlyPattern009.setCode("009");
		monthlyPattern009.setName("009");
		mothlyPatterns.add(monthlyPattern009);
		MonthlyPatternDto monthlyPattern010 = new MonthlyPatternDto();
		monthlyPattern010.setCode("010");
		monthlyPattern010.setName("010");
		mothlyPatterns.add(monthlyPattern010);
		MonthlyPatternDto monthlyPattern011 = new MonthlyPatternDto();
		monthlyPattern011.setCode("011");
		monthlyPattern011.setName("011");
		mothlyPatterns.add(monthlyPattern011);
		return mothlyPatterns;
	}
	
	/**
	 * Find by id.
	 *
	 * @param monthlyPatternCode the monthly pattern code
	 * @return the monthly pattern dto
	 */
	public MonthlyPatternDto findById(String monthlyPatternCode){
		return this.findAll().stream().filter(dto -> dto.getCode().equals(monthlyPatternCode))
				.findFirst().orElse(new MonthlyPatternDto());
	}
}
