/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.vacation.setting.sixtyhour;

import nts.uk.ctx.at.shared.dom.vacation.setting.ManageDistinct;
import nts.uk.ctx.at.shared.dom.vacation.setting.SixtyHourExtra;
import nts.uk.ctx.at.shared.dom.vacation.setting.TimeDigestiveUnit;
import nts.uk.ctx.at.shared.dom.vacation.setting.sixtyhours.SixtyHourVacationSettingSetMemento;
import nts.uk.ctx.at.shared.infra.entity.vacation.setting.sixtyhours.KshstSixtyHourVacationSetting;

/**
 * The Class JpaSubstVacationSettingSetMemento.
 */
public class Jpa60HourVacationSettingSetMemento<T extends KshstSixtyHourVacationSetting>
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
		this.typeValue.setManageDistinct(isManage.value);
	}

	@Override
	public void setSixtyHourExtra(SixtyHourExtra sixtyHourExtra) {
		this.typeValue.setSixtyHourExtra(sixtyHourExtra.value);
	}

	@Override
	public void setDigestiveUnit(TimeDigestiveUnit digestiveUnit) {
		this.typeValue.setTimeDigestTive(digestiveUnit.value);
	}

}
