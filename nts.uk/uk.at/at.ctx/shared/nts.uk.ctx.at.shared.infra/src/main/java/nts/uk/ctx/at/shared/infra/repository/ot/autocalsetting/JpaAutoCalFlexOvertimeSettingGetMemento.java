package nts.uk.ctx.at.shared.infra.repository.ot.autocalsetting;

import nts.uk.ctx.at.shared.dom.ot.autocalsetting.AutoCalAtrOvertime;
import nts.uk.ctx.at.shared.dom.ot.autocalsetting.AutoCalFlexOvertimeSettingGetMemento;
import nts.uk.ctx.at.shared.dom.ot.autocalsetting.AutoCalSetting;
import nts.uk.ctx.at.shared.dom.ot.autocalsetting.TimeLimitUpperLimitSetting;
import nts.uk.ctx.at.shared.infra.entity.ot.autocalsetting.KshmtAutoCalSet;

public class JpaAutoCalFlexOvertimeSettingGetMemento implements AutoCalFlexOvertimeSettingGetMemento {
	/** The entity. */
	private KshmtAutoCalSet entity;

	/**
	 * Instantiates a new jpa auto cal overtime setting get memento.
	 *
	 * @param company
	 *            the company
	 * @param entity
	 *            the entity
	 */
	public JpaAutoCalFlexOvertimeSettingGetMemento(KshmtAutoCalSet entity) {
		this.entity = entity;
	}

	@Override
	public AutoCalSetting getFlexOtTime() {
		return new AutoCalSetting(TimeLimitUpperLimitSetting.valueOf(this.entity.getFlexOtTimeLimit()),
				AutoCalAtrOvertime.valueOf(this.entity.getFlexOtTimeAtr()));
	}

}
