/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.gateway.dom.singlesignon;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;

//他システムアカウント
@Getter
public class OtherSysAccount extends AggregateRoot{

	//ユーザID
	/** The user id. */
	private String userId;

	/** The company code. */
	//会社コード
	private CompanyCode companyCode;

	//ユーザ名
	/** The user name. */
	private UserName userName;

	//利用区分
	/** The use division. */
	private UseAtr useAtr;
	
	public OtherSysAccount(OtherSysAccountGetMemento memento) {
		this.userId = memento.getUserId();
		this.companyCode = memento.getCompanyCode();
		this.userName = memento.getUserName();
		this.useAtr = memento.getUseAtr();
	}
	
	public void saveToMemento(OtherSysAccountSetMemento memento) {
		memento.setUserId(this.userId);
		memento.setCompanyCode(this.companyCode);;
		memento.setUserName(this.userName);
		memento.setUseAtr(this.useAtr);
	}

	public OtherSysAccount() {
		super();
	}

}
