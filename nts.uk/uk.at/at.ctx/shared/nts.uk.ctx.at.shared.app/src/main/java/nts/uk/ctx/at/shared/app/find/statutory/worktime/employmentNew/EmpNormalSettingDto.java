/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.find.statutory.worktime.employmentNew;

import java.util.List;
import java.util.stream.Collectors;

import lombok.Data;
import nts.uk.ctx.at.shared.app.command.statutory.worktime.common.MonthlyUnitDto;
import nts.uk.ctx.at.shared.dom.statutory.worktime.employmentNew.EmpNormalSetting;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class EmpNormalSettingDto.
 */
@Data
public class EmpNormalSettingDto {
	/** The employee id. */
	private String employmentCode;

	/** The year. */
	private int year;
	
	/** The company id. */
	private String companyId;

	/** The statutory setting. */
	private List<MonthlyUnitDto> statutorySetting;

	public static EmpNormalSettingDto fromDomain(EmpNormalSetting domain) {
		EmpNormalSettingDto dto = new EmpNormalSettingDto();
		dto.setYear(domain.getYear().v());
		dto.setCompanyId(AppContexts.user().companyId());
		dto.setEmploymentCode(domain.getEmploymentCode().v());
		
		List<MonthlyUnitDto> monthlyUnitdtos = domain.getStatutorySetting().stream().map(monthly -> {
			return new MonthlyUnitDto(monthly.getMonth().v(), monthly.getMonthlyTime().v());
		}).collect(Collectors.toList());
		dto.setStatutorySetting(monthlyUnitdtos);
		return dto;
	}
}
