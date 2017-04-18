/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.report.dom.salarydetail.aggregate;

import lombok.Getter;

/**
 * The Class SalaryAggregateItemHeader.
 */
@Getter
public class SalaryAggregateItemHeader {

	/** The code. */
	private SalaryAggregateItemCode aggregateItemCode;

	/** The tax division. */
	private TaxDivision taxDivision;

	/** The company code. */
	private String companyCode;

	/**
	 * Instantiates a new salary aggregate item header.
	 */
	public SalaryAggregateItemHeader(SalaryAggregateItemHeaderGetMemento memento) {
		super();
		this.aggregateItemCode = memento.getSalaryAggregateItemCode();
		this.taxDivision = memento.getTaxDivision();
		this.companyCode = memento.getCompanyCode();
	}

	/**
	 * Save to memento.
	 *
	 * @param memento
	 *            the memento
	 */
	public void saveToMemento(SalaryAggregateItemHeaderSetMemento memento) {
		memento.setCompanyCode(this.companyCode);
		memento.setSalaryAggregateItemCode(this.aggregateItemCode);
		memento.setTaxDivision(this.taxDivision);
	}
}
