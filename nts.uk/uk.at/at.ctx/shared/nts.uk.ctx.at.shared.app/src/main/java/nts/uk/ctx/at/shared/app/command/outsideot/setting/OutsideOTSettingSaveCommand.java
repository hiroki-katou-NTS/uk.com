/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.command.outsideot.setting;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.app.find.outsideot.dto.OutsideOTSettingDto;
import nts.uk.ctx.at.shared.app.find.outsideot.dto.SuperHD60HConMedDto;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.outsideot.OutsideOTSetting;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.outsideot.holiday.SuperHD60HConMed;

@Getter
@Setter
public class OutsideOTSettingSaveCommand {

	/** The setting. */
	private OutsideOTSettingDto setting;
	
//	/** The superholiday convert method. */
//	private SuperHD60HConMedDto superholidayConvertMethod;
	
	/**
	 * To domain setting.
	 *
	 * @param companyId the company id
	 * @return the outside OT setting
	 */
	public OutsideOTSetting toDomainSetting(String companyId){
		return setting.domain();
	}
	
//	/**
//	 * To domain super.
//	 *
//	 * @param companyId the company id
//	 * @return the super HD 60 H con med
//	 */
//	public SuperHD60HConMed toDomainSuper(String companyId) {
//
//		return superholidayConvertMethod.domain();
//	}
}
