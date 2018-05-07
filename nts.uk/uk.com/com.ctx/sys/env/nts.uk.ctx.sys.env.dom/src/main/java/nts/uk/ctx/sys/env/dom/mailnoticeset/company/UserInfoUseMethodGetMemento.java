/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.env.dom.mailnoticeset.company;

import java.util.Optional;

import nts.uk.ctx.sys.env.dom.mailnoticeset.employee.UserInfoItem;

/**
 * The Interface UserInfoUseMethodGetMemento.
 */
public interface UserInfoUseMethodGetMemento {
	
	/**
	 * Gets the company id.
	 *
	 * @return the company id
	 */
	public String getCompanyId();

	/**
	 * Gets the setting item.
	 *
	 * @return the setting item
	 */
	public UserInfoItem getSettingItem();

	/**
	 * Gets the self edit.
	 *
	 * @return the self edit
	 */
	public SelfEditUserInfo getSelfEdit();

	/**
	 * Gets the setting use mail.
	 *
	 * @return the setting use mail
	 */
	public Optional<SettingUseSendMail> getSettingUseMail();
}
