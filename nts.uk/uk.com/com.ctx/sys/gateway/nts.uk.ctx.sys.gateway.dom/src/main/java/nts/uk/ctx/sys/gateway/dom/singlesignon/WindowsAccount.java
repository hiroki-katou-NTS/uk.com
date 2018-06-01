/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.gateway.dom.singlesignon;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import lombok.Getter;
import nts.arc.error.BundledBusinessException;
import nts.arc.layer.dom.AggregateRoot;
import nts.gul.text.StringUtil;

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

	@Override
	public void validate() {
		super.validate();
		final Integer MAX_FREQUENCY = 1;

		List<WindowsAccountInfo> lstCheck = new ArrayList<>(this.accountInfos);
		// check duplicate account host name & user name
		this.accountInfos.forEach(acc -> {
			boolean isNameNotNull = !StringUtil.isNullOrEmpty(acc.getHostName().v(), true)
					&& !StringUtil.isNullOrEmpty(acc.getUserName().v(), true);

			lstCheck.remove(acc); // remove so it won't be duplicated with itself
			boolean isDuplicated = Collections.frequency(lstCheck, acc) > MAX_FREQUENCY;
			
			if (isNameNotNull && isDuplicated) {
				BundledBusinessException exceptions = BundledBusinessException.newInstance();
				exceptions.addMessage("Msg_616");
				exceptions.throwExceptions();
			}
			
			lstCheck.add(acc); // add it back to check duplicate with next element
		});
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
