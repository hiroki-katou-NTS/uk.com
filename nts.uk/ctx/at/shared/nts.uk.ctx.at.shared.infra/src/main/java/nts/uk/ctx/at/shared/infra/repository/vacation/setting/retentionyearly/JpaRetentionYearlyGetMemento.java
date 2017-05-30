/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.vacation.setting.retentionyearly;

import nts.uk.ctx.at.shared.dom.vacation.setting.retentionyearly.RetentionYearlySettingGetMemento;
import nts.uk.ctx.at.shared.dom.vacation.setting.retentionyearly.UpperLimitSetting;
import nts.uk.ctx.at.shared.infra.entity.vacation.setting.KmfmtRetentionYearly;

/**
 * The Class JpaRetentionYearlyGetMemento.
 */
public class JpaRetentionYearlyGetMemento implements RetentionYearlySettingGetMemento {

	/** The type value. */
	private KmfmtRetentionYearly typeValue;
	
	/**
	 * Instantiates a new jpa retention yearly get memento.
	 *
	 * @param typeValue the type value
	 */
	public JpaRetentionYearlyGetMemento(KmfmtRetentionYearly typeValue) {
		this.typeValue = typeValue;
	}
	
	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.vacation.setting.retentionyearly.RetentionYearlySettingGetMemento#getCompanyId()
	 */
	@Override
	public String getCompanyId() {
		return this.typeValue.getCid();
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.vacation.setting.retentionyearly.RetentionYearlySettingGetMemento#getUpperLimitSetting()
	 */
	@Override
	public UpperLimitSetting getUpperLimitSetting() {
		return new UpperLimitSetting(new JpaUpperLimitSettingGetMemento(this.typeValue));
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.vacation.setting.retentionyearly.RetentionYearlySettingGetMemento#getCanAddToCumulationYearlyAsNormalWorkDay()
	 */
	@Override
	public Boolean getCanAddToCumulationYearlyAsNormalWorkDay() {
		// TODO Auto-generated method stub
		return null;
	}

}
