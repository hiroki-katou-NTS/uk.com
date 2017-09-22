package nts.uk.ctx.at.schedule.infra.repository.automaticcalculation;

import nts.uk.ctx.at.schedule.dom.shift.autocalsetting.AutoCalFlexOvertimeSettingGetMemento;
import nts.uk.ctx.at.schedule.dom.shift.autocalsetting.AutoCalSetting;
import nts.uk.ctx.at.schedule.infra.entity.shift.autocalsetting.KshmtAutoCalSet;

public class JpaAutoCalFlexOvertimeSettingGetMemento implements AutoCalFlexOvertimeSettingGetMemento {
	/** The entity. */
	private KshmtAutoCalSet entity;
	

	/**
	 * Instantiates a new jpa auto cal overtime setting get memento.
	 *
	 * @param company the company
	 * @param entity the entity
	 */
	public JpaAutoCalFlexOvertimeSettingGetMemento(KshmtAutoCalSet entity) {
		this.entity = entity;
	}


	@Override
	public AutoCalSetting getFlexOtTime() {
		return new AutoCalSetting(new JpaAutoCalSettingGetMemento(this.entity));
	}


	@Override
	public AutoCalSetting getFlexOtNightTime() {
		return new AutoCalSetting(new JpaAutoCalSettingGetMemento(this.entity));
	}
}
