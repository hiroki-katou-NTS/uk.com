/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.command.outsideot.holiday;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.outsideot.holiday.SuperHD60HConMed;

/**
 * The Class SuperHD60HConMedSaveCommand.
 */
@Getter
@Setter
public class SuperHD60HConMedSaveCommand {
	private SuperHD60HConMedSaveDto setting;
	
	/**
	 * To domain.
	 *
	 * @param companyId the company id
	 * @return the super HD 60 H con med
	 */
	public SuperHD60HConMed toDomain(String companyId){
		this.setting.setCompanyId(companyId);
		return new SuperHD60HConMed(this.setting);
	}
}
