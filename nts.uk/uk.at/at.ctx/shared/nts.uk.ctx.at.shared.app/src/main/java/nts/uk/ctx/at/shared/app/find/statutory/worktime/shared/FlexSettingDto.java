/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.find.statutory.worktime.shared;

import java.util.List;
import java.util.stream.Collectors;

import lombok.Data;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.monunit.MonthlyWorkTimeSet;

/**
 * The Class FlexSettingDto.
 */

/**
 * Instantiates a new flex setting dto.
 */
@Data
public class FlexSettingDto {

	/** The flex daily. */
	private FlexDaily flexDaily;

	/** The flex monthly. */
	private List<FlexMonth> flexMonthly;
	
	public static <T extends MonthlyWorkTimeSet> FlexSettingDto with(List<T> flex) {
		
		FlexSettingDto dto = new FlexSettingDto();
		
		dto.setFlexDaily(new FlexDaily());
		dto.setFlexMonthly(flex.stream().map(c -> new FlexMonth(
												c.getYm().month(), 
												c.getLaborTime().getLegalLaborTime().v(),
												c.getLaborTime().getWithinLaborTime().get().v(),
												c.getLaborTime().getWeekAvgTime().get().v()))
				.collect(Collectors.toList()));
		
		return dto;
	}
}
