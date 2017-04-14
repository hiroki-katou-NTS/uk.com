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

	/** The code. */
	private SalaryAggregateItemCode code;

	/** The company code. */
	private String companyCode;

	/** The name. */
	private SalaryAggregateItemName name;

	/** The sub item codes. */
	private Set<SalaryItem> subItemCodes;

	/** The tax division. */
	private TaxDivision taxDivision;

	/** The item category. */
	// TODO: need review with EAP.
	private int itemCategory;

	/**
	 * Instantiates a new salary aggregate item.
	 *
	 * @param memento the memento
	 */
	public SalaryAggregateItem(SalaryAggregateItemGetMemento memento) {
		super();
		this.code = memento.getSalaryAggregateItemCode();
		this.name = memento.getSalaryAggregateItemName();
		this.companyCode = memento.getCompanyCode();
		this.subItemCodes = memento.getSubItemCodes();
		this.taxDivision = memento.getTaxDivision();
		this.itemCategory = memento.getItemCategory();
	}

	/**
	 * Save to memento.
	 *
	 * @param memento the memento
	 */
	public void saveToMemento(SalaryAggregateItemSetMemento memento) {
		memento.setCompanyCode(this.companyCode);
		memento.setSalaryAggregateItemCode(this.code);
		memento.setSalaryAggregateItemName(this.name);
		memento.setSubItemCodes(this.subItemCodes);
		memento.setTaxDivision(this.taxDivision);
		memento.setItemCategory(this.itemCategory);
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((code == null) ? 0 : code.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SalaryAggregateItem other = (SalaryAggregateItem) obj;
		if (code == null) {
			if (other.code != null)
				return false;
		} else if (!code.equals(other.code))
			return false;
		return true;
	}

}
