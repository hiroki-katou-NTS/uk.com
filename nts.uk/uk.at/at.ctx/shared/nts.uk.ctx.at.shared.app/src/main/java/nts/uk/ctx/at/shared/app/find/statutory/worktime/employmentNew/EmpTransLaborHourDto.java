/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.find.statutory.worktime.employmentNew;

import lombok.Data;
import nts.uk.ctx.at.shared.app.command.statutory.worktime.common.WorkingTimeSettingDto;
import nts.uk.ctx.at.shared.dom.statutory.worktime.employmentNew.EmpTransLaborTime;

/**
 * The Class EmpTransLaborHourDto.
 */

@Data
public class EmpTransLaborHourDto {

	/** The employment code. */
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
	 * @return the emp trans labor hour dto
	 */
	public static EmpTransLaborHourDto fromDomain(EmpTransLaborTime domain) {
		EmpTransLaborHourDto dto = new EmpTransLaborHourDto();
		WorkingTimeSettingDto workingTimeSetting = WorkingTimeSettingDto.fromDomain(domain.getWorkingTimeSet());
		dto.setWorkingTimeSetting(workingTimeSetting);
		dto.setCompanyId(domain.getCompanyId().v());
		dto.setEmploymentCode(domain.getEmploymentCode().v());
		return dto;
	}
}
