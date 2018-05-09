/******************************************************************
 * Copyright (c) 2018 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.env.app.find.mailnoticeset.dto;

import lombok.Data;
import nts.uk.ctx.sys.env.dom.mailnoticeset.FunctionId;
import nts.uk.ctx.sys.env.dom.mailnoticeset.FunctionName;
import nts.uk.ctx.sys.env.dom.mailnoticeset.MailFunctionSetMemento;
import nts.uk.ctx.sys.env.dom.mailnoticeset.SortOrder;

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
	public void setFunctionId(FunctionId functionId) {
		this.functionId = functionId.v();

	}

	@Override
	public void setFunctionName(FunctionName functionName) {
		this.functionName = functionName.v();

	}

	@Override
	public void setProprietySendMailSettingAtr(boolean proprietySendMailSettingAtr) {
		this.proprietySendMailSettingAtr = proprietySendMailSettingAtr;

	}

	@Override
	public void setSortOrder(SortOrder sortOrder) {
		this.sortOrder = sortOrder.v();

	}

}
