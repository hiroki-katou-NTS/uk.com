/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.gateway.app.command.singlesignon;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.sys.gateway.dom.singlesignon.CompanyCode;
import nts.uk.ctx.sys.gateway.dom.singlesignon.OtherSysAccountGetMemento;
import nts.uk.ctx.sys.gateway.dom.singlesignon.UseAtr;
import nts.uk.ctx.sys.gateway.dom.singlesignon.UserName;

/**
 * The Class SaveOtherSysAccountCommand.
 */
@Setter
@Getter
public class SaveOtherSysAccountCommand implements OtherSysAccountGetMemento {

	// ユーザID
	/** The user id. */
	private String userId;

	/** The company code. */
	// 会社コード
	private String companyCode;

	// ユーザ名
	/** The user name. */
	private String userName;

	// 利用区分
	/** The use atr. */
	private Integer useAtr;

	/* (non-Javadoc)
	 * @see nts.uk.ctx.sys.gateway.dom.singlesignon.OtherSysAccountGetMemento#getUserId()
	 */
	@Override
	public String getUserId() {
		return this.userId;
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.sys.gateway.dom.singlesignon.OtherSysAccountGetMemento#getCompanyCode()
	 */
	@Override
	public CompanyCode getCompanyCode() {
		return new CompanyCode(this.companyCode);
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.sys.gateway.dom.singlesignon.OtherSysAccountGetMemento#getUserName()
	 */
	@Override
	public UserName getUserName() {
		return new UserName(this.userName);
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.sys.gateway.dom.singlesignon.OtherSysAccountGetMemento#getUseAtr()
	 */
	@Override
	public UseAtr getUseAtr() {
		return UseAtr.valueOf(this.useAtr);
	}
}
