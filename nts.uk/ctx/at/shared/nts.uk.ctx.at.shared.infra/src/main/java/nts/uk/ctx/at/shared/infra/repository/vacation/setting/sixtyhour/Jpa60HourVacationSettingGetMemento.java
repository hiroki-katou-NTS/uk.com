/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.vacation.setting.sixtyhour;

import nts.uk.ctx.at.shared.dom.vacation.setting.ManageDistinct;
import nts.uk.ctx.at.shared.dom.vacation.setting.SixtyHourExtra;
import nts.uk.ctx.at.shared.dom.vacation.setting.TimeDigestiveUnit;
import nts.uk.ctx.at.shared.dom.vacation.setting.sixtyhours.SixtyHourVacationSettingGetMemento;
import nts.uk.ctx.at.shared.infra.entity.vacation.setting.sixtyhours.KshstSixtyHourVacationSetting;

/**
 * The Class JpaSubstVacationSettingGetMemento.
 *
 * @param <T> the generic type
 */
public class Jpa60HourVacationSettingGetMemento<T extends KshstSixtyHourVacationSetting>
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

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.vacation.setting.sixtyhours.SixtyHourVacationSettingGetMemento#getIsManage()
	 */
	@Override
	public ManageDistinct getIsManage() {
		return ManageDistinct.valueOf(this.typeValue.getManageDistinct());
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.vacation.setting.sixtyhours.SixtyHourVacationSettingGetMemento#getSixtyHourExtra()
	 */
	@Override
	public SixtyHourExtra getSixtyHourExtra() {
		return SixtyHourExtra.valueOf(this.typeValue.getSixtyHourExtra());
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.vacation.setting.sixtyhours.SixtyHourVacationSettingGetMemento#getDigestiveUnit()
	 */
	@Override
	public TimeDigestiveUnit getDigestiveUnit() {
		return TimeDigestiveUnit.valueOf(this.typeValue.getTimeDigestTive());
	}

}
