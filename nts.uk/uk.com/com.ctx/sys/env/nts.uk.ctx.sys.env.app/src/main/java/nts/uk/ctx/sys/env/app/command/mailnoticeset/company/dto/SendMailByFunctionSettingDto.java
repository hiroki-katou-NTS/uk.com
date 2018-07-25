/******************************************************************
 * Copyright (c) 2018 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.env.app.command.mailnoticeset.company.dto;

import lombok.Value;
import nts.uk.ctx.sys.env.dom.mailnoticeset.FunctionId;
import nts.uk.ctx.sys.env.dom.mailnoticeset.company.SendMailByFunctionSettingGetMemento;
import nts.uk.shr.com.enumcommon.NotUseAtr;

/**
 * The Class SendMailByFunctionSettingDto.
 */
@Value
public class SendMailByFunctionSettingDto implements SendMailByFunctionSettingGetMemento {

	/** The function id. */
	private Integer functionId;

	/** The send setting. */
	private Integer sendSetting;

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.sys.env.dom.mailnoticeset.company.
	 * SendMailByFunctionSettingGetMemento#getFunctionId()
	 */
	@Override
	public FunctionId getFunctionId() {
		return new FunctionId(this.functionId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.sys.env.dom.mailnoticeset.company.
	 * SendMailByFunctionSettingGetMemento#getSendSetting()
	 */
	@Override
	public NotUseAtr getSendSetting() {
		return NotUseAtr.valueOf(this.sendSetting);
	}

}
