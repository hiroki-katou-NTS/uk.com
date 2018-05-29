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
 * The Class MailFunctionDto.
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.sys.env.dom.mailnoticeset.MailFunctionSetMemento#setFunctionId
	 * (nts.uk.ctx.sys.env.dom.mailnoticeset.FunctionId)
	 */
	@Override
	public void setFunctionId(FunctionId functionId) {
		this.functionId = functionId.v();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.sys.env.dom.mailnoticeset.MailFunctionSetMemento#
	 * setFunctionName(nts.uk.ctx.sys.env.dom.mailnoticeset.FunctionName)
	 */
	@Override
	public void setFunctionName(FunctionName functionName) {
		this.functionName = functionName.v();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.sys.env.dom.mailnoticeset.MailFunctionSetMemento#
	 * setProprietySendMailSettingAtr(boolean)
	 */
	@Override
	public void setProprietySendMailSettingAtr(boolean proprietySendMailSettingAtr) {
		this.proprietySendMailSettingAtr = proprietySendMailSettingAtr;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.sys.env.dom.mailnoticeset.MailFunctionSetMemento#setSortOrder(
	 * nts.uk.ctx.sys.env.dom.mailnoticeset.SortOrder)
	 */
	@Override
	public void setSortOrder(SortOrder sortOrder) {
		this.sortOrder = sortOrder.v();
	}

}
