/******************************************************************
 * Copyright (c) 2018 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.env.app.find.mailnoticeset.dto;

import lombok.Data;
import nts.uk.ctx.sys.env.dom.mailnoticeset.MailFunctionSetMemento;

/**
 * Instantiates a new mail function dto.
 */
@Data
public class MailFunctionDto implements MailFunctionSetMemento {

	/** The function id. */
	public Integer functionId;

	/** The function name. */
	public String functionName;

	/** The propriety send mail setting atr. */
	public boolean proprietySendMailSettingAtr;

	/** The sort order. */
	public Integer sortOrder;

	@Override
	public void setFunctionId(Integer functionId) {
		this.functionId = functionId;

	}

	@Override
	public void setFunctionName(String functionName) {
		this.functionName = functionName;

	}

	@Override
	public void setProprietySendMailSettingAtr(boolean proprietySendMailSettingAtr) {
		this.proprietySendMailSettingAtr = proprietySendMailSettingAtr;

	}

	@Override
	public void setSortOrder(Integer sortOrder) {
		this.sortOrder = sortOrder;

	}

}
