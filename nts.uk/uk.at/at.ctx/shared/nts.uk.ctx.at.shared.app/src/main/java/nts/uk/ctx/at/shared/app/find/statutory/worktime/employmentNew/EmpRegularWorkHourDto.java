/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.find.statutory.worktime.employmentNew;

import lombok.Data;
import nts.uk.ctx.at.shared.app.command.statutory.worktime.common.WorkingTimeSettingDto;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.week.regular.RegularLaborTimeEmp;

/**
 * The Class EmpRegularWorkHourDto.
 */

@Data
public class EmpRegularWorkHourDto {

	/** The employment code. */
	/** 社員ID. */
	private String employmentCode;
	
	/** The company id. */
	private String companyId;

	/** The working time setting. */
	/** 会社労働時間設定. */
	private WorkingTimeSettingDto workingTimeSetting;

	/**
	 * From domain.
	 *
	 * @param domain the domain
	 * @return the shain regular work hour dto
	 */
	public static EmpRegularWorkHourDto fromDomain(RegularLaborTimeEmp domain) {
		EmpRegularWorkHourDto dto = new EmpRegularWorkHourDto();
		WorkingTimeSettingDto workingTimeSetting = WorkingTimeSettingDto.fromDomain(domain);
		dto.setWorkingTimeSetting(workingTimeSetting);
		dto.setEmploymentCode(domain.getEmploymentCode().v());
		dto.setCompanyId(domain.getComId());
		return dto;
	}

}
