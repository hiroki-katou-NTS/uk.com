/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.vacation.setting.retentionyearly;

import nts.uk.ctx.at.shared.dom.vacation.setting.retentionyearly.MaxDaysRetention;
import nts.uk.ctx.at.shared.dom.vacation.setting.retentionyearly.RetentionYearsAmount;
import nts.uk.ctx.at.shared.dom.vacation.setting.retentionyearly.UpperLimitSettingGetMemento;
import nts.uk.ctx.at.shared.infra.entity.vacation.setting.KshmtHdstkSetCom;

/**
 * The Class JpaUpperLimitSettingGetMemento.
 */
public class JpaUpperLimitSettingGetMemento implements UpperLimitSettingGetMemento {
	
	/** The type value. */
	private KshmtHdstkSetCom typeValue;

	/**
	 * Instantiates a new jpa upper limit setting get memento.
	 *
	 * @param typeValue the type value
	 */
	public JpaUpperLimitSettingGetMemento(KshmtHdstkSetCom typeValue) {
		this.typeValue = typeValue;
	}
	
	/*
	 * (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.vacation.setting.retentionyearly.
	 * UpperLimitSettingGetMemento#getRetentionYearsAmount()
	 */
	@Override
	public RetentionYearsAmount getRetentionYearsAmount() {
		return new RetentionYearsAmount((int) this.typeValue.getYearAmount());
	}

	/*
	 * (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.vacation.setting.retentionyearly.
	 * UpperLimitSettingGetMemento#getMaxDaysCumulation()
	 */
	@Override
	public MaxDaysRetention getMaxDaysCumulation() {
		return new MaxDaysRetention((int) this.typeValue.getMaxDaysRetention());
	}

}
