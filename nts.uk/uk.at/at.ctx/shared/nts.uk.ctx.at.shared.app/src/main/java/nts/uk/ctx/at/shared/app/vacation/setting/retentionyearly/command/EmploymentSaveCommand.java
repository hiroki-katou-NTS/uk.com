/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.vacation.setting.retentionyearly.command;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.app.vacation.setting.retentionyearly.command.dto.EmploymentSettingDto;
import nts.uk.ctx.at.shared.dom.vacation.setting.retentionyearly.EmploymentSetting;

/**
 * Gets the employment setting.
 *
 * @return the employment setting
 */
@Getter

/**
 * Sets the employment setting.
 *
 * @param employmentSetting the new employment setting
 */
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
	public EmploymentSetting toDomain(String companyId) {
		return this.employmentSetting.toDomain(companyId);
	}
}
