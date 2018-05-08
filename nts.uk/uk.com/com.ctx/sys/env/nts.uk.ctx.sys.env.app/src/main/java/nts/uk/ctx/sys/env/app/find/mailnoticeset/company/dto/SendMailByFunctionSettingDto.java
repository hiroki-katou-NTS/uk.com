/******************************************************************
 * Copyright (c) 2018 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.env.app.find.mailnoticeset.company.dto;

import nts.uk.ctx.sys.env.dom.mailnoticeset.company.SendMailByFunctionSettingSetMemento;
import nts.uk.shr.com.enumcommon.NotUseAtr;

/**
 * The Class SendMailByFunctionSettingDto.
 */
public class SendMailByFunctionSettingDto implements SendMailByFunctionSettingSetMemento {

	/** The function id. */
	// 機能ID
	public Integer functionId;

	/** The send setting. */
	// 送信設定
	public Integer sendSetting;
	
	/* (non-Javadoc)
	 * @see nts.uk.ctx.sys.env.dom.mailnoticeset.company.SendMailByFunctionSettingSetMemento#setFunctionId(java.lang.Integer)
	 */
	@Override
	public void setFunctionId(Integer functionId) {
		this.functionId = functionId;
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.sys.env.dom.mailnoticeset.company.SendMailByFunctionSettingSetMemento#setSendSetting(nts.uk.shr.com.enumcommon.NotUseAtr)
	 */
	@Override
	public void setSendSetting(NotUseAtr sendSetting) {
		this.sendSetting = sendSetting.value;
	}
}
