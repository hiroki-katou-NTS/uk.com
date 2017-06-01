/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.vacation.setting.sixtyhour;

import nts.uk.ctx.at.shared.dom.vacation.setting.sixtyhours.Emp60HourVacationGetMemento;
import nts.uk.ctx.at.shared.dom.vacation.setting.sixtyhours.SixtyHourVacationSetting;

/**
 * The Class JpaEmpSubstVacationGetMemento.
 */
public class JpaEmp60HourVacationGetMemento implements Emp60HourVacationGetMemento {

	/** The type value. */
	private Object typeValue;

	/**
	 * Instantiates a new jpa emp subst vacation get memento.
	 *
	 * @param typeValue
	 *            the type value
	 */
	public JpaEmp60HourVacationGetMemento(Object typeValue) {
		this.typeValue = typeValue;
	}

	@Override
	public String getCompanyId() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getEmpContractTypeCode() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SixtyHourVacationSetting getSetting() {
		// TODO Auto-generated method stub
		return null;
	}

}
