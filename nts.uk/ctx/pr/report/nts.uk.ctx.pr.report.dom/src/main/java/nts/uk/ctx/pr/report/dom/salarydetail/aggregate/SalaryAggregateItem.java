/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.report.dom.salarydetail.aggregate;

import java.util.Set;

import lombok.Getter;
import nts.arc.layer.dom.DomainObject;
import nts.uk.ctx.pr.report.dom.salarydetail.item.SalaryItem;

/**
 * The Class SalaryAggregateItem.
 */
@Getter
public class SalaryAggregateItem extends DomainObject {

	
	private SalaryAggregateItemHeader header;

	/** The name. */
	private SalaryAggregateItemName name;

	/** The sub item codes. */
	private Set<SalaryItem> subItemCodes;

	/**
	 * Instantiates a new salary aggregate item.
	 *
	 * @param memento the memento
	 */
	public SalaryAggregateItem(SalaryAggregateItemGetMemento memento) {
		super();
		this.header = memento.getSalaryAggregateItemHeader();
		this.name = memento.getSalaryAggregateItemName();
		this.subItemCodes = memento.getSubItemCodes();
	}

	/**
	 * Save to memento.
	 *
	 * @param memento the memento
	 */
	public void saveToMemento(SalaryAggregateItemSetMemento memento) {
		memento.setSalaryAggregateItemHeader(this.header);
		memento.setSalaryAggregateItemName(this.name);
		memento.setSubItemCodes(this.subItemCodes);
	}
}
