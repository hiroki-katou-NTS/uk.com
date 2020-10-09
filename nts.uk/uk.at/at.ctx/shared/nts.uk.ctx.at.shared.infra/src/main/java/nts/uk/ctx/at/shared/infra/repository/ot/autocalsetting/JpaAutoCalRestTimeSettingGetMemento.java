/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.ot.autocalsetting;

import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.autocalsetting.AutoCalAtrOvertime;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.autocalsetting.AutoCalRestTimeSettingGetMemento;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.autocalsetting.AutoCalSetting;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.autocalsetting.TimeLimitUpperLimitSetting;
import nts.uk.ctx.at.shared.infra.entity.ot.autocalsetting.KshmtAutoCalSet;

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
		return new AutoCalSetting(TimeLimitUpperLimitSetting.valueOf(this.entity.getRestTimeLimit()),
				AutoCalAtrOvertime.valueOf(this.entity.getRestTimeAtr()));
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.schedule.dom.shift.autocalsetting.AutoCalRestTimeSettingGetMemento#getLateNightTime()
	 */
	@Override
	public AutoCalSetting getLateNightTime() {
		return new AutoCalSetting(TimeLimitUpperLimitSetting.valueOf(this.entity.getLateNightTimeLimit()),
				AutoCalAtrOvertime.valueOf(this.entity.getLateNightTimeAtr()));
	}

}
