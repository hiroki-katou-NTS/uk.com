/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.gateway.dom.singlesignon;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;

/**
 * The Class WindowAccount.
 */
//Windowsアカウント

/**
 * Gets the use atr.
 *
 * @return the use atr
 */
@Getter
public class WindowAccount extends AggregateRoot{

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

	// 利用区分
	/** The use atr. */
	private UseAtr useAtr;

	/**
	 * Instantiates a new window account.
	 *
	 * @param memento
	 *            the memento
	 */
	public WindowAccount(WindowAccountGetMemento memento) {
		this.userId = memento.getUserId();
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
	public void saveToMemento(WindowAccountSetMemento memento) {
		memento.setUserId(this.userId);
		memento.setHostName(this.hostName);
		memento.setUserName(this.userName);
		memento.setNo(this.no);
		memento.setUseAtr(this.useAtr);
	}

	/**
	 * Instantiates a new window account.
	 */
	public WindowAccount() {
		super();
	}

}
