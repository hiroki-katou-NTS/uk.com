/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.gateway.dom.securitypolicy.acountlock.locked;

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

	private LockOutData(
			String userId,
			GeneralDateTime lockOutDateTime,
			LockType logType,
			ContractCode contractCode,
			LoginMethod loginMethod) {
		
		this.userId = userId;
		this.lockOutDateTime = lockOutDateTime;
		this.logType = logType;
		this.contractCode = contractCode;
		this.loginMethod = loginMethod;
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
	
	/**
	 * 自動ロックをかける
	 * @param userId
	 * @param contractCode
	 * @return
	 */
	public static LockOutData autoLock(String userId, ContractCode contractCode) {
		return new LockOutData(
				userId,
				GeneralDateTime.now(),
				LockType.AUTO_LOCK,
				contractCode,
				LoginMethod.NORMAL_LOGIN);
	}

}
