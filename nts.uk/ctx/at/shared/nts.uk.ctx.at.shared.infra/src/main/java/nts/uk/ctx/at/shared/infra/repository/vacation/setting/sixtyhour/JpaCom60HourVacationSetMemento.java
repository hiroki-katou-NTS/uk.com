/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.vacation.setting.sixtyhour;

import nts.uk.ctx.at.shared.dom.vacation.setting.sixtyhours.Com60HourVacationSetMemento;
import nts.uk.ctx.at.shared.dom.vacation.setting.sixtyhours.SixtyHourVacationSetting;

/**
 * The Class JpaComSubstVacationSetMemento.
 */
public class JpaCom60HourVacationSetMemento implements Com60HourVacationSetMemento {

	/** The type value. */
	private Object typeValue;

	/**
	 * Instantiates a new jpa com subst vacation set memento.
	 *
	 * @param typeValue
	 *            the type value
	 */
	public JpaCom60HourVacationSetMemento(Object typeValue) {
		this.typeValue = typeValue;
	}

	@Override
	public void setCompanyId(String companyId) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setSetting(SixtyHourVacationSetting setting) {
		// TODO Auto-generated method stub

	}

}
