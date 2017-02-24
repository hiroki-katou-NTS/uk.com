/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.dom.wagetable;

import lombok.Getter;
import nts.arc.layer.dom.DomainObject;
import nts.uk.ctx.core.dom.company.CompanyCode;
import nts.uk.ctx.pr.core.dom.wagetable.certification.CertifyGroupGetMemento;
import nts.uk.ctx.pr.core.dom.wagetable.certification.CertifyGroupSetMemento;
import nts.uk.shr.com.primitive.Memo;

/**
 * The Class WageTableHead.
 */
@Getter
public class WageTableHead extends DomainObject {

	/** The company code. */
	private CompanyCode companyCode;

	/** The code. */
	private WageTableCode code;

	/** The name. */
	private WageTableName name;

	/** The demension set. */
	private ElementCount demensionSet;

	/** The memo. */
	private Memo memo;

	// List<WageTableElement> elementSetting;

	// =================== Memento State Support Method ===================
	/**
	 * Instantiates a new wage table head.
	 *
	 * @param memento the memento
	 */
	public WageTableHead(CertifyGroupGetMemento memento) {
		this.companyCode = memento.getCompanyCode();
	}

	/**
	 * Save to memento.
	 *
	 * @param memento the memento
	 */
	public void saveToMemento(CertifyGroupSetMemento memento) {
		memento.setCompanyCode(this.companyCode);
	}

}
