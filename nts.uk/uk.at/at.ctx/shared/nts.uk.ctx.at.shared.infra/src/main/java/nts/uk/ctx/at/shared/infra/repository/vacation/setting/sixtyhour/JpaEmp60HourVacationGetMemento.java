/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.vacation.setting.sixtyhour;

import nts.uk.ctx.at.shared.dom.vacation.setting.sixtyhours.Emp60HourVacationGetMemento;
import nts.uk.ctx.at.shared.dom.vacation.setting.sixtyhours.SixtyHourVacationSetting;
import nts.uk.ctx.at.shared.infra.entity.vacation.setting.sixtyhours.KshstEmp60hVacation;
import nts.uk.ctx.at.shared.infra.entity.vacation.setting.sixtyhours.KshstSixtyHourVacationSetting;

/**
 * The Class JpaEmpSubstVacationGetMemento.
 */
public class JpaEmp60HourVacationGetMemento implements Emp60HourVacationGetMemento {

	/** The type value. */
	private KshstEmp60hVacation typeValue;

	/**
	 * Instantiates a new jpa emp subst vacation get memento.
	 *
	 * @param typeValue
	 *            the type value
	 */
	public JpaEmp60HourVacationGetMemento(KshstEmp60hVacation typeValue) {
		this.typeValue = typeValue;
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.vacation.setting.sixtyhours.Emp60HourVacationGetMemento#getCompanyId()
	 */
	@Override
	public String getCompanyId() {
		return this.typeValue.getKshstEmp60hVacationPK().getCid();
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.vacation.setting.sixtyhours.Emp60HourVacationGetMemento#getEmpContractTypeCode()
	 */
	@Override
	public String getEmpContractTypeCode() {
		return this.typeValue.getKshstEmp60hVacationPK().getContractTypeCd();
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.vacation.setting.sixtyhours.Emp60HourVacationGetMemento#getSetting()
	 */
	@Override
	public SixtyHourVacationSetting getSetting() {
		return new SixtyHourVacationSetting(new Jpa60HourVacationSettingGetMemento<KshstSixtyHourVacationSetting>(this.typeValue));
	}

}
