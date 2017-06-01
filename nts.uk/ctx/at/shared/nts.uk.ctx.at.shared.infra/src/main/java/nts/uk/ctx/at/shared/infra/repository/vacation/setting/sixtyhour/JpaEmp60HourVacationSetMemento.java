/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.vacation.setting.sixtyhour;

import nts.uk.ctx.at.shared.dom.vacation.setting.sixtyhours.Emp60HourVacationSetMemento;
import nts.uk.ctx.at.shared.dom.vacation.setting.sixtyhours.SixtyHourVacationSetting;

/**
 * The Class JpaEmpSubstVacationSetMemento.
 */
public class JpaEmp60HourVacationSetMemento implements Emp60HourVacationSetMemento {

	/** The type value. */
	private Object typeValue;

	/**
	 * Instantiates a new jpa emp subst vacation set memento.
	 *
	 * @param typeValue
	 *            the type value
	 */
	public JpaEmp60HourVacationSetMemento(Object typeValue) {
		this.typeValue = typeValue;
	}

	@Override
	public void setCompanyId(String companyId) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setEmpContractTypeCode(String contractTypeCode) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setSetting(SixtyHourVacationSetting setting) {
		// TODO Auto-generated method stub

	}

}
