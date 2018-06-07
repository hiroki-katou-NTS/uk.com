/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.env.dom.mailnoticeset.company;

import java.util.List;

import nts.uk.ctx.sys.env.dom.mailnoticeset.employee.UserInfoItem;
import nts.uk.shr.com.enumcommon.NotUseAtr;

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

	public List<SendMailByFunctionSetting> getSendByFunctionSetting(NotUseAtr use);
}
