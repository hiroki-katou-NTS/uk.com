/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.infra.repository.automaticcalculation;

import nts.uk.ctx.at.schedule.dom.shift.autocalsetting.AutoCalOvertimeSettingGetMemento;
import nts.uk.ctx.at.schedule.dom.shift.autocalsetting.AutoCalSetting;
import nts.uk.ctx.at.schedule.infra.entity.shift.autocalsetting.KshmtAutoCalSet;

/**
 * The Class JpaAutoCalOvertimeSettingGetMemento.
 */
public class JpaAutoCalOvertimeSettingGetMemento implements AutoCalOvertimeSettingGetMemento {

	/** The entity. */
	private KshmtAutoCalSet entity;
	

	/**
	 * Instantiates a new jpa auto cal overtime setting get memento.
	 *
	 * @param company the company
	 * @param entity the entity
	 */
	public JpaAutoCalOvertimeSettingGetMemento(KshmtAutoCalSet entity) {
		this.entity = entity;
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.schedule.dom.shift.autocalsetting.AutoCalOvertimeSettingGetMemento#getEarlyOtTime()
	 */
	@Override
	public AutoCalSetting getEarlyOtTime() {
		return new AutoCalSetting(new JpaAutoCalSettingGetMemento(this.entity));
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.schedule.dom.shift.autocalsetting.AutoCalOvertimeSettingGetMemento#getEarlyMidOtTime()
	 */
	@Override
	public AutoCalSetting getEarlyMidOtTime() {
		return new AutoCalSetting(new JpaAutoCalSettingGetMemento(this.entity));
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.schedule.dom.shift.autocalsetting.AutoCalOvertimeSettingGetMemento#getNormalOtTime()
	 */
	@Override
	public AutoCalSetting getNormalOtTime() {
		return new AutoCalSetting(new JpaAutoCalSettingGetMemento(this.entity));
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.schedule.dom.shift.autocalsetting.AutoCalOvertimeSettingGetMemento#getNormalMidOtTime()
	 */
	@Override
	public AutoCalSetting getNormalMidOtTime() {
		return new AutoCalSetting(new JpaAutoCalSettingGetMemento(this.entity));
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.schedule.dom.shift.autocalsetting.AutoCalOvertimeSettingGetMemento#getLegalOtTime()
	 */
	@Override
	public AutoCalSetting getLegalOtTime() {
		return new AutoCalSetting(new JpaAutoCalSettingGetMemento(this.entity));
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.schedule.dom.shift.autocalsetting.AutoCalOvertimeSettingGetMemento#getLegalMidOtTime()
	 */
	@Override
	public AutoCalSetting getLegalMidOtTime() {
		return new AutoCalSetting(new JpaAutoCalSettingGetMemento(this.entity));
	}

}
