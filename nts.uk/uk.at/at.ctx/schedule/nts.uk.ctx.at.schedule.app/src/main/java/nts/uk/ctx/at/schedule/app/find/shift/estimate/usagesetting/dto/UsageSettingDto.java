/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.app.find.shift.estimate.usagesetting.dto;

import lombok.Data;
import nts.uk.ctx.at.schedule.dom.shift.estimate.usagesetting.UsageSettingSetMemento;
import nts.uk.ctx.at.schedule.dom.shift.estimate.usagesetting.UseClassification;
import nts.uk.ctx.at.shared.dom.common.CompanyId;

/**
 * The Class UsageSettingDto.
 */
@Data
public class UsageSettingDto implements UsageSettingSetMemento {

	/** The employment setting. */
	private boolean employmentSetting;

	/** The personal setting. */
	private boolean personalSetting;

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.schedule.dom.shift.estimate.guideline.
	 * CommonGuidelineSettingSetMemento#setCompanyId(nts.uk.ctx.at.shared.dom.
	 * common.CompanyId)
	 */
	@Override
	public void setCompanyId(CompanyId companyId) {
		// Do nothing.
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.schedule.dom.shift.estimate.usagesetting.
	 * UsageSettingSetMemento#setEmploymentSetting(nts.uk.ctx.at.schedule.dom.
	 * shift.estimate.usagesetting.UseClassification)
	 */
	@Override
	public void setEmploymentSetting(UseClassification employmentSetting) {
		this.employmentSetting = (employmentSetting.value == UseClassification.USE.value);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.schedule.dom.shift.estimate.usagesetting.
	 * UsageSettingSetMemento#setPersonalSetting(nts.uk.ctx.at.schedule.dom.
	 * shift.estimate.usagesetting.UseClassification)
	 */
	@Override
	public void setPersonalSetting(UseClassification personalSetting) {
		this.personalSetting = (personalSetting.value == UseClassification.USE.value);
	}

}
