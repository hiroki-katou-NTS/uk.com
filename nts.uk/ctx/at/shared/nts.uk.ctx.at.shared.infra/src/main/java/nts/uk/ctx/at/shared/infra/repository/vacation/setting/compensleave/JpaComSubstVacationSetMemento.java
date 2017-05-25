/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.vacation.setting.compensleave;

import nts.uk.ctx.at.shared.dom.vacation.setting.compensleave.ComSubstVacationSetMemento;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensleave.SubstVacationSetting;
import nts.uk.ctx.at.shared.infra.entity.vacation.setting.compensleave.KsvstComSubstVacation;

/**
 * The Class JpaComSubstVacationSetMemento.
 */
public class JpaComSubstVacationSetMemento implements ComSubstVacationSetMemento {

	/** The type value. */
	private KsvstComSubstVacation typeValue;

	/**
	 * Instantiates a new jpa com compens leave set memento.
	 *
	 * @param typeValue
	 *            the type value
	 */
	public JpaComSubstVacationSetMemento(KsvstComSubstVacation typeValue) {
		this.typeValue = typeValue;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.vacation.setting.compensleave.
	 * ComSubstVacationSetMemento#setCompanyId(java.lang.String)
	 */
	@Override
	public void setCompanyId(String companyId) {
		this.typeValue.setCid(companyId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.vacation.setting.compensleave.
	 * ComSubstVacationSetMemento#setSetting(nts.uk.ctx.at.shared.dom.vacation.
	 * setting.compensleave.CompensatoryLeaveSetting)
	 */
	@Override
	public void setSetting(SubstVacationSetting setting) {
		this.typeValue.setIsManage(setting.getIsManage().value);
		this.typeValue.setExpirationDateSet(setting.getExpirationDate().value);
		this.typeValue.setAllowPrepaidLeave(setting.getAllowPrepaidLeave().value);
	}

}
