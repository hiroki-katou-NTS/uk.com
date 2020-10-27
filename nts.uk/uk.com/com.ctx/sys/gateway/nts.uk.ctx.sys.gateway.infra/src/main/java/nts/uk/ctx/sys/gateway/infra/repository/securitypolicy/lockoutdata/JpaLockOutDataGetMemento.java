package nts.uk.ctx.sys.gateway.infra.repository.securitypolicy.lockoutdata;

import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.sys.gateway.dom.login.ContractCode;
import nts.uk.ctx.sys.gateway.dom.securitypolicy.lockoutdata.LockOutDataGetMemento;
import nts.uk.ctx.sys.gateway.dom.securitypolicy.lockoutdata.LockType;
import nts.uk.ctx.sys.gateway.dom.securitypolicy.lockoutdata.LoginMethod;
import nts.uk.ctx.sys.gateway.infra.entity.securitypolicy.lockoutdata.SgwdtLockout;

/**
 * The Class JpaLockOutDataGetMemento.
 */
public class JpaLockOutDataGetMemento implements LockOutDataGetMemento {
	
	/** The entity. */
	private SgwdtLockout entity;

	/**
	 * Instantiates a new jpa lock out data get memento.
	 *
	 * @param sgwmtLogoutData the sgwmt logout data
	 */
	public JpaLockOutDataGetMemento(SgwdtLockout sgwmtLogoutData) {
		this.entity = sgwmtLogoutData;
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.sys.gateway.dom.securitypolicy.lockoutdata.LockOutDataGetMemento#getUserId()
	 */
	@Override
	public String getUserId() {
		return this.entity.getSgwdtLockoutPK().getUserId();
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.sys.gateway.dom.securitypolicy.lockoutdata.LockOutDataGetMemento#getLockOutDateTime()
	 */
	@Override
	public GeneralDateTime getLockOutDateTime() {
		return this.entity.getSgwdtLockoutPK().getLockoutDateTime();
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.sys.gateway.dom.securitypolicy.lockoutdata.LockOutDataGetMemento#getLogType()
	 */
	@Override
	public LockType getLogType() {
		return LockType.valueOf(this.entity.getLockType());
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.sys.gateway.dom.securitypolicy.lockoutdata.LockOutDataGetMemento#getContractCode()
	 */
	@Override
	public ContractCode getContractCode() {
		return new ContractCode(this.entity.getSgwdtLockoutPK().getContractCd());
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.sys.gateway.dom.securitypolicy.lockoutdata.LockOutDataGetMemento#getLoginMethod()
	 */
	@Override
	public LoginMethod getLoginMethod() {
		return LoginMethod.valueOf(this.entity.getLoginMethod());
	}

}
