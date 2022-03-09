/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.vacation.setting.sixtyhour;

import nts.uk.ctx.at.shared.dom.vacation.setting.ManageDistinct;
import nts.uk.ctx.at.shared.dom.vacation.setting.SixtyHourExtra;
import nts.uk.ctx.at.shared.dom.vacation.setting.TimeDigestiveUnit;
import nts.uk.ctx.at.shared.dom.vacation.setting.TimeVacationDigestUnit;
import nts.uk.ctx.at.shared.dom.vacation.setting.sixtyhours.Com60HourVacationGetMemento;
import nts.uk.ctx.at.shared.infra.entity.vacation.setting.sixtyhours.KshmtHd60hCom;

/**
 * The Class JpaComSubstVacationGetMemento.
 */
public class JpaCom60HourVacationGetMemento implements Com60HourVacationGetMemento {

	/** The type value. */
	private KshmtHd60hCom typeValue;

	/**
	 * Instantiates a new jpa com subst vacation get memento.
	 *
	 * @param typeValue
	 *            the type value
	 */
	public JpaCom60HourVacationGetMemento(KshmtHd60hCom typeValue) {
		this.typeValue = typeValue;
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.vacation.setting.sixtyhours.Com60HourVacationGetMemento#getCompanyId()
	 */
	@Override
	public String getCompanyId() {
		return this.typeValue.getCid();
	}

	@Override
	public TimeVacationDigestUnit getDigestiveUnit() {
		return new TimeVacationDigestUnit(ManageDistinct.valueOf(typeValue.getManageDistinct()),
				TimeDigestiveUnit.valueOf(typeValue.getTimeDigestTive()));
	}

	@Override
	public SixtyHourExtra getSixtyHourExtra() {
		return SixtyHourExtra.valueOf(typeValue.getSixtyHourExtra());
	}

}
