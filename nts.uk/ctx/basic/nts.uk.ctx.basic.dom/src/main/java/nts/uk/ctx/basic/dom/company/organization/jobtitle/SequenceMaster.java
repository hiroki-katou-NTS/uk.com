/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.basic.dom.company.organization.jobtitle;

import nts.arc.layer.dom.AggregateRoot;

/**
 * The Class SequenceMaster.
 */
public class SequenceMaster extends AggregateRoot {

	/** The order. */
	private int order;

	/** The company id. */
	private CompanyId companyId;

	/** The sequence code. */
	private SequenceCode sequenceCode;

	/** The sequence name. */
	private SequenceName sequenceName;

	/**
	 * Instantiates a new sequence master.
	 *
	 * @param memento the memento
	 */
	public SequenceMaster(SequenceMasterGetMemento memento) {
		this.order = memento.getOrder();
		this.companyId = memento.getCompanyId();
		this.sequenceCode = memento.getSequenceCode();
		this.sequenceName = memento.getSequenceName();
	}

	/**
	 * Save to memento.
	 *
	 * @param memento the memento
	 */
	public void saveToMemento(SequenceMasterSetMemento memento) {
		memento.setCompanyId(this.companyId);
		memento.setOrder(this.order);
		memento.setSequenceCode(this.sequenceCode);
		memento.setSequenceName(this.sequenceName);
	}
}
