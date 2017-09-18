/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.command.outsideot.setting;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.outsideot.OutsideOTSetting;

@Getter
@Setter
public class OutsideOTSettingSaveCommand {

	/** The setting. */
	private OutsideOTSettingSaveDto setting;
	
	/**
	 * To domain.
	 *
	 * @param companyId the company id
	 * @return the overtime setting
	 */
	public OutsideOTSetting toDomain(String companyId){
		setting.setCompanyId(companyId);
		return new OutsideOTSetting(setting);
	}
}
