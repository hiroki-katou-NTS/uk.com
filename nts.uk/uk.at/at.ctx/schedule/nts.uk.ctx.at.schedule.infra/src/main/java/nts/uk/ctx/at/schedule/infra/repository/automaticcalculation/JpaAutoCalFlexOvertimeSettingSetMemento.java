/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.infra.repository.automaticcalculation;

import nts.uk.ctx.at.schedule.dom.shift.autocalsetting.AutoCalFlexOvertimeSettingSetMemento;
import nts.uk.ctx.at.schedule.dom.shift.autocalsetting.AutoCalSetting;
import nts.uk.ctx.at.schedule.infra.entity.shift.autocalsetting.KshmtAutoComCalSet;

public class JpaAutoCalFlexOvertimeSettingSetMemento implements AutoCalFlexOvertimeSettingSetMemento {

	/** The entity. */
	private KshmtAutoComCalSet entity;
	
	/**
	 * Instantiates a new jpa total times set memento.
	 *
	 * @param totalTimes
	 *            the total times
	 */
	public JpaAutoCalFlexOvertimeSettingSetMemento(KshmtAutoComCalSet auto) {
		this.entity = auto;
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.schedule.dom.shift.autocalsetting.AutoCalFlexOvertimeSettingSetMemento#setFlexOtTime(nts.uk.ctx.at.schedule.dom.shift.autocalsetting.AutoCalSetting)
	 */
	@Override
	public void setFlexOtTime(AutoCalSetting flexOtTime) {
		this.entity.setFlexOtTimeAtr(flexOtTime.getCalAtr().value);
		this.entity.setFlexOtTimeLimit(flexOtTime.getCalAtr().value);
	}

	@Override
	public void setFlexOtNightTime(AutoCalSetting flexOtNightTime) {
		this.entity.setFlexOtNightTimeAtr(flexOtNightTime.getCalAtr().value);
		this.entity.setFlexOtNightTimeLimit(flexOtNightTime.getUpLimitOtSet().value);
	}
}
