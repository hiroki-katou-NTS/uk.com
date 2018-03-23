/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.find.statutory.worktime.employmentNew;

import lombok.Data;
import nts.uk.ctx.at.shared.app.command.statutory.worktime.common.WorkingTimeSettingDto;
import nts.uk.ctx.at.shared.dom.statutory.worktime.employmentNew.EmpRegularLaborTime;

/**
 * The Class EmployeeRegularWorkHourDto.
 */
@Data
public class EmpRegularWorkHourDto {

	/** The employee id. */
	/** 社員ID. */
	private String employmentCode;
	
	/** The company id. */
	private String companyId;

	/** The working time setting new. */
	/** 会社労働時間設定. */
	private WorkingTimeSettingDto workingTimeSetting;

	public static EmpRegularWorkHourDto fromDomain(EmpRegularLaborTime domain) {
		EmpRegularWorkHourDto dto = new EmpRegularWorkHourDto();
		WorkingTimeSettingDto workingTimeSetting = WorkingTimeSettingDto.fromDomain(domain.getWorkingTimeSet());
		dto.setWorkingTimeSetting(workingTimeSetting);
		dto.setEmploymentCode(domain.getEmploymentCode().v());
		dto.setCompanyId(domain.getCompanyId().v());
		return dto;
	}

}
