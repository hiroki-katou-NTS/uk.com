/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.report.dom.salarydetail.aggregate;

import java.util.Set;

import lombok.Getter;
import nts.arc.layer.dom.DomainObject;
import nts.uk.ctx.pr.report.dom.company.CompanyCode;

/**
 * The Class SalaryAggregateItem.
 */
@Getter
public class SalaryAggregateItem extends DomainObject {

	/** The code. */
	private SalaryAggregateItemCode code;

	/** The company code. */
	private CompanyCode companyCode;

	/** The name. */
	private SalaryAggregateItemName name;

	/** The sub item codes. */
	private Set<String> subItemCodes;

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
		memento.setItemCategory(this.itemCategory);
		memento.setSubItemCodes(this.subItemCodes);
		memento.setTaxDivision(this.taxDivision);
	}

}
