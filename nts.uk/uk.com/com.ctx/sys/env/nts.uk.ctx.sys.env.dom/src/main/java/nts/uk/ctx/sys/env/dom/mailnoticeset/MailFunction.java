/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.env.dom.mailnoticeset;

/**
 * The Class MailFunction.
 */
//メール機能
public class MailFunction {

	/** The function id. */
	//機能ID
	private Integer functionId;
	
	/** The function name. */
	//機能名
	private String functionName;
	
	/** The propriety send mail setting atr. */
	//メール送信設定可否区分
	private boolean proprietySendMailSettingAtr;
	
	/** The sort order. */
	//並び順
	private Integer sortOrder;
	
	/**
	 * Save to memento.
	 *
	 * @param memento the memento
	 */
	public void saveToMemento(MailFunctionSetMemento memento)
	{
		memento.setFunctionId(this.functionId);
		memento.setFunctionName(this.functionName);
		memento.setProprietySendMailSettingAtr(this.proprietySendMailSettingAtr);
		memento.setSortOrder(this.sortOrder);
	}
	
	/**
	 * Instantiates a new mail function.
	 *
	 * @param memento the memento
	 */
	public MailFunction(MailFunctionGetMemento memento)
	{
		this.functionId= memento.getFunctionId();
		this.functionName = memento.getFunctionName();
		this.proprietySendMailSettingAtr = memento.isProprietySendMailSettingAtr();
		this.sortOrder = memento.getSortOrder();
	}
}
