/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.gateway.app.find.singlesignon;

import lombok.Getter;
import nts.uk.ctx.sys.gateway.dom.singlesignon.UseAtr;
import nts.uk.ctx.sys.gateway.dom.singlesignon.WindowAccountSetMemento;

/**
 * Gets the use atr.
 *
 * @return the use atr
 */
@Getter
public class WindownAccountFinderDto implements WindowAccountSetMemento{

	// ユーザID
	/** The user id. */
	private String userId;

	// ホスト名
	/** The hot name. */
	private String hostName;

	// ユーザ名
	/** The user name. */
	private String userName;

	// NO
	/** The no. */
	private Integer no;

	/** The use atr. */
	// 利用区分
	private Integer useAtr;

	/* (non-Javadoc)
	 * @see nts.uk.ctx.sys.gateway.dom.singlesignon.WindowAccountSetMemento#setUserId(java.lang.String)
	 */
	@Override
	public void setUserId(String userId) {
		this.userId = userId;
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.sys.gateway.dom.singlesignon.WindowAccountSetMemento#setHotName(java.lang.String)
	 */
	@Override
	public void setHostName(String hostName) {
		this.hostName = hostName;
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.sys.gateway.dom.singlesignon.WindowAccountSetMemento#setUserName(java.lang.String)
	 */
	@Override
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.sys.gateway.dom.singlesignon.WindowAccountSetMemento#setNo(java.lang.Integer)
	 */
	@Override
	public void setNo(Integer no) {
		this.no = no;
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.sys.gateway.dom.singlesignon.WindowAccountSetMemento#setUseAtr(nts.uk.ctx.sys.gateway.dom.singlesignon.UseAtr)
	 */
	@Override
	public void setUseAtr(UseAtr useAtr) {
		this.useAtr = useAtr.value;
	}
}
