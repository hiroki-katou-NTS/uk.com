/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.infra.repository.wagetable.history;

import java.math.BigDecimal;

import nts.uk.ctx.pr.core.dom.wagetable.ElementId;
import nts.uk.ctx.pr.core.dom.wagetable.history.WageTableItemSetMemento;
import nts.uk.ctx.pr.core.infra.entity.wagetable.certification.QwtmtWagetableCertifyG;

/**
 * The Class JpaCertifyGroupSetMemento.
 */
public class JpaWageTableItemSetMemento implements WageTableItemSetMemento {

	/** The type value. */
	protected QwtmtWagetableCertifyG typeValue;

	/**
	 * Instantiates a new jpa certify group set memento.
	 *
	 * @param typeValue
	 *            the type value
	 */
	public JpaWageTableItemSetMemento(QwtmtWagetableCertifyG typeValue) {
		this.typeValue = typeValue;
	}

	@Override
	public void setElement1Id(ElementId element1Id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setElement2Id(ElementId element2Id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setElement3Id(ElementId element3Id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setAmount(BigDecimal amount) {
		// TODO Auto-generated method stub
		
	}

}
