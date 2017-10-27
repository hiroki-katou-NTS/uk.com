/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.command.vacation.setting.retentionyearly;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.app.command.vacation.setting.retentionyearly.dto.RetentionYearlyDto;
import nts.uk.ctx.at.shared.dom.vacation.setting.retentionyearly.RetentionYearlySetting;


/**
 * The Class RetentionYearlySaveCommand.
 */
@Getter
@Setter
public class RetentionYearlySaveCommand {

	/** The retention yearly. */
	private RetentionYearlyDto retentionYearly;
	
	/**
	 * To domain.
	 *
	 * @param companyId the company id
	 * @return the retention yearly setting
	 */
	public RetentionYearlySetting toDomain(String companyId) {
		return this.retentionYearly.toDomain(companyId);
	}
}
