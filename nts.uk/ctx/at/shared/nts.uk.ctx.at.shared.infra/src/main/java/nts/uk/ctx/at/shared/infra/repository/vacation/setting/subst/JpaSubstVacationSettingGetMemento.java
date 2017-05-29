/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.vacation.setting.subst;

import nts.uk.ctx.at.shared.dom.vacation.setting.ApplyPermission;
import nts.uk.ctx.at.shared.dom.vacation.setting.ExpirationTime;
import nts.uk.ctx.at.shared.dom.vacation.setting.ManageDistinct;
import nts.uk.ctx.at.shared.dom.vacation.setting.subst.SubstVacationSettingGetMemento;
import nts.uk.ctx.at.shared.infra.entity.vacation.setting.subst.KsvstSubstVacationSetting;

/**
 * The Class JpaSubstVacationSettingGetMemento.
 */
public class JpaSubstVacationSettingGetMemento<T extends KsvstSubstVacationSetting>
		implements SubstVacationSettingGetMemento {

	/** The type value. */
	private T typeValue;

	/**
	 * Instantiates a new jpa subst vacation setting get memento.
	 *
	 * @param typeValue
	 *            the type value
	 */
	public JpaSubstVacationSettingGetMemento(T typeValue) {
		this.typeValue = typeValue;
	}

	/**
	 * Gets the checks if is manage.
	 *
	 * @return the checks if is manage
	 */
	@Override
	public ManageDistinct getIsManage() {
		return ManageDistinct.valueOf(this.typeValue.getIsManage());
	}

	/**
	 * Gets the expiration date.
	 *
	 * @return the expiration date
	 */
	@Override
	public ExpirationTime getExpirationDate() {
		return ExpirationTime.valueOf(this.typeValue.getExpirationDateSet());
	}

	/**
	 * Gets the allow prepaid leave.
	 *
	 * @return the allow prepaid leave
	 */
	@Override
	public ApplyPermission getAllowPrepaidLeave() {
		return ApplyPermission.valueOf(this.typeValue.getAllowPrepaidLeave());
	}

}
