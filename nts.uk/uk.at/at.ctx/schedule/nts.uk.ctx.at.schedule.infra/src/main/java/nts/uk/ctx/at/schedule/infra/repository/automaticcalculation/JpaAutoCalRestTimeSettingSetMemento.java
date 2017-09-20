/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.infra.repository.automaticcalculation;

import nts.uk.ctx.at.schedule.dom.shift.autocalsetting.AutoCalRestTimeSettingSetMemento;
import nts.uk.ctx.at.schedule.dom.shift.autocalsetting.AutoCalSetting;
import nts.uk.ctx.at.schedule.infra.entity.shift.autocalsetting.KshmtAutoCalSet;

/**
 * The Class JpaAutoCalRestTimeSettingSetMemento.
 */
public class JpaAutoCalRestTimeSettingSetMemento implements AutoCalRestTimeSettingSetMemento {

	/** The entity. */
	private KshmtAutoCalSet entity;

	/**
	 * Instantiates a new jpa auto cal rest time setting set memento.
	 *
	 * @param entity
	 *            the entity
	 */
	public JpaAutoCalRestTimeSettingSetMemento(KshmtAutoCalSet entity) {
		this.entity = entity;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.schedule.dom.shift.autocalsetting.
	 * AutoCalRestTimeSettingSetMemento#setRestTime(nts.uk.ctx.at.schedule.dom.
	 * shift.autocalsetting.AutoCalSetting)
	 */
	@Override
	public void setRestTime(AutoCalSetting restTime) {
		this.entity.setRestTimeAtr(restTime.getCalAtr().value);
		this.entity.setRestTimeLimit(restTime.getUpLimitOtSet().value);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.schedule.dom.shift.autocalsetting.
	 * AutoCalRestTimeSettingSetMemento#setLateNightTime(nts.uk.ctx.at.schedule.
	 * dom.shift.autocalsetting.AutoCalSetting)
	 */
	@Override
	public void setLateNightTime(AutoCalSetting lateNightTime) {
		this.entity.setLateNightTimeAtr(lateNightTime.getCalAtr().value);
		this.entity.setLateNightTimeLimit(lateNightTime.getUpLimitOtSet().value);
	}

}
