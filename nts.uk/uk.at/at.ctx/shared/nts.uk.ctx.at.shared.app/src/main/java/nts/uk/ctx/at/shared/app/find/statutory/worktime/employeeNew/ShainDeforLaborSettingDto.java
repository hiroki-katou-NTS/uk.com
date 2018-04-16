/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.find.statutory.worktime.employeeNew;

import java.util.List;
import java.util.stream.Collectors;

import lombok.Data;
import nts.uk.ctx.at.shared.app.command.statutory.worktime.common.MonthlyUnitDto;
import nts.uk.ctx.at.shared.dom.statutory.worktime.employeeNew.ShainDeforLaborSetting;
import nts.uk.shr.com.context.AppContexts;


/**
 * The Class ShainDeforLaborSettingDto.
 */
@Data
public class ShainDeforLaborSettingDto {

	/** The company id. */
	/** 会社ID. */
	private String companyId;

	/** The employee id. */
	/** 社員ID. */
	private String employeeId;

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
	 * @return the shain defor labor setting dto
	 */
	public static ShainDeforLaborSettingDto fromDomain(ShainDeforLaborSetting domain) {
		ShainDeforLaborSettingDto dto = new ShainDeforLaborSettingDto();
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
