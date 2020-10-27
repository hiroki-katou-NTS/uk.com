/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.ot.autocalsetting;

import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.autocalsetting.AutoCalFlexOvertimeSettingSetMemento;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.autocalsetting.AutoCalSetting;
import nts.uk.ctx.at.shared.infra.entity.ot.autocalsetting.com.KrcmtCalcSetCom;

public class JpaAutoCalFlexOvertimeSettingSetMemento implements AutoCalFlexOvertimeSettingSetMemento {

	/** The entity. */
	private KrcmtCalcSetCom entity;
	
	/**
	 * Instantiates a new jpa total times set memento.
	 *
	 * @param totalTimes
	 *            the total times
	 */
	public JpaAutoCalFlexOvertimeSettingSetMemento(KrcmtCalcSetCom auto) {
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

}
