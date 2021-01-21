/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.find.statutory.worktime.companyNew;

import java.util.List;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.ctx.at.shared.app.command.statutory.worktime.common.MonthlyUnitDto;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.monunit.MonthlyWorkTimeSet;

/**
 * The Class ComDeforLaborSettingDto.
 */

@Data
@AllArgsConstructor
public class ComDeforLaborSettingDto {

	/** The year. */
	/** 年. */
	private int year;
	
	/** The company id. */
	private String companyId;

	/** The statutory setting. */
	/** 法定時間. */
	private List<MonthlyUnitDto> statutorySetting;

	public static <T extends MonthlyWorkTimeSet> ComDeforLaborSettingDto with (int year, String companyId, List<T> workTime) {
		
		return new ComDeforLaborSettingDto(year, companyId, workTime.stream()
				.map(c -> new MonthlyUnitDto(c.getYm().month(), c.getLaborTime().getLegalLaborTime().v()))
				.collect(Collectors.toList()));
	}
}
