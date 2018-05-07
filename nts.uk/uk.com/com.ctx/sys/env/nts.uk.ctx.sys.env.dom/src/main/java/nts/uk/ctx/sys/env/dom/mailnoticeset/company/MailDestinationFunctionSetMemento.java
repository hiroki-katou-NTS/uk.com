/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.env.dom.mailnoticeset.company;

import java.util.List;

/**
 * The Interface MailDestinationFunctionSetMemento.
 */
public interface MailDestinationFunctionSetMemento {
	
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
	 * Sets the send by function setting.
	 *
	 * @param sendByFunctionSetting the new send by function setting
	 */
	public void setSendByFunctionSetting(List<SendMailByFunctionSetting> sendByFunctionSetting);
}
