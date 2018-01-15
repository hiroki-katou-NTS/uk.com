/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.vacation.setting.sixtyhour;

import nts.uk.ctx.at.shared.dom.vacation.setting.sixtyhours.Emp60HourVacationSetMemento;
import nts.uk.ctx.at.shared.dom.vacation.setting.sixtyhours.SixtyHourVacationSetting;
import nts.uk.ctx.at.shared.infra.entity.vacation.setting.sixtyhours.KshstEmp60hVacation;
import nts.uk.ctx.at.shared.infra.entity.vacation.setting.sixtyhours.KshstEmp60hVacationPK;
import nts.uk.ctx.at.shared.infra.entity.vacation.setting.sixtyhours.KshstSixtyHourVacationSetting;

/**
 * The Class JpaEmpSubstVacationSetMemento.
 */
public class JpaEmp60HourVacationSetMemento implements Emp60HourVacationSetMemento {

	/** The type value. */
	private KshstEmp60hVacation typeValue;

	/**
	 * Instantiates a new jpa emp subst vacation set memento.
	 *
	 * @param typeValue
	 *            the type value
	 */
	public JpaEmp60HourVacationSetMemento(KshstEmp60hVacation typeValue) {
		this.typeValue = typeValue;
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.vacation.setting.sixtyhours.Emp60HourVacationSetMemento#setCompanyId(java.lang.String)
	 */
	@Override
	public void setCompanyId(String companyId) {
		KshstEmp60hVacationPK emp60hVacationPK = new KshstEmp60hVacationPK();
		emp60hVacationPK.setCid(companyId);
		this.typeValue.setKshstEmp60hVacationPK(emp60hVacationPK);
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.vacation.setting.sixtyhours.Emp60HourVacationSetMemento#setEmpContractTypeCode(java.lang.String)
	 */
	@Override
	public void setEmpContractTypeCode(String contractTypeCode) {
		KshstEmp60hVacationPK emp60hVacationPK = this.typeValue.getKshstEmp60hVacationPK();
		emp60hVacationPK.setContractTypeCd(contractTypeCode);
		this.typeValue.setKshstEmp60hVacationPK(emp60hVacationPK);

	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.vacation.setting.sixtyhours.Emp60HourVacationSetMemento#setSetting(nts.uk.ctx.at.shared.dom.vacation.setting.sixtyhours.SixtyHourVacationSetting)
	 */
	@Override
	public void setSetting(SixtyHourVacationSetting setting) {
		setting.saveToMemento(
				new Jpa60HourVacationSettingSetMemento<KshstSixtyHourVacationSetting>(this.typeValue));
	}

}
