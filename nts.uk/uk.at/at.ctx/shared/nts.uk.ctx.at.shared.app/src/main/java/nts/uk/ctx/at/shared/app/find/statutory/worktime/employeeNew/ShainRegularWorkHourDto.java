/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.find.statutory.worktime.employeeNew;

import lombok.Data;
import nts.uk.ctx.at.shared.app.command.statutory.worktime.common.WorkingTimeSettingDto;
import nts.uk.ctx.at.shared.dom.statutory.worktime.employeeNew.ShainRegularWorkTime;

/**
 * The Class EmployeeRegularWorkHourDto.
 */
@Data
public class ShainRegularWorkHourDto {

	/** The employee id. */
	/** 社員ID. */
	private String employeeId;
	
	/** The company id. */
	private String companyId;

	/** The working time setting new. */
	/** 会社労働時間設定. */
	private WorkingTimeSettingDto workingTimeSetting;

	public static ShainRegularWorkHourDto fromDomain(ShainRegularWorkTime domain) {
		ShainRegularWorkHourDto dto = new ShainRegularWorkHourDto();
		WorkingTimeSettingDto workingTimeSetting = WorkingTimeSettingDto.fromDomain(domain.getWorkingTimeSet());
		dto.setWorkingTimeSetting(workingTimeSetting);
		return dto;
	}

}
