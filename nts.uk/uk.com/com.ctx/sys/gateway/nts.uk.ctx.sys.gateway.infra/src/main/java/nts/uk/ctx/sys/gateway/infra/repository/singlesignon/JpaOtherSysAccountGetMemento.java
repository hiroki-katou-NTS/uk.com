/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.gateway.infra.repository.singlesignon;

import nts.uk.ctx.sys.gateway.dom.singlesignon.CompanyCode;
import nts.uk.ctx.sys.gateway.dom.singlesignon.OtherSysAccountGetMemento;
import nts.uk.ctx.sys.gateway.dom.singlesignon.UseAtr;
import nts.uk.ctx.sys.gateway.dom.singlesignon.UserName;
import nts.uk.ctx.sys.gateway.infra.entity.singlesignon.SgwmtSsoOtherSysAcc;
import nts.uk.ctx.sys.gateway.infra.entity.singlesignon.SgwmtSsoOtherSysAccPK;

/**
 * The Class JpaOtherSysAccountGetMemento.
 */
public class JpaOtherSysAccountGetMemento implements OtherSysAccountGetMemento {

	/** The typed value. */
	private SgwmtSsoOtherSysAcc typedValue;

	/**
	 * Instantiates a new jpa other sys account get memento.
	 *
	 * @param typedValue the typed value
	 */
	public JpaOtherSysAccountGetMemento(SgwmtSsoOtherSysAcc typedValue) {
		this.typedValue = typedValue;
		if (this.typedValue.getSgwmtSsoOtherSysAccPK() == null) {
			this.typedValue.setSgwmtSsoOtherSysAccPK(new SgwmtSsoOtherSysAccPK());
		}
	}	

	/* (non-Javadoc)
	 * @see nts.uk.ctx.sys.gateway.dom.singlesignon.OtherSysAccountGetMemento#getUserId()
	 */
	@Override
	public String getEmployeeId() {
		return this.typedValue.getSgwmtSsoOtherSysAccPK().getEmployeeId();
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.sys.gateway.dom.singlesignon.OtherSysAccountGetMemento#getCompanyCode()
	 */
	@Override
	public CompanyCode getCompanyCode() {
		return new CompanyCode(this.typedValue.getCcd());
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.sys.gateway.dom.singlesignon.OtherSysAccountGetMemento#getUserName()
	 */
	@Override
	public UserName getUserName() {
		return new UserName(this.typedValue.getUserName());
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.sys.gateway.dom.singlesignon.OtherSysAccountGetMemento#getUseAtr()
	 */
	@Override
	public UseAtr getUseAtr() {
		return UseAtr.valueOf(this.typedValue.getUseAtr());
	}

	@Override
	public String getCompanyId() {
		return this.typedValue.getSgwmtSsoOtherSysAccPK().getCid();
	}

}
