/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.gateway.infra.repository.singlesignon;

import nts.uk.ctx.sys.gateway.dom.singlesignon.UseAtr;
import nts.uk.ctx.sys.gateway.dom.singlesignon.WindowAccountSetMemento;
import nts.uk.ctx.sys.gateway.infra.entity.singlesignon.SgwmtWindowAcc;
import nts.uk.ctx.sys.gateway.infra.entity.singlesignon.SgwmtWindowAccPK;

/**
 * The Class JpaWindowAccountSetMemento.
 */
public class JpaWindowAccountSetMemento implements WindowAccountSetMemento {
	
	/** The typed value. */
	private SgwmtWindowAcc typedValue;
	
	/**
	 * Instantiates a new jpa window account set memento.
	 *
	 * @param typedValue the typed value
	 */
	public JpaWindowAccountSetMemento(SgwmtWindowAcc typedValue) {
		this.typedValue = typedValue;
		if (this.typedValue.getSgwmtWindowAccPK() == null) {
			this.typedValue.setSgwmtWindowAccPK(new SgwmtWindowAccPK());
		}
	}
	

	/* (non-Javadoc)
	 * @see nts.uk.ctx.sys.gateway.dom.singlesignon.WindowAccountSetMemento#setUserId(java.lang.String)
	 */
	@Override
	public void setUserId(String userId) {
		this.typedValue.getSgwmtWindowAccPK().setUserId(userId);
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.sys.gateway.dom.singlesignon.WindowAccountSetMemento#setHotName(java.lang.String)
	 */
	@Override
	public void setHostName(String hostName) {
		this.typedValue.getSgwmtWindowAccPK().setHostName(hostName);
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.sys.gateway.dom.singlesignon.WindowAccountSetMemento#setUserName(java.lang.String)
	 */
	@Override
	public void setUserName(String userName) {
		this.typedValue.getSgwmtWindowAccPK().setUserName(userName);
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.sys.gateway.dom.singlesignon.WindowAccountSetMemento#setUseAtr(nts.uk.ctx.sys.gateway.dom.singlesignon.UseAtr)
	 */
	@Override
	public void setUseAtr(UseAtr useAtr) {
			this.typedValue.setUseAtr(useAtr.value);
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.sys.gateway.dom.singlesignon.WindowAccountSetMemento#setNo(java.lang.Integer)
	 */
	@Override
	public void setNo(Integer no) {
		this.typedValue.setNo(no);
	}

}
