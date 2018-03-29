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
import nts.uk.ctx.at.shared.dom.statutory.worktime.companyNew.ComFlexSetting;
import nts.uk.ctx.at.shared.dom.statutory.worktime.sharedNew.MonthlyUnit;

/**
 * The Class ComFlexSettingDto.
 */

/**
 * Instantiates a new com flex setting dto.
 */
@Data
public class ComFlexSettingDto {

	/** The year. */
	/** 年. */
	private int year;

	/** The statutory setting. */
	/** 法定時間. */
	private List<MonthlyUnitDto> statutorySetting;

	/** The specified setting. */
	/** 所定時間. */
	private List<MonthlyUnitDto> specifiedSetting;

	/**
	 * From domain.
	 *
	 * @param domain the domain
	 * @return the com flex setting dto
	 */
	public static ComFlexSettingDto fromDomain(ComFlexSetting domain) {
		ComFlexSettingDto dto = new ComFlexSettingDto();
		dto.setYear(domain.getYear().v());
		
		Function<MonthlyUnit, MonthlyUnitDto> funMap  = monthly -> {
			return new MonthlyUnitDto(monthly.getMonth().v(), monthly.getMonthlyTime().v());
		};
		List<MonthlyUnitDto> statutorySetting = domain.getStatutorySetting().stream().map(funMap).collect(Collectors.toList());
		List<MonthlyUnitDto> specifiedSetting = domain.getSpecifiedSetting().stream().map(funMap).collect(Collectors.toList());
		
		dto.setStatutorySetting(statutorySetting);
		dto.setSpecifiedSetting(specifiedSetting);
		return dto;
	}
}
