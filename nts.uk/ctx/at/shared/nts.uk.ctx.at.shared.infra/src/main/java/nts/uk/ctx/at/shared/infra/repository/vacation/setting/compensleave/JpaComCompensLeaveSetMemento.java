/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.vacation.setting.compensleave;

import nts.uk.ctx.at.shared.dom.vacation.setting.compensleave.ComCompensLeaveSetMemento;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensleave.CompensatoryLeaveSetting;
import nts.uk.ctx.at.shared.infra.entity.vacation.setting.compensleave.KclstComCompensLeave;

/**
 * The Class JpaComCompensLeaveSetMemento.
 */
public class JpaComCompensLeaveSetMemento implements ComCompensLeaveSetMemento {

	/** The type value. */
	private KclstComCompensLeave typeValue;

	/**
	 * Instantiates a new jpa com compens leave set memento.
	 *
	 * @param typeValue
	 *            the type value
	 */
	public JpaComCompensLeaveSetMemento(KclstComCompensLeave typeValue) {
		this.typeValue = typeValue;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.vacation.setting.compensleave.
	 * ComCompensLeaveSetMemento#setCompanyId(java.lang.String)
	 */
	@Override
	public void setCompanyId(String companyId) {
		this.typeValue.setCid(companyId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.vacation.setting.compensleave.
	 * ComCompensLeaveSetMemento#setSetting(nts.uk.ctx.at.shared.dom.vacation.
	 * setting.compensleave.CompensatoryLeaveSetting)
	 */
	@Override
	public void setSetting(CompensatoryLeaveSetting setting) {
		this.typeValue.setIsManage(setting.getIsManage().value);
		this.typeValue.setExpirationDateSet(setting.getExpirationDate().value);
		this.typeValue.setAllowPrepaidLeave(setting.getAllowPrepaidLeave().value);
	}

}
