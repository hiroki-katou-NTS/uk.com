/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.find.statutory.worktime.workplaceNew;

import lombok.Data;
import nts.uk.ctx.at.shared.app.command.statutory.worktime.common.WorkingTimeSettingDto;
import nts.uk.ctx.at.shared.dom.statutory.worktime.workplaceNew.WkpTransLaborTime;

/**
 * The Class WkpTransLaborHourDto.
 */
@Data
public class WkpTransLaborHourDto {

	/** The wkp id. */
	private String wkpId;

	/** The company id. */
	private String companyId;

	/** The working time setting. */
	/** 会社労働時間設定. */
	private WorkingTimeSettingDto workingTimeSetting;

	/**
	 * From domain.
	 *
	 * @param domain the domain
	 * @return the wkp trans labor hour dto
	 */
	public static WkpTransLaborHourDto fromDomain(WkpTransLaborTime domain) {
		WkpTransLaborHourDto dto = new WkpTransLaborHourDto();
		WorkingTimeSettingDto workingTimeSetting = WorkingTimeSettingDto.fromDomain(domain.getWorkingTimeSet());
		dto.setWorkingTimeSetting(workingTimeSetting);
		dto.setCompanyId(domain.getCompanyId().v());
		dto.setWkpId(domain.getWorkplaceId().v());
		return dto;
	}
}
