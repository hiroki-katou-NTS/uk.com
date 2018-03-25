/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.gateway.dom.singlesignon;

import java.util.List;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;

/**
 * The Class WindowAccount.
 */
// Windowsアカウント
@Getter
public class WindowsAccount extends AggregateRoot{

	// ユーザID
	/** The user id. */
	private String userId;

	// アカウント情報
	/** The hot name. */
	private List<WindowsAccountInfo> accountInfos;

	/**
	 * Instantiates a new window account.
	 *
	 * @param memento
	 *            the memento
	 */
	public WindowsAccount(WindowsAccountGetMemento memento) {
		this.userId = memento.getUserId();
		this.accountInfos = memento.getAccountInfos();
	}

	/**
	 * Save to memento.
	 *
	 * @param memento
	 *            the memento
	 */
	public void saveToMemento(WindowsAccountSetMemento memento) {
		memento.setUserId(this.userId);
		memento.setAccountInfos(this.accountInfos);
	}
	
}
