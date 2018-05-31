/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.env.dom.mailnoticeset.company;

import java.util.List;

import nts.uk.ctx.sys.env.dom.mailnoticeset.employee.UserInfoItem;
<<<<<<< HEAD
import nts.uk.shr.com.enumcommon.NotUseAtr;
=======
>>>>>>> delivery/release_user

/**
 * The Interface MailDestinationFunctionGetMemento.
 */
public interface MailDestinationFunctionGetMemento {
	
	/**
	 * Gets the setting item.
	 *
	 * @return the setting item
	 */
	public UserInfoItem getSettingItem();

	/**
	 * Gets the send by function setting.
	 *
	 * @return the send by function setting
	 */
	public List<SendMailByFunctionSetting> getSendByFunctionSetting();
<<<<<<< HEAD

	public List<SendMailByFunctionSetting> getSendByFunctionSetting(NotUseAtr use);
=======
>>>>>>> delivery/release_user
}
