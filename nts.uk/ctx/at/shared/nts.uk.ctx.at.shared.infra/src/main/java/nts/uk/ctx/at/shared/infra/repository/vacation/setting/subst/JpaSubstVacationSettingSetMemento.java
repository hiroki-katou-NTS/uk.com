/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.vacation.setting.subst;

import nts.uk.ctx.at.shared.dom.vacation.setting.ApplyPermission;
import nts.uk.ctx.at.shared.dom.vacation.setting.ExpirationTime;
import nts.uk.ctx.at.shared.dom.vacation.setting.ManageDistinct;
import nts.uk.ctx.at.shared.dom.vacation.setting.subst.SubstVacationSettingSetMemento;
import nts.uk.ctx.at.shared.infra.entity.vacation.setting.subst.KsvstSubstVacationSetting;

/**
 * The Class JpaSubstVacationSettingSetMemento.
 */
public class JpaSubstVacationSettingSetMemento<T extends KsvstSubstVacationSetting>
		implements SubstVacationSettingSetMemento {

	/** The type value. */
	private T typeValue;

	/**
	 * Instantiates a new jpa subst vacation setting set memento.
	 *
	 * @param typeValue
	 *            the type value
	 */
	public JpaSubstVacationSettingSetMemento(T typeValue) {
		this.typeValue = typeValue;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.vacation.setting.subst.
	 * SubstVacationSettingSetMemento#setIsManage(nts.uk.ctx.at.shared.dom.
	 * vacation.setting.ManageDistinct)
	 */
	@Override
	public void setIsManage(ManageDistinct isManage) {
		this.typeValue.setIsManage(isManage.value);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.vacation.setting.subst.
	 * SubstVacationSettingSetMemento#setExpirationDate(nts.uk.ctx.at.shared.dom
	 * .vacation.setting.subst.VacationExpiration)
	 */
	@Override
	public void setExpirationDate(ExpirationTime expirationDate) {
		this.typeValue.setExpirationDateSet(expirationDate.value);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.vacation.setting.subst.
	 * SubstVacationSettingSetMemento#setAllowPrepaidLeave(nts.uk.ctx.at.shared.
	 * dom.vacation.setting.ApplyPermission)
	 */
	@Override
	public void setAllowPrepaidLeave(ApplyPermission allowPrepaidLeave) {
		this.typeValue.setAllowPrepaidLeave(allowPrepaidLeave.value);
	}

}
