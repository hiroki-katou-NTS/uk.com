/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.vacation.setting.sixtyhour;

import nts.uk.ctx.at.shared.dom.vacation.setting.sixtyhours.Com60HourVacationSetMemento;
import nts.uk.ctx.at.shared.dom.vacation.setting.sixtyhours.SixtyHourVacationSetting;
import nts.uk.ctx.at.shared.infra.entity.vacation.setting.sixtyhours.KshstCom60hVacation;
import nts.uk.ctx.at.shared.infra.entity.vacation.setting.sixtyhours.KshstSixtyHourVacationSetting;

/**
 * The Class JpaComSubstVacationSetMemento.
 */
public class JpaCom60HourVacationSetMemento implements Com60HourVacationSetMemento {

	/** The type value. */
	private KshstCom60hVacation typeValue;

	/**
	 * Instantiates a new jpa com subst vacation set memento.
	 *
	 * @param typeValue
	 *            the type value
	 */
	public JpaCom60HourVacationSetMemento(KshstCom60hVacation typeValue) {
		this.typeValue = typeValue;
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.vacation.setting.sixtyhours.Com60HourVacationSetMemento#setCompanyId(java.lang.String)
	 */
	@Override
	public void setCompanyId(String companyId) {
		this.typeValue.setCid(companyId);
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.vacation.setting.sixtyhours.Com60HourVacationSetMemento#setSetting(nts.uk.ctx.at.shared.dom.vacation.setting.sixtyhours.SixtyHourVacationSetting)
	 */
	@Override
	public void setSetting(SixtyHourVacationSetting setting) {
		setting.saveToMemento(new Jpa60HourVacationSettingSetMemento<KshstSixtyHourVacationSetting>(this.typeValue));
	}

}
