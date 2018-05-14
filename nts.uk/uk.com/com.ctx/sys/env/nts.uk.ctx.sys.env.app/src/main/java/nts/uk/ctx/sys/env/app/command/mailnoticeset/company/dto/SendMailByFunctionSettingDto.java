package nts.uk.ctx.sys.env.app.command.mailnoticeset.company.dto;

import nts.uk.ctx.sys.env.dom.mailnoticeset.FunctionId;
import nts.uk.ctx.sys.env.dom.mailnoticeset.company.SendMailByFunctionSettingGetMemento;
import nts.uk.shr.com.enumcommon.NotUseAtr;

public class SendMailByFunctionSettingDto implements SendMailByFunctionSettingGetMemento {

	/** The function id. */
	private Integer functionId;

	/** The send setting. */
	private Integer sendSetting;

	@Override
	public FunctionId getFunctionId() {
		return new FunctionId(this.functionId);
	}

	@Override
	public NotUseAtr getSendSetting() {
		return NotUseAtr.valueOf(this.sendSetting);
	}

}
