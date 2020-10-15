/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.find.statutory.worktime.companyNew;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.ctx.at.shared.app.command.statutory.worktime.common.MonthlyUnitDto;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.monunit.MonthlyWorkTimeSet;

/**
 * The Class ComFlexSettingDto.
 */

/**
 * Instantiates a new com flex setting dto.
 */
@Data
@AllArgsConstructor
public class ComFlexSettingDto {

	/** 年. */
	private int year;

	/** 法定時間. */
	private List<MonthlyUnitDto> statutorySetting;

	/** 所定時間. */
	private List<MonthlyUnitDto> specifiedSetting;

	/** 週平均時間 */
	private List<MonthlyUnitDto> weekAvgSetting;
	
	public static <T extends MonthlyWorkTimeSet> ComFlexSettingDto with (int year, List<T> workTime) {
		
		ComFlexSettingDto dto = new ComFlexSettingDto(year, new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
		
		workTime.stream().forEach(wt -> {
			
			dto.getStatutorySetting().add(new MonthlyUnitDto(wt.getYm().month(), wt.getLaborTime().getLegalLaborTime().v()));
			dto.getSpecifiedSetting().add(new MonthlyUnitDto(wt.getYm().month(), wt.getLaborTime().getWithinLaborTime().get().v()));
			dto.getWeekAvgSetting().add(new MonthlyUnitDto(wt.getYm().month(), wt.getLaborTime().getWeekAvgTime().get().v()));
		});
		
		return dto;
	}
}
