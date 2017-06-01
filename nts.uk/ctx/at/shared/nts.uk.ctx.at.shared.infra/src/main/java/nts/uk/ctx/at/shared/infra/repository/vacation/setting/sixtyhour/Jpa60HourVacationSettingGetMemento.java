/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.vacation.setting.sixtyhour;

import nts.uk.ctx.at.shared.dom.vacation.setting.ManageDistinct;
import nts.uk.ctx.at.shared.dom.vacation.setting.SixtyHourExtra;
import nts.uk.ctx.at.shared.dom.vacation.setting.TimeDigestiveUnit;
import nts.uk.ctx.at.shared.dom.vacation.setting.sixtyhours.SixtyHourVacationSettingGetMemento;

/**
 * The Class JpaSubstVacationSettingGetMemento.
 */
public class Jpa60HourVacationSettingGetMemento<T extends Object>
		implements SixtyHourVacationSettingGetMemento {

	/** The type value. */
	private T typeValue;

	/**
	 * Instantiates a new jpa subst vacation setting get memento.
	 *
	 * @param typeValue
	 *            the type value
	 */
	public Jpa60HourVacationSettingGetMemento(T typeValue) {
		this.typeValue = typeValue;
	}

	@Override
	public ManageDistinct getIsManage() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SixtyHourExtra getSixtyHourExtra() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TimeDigestiveUnit getDigestiveUnit() {
		// TODO Auto-generated method stub
		return null;
	}

}
