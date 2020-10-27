package nts.uk.ctx.sys.gateway.infra.repository.securitypolicy.lockoutdata;

import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.sys.gateway.dom.login.ContractCode;
import nts.uk.ctx.sys.gateway.dom.securitypolicy.lockoutdata.LockOutDataSetMemento;
import nts.uk.ctx.sys.gateway.dom.securitypolicy.lockoutdata.LockType;
import nts.uk.ctx.sys.gateway.dom.securitypolicy.lockoutdata.LoginMethod;
import nts.uk.ctx.sys.gateway.infra.entity.securitypolicy.lockoutdata.SgwdtLockout;
import nts.uk.ctx.sys.gateway.infra.entity.securitypolicy.lockoutdata.SgwdtLockoutPK;

/**
 * The Class JpaLockOutDataSetMemento.
 */
public class JpaLockOutDataSetMemento implements LockOutDataSetMemento {
	
	/** The sgwmt lockout data. */
	private SgwdtLockout sgwdtLockout;

	/**
	 * Instantiates a new jpa lock out data set memento.
	 *
	 * @param entity the entity
	 */
	public JpaLockOutDataSetMemento(SgwdtLockout entity) {
		if (entity.getSgwdtLockoutPK() == null) {
			entity.setSgwdtLockoutPK(new SgwdtLockoutPK());
		}
		this.sgwdtLockout = entity;
	}

	/**
	 * Sets the user id.
	 *
	 * @param userId the new user id
	 */
	@Override
	public void setUserId(String userId) {
		this.sgwdtLockout.getSgwdtLockoutPK().setUserId(userId);
	}

	/**
	 * Sets the logout date time.
	 *
	 * @param logoutDateTime the new logout date time
	 */
	@Override
	public void setLogoutDateTime(GeneralDateTime logoutDateTime) {
		this.sgwdtLockout.getSgwdtLockoutPK().setLockoutDateTime(logoutDateTime);;
	}

	/**
	 * Sets the log type.
	 *
	 * @param logType the new log type
	 */
	@Override
	public void setLogType(LockType logType) {
		this.sgwdtLockout.setLockType(logType.value);
	}

	/**
	 * Sets the contract code.
	 *
	 * @param contractCode the new contract code
	 */
	@Override
	public void setContractCode(ContractCode contractCode) {
		this.sgwdtLockout.getSgwdtLockoutPK().setContractCd(contractCode.v());
	}

	/**
	 * Sets the login method.
	 *
	 * @param loginMethod the new login method
	 */
	@Override
	public void setLoginMethod(LoginMethod loginMethod) {
		this.sgwdtLockout.setLoginMethod(loginMethod == null ? null : loginMethod.value);
	}

}
