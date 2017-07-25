/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.command.vacation.setting.retentionyearly;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.app.command.vacation.setting.retentionyearly.dto.EmploymentSettingDto;
import nts.uk.ctx.at.shared.dom.vacation.setting.retentionyearly.EmptYearlyRetentionSetting;

@Getter
@Setter
public class EmploymentSaveCommand {
	
	/** The employment setting. */
	private EmploymentSettingDto employmentSetting;
	
	/**
	 * To domain.
	 *
	 * @param companyId the company id
	 * @return the employment setting
	 */
	public EmptYearlyRetentionSetting toDomain(String companyId) {
		return this.employmentSetting.toDomain(companyId);
	}
}
