/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.env.dom.mailnoticeset.company;

import java.util.Optional;

import nts.uk.ctx.sys.env.dom.mailnoticeset.employee.UserInfoItem;

/**
 * The Interface UserInfoUseMethodSetMemento.
 */
public interface UserInfoUseMethodSetMemento {
	
	/**
	 * Sets the company id.
	 *
	 * @param companyId the new company id
	 */
	public void setCompanyId(String companyId);

	/**
	 * Sets the setting item.
	 *
	 * @param settingItem the new setting item
	 */
	public void setSettingItem(UserInfoItem settingItem);

	/**
	 * Sets the self edit.
	 *
	 * @param selfEdit the new self edit
	 */
	public void setSelfEdit(SelfEditUserInfo selfEdit);

	/**
	 * Sets the setting use mail.
	 *
	 * @param settingUseMail the new setting use mail
	 */
	public void setSettingUseMail(Optional<SettingUseSendMail> settingUseMail);
}
