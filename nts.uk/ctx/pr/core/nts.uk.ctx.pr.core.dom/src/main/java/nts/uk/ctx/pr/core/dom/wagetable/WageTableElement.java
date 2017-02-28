/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.dom.wagetable;

import lombok.Getter;
import nts.uk.ctx.pr.core.dom.wagetable.certification.CertifyGroupGetMemento;
import nts.uk.ctx.pr.core.dom.wagetable.certification.CertifyGroupSetMemento;
import nts.uk.ctx.pr.core.dom.wagetable.element.ElementMode;

/**
 * The Class WageTableElement.
 */
@Getter
public class WageTableElement {

	/** The demension no. */
	private DemensionOrder demensionNo;

	// /** The demension type. */
	// private ElementType demensionType;

	/** The demension ref no. */
	private ElementMode elementModeSetting;

	// =================== Memento State Support Method ===================
	/**
	 * Instantiates a new wage table element.
	 *
	 * @param memento
	 *            the memento
	 */
	public WageTableElement(CertifyGroupGetMemento memento) {
		// this.companyCode = memento.getCompanyCode();
	}

	/**
	 * Save to memento.
	 *
	 * @param memento
	 *            the memento
	 */
	public void saveToMemento(CertifyGroupSetMemento memento) {
		// memento.setCompanyCode(this.companyCode);
	}

}
