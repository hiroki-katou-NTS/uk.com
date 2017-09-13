/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.command.overtime.setting;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.overtime.setting.OvertimeSetting;

@Getter
@Setter
public class OvertimeSettingSaveCommand {

	/** The setting. */
	private OvertimeSettingSaveDto setting;
	
	/**
	 * To domain.
	 *
	 * @param companyId the company id
	 * @return the overtime setting
	 */
	public OvertimeSetting toDomain(String companyId){
		setting.setCompanyId(companyId);
		return new OvertimeSetting(setting);
	}
}
