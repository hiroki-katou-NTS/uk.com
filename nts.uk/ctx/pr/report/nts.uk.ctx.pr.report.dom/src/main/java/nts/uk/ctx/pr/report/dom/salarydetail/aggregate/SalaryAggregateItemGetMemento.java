package nts.uk.ctx.pr.report.dom.salarydetail.aggregate;

import java.util.Set;

import nts.uk.ctx.pr.report.dom.company.CompanyCode;
import nts.uk.ctx.pr.report.dom.salarydetail.item.SalaryItem;

/**
 * The Interface SalaryAggregateItemGetMemento.
 */
public interface SalaryAggregateItemGetMemento {

	/**
	 * Gets the company code.
	 *
	 * @return the company code
	 */
	CompanyCode getCompanyCode();

	/**
	 * Gets the salary aggregate item code.
	 *
	 * @return the salary aggregate item code
	 */
	SalaryAggregateItemCode getSalaryAggregateItemCode();

	/**
	 * Gets the salary aggregate item name.
	 *
	 * @return the salary aggregate item name
	 */
	SalaryAggregateItemName getSalaryAggregateItemName();

	/**
	 * Gets the sub item codes.
	 *
	 * @return the sub item codes
	 */
	Set<SalaryItem> getSubItemCodes();

	/**
	 * Gets the tax division.
	 *
	 * @return the tax division
	 */
	TaxDivision getTaxDivision();

	/**
	 * Gets the item category.
	 *
	 * @return the item category
	 */
	// TODO: need review with EAP.
	int getItemCategory();
}
