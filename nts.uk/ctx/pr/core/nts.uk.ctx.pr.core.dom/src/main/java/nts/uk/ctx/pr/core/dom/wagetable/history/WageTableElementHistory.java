/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.dom.wagetable.history;

import lombok.Getter;
import nts.arc.layer.dom.DomainObject;
import nts.uk.ctx.pr.core.dom.wagetable.certification.CertifyGroupGetMemento;
import nts.uk.ctx.pr.core.dom.wagetable.certification.CertifyGroupSetMemento;

/**
 * The Class WageTableHist.
 */
@Getter
public class WageTableElementHistory extends DomainObject {

	/** The company code. */
	private Integer upperLimit;

	private Integer lowerLimit;

	private Integer demensionNo;

	private Integer interval;

	// private List<WageTableCodeT> wageTableCodes;

	// =================== Memento State Support Method ===================
	/**
	 * Instantiates a new wage table hist.
	 *
	 * @param memento
	 *            the memento
	 */
	public WageTableElementHistory(CertifyGroupGetMemento memento) {
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
