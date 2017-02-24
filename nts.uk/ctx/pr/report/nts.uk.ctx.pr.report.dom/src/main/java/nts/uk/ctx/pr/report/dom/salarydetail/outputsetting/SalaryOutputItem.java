/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.report.dom.salarydetail.outputsetting;

import lombok.Getter;
import nts.uk.ctx.pr.report.dom.salarydetail.SalaryItemType;

/**
 * The Class SalaryOutputItem.
 */

/**
 * Gets the type.
 *
 * @return the type
 */
@Getter
public class SalaryOutputItem {

	/** The linkage code. */
	private String linkageCode;

	/** The type. */
	private SalaryItemType type;

	/**
	 * Instantiates a new salary output item.
	 *
	 * @param memento the memento
	 */
	public SalaryOutputItem(SalaryOutputItemGetMemento memento) {
		super();
		this.linkageCode = memento.getLinkageCode();
		this.type = memento.getType();
	}

	/**
	 * Save to memento.
	 *
	 * @param memento the memento
	 */
	public void saveToMemento(SalaryOutputItemSetMemento memento) {
		memento.setLinkageCode(this.linkageCode);
		memento.setType(this.type);
	}
}
