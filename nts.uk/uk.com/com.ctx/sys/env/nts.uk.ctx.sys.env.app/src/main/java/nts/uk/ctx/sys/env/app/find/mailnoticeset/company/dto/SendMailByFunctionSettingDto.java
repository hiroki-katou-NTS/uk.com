/******************************************************************
 * Copyright (c) 2018 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.env.app.find.mailnoticeset.company.dto;

import lombok.Data;
import nts.uk.ctx.sys.env.dom.mailnoticeset.FunctionId;
import nts.uk.ctx.sys.env.dom.mailnoticeset.company.SendMailByFunctionSettingSetMemento;
import nts.uk.shr.com.enumcommon.NotUseAtr;

/**
 * The Class SendMailByFunctionSettingDto.
 */
@Data
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
	public void setFunctionId(FunctionId functionId) {
		this.functionId = functionId.v();
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.sys.env.dom.mailnoticeset.company.SendMailByFunctionSettingSetMemento#setSendSetting(nts.uk.shr.com.enumcommon.NotUseAtr)
	 */
	@Override
	public void setSendSetting(NotUseAtr sendSetting) {
		this.sendSetting = sendSetting.value;
	}
}
