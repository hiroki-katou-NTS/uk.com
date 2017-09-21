/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.infra.repository.automaticcalculation;

import nts.uk.ctx.at.schedule.dom.shift.autocalsetting.AutoCalAtrOvertime;
import nts.uk.ctx.at.schedule.dom.shift.autocalsetting.AutoCalSettingGetMemento;
import nts.uk.ctx.at.schedule.dom.shift.autocalsetting.TimeLimitUpperLimitSetting;
import nts.uk.ctx.at.schedule.infra.entity.shift.autocalsetting.KshmtAutoCalSet;

/**
 * The Class JpaAutoCalSettingGetMemento.
 */
public class JpaAutoCalSettingGetMemento implements AutoCalSettingGetMemento {

	/** The entity. */
	private KshmtAutoCalSet entity;

	/**
	 * Instantiates a new jpa auto cal setting get memento.
	 *
	 * @param auto the auto
	 */
	public JpaAutoCalSettingGetMemento(KshmtAutoCalSet auto) {
		this.entity = auto;
	}

	
	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.schedule.dom.shift.autocalsetting.AutoCalSettingGetMemento#getUpLimitOtSet()
	 */
	@Override
	public TimeLimitUpperLimitSetting getUpLimitOtSet() {
		return TimeLimitUpperLimitSetting.valueOf(this.entity.getEarlyMidOtTimeLimit());
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.schedule.dom.shift.autocalsetting.AutoCalSettingGetMemento#getcalAtr()
	 */
	@Override
	public AutoCalAtrOvertime getcalAtr() {
		return AutoCalAtrOvertime.valueOf(this.entity.getEarlyMidOtTimeLimit());
	}

}
