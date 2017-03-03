/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.infra.repository.wagetable;

import nts.uk.ctx.pr.core.dom.wagetable.DemensionOrder;
import nts.uk.ctx.pr.core.dom.wagetable.WageTableElementGetMemento;
import nts.uk.ctx.pr.core.dom.wagetable.element.ElementMode;
import nts.uk.ctx.pr.core.infra.entity.wagetable.certification.QwtmtWagetableCertifyG;

/**
 * The Class JpaWageTableElementGetMemento.
 */
public class JpaWageTableElementGetMemento implements WageTableElementGetMemento {

	/** The type value. */
	protected QwtmtWagetableCertifyG typeValue;

	/**
	 * Instantiates a new jpa certify group get memento.
	 *
	 * @param typeValue
	 *            the type value
	 */
	public JpaWageTableElementGetMemento(QwtmtWagetableCertifyG typeValue) {
		this.typeValue = typeValue;
	}

	@Override
	public DemensionOrder getDemensionNo() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ElementMode getElementModeSetting() {
		// TODO Auto-generated method stub
		return null;
	}

}
