/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.vacation.setting.retentionyearly;

import nts.uk.ctx.at.shared.dom.vacation.setting.retentionyearly.MaxDaysRetention;
import nts.uk.ctx.at.shared.dom.vacation.setting.retentionyearly.RetentionYearsAmount;
import nts.uk.ctx.at.shared.dom.vacation.setting.retentionyearly.UpperLimitSettingSetMemento;
import nts.uk.ctx.at.shared.infra.entity.vacation.setting.KshmtHdstkSetEmp;

/**
 * The Class JpaUpperLimitEmpSetMemento.
 */
public class JpaUpperLimitEmpSetMemento implements UpperLimitSettingSetMemento {
	
/** The type value. */
private KshmtHdstkSetEmp typeValue;
	
	/**
	 * Instantiates a new jpa upper limit emp set memento.
	 *
	 * @param typeValue the type value
	 */
	public JpaUpperLimitEmpSetMemento(KshmtHdstkSetEmp typeValue) {
		this.typeValue = typeValue;
	}

	/*
	 * (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.vacation.setting.retentionyearly.
	 * UpperLimitSettingSetMemento#setRetentionYearsAmount(nts.uk.ctx.at.shared.
	 * dom.vacation.setting.retentionyearly.RetentionYearsAmount)
	 */
	@Override
	public void setRetentionYearsAmount(RetentionYearsAmount retentionYearsAmount) {
		this.typeValue.setYearAmount(retentionYearsAmount.v().shortValue());
	}

	/*
	 * (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.vacation.setting.retentionyearly.
	 * UpperLimitSettingSetMemento#setMaxDaysRetention(nts.uk.ctx.at.shared.dom.
	 * vacation.setting.retentionyearly.MaxDaysRetention)
	 */
	@Override
	public void setMaxDaysRetention(MaxDaysRetention maxDaysCumulation) {
		this.typeValue.setMaxDaysRetention(maxDaysCumulation.v().shortValue());
	}

}
