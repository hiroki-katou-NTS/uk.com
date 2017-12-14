/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.gateway.infra.repository.singlesignon;

import nts.uk.ctx.sys.gateway.dom.singlesignon.OtherSysAccountGetMemento;
import nts.uk.ctx.sys.gateway.dom.singlesignon.UseAtr;
import nts.uk.ctx.sys.gateway.infra.entity.singlesignon.SgwmtOtherSysAcc;
import nts.uk.ctx.sys.gateway.infra.entity.singlesignon.SgwmtOtherSysAccPK;

/**
 * The Class JpaOtherSysAccountGetMemento.
 */
public class JpaOtherSysAccountGetMemento implements OtherSysAccountGetMemento {

	/** The typed value. */
	private SgwmtOtherSysAcc typedValue;

	/**
	 * Instantiates a new jpa other sys account get memento.
	 *
	 * @param typedValue the typed value
	 */
	public JpaOtherSysAccountGetMemento(SgwmtOtherSysAcc typedValue) {
		this.typedValue = typedValue;
		if (this.typedValue.getSgwmtOtherSysAccPK() == null) {
			this.typedValue.setSgwmtOtherSysAccPK(new SgwmtOtherSysAccPK());
		}
	}	

	/* (non-Javadoc)
	 * @see nts.uk.ctx.sys.gateway.dom.singlesignon.OtherSysAccountGetMemento#getUserId()
	 */
	@Override
	public String getUserId() {
		return this.typedValue.getSgwmtOtherSysAccPK().getUserId();
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.sys.gateway.dom.singlesignon.OtherSysAccountGetMemento#getCompanyCode()
	 */
	@Override
	public String getCompanyCode() {
		return this.typedValue.getSgwmtOtherSysAccPK().getCcd();
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.sys.gateway.dom.singlesignon.OtherSysAccountGetMemento#getUserName()
	 */
	@Override
	public String getUserName() {
		return this.typedValue.getSgwmtOtherSysAccPK().getUserName();
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.sys.gateway.dom.singlesignon.OtherSysAccountGetMemento#getUseAtr()
	 */
	@Override
	public UseAtr getUseAtr() {
		return UseAtr.valueOf(this.typedValue.getUseAtr());
	}

}
