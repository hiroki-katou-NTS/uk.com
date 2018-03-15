/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.gateway.dom.securitypolicy.logoutdata;

import lombok.Getter;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.sys.gateway.dom.login.ContractCode;

/**
 * The Class LogoutData.
 */
@Getter
public class LogoutData {

	/** The user id. */
	private String userId;
	
	/** The logout date time. */
	private GeneralDate logoutDateTime;
	
	/** The log type. */
	private LogType logType;
	
	/** The contract code. */
	private ContractCode contractCode;
	
	/** The login method. */
	private LoginMethod loginMethod;
	
	/**
	 * Instantiates a new logout data.
	 *
	 * @param memento the memento
	 */
	public LogoutData(LogoutDataGetMemento memento) {
		this.userId = memento.getUserId();
		this.logoutDateTime = memento.getLogoutDateTime();
		this.logType = memento.getLogType();
		this.contractCode = memento.getContractCode();
		this.loginMethod = memento.getLoginMethod();
	}
	
	/**
	 * Save to memento.
	 *
	 * @param memento the memento
	 */
	public void saveToMemento(LogoutDataSetMemento memento) {
		memento.setUserId(this.userId);
		memento.setLogoutDateTime(this.logoutDateTime);
		memento.setLogType(this.logType);
		memento.setContractCode(this.contractCode);
		memento.setLoginMethod(this.loginMethod);
	}
	
}
