/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.vacation.setting.retentionyearly;

import nts.uk.ctx.at.shared.dom.vacation.setting.retentionyearly.MaxDaysRetention;
import nts.uk.ctx.at.shared.dom.vacation.setting.retentionyearly.RetentionYearsAmount;
import nts.uk.ctx.at.shared.dom.vacation.setting.retentionyearly.UpperLimitSettingGetMemento;
import nts.uk.ctx.at.shared.infra.entity.vacation.setting.KshmtHdstkSetEmp;

/**
 * The Class JpaUpperLimitEmpGetMemento.
 */
public class JpaUpperLimitEmpGetMemento implements UpperLimitSettingGetMemento {

	/** The type value. */
	private KshmtHdstkSetEmp typeValue;
	
	/**
	 * Instantiates a new jpa upper limit emp get memento.
	 *
	 * @param typeValue the type value
	 */
	public JpaUpperLimitEmpGetMemento(KshmtHdstkSetEmp typeValue) {
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
