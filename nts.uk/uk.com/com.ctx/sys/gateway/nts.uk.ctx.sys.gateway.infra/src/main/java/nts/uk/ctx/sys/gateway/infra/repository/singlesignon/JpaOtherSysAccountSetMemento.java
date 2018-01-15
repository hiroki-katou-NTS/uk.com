/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.gateway.infra.repository.singlesignon;

import nts.uk.ctx.sys.gateway.dom.singlesignon.OtherSysAccountSetMemento;
import nts.uk.ctx.sys.gateway.dom.singlesignon.UseAtr;
import nts.uk.ctx.sys.gateway.infra.entity.singlesignon.SgwmtOtherSysAcc;
import nts.uk.ctx.sys.gateway.infra.entity.singlesignon.SgwmtOtherSysAccPK;

/**
 * The Class JpaOtherSysAccountSetMemento.
 */
public class JpaOtherSysAccountSetMemento implements OtherSysAccountSetMemento {

	/** The typed value. */
	private SgwmtOtherSysAcc typedValue;

	/**
	 * Instantiates a new jpa other sys account set memento.
	 *
	 * @param typedValue the typed value
	 */
	public JpaOtherSysAccountSetMemento(SgwmtOtherSysAcc typedValue) {
		this.typedValue = typedValue;
		if (this.typedValue.getSgwmtOtherSysAccPK() == null) {
			this.typedValue.setSgwmtOtherSysAccPK(new SgwmtOtherSysAccPK());
		}
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.sys.gateway.dom.singlesignon.OtherSysAccountSetMemento#setUserId(java.lang.String)
	 */
	@Override
	public void setUserId(String userId) {
		this.typedValue.getSgwmtOtherSysAccPK().setUserId(userId);
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.sys.gateway.dom.singlesignon.OtherSysAccountSetMemento#setCompanyCode(java.lang.String)
	 */
	@Override
	public void setCompanyCode(String companyCode) {
		this.typedValue.getSgwmtOtherSysAccPK().setCcd(companyCode);
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.sys.gateway.dom.singlesignon.OtherSysAccountSetMemento#setUserName(java.lang.String)
	 */
	@Override
	public void setUserName(String userName) {
		this.typedValue.getSgwmtOtherSysAccPK().setUserName(userName);
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.sys.gateway.dom.singlesignon.OtherSysAccountSetMemento#setUseAtr(nts.uk.ctx.sys.gateway.dom.singlesignon.UseAtr)
	 */
	@Override
	public void setUseAtr(UseAtr useAtr) {
		this.typedValue.setUseAtr(useAtr.value);
	}

}
