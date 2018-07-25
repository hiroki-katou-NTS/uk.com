/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.env.dom.mailnoticeset.company;

import lombok.Getter;
import nts.arc.layer.dom.DomainObject;
import nts.uk.ctx.sys.env.dom.mailnoticeset.FunctionId;
import nts.uk.shr.com.enumcommon.NotUseAtr;

/**
 * The Class SendMailByFunctionSetting.
 */

@Getter
//機能別送信設定
public class SendMailByFunctionSetting extends DomainObject {

	/** The function id. */
	// 機能ID
	private FunctionId functionId;

	/** The send setting. */
	// 送信設定
	private NotUseAtr sendSetting;

	/**
	 * Save to memento.
	 *
	 * @param memento the memento
	 */
	public void saveToMemento(SendMailByFunctionSettingSetMemento memento) {
		memento.setFunctionId(this.functionId);
		memento.setSendSetting(this.sendSetting);
	}

	/**
	 * Instantiates a new send mail by function setting.
	 *
	 * @param memento the memento
	 */
	public SendMailByFunctionSetting(SendMailByFunctionSettingGetMemento memento) {
		this.functionId = memento.getFunctionId();
		this.sendSetting = memento.getSendSetting();
	}
}
