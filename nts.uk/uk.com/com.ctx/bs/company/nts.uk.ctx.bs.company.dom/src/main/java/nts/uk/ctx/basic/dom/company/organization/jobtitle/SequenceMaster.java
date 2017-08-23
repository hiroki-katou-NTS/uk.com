/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.basic.dom.company.organization.jobtitle;

import nts.arc.layer.dom.AggregateRoot;

/**
 * 序列マスタ.
 */
public class SequenceMaster extends AggregateRoot {

	/** 並び順. */
	private int order;

	/** 会社ID. */
	private CompanyId companyId;

	/** 序列コード. */
	private SequenceCode sequenceCode;

	/** 序列名称. */
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
