/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.vacation.setting.sixtyhour;

import nts.uk.ctx.at.shared.dom.vacation.setting.ManageDistinct;
import nts.uk.ctx.at.shared.dom.vacation.setting.SixtyHourExtra;
import nts.uk.ctx.at.shared.dom.vacation.setting.TimeDigestiveUnit;
import nts.uk.ctx.at.shared.dom.vacation.setting.sixtyhours.SixtyHourVacationSettingSetMemento;

/**
 * The Class JpaSubstVacationSettingSetMemento.
 */
public class Jpa60HourVacationSettingSetMemento<T extends Object>
		implements SixtyHourVacationSettingSetMemento {

	/** The type value. */
	private T typeValue;

	/**
	 * Instantiates a new jpa subst vacation setting set memento.
	 *
	 * @param typeValue
	 *            the type value
	 */
	public Jpa60HourVacationSettingSetMemento(T typeValue) {
		this.typeValue = typeValue;
	}

	@Override
	public void setIsManage(ManageDistinct isManage) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setSixtyHourExtra(SixtyHourExtra sixtyHourExtra) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setDigestiveUnit(TimeDigestiveUnit digestiveUnit) {
		// TODO Auto-generated method stub

	}

}
