/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.gateway.app.command.singlesignon;

import lombok.Setter;
import nts.uk.ctx.sys.gateway.dom.singlesignon.UseAtr;
import nts.uk.ctx.sys.gateway.dom.singlesignon.WindowAccountGetMemento;

/**
 * The Class WindowAccountDto.
 */
@Setter
public class WindowAccountDto implements WindowAccountGetMemento {

	// ユーザID
	/** The user id. */
	private String userId;

	// ホスト名
	/** The host name. */
	private String hostName;

	// ユーザ名
	/** The user name. */
	private String userName;

	// NO
	/** The no. */
	private Integer no;

	// 利用区分
	/** The use atr. */
	private Integer useAtr;

	/** The is change. */
	private Boolean isChange;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.sys.gateway.dom.singlesignon.WindowAccountGetMemento#getUserId
	 * ()
	 */
	@Override
	public String getUserId() {
		return this.userId;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.sys.gateway.dom.singlesignon.WindowAccountGetMemento#
	 * getHostName()
	 */
	@Override
	public String getHostName() {
		return this.hostName;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.sys.gateway.dom.singlesignon.WindowAccountGetMemento#
	 * getUserName()
	 */
	@Override
	public String getUserName() {
		return this.userName;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.sys.gateway.dom.singlesignon.WindowAccountGetMemento#getNo()
	 */
	@Override
	public Integer getNo() {
		return this.no;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.sys.gateway.dom.singlesignon.WindowAccountGetMemento#getUseAtr
	 * ()
	 */
	@Override
	public UseAtr getUseAtr() {
		return UseAtr.valueOf(this.useAtr);
	}
	
	/**
	 * Gets the checks if is change.
	 *
	 * @return the checks if is change
	 */
	public Boolean getIsChange() {
		return this.isChange;
	}
}
