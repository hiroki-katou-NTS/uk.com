/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.app.find.shift.pattern;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.schedule.app.find.shift.pattern.dto.WorkMonthlySettingDto;
import nts.uk.ctx.at.schedule.dom.shift.pattern.MonthlyPatternCode;

/**
 * The Class WorkMonthlySettingFinder.
 */
@Stateless
public class WorkMonthlySettingFinder {
	
	public List<WorkMonthlySettingDto> findById(String monthlyPatternCode){
		List<WorkMonthlySettingDto> workMonthlySettings = new ArrayList<>();
		for (int i = 0; i <= 10; i++) {
			WorkMonthlySettingDto dto = new WorkMonthlySettingDto();
			dto.setCode(monthlyPatternCode);
			dto.setDate(GeneralDate.ymd(2017, 05, 07));
			dto.setMonthlyPatternCode(new MonthlyPatternCode(monthlyPatternCode));
			workMonthlySettings.add(dto);
		}
		
		return workMonthlySettings;
	}

}
