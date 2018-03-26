/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.find.statutory.worktime.companyNew;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.app.command.statutory.worktime.common.WorkingTimeSettingDto;
import nts.uk.ctx.at.shared.dom.statutory.worktime.companyNew.ComTransLaborTime;

/**
 * The Class ComTransLaborTimeDto.
 */
@Getter
@Setter
public class ComTransLaborTimeDto {

	/** The working time setting. */
	/** 会社労働時間設定. */
	private WorkingTimeSettingDto workingTimeSetting;

	/**
	 * From domain.
	 *
	 * @param domain the domain
	 * @return the com trans labor time dto
	 */
	public static ComTransLaborTimeDto fromDomain(ComTransLaborTime domain) {
		ComTransLaborTimeDto dto = new ComTransLaborTimeDto();
		WorkingTimeSettingDto workingTimeSetting = WorkingTimeSettingDto.fromDomain(domain.getWorkingTimeSet());
		dto.setWorkingTimeSetting(workingTimeSetting);
		return dto;
	}
}
