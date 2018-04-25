/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.gateway.dom.singlesignon;

import lombok.Getter;

/**
 * The Class WindowAccount.
 */
// Windowsアカウント情報
@Getter
public class WindowsAccountInfo {

	// NO
	/** The no. */
	private Integer no;

	// 利用区分
	/** The use atr. */
	private UseAtr useAtr;

	// ホスト名
	/** The hot name. */
	private HostName hostName;

	// ユーザ名
	/** The user name. */
	private UserName userName;

	/**
	 * Instantiates a new window account.
	 *
	 * @param memento
	 *            the memento
	 */
	public WindowsAccountInfo(WindowsAccountInfoGetMemento memento) {
		this.hostName = memento.getHostName();
		this.userName = memento.getUserName();
		this.no = memento.getNo();
		this.useAtr = memento.getUseAtr();
	}

	/**
	 * Save to memento.
	 *
	 * @param memento
	 *            the memento
	 */
	public void saveToMemento(WindowsAccountInfoSetMemento memento) {
		memento.setHostName(this.hostName);
		memento.setUserName(this.userName);
		memento.setNo(this.no);
		memento.setUseAtr(this.useAtr);
	}
	
	/**
	 * Checks if is setting.
	 *
	 * @return the boolean
	 */
	public Boolean isSetting() {
		if (this.useAtr == UseAtr.NotUse) {
			return false;
		}
		return true;
	}
	
	/**
	 * Instantiates a new window account.
	 */
	public WindowsAccountInfo() {
		super();
	}

}
