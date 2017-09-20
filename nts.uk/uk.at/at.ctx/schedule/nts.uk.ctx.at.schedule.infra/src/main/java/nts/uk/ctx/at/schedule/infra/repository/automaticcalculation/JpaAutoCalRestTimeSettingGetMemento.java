/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.infra.repository.automaticcalculation;

import nts.uk.ctx.at.schedule.dom.shift.autocalsetting.AutoCalRestTimeSettingGetMemento;
import nts.uk.ctx.at.schedule.dom.shift.autocalsetting.AutoCalSetting;
import nts.uk.ctx.at.schedule.infra.entity.shift.autocalsetting.KshmtAutoCalSet;

/**
 * The Class JpaAutoCalRestTimeSettingGetMemento.
 */
public class JpaAutoCalRestTimeSettingGetMemento implements AutoCalRestTimeSettingGetMemento{

	/** The entity. */
	private KshmtAutoCalSet entity;
	

	/**
	 * Instantiates a new jpa auto cal rest time setting get memento.
	 *
	 * @param entity the entity
	 */
	public JpaAutoCalRestTimeSettingGetMemento(KshmtAutoCalSet entity) {
		this.entity = entity;
	}
	
	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.schedule.dom.shift.autocalsetting.AutoCalRestTimeSettingGetMemento#getRestTime()
	 */
	@Override
	public AutoCalSetting getRestTime() {
		return new AutoCalSetting(new JpaAutoCalSettingGetMemento(this.entity));
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.schedule.dom.shift.autocalsetting.AutoCalRestTimeSettingGetMemento#getLateNightTime()
	 */
	@Override
	public AutoCalSetting getLateNightTime() {
		return new AutoCalSetting(new JpaAutoCalSettingGetMemento(this.entity));
	}

}
