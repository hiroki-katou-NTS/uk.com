/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.bs.employee.dom.jobtitle.info;

import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.bs.employee.dom.common.CompanyId;

/**
 * 序列マスタ.
 */
public class SequenceMaster extends AggregateRoot {

	/** 会社ID. */
	private CompanyId companyId;

	/** 並び順. */
	private short order;

	/** 序列コード. */
	private SequenceCode sequenceCode;

	/** 序列名称. */
	private SequenceName sequenceName;

	/**
	 * Instantiates a new sequence master.
	 *
	 * @param memento
	 *            the memento
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
	 * @param memento
	 *            the memento
	 */
	public void saveToMemento(SequenceMasterSetMemento memento) {
		memento.setCompanyId(this.companyId);
		memento.setOrder(this.order);
		memento.setSequenceCode(this.sequenceCode);
		memento.setSequenceName(this.sequenceName);
	}
}
