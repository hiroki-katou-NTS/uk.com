/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.gateway.infra.repository.singlesignon;

import nts.uk.ctx.sys.gateway.dom.singlesignon.UseAtr;
import nts.uk.ctx.sys.gateway.dom.singlesignon.WindowAccountGetMemento;
import nts.uk.ctx.sys.gateway.infra.entity.singlesignon.SgwmtWindowAcc;
import nts.uk.ctx.sys.gateway.infra.entity.singlesignon.SgwmtWindowAccPK;

/**
 * The Class JpaWindowAccountGetMemento.
 */
public class JpaWindowAccountGetMemento implements WindowAccountGetMemento{
	
	/** The typed value. */
	private SgwmtWindowAcc typedValue;
	
	/**
	 * Instantiates a new jpa window account get memento.
	 *
	 * @param typedValue the typed value
	 */
	public JpaWindowAccountGetMemento(SgwmtWindowAcc typedValue) {
		this.typedValue = typedValue;
		if (this.typedValue.getSgwmtWindowAccPK() == null) {
			this.typedValue.setSgwmtWindowAccPK(new SgwmtWindowAccPK());
		}
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.sys.gateway.dom.singlesignon.WindowAccountGetMemento#getUserId()
	 */
	@Override
	public String getUserId() {
		return this.typedValue.getSgwmtWindowAccPK().getUserId();
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.sys.gateway.dom.singlesignon.WindowAccountGetMemento#getHotName()
	 */
	@Override
	public String getHostName() {
		return this.typedValue.getSgwmtWindowAccPK().getHostName();
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.sys.gateway.dom.singlesignon.WindowAccountGetMemento#getUserName()
	 */
	@Override
	public String getUserName() {
		return this.typedValue.getSgwmtWindowAccPK().getUserName();
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.sys.gateway.dom.singlesignon.WindowAccountGetMemento#getUseAtr()
	 */
	@Override
	public UseAtr getUseAtr() {
		return UseAtr.valueOf(this.typedValue.getUseAtr());
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.sys.gateway.dom.singlesignon.WindowAccountGetMemento#getNo()
	 */
	@Override
	public Integer getNo() {
		return this.typedValue.getNo();
	}

}
