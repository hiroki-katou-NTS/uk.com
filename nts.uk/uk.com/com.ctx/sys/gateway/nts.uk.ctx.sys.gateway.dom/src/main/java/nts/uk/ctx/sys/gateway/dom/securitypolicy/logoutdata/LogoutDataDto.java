/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.gateway.dom.securitypolicy.logoutdata;

import lombok.Builder;
import lombok.Value;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.sys.gateway.dom.login.ContractCode;

/**
 * The Class LogoutDataDto.
 */
@Builder
@Value
public class LogoutDataDto implements LogoutDataGetMemento {

	/** The user id. */
	private String userId;

	/** The logout date time. */
	private GeneralDate logoutDateTime;

	/** The log type. */
	private Integer logType;

	/** The contract code. */
	private String contractCode;

	/** The login method. */
	private Integer loginMethod;

	/* (non-Javadoc)
	 * @see nts.uk.ctx.sys.gateway.dom.securitypolicy.logoutdata.LogoutDataGetMemento#getUserId()
	 */
	@Override
	public String getUserId() {
		return this.userId;
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.sys.gateway.dom.securitypolicy.logoutdata.LogoutDataGetMemento#getLogoutDateTime()
	 */
	@Override
	public GeneralDate getLogoutDateTime() {
		return this.logoutDateTime;
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.sys.gateway.dom.securitypolicy.logoutdata.LogoutDataGetMemento#getLogType()
	 */
	@Override
	public LogType getLogType() {
		return LogType.valueOf(this.logType);
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.sys.gateway.dom.securitypolicy.logoutdata.LogoutDataGetMemento#getContractCode()
	 */
	@Override
	public ContractCode getContractCode() {
		return new ContractCode(this.contractCode);
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.sys.gateway.dom.securitypolicy.logoutdata.LogoutDataGetMemento#getLoginMethod()
	 */
	@Override
	public LoginMethod getLoginMethod() {
		return LoginMethod.valueOf(this.loginMethod);
	}
}
