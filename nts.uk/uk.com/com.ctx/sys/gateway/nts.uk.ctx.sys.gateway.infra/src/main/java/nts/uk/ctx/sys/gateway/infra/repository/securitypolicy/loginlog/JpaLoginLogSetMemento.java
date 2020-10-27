package nts.uk.ctx.sys.gateway.infra.repository.securitypolicy.loginlog;

import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.sys.gateway.dom.login.ContractCode;
import nts.uk.ctx.sys.gateway.dom.securitypolicy.loginlog.LoginLogSetMemento;
import nts.uk.ctx.sys.gateway.dom.securitypolicy.loginlog.OperationSection;
import nts.uk.ctx.sys.gateway.dom.securitypolicy.loginlog.SuccessFailureClassification;
import nts.uk.ctx.sys.gateway.infra.entity.securitypolicy.loginlog.SgwdtLoginLog;

/**
 * The Class JpaLoginLogSetMemento.
 */
public class JpaLoginLogSetMemento implements LoginLogSetMemento {
	
	/** The sgwmt login log. */
	private SgwdtLoginLog sgwdtLoginLog;
	
	/**
	 * Instantiates a new jpa login log set memento.
	 *
	 * @param sgwdtLoginLog the sgwmt login log
	 */
	public JpaLoginLogSetMemento(SgwdtLoginLog sgwdtLoginLog) {
		this.sgwdtLoginLog = sgwdtLoginLog;
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.sys.gateway.dom.securitypolicy.loginlog.LoginLogSetMemento#setUserId(java.lang.String)
	 */
	@Override
	public void setUserId(String userId) {
		this.sgwdtLoginLog.setUserId(userId);;
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.sys.gateway.dom.securitypolicy.loginlog.LoginLogSetMemento#setContractCode(nts.uk.ctx.sys.gateway.dom.login.ContractCode)
	 */
	@Override
	public void setContractCode(ContractCode contractCode) {
		this.sgwdtLoginLog.setContractCd(contractCode.v());
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.sys.gateway.dom.securitypolicy.loginlog.LoginLogSetMemento#setProgramId(java.lang.String)
	 */
	@Override
	public void setProgramId(String programId) {
		this.sgwdtLoginLog.setProgramId(programId);;
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.sys.gateway.dom.securitypolicy.loginlog.LoginLogSetMemento#setProcessDateTime(nts.arc.time.GeneralDateTime)
	 */
	@Override
	public void setProcessDateTime(GeneralDateTime processDateTime) {
		this.sgwdtLoginLog.setProcessDateTime(processDateTime);
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.sys.gateway.dom.securitypolicy.loginlog.LoginLogSetMemento#setSuccessOrFail(nts.uk.ctx.sys.gateway.dom.securitypolicy.loginlog.SuccessFailureClassification)
	 */
	@Override
	public void setSuccessOrFail(SuccessFailureClassification successOrFail) {
		this.sgwdtLoginLog.setSuccessOrFailure(successOrFail.value);
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.sys.gateway.dom.securitypolicy.loginlog.LoginLogSetMemento#setOperation(nts.uk.ctx.sys.gateway.dom.securitypolicy.loginlog.OperationSection)
	 */
	@Override
	public void setOperation(OperationSection operation) {
		this.sgwdtLoginLog.setOperationSection(operation.value);;
	}

}
