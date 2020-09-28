/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.find.statutory.worktime.employeeNew;

import lombok.Data;
import nts.uk.ctx.at.shared.app.command.statutory.worktime.common.WorkingTimeSettingDto;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.week.WorkingTimeSetting;

/**
 * The Class ShainSpeDeforLaborHourDto.
 */

@Data
public class ShainSpeDeforLaborHourDto {

	/** The employee id. */
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
	 * @return the shain spe defor labor hour dto
	 */
	public static ShainSpeDeforLaborHourDto fromDomain(String cid, String sid, WorkingTimeSetting domain) {
		ShainSpeDeforLaborHourDto dto = new ShainSpeDeforLaborHourDto();
		WorkingTimeSettingDto workingTimeSetting = WorkingTimeSettingDto.fromDomain(domain);
		dto.setWorkingTimeSetting(workingTimeSetting);
		dto.setEmployeeId(sid);
		dto.setCompanyId(cid);
		return dto;
	}
}
