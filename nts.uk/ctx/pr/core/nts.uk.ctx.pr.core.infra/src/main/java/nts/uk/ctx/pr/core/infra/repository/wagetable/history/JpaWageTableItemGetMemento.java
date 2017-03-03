/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.infra.repository.wagetable.history;

import java.math.BigDecimal;

import nts.uk.ctx.pr.core.dom.wagetable.ElementId;
import nts.uk.ctx.pr.core.dom.wagetable.history.WageTableItemGetMemento;
import nts.uk.ctx.pr.core.infra.entity.wagetable.certification.QwtmtWagetableCertifyG;

/**
 * The Class JpaWageTableHistoryGetMemento.
 */
public class JpaWageTableItemGetMemento implements WageTableItemGetMemento {

	/** The type value. */
	protected QwtmtWagetableCertifyG typeValue;

	/**
	 * Instantiates a new jpa certify group get memento.
	 *
	 * @param typeValue
	 *            the type value
	 */
	public JpaWageTableItemGetMemento(QwtmtWagetableCertifyG typeValue) {
		this.typeValue = typeValue;
	}

	@Override
	public ElementId getElement1Id() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ElementId getElement2Id() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ElementId getElement3Id() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BigDecimal getAmount() {
		// TODO Auto-generated method stub
		return null;
	}

}
