/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.gateway.app.find.singlesignon;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.sys.gateway.dom.singlesignon.OtherSysAccountSetMemento;
import nts.uk.ctx.sys.gateway.dom.singlesignon.UseAtr;

/**
 * Gets the use atr.
 *
 * @return the use atr
 */
@Getter
@Setter
public class OtherSysAccFinderDto implements OtherSysAccountSetMemento{
	
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
	 * @see nts.uk.ctx.sys.gateway.dom.singlesignon.OtherSysAccountSetMemento#setUserId(java.lang.String)
	 */
	@Override
	public void setUserId(String userId) {
		this.userId = userId;
		
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.sys.gateway.dom.singlesignon.OtherSysAccountSetMemento#setCompanyCode(java.lang.String)
	 */
	@Override
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.sys.gateway.dom.singlesignon.OtherSysAccountSetMemento#setUserName(java.lang.String)
	 */
	@Override
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.sys.gateway.dom.singlesignon.OtherSysAccountSetMemento#setUseAtr(nts.uk.ctx.sys.gateway.dom.singlesignon.UseAtr)
	 */
	@Override
	public void setUseAtr(UseAtr useAtr) {
		this.useAtr = useAtr.value;
	}

}
