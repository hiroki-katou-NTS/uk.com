package nts.uk.ctx.pr.report.dom.salarydetail.aggregate;

import java.util.Set;

import nts.uk.ctx.pr.report.dom.company.CompanyCode;

/**
 * The Interface SalaryAggregateItemSetMemento.
 */
public interface SalaryAggregateItemSetMemento {

	/**
	 * Sets the company code.
	 *
	 * @param companyCode the new company code
	 */
	 void setCompanyCode(CompanyCode companyCode);

	/**
	 * Sets the salary aggregate item code.
	 *
	 * @param salaryAggregateItemCode the new salary aggregate item code
	 */
	void setSalaryAggregateItemCode(SalaryAggregateItemCode salaryAggregateItemCode);

	/**
	 * Sets the salary aggregate item name.
	 *
	 * @param salaryAggregateItemName the new salary aggregate item name
	 */
	void setSalaryAggregateItemName(SalaryAggregateItemName salaryAggregateItemName);

	/**
	 * Sets the sub item codes.
	 *
	 * @param subItemCodes the new sub item codes
	 */
	void setSubItemCodes(Set<String> subItemCodes);

	/**
	 * Sets the tax division.
	 *
	 * @param taxDivision the new tax division
	 */
	void setTaxDivision(TaxDivision taxDivision);

	/**
	 * Sets the item category.
	 *
	 * @param itemCategory the new item category
	 */
	// TODO: need review with EAP.
	void setItemCategory(int itemCategory);
}
