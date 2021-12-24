/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.vacation.setting.sixtyhour;

import nts.uk.ctx.at.shared.dom.vacation.setting.SixtyHourExtra;
import nts.uk.ctx.at.shared.dom.vacation.setting.TimeVacationDigestUnit;
import nts.uk.ctx.at.shared.dom.vacation.setting.sixtyhours.Com60HourVacationSetMemento;
import nts.uk.ctx.at.shared.infra.entity.vacation.setting.sixtyhours.KshmtHd60hCom;

/**
 * The Class JpaComSubstVacationSetMemento.
 */
public class JpaCom60HourVacationSetMemento implements Com60HourVacationSetMemento {

	/** The type value. */
	private KshmtHd60hCom typeValue;

	/**
	 * Instantiates a new jpa com subst vacation set memento.
	 *
	 * @param typeValue
	 *            the type value
	 */
	public JpaCom60HourVacationSetMemento(KshmtHd60hCom typeValue) {
		this.typeValue = typeValue;
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.vacation.setting.sixtyhours.Com60HourVacationSetMemento#setCompanyId(java.lang.String)
	 */
	@Override
	public void setCompanyId(String companyId) {
		this.typeValue.setCid(companyId);
	}

	@Override
	public void setTimeVacationDigestUnit(TimeVacationDigestUnit digestiveUnit) {
		this.typeValue.setTimeDigestTive(digestiveUnit.getDigestUnit().value);
		this.typeValue.setManageDistinct(digestiveUnit.getManage().value);
	}

	@Override
	public void setSixtyHourExtra(SixtyHourExtra sixtyHourExtra) {
		this.typeValue.setSixtyHourExtra(sixtyHourExtra.value);
	}

}
