/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.find.statutory.worktime.workplaceNew;

import lombok.Data;
import nts.uk.ctx.at.shared.app.command.statutory.worktime.common.WorkingTimeSettingDto;
import nts.uk.ctx.at.shared.dom.statutory.worktime.workplaceNew.WkpRegularLaborTime;

/**
 * The Class WkpRegularWorkHourDto.
 */

@Data
public class WkpRegularWorkHourDto {

	/** The wkp id. */
	/** 社員ID. */
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
	 * @return the wkp regular work hour dto
	 */
	public static WkpRegularWorkHourDto fromDomain(WkpRegularLaborTime domain) {
		WkpRegularWorkHourDto dto = new WkpRegularWorkHourDto();
		WorkingTimeSettingDto workingTimeSetting = WorkingTimeSettingDto.fromDomain(domain.getWorkingTimeSet());
		dto.setWorkingTimeSetting(workingTimeSetting);
		dto.setWkpId(domain.getWorkplaceId().v());
		dto.setCompanyId(domain.getCompanyId().v());
		return dto;
	}

}
