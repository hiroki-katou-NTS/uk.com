/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.env.dom.mailnoticeset.company;

import java.util.List;

import nts.arc.layer.dom.AggregateRoot;

/**
 * The Class MailDestinationFunction.
 */
// メール送信先機能
public class MailDestinationFunction extends AggregateRoot{

	/** The company id. */
	// 会社ID
	private String companyId;

	/** The setting item. */
	// 設定項目
	private UserInfoItem settingItem;

	/** The send by function setting. */
	// 機能別送信設定
	private List<SendMailByFunctionSetting> sendByFunctionSetting;

	/**
	 * Save to memento.
	 *
	 * @param memento the memento
	 */
	public void saveToMemento(MailDestinationFunctionSetMemento memento) {
		memento.setCompanyId(this.companyId);
		memento.setSettingItem(this.settingItem);
		memento.setSendByFunctionSetting(this.sendByFunctionSetting);
	}

	/**
	 * Instantiates a new mail destination function.
	 *
	 * @param memento the memento
	 */
	public MailDestinationFunction(MailDestinationFunctionGetMemento memento) {
		this.companyId = memento.getCompanyId();
		this.settingItem = memento.getSettingItem();
		this.sendByFunctionSetting = memento.getSendByFunctionSetting();
	}
}
