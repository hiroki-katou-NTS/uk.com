package nts.uk.ctx.sys.gateway.infra.repository.securitypolicy.lockoutdata;

import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.sys.gateway.dom.loginold.ContractCode;
import nts.uk.ctx.sys.gateway.dom.securitypolicy.acountlock.locked.LockOutDataSetMemento;
import nts.uk.ctx.sys.gateway.dom.securitypolicy.acountlock.locked.LockType;
import nts.uk.ctx.sys.gateway.dom.securitypolicy.acountlock.locked.LoginMethod;
import nts.uk.ctx.sys.gateway.infra.entity.securitypolicy.lockoutdata.SgwmtLockoutData;
import nts.uk.ctx.sys.gateway.infra.entity.securitypolicy.lockoutdata.SgwmtLockoutDataPK;

/**
 * The Class JpaLockOutDataSetMemento.
 */
public class JpaLockOutDataSetMemento implements LockOutDataSetMemento {
	
	/** The sgwmt lockout data. */
	private SgwmtLockoutData sgwmtLockoutData;

	/**
	 * Instantiates a new jpa lock out data set memento.
	 *
	 * @param entity the entity
	 */
	public JpaLockOutDataSetMemento(SgwmtLockoutData entity) {
		if (entity.getSgwmtLockoutDataPK() == null) {
			entity.setSgwmtLockoutDataPK(new SgwmtLockoutDataPK());
		}
		this.sgwmtLockoutData = entity;
	}

	/**
	 * Sets the user id.
	 *
	 * @param userId the new user id
	 */
	@Override
	public void setUserId(String userId) {
		this.sgwmtLockoutData.getSgwmtLockoutDataPK().setUserId(userId);
	}

	/**
	 * Sets the logout date time.
	 *
	 * @param logoutDateTime the new logout date time
	 */
	@Override
	public void setLogoutDateTime(GeneralDateTime logoutDateTime) {
		this.sgwmtLockoutData.getSgwmtLockoutDataPK().setLockoutDateTime(logoutDateTime);;
	}

	/**
	 * Sets the log type.
	 *
	 * @param logType the new log type
	 */
	@Override
	public void setLogType(LockType logType) {
		this.sgwmtLockoutData.setLockType(logType.value);
	}

	/**
	 * Sets the contract code.
	 *
	 * @param contractCode the new contract code
	 */
	@Override
	public void setContractCode(ContractCode contractCode) {
		this.sgwmtLockoutData.getSgwmtLockoutDataPK().setContractCd(contractCode.v());
	}

	/**
	 * Sets the login method.
	 *
	 * @param loginMethod the new login method
	 */
	@Override
	public void setLoginMethod(LoginMethod loginMethod) {
		this.sgwmtLockoutData.setLoginMethod(loginMethod == null ? null : loginMethod.value);
	}

}
