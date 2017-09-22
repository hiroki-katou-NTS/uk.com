/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.infra.repository.automaticcalculation;

import nts.uk.ctx.at.schedule.dom.shift.autocalsetting.AutoCalAtrOvertime;
import nts.uk.ctx.at.schedule.dom.shift.autocalsetting.AutoCalSettingSetMemento;
import nts.uk.ctx.at.schedule.dom.shift.autocalsetting.TimeLimitUpperLimitSetting;
import nts.uk.ctx.at.schedule.infra.entity.shift.autocalsetting.KshmtAutoCalSet;

public class JpaAutoCalSettingSetMemento implements AutoCalSettingSetMemento {
	/** The entity. */
	private KshmtAutoCalSet entity;
	
	/**
	 * Instantiates a new jpa auto cal overtime setting set memento.
	 *
	 * @param entity the entity
	 */
	public JpaAutoCalSettingSetMemento(KshmtAutoCalSet entity) {
		this.entity = entity;
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.schedule.dom.shift.autocalsetting.AutoCalSettingSetMemento#setTimeLimitUpperLimitSetting(nts.uk.ctx.at.schedule.dom.shift.autocalsetting.TimeLimitUpperLimitSetting)
	 */
	@Override
	public void setTimeLimitUpperLimitSetting(TimeLimitUpperLimitSetting timeLimitUpperLimitSetting) {
		this.entity.setEarlyOtTimeAtr(timeLimitUpperLimitSetting.value);
		
	}

	@Override
	public void setAutoCalAtrOvertime(AutoCalAtrOvertime autoCalAtrOvertime) {
		this.entity.setEarlyOtTimeAtr(autoCalAtrOvertime.value);
		
	}

}
