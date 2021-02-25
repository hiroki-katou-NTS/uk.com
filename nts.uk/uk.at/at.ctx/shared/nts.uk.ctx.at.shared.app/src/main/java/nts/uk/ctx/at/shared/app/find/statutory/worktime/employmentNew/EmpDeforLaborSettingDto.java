/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.find.statutory.worktime.employmentNew;

import java.util.List;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.ctx.at.shared.app.command.statutory.worktime.common.MonthlyUnitDto;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.monunit.MonthlyWorkTimeSet;


/**
 * The Class EmpDeforLaborSettingDto.
 */

@Data
@AllArgsConstructor
public class EmpDeforLaborSettingDto {

	/** The company id. */
	/** 会社ID. */
	private String companyId;

	/** The employment code. */
	/** 社員ID. */
	private String employmentCode;

	/** The year. */
	/** 年. */
	private int year;

	/** The statutory setting. */
	/** 法定時間. */
	private List<MonthlyUnitDto> statutorySetting;
	
	public static <T extends MonthlyWorkTimeSet> EmpDeforLaborSettingDto with (
			int year, String companyId, String employmentCode, List<T> workTime) {
		
		return new EmpDeforLaborSettingDto(companyId, employmentCode, year, workTime.stream()
				.map(c -> new MonthlyUnitDto(c.getYm().month(), c.getLaborTime().getLegalLaborTime().v()))
				.collect(Collectors.toList()));
	}
}
