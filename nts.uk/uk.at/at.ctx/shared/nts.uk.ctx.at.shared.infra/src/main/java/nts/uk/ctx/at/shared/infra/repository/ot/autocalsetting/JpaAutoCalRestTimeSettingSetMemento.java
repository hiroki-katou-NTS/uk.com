/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.ot.autocalsetting;

import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.autocalsetting.AutoCalRestTimeSettingSetMemento;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.autocalsetting.AutoCalSetting;
import nts.uk.ctx.at.shared.infra.entity.ot.autocalsetting.KshmtAutoCalSet;

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
		this.entity.setRestTimeLimit(restTime.getUpLimitORtSet().value);
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
		this.entity.setLateNightTimeLimit(lateNightTime.getUpLimitORtSet().value);
	}

}
