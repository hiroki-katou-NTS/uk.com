/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.vacation.setting.sixtyhour;

import nts.uk.ctx.at.shared.dom.vacation.setting.sixtyhours.Com60HourVacationGetMemento;
import nts.uk.ctx.at.shared.dom.vacation.setting.sixtyhours.SixtyHourVacationSetting;
import nts.uk.ctx.at.shared.infra.entity.vacation.setting.subst.KsvstComSubstVacation;

/**
 * The Class JpaComSubstVacationGetMemento.
 */
public class JpaCom60HourVacationGetMemento implements Com60HourVacationGetMemento {

	/** The type value. */
	private Object typeValue;

	/**
	 * Instantiates a new jpa com subst vacation get memento.
	 *
	 * @param typeValue
	 *            the type value
	 */
	public JpaCom60HourVacationGetMemento(KsvstComSubstVacation typeValue) {
		this.typeValue = typeValue;
	}

	@Override
	public String getCompanyId() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SixtyHourVacationSetting getSetting() {
		// TODO Auto-generated method stub
		return null;
	}

}
