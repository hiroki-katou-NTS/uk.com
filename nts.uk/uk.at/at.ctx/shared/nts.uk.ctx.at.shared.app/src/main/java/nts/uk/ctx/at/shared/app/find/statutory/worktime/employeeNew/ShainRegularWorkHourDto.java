/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.find.statutory.worktime.employeeNew;

import lombok.Data;
import nts.uk.ctx.at.shared.app.command.statutory.worktime.common.WorkingTimeSettingDto;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.week.regular.RegularLaborTimeSha;

/**
 * The Class ShainRegularWorkHourDto.
 */

@Data
public class ShainRegularWorkHourDto {

	/** The employee id. */
	/** 社員ID. */
	private String employeeId;
	
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
	public static ShainRegularWorkHourDto fromDomain(RegularLaborTimeSha domain) {
		ShainRegularWorkHourDto dto = new ShainRegularWorkHourDto();
		WorkingTimeSettingDto workingTimeSetting = WorkingTimeSettingDto.fromDomain(domain);
		dto.setWorkingTimeSetting(workingTimeSetting);
		dto.setEmployeeId(domain.getEmpId());
		dto.setCompanyId(domain.getComId());
		return dto;
	}

}
