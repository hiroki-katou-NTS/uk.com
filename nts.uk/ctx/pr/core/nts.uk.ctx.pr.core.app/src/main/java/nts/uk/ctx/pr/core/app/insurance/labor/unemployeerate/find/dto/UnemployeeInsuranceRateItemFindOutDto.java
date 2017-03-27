/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.app.insurance.labor.unemployeerate.find.dto;

import lombok.Data;
import nts.uk.ctx.pr.core.dom.insurance.labor.unemployeerate.CareerGroup;
import nts.uk.ctx.pr.core.dom.insurance.labor.unemployeerate.UnemployeeInsuranceRateItemSetMemento;
import nts.uk.ctx.pr.core.dom.insurance.labor.unemployeerate.UnemployeeInsuranceRateItemSetting;

/**
 * The Class UnemployeeInsuranceRateItemDto.
 */
@Data
public class UnemployeeInsuranceRateItemFindOutDto implements UnemployeeInsuranceRateItemSetMemento {
	/** The career group. */
	private Integer careerGroup;

	/** The company setting. */
	private UnemployeeInsuranceRateItemSettingFindOutDto companySetting;

	/** The personal setting. */
	private UnemployeeInsuranceRateItemSettingFindOutDto personalSetting;

	@Override
	public void setCareerGroup(CareerGroup careerGroup) {
		// TODO Auto-generated method stub
		this.careerGroup = careerGroup.value;
	}

	@Override
	public void setCompanySetting(UnemployeeInsuranceRateItemSetting companySetting) {
		// TODO Auto-generated method stub
		if (this.companySetting == null)
			this.companySetting = new UnemployeeInsuranceRateItemSettingFindOutDto();
		this.companySetting.setRate(companySetting.getRate());
		this.companySetting.setRoundAtr(companySetting.getRoundAtr().value);
	}

	@Override
	public void setPersonalSetting(UnemployeeInsuranceRateItemSetting personalSetting) {
		// TODO Auto-generated method stub
		if (this.personalSetting == null)
			this.personalSetting = new UnemployeeInsuranceRateItemSettingFindOutDto();
		this.personalSetting.setRate(personalSetting.getRate());
		this.personalSetting.setRoundAtr(personalSetting.getRoundAtr().value);
	}
}
