/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.find.statutory.worktime.companyNew;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import lombok.Data;
import nts.uk.ctx.at.shared.app.command.statutory.worktime.common.MonthlyUnitDto;
import nts.uk.ctx.at.shared.dom.statutory.worktime.companyNew.ComNormalSetting;
import nts.uk.ctx.at.shared.dom.statutory.worktime.sharedNew.MonthlyUnit;

/**
 * The Class ComNormalSettingDto.
 */

@Data
public class ComNormalSettingDto {

	/** The year. */
	/** 年. */
	private int year;

	/** The statutory setting. */
	/** 法定時間. */
	private List<MonthlyUnitDto> statutorySetting;

	/**
	 * From domain.
	 *
	 * @param domain the domain
	 * @return the com normal setting dto
	 */
	public static ComNormalSettingDto fromDomain(ComNormalSetting domain) {
		ComNormalSettingDto dto = new ComNormalSettingDto();
		dto.setYear(domain.getYear().v());
		Function<MonthlyUnit, MonthlyUnitDto> funMap  = monthly -> {
			return new MonthlyUnitDto(monthly.getMonth().v(), monthly.getMonthlyTime().v());
		};
		List<MonthlyUnitDto> statutorySetting = domain.getStatutorySetting().stream().map(funMap).collect(Collectors.toList());
		dto.setStatutorySetting(statutorySetting);
		return dto;
	}
	
}
