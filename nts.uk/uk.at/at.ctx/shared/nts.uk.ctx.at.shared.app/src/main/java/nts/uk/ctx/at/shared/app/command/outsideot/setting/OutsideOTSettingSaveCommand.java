/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.command.outsideot.setting;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.app.command.outsideot.holiday.SuperHD60HConMedSaveDto;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.outsideot.OutsideOTSetting;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.outsideot.holiday.SuperHD60HConMed;

@Getter
@Setter
public class OutsideOTSettingSaveCommand {

	/** The setting. */
	private OutsideOTSettingSaveDto setting;
	
	/** The superholiday convert method. */
	private SuperHD60HConMedSaveDto superholidayConvertMethod;
	
	/**
	 * To domain setting.
	 *
	 * @param companyId the company id
	 * @return the outside OT setting
	 */
	public OutsideOTSetting toDomainSetting(String companyId){
		setting.setCompanyId(companyId);
		return new OutsideOTSetting(setting);
	}
	
	/**
	 * To domain super.
	 *
	 * @param companyId the company id
	 * @return the super HD 60 H con med
	 */
	public SuperHD60HConMed toDomainSuper(String companyId) {
		superholidayConvertMethod.setCompanyId(companyId);
		return new SuperHD60HConMed(superholidayConvertMethod);
	}
}
