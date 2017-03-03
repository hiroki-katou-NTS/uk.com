/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.infra.repository.wagetable;

import nts.uk.ctx.pr.core.dom.wagetable.DemensionOrder;
import nts.uk.ctx.pr.core.dom.wagetable.WageTableElementSetMemento;
import nts.uk.ctx.pr.core.dom.wagetable.element.ElementMode;
import nts.uk.ctx.pr.core.infra.entity.wagetable.certification.QwtmtWagetableCertifyG;

/**
 * The Class JpaCertifyGroupSetMemento.
 */
public class JpaWageTableElementSetMemento implements WageTableElementSetMemento {

	/** The type value. */
	protected QwtmtWagetableCertifyG typeValue;

	/**
	 * Instantiates a new jpa certify group set memento.
	 *
	 * @param typeValue
	 *            the type value
	 */
	public JpaWageTableElementSetMemento(QwtmtWagetableCertifyG typeValue) {
		this.typeValue = typeValue;
	}

	@Override
	public void setDemensionNo(DemensionOrder demensionNo) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setElementModeSetting(ElementMode elementModeSetting) {
		// TODO Auto-generated method stub

	}

}
