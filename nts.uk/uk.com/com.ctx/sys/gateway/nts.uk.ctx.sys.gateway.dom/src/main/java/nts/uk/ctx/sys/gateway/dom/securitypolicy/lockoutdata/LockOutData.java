/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.gateway.dom.securitypolicy.lockoutdata;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.sys.gateway.dom.loginold.ContractCode;

/**
 * The Class LogoutData.
 */
@Getter
//ロックアウトデータ
public class LockOutData extends AggregateRoot{

	/** The user id. */
	private String userId;
	
	/** The logout date time. */
	private GeneralDateTime lockOutDateTime;
	
	/** The log type. */
	private LockType logType;
	
	/** The contract code. */
	private ContractCode contractCode;
	
	/** The login method. */
	private LoginMethod loginMethod;
	
	/**
	 * Instantiates a new logout data.
	 *
	 * @param memento the memento
	 */
	public LockOutData(LockOutDataGetMemento memento) {
		this.userId = memento.getUserId();
		this.lockOutDateTime = memento.getLockOutDateTime();
		this.logType = memento.getLogType();
		this.contractCode = memento.getContractCode();
		this.loginMethod = memento.getLoginMethod();
	}
	
	/**
	 * Save to memento.
	 *
	 * @param memento the memento
	 */
	public void saveToMemento(LockOutDataSetMemento memento) {
		memento.setUserId(this.userId);
		memento.setLogoutDateTime(this.lockOutDateTime);
		memento.setLogType(this.logType);
		memento.setContractCode(this.contractCode);
		memento.setLoginMethod(this.loginMethod);
	}
}
