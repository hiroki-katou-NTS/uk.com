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
 * The Class EmpNormalSettingDto.
 */
@Data
@AllArgsConstructor
public class EmpNormalSettingDto {
	
	/** The employment code. */
	private String employmentCode;

	/** The year. */
	private int year;
	
	/** The company id. */
	private String companyId;

	/** The statutory setting. */
	private List<MonthlyUnitDto> statutorySetting;

	public static <T extends MonthlyWorkTimeSet> EmpNormalSettingDto with (String cid, String employmentCode,
			int year, List<T> workTime) {
		
		return new EmpNormalSettingDto(employmentCode, year, cid, workTime.stream()
				.map(c -> new MonthlyUnitDto(c.getYm().month(), c.getLaborTime().getLegalLaborTime().v()))
				.collect(Collectors.toList()));
	}
}
