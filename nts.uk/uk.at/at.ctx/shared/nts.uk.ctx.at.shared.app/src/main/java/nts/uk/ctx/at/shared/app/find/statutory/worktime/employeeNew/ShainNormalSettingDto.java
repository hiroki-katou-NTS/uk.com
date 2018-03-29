/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.find.statutory.worktime.employeeNew;

import java.util.List;
import java.util.stream.Collectors;

import lombok.Data;
import nts.uk.ctx.at.shared.app.command.statutory.worktime.common.MonthlyUnitDto;
import nts.uk.ctx.at.shared.dom.statutory.worktime.employeeNew.ShainNormalSetting;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class ShainNormalSettingDto.
 */

@Data
public class ShainNormalSettingDto {
	
	/** The employee id. */
	private String employeeId;

	/** The year. */
	private int year;
	
	/** The company id. */
	private String companyId;

	/** The statutory setting. */
	private List<MonthlyUnitDto> statutorySetting;

	/**
	 * From domain.
	 *
	 * @param domain the domain
	 * @return the shain normal setting dto
	 */
	public static ShainNormalSettingDto fromDomain(ShainNormalSetting domain) {
		ShainNormalSettingDto dto = new ShainNormalSettingDto();
		dto.setYear(domain.getYear().v());
		dto.setCompanyId(AppContexts.user().companyId());
		dto.setEmployeeId(domain.getEmployeeId().v());
		
		List<MonthlyUnitDto> monthlyUnitdtos = domain.getStatutorySetting().stream().map(monthly -> {
			return new MonthlyUnitDto(monthly.getMonth().v(), monthly.getMonthlyTime().v());
		}).collect(Collectors.toList());
		dto.setStatutorySetting(monthlyUnitdtos);
		return dto;
	}
}
