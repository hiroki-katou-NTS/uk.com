/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.find.statutory.worktime.employmentNew;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import lombok.Data;
import nts.uk.ctx.at.shared.app.command.statutory.worktime.common.MonthlyUnitDto;
import nts.uk.ctx.at.shared.dom.statutory.worktime.employmentNew.EmpFlexSetting;
import nts.uk.ctx.at.shared.dom.statutory.worktime.sharedNew.MonthlyUnit;
import nts.uk.shr.com.context.AppContexts;


/**
 * The Class EmpFlexSettingDto.
 */

@Data
public class EmpFlexSettingDto{
	
	/** The employment code. */
	/** 社員ID. */
	private String employmentCode;
	
	/** The year. */
	/** 年. */
	private int year;
	
	/** The company id. */
	private String companyId;
	
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
	 * @return the emp flex setting dto
	 */
	public static EmpFlexSettingDto fromDomain(EmpFlexSetting domain) {
		EmpFlexSettingDto dto = new EmpFlexSettingDto();
		dto.setYear(domain.getYear().v());
		dto.setCompanyId(AppContexts.user().companyId());
		dto.setEmploymentCode(domain.getEmploymentCode().v());
		
		Function<MonthlyUnit, MonthlyUnitDto> funMap = monthly -> {
			return new MonthlyUnitDto(monthly.getMonth().v(), monthly.getMonthlyTime().v());
		};
		
		List<MonthlyUnitDto> statutoryMonthlys = domain.getStatutorySetting().stream().map(funMap).collect(Collectors.toList());
		dto.setStatutorySetting(statutoryMonthlys);
		
		List<MonthlyUnitDto> specMonthlys = domain.getSpecifiedSetting().stream().map(funMap).collect(Collectors.toList());
		dto.setSpecifiedSetting(specMonthlys);
		return dto;
	}
}
