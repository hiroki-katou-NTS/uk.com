/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.report.dom.salarydetail.printsetting;

import nts.uk.ctx.pr.report.dom.salarydetail.SalaryOutputDistinction;

/**
 * The Interface SalaryPrintSettingGetMemento.
 */
public interface SalaryPrintSettingGetMemento {

	/**
	 * Gets the company code.
	 *
	 * @return the company code
	 */
	 String getCompanyCode();

	/**
	 * Gets the output distinction.
	 *
	 * @return the output distinction
	 */
		SalaryOutputDistinction getOutputDistinction();

	/**
	 * Gets the show department monthly amount.
	 *
	 * @return the show department monthly amount
	 */
		Boolean getShowDepartmentMonthlyAmount();

	/**
	 * Gets the show detail.
	 *
	 * @return the show detail
	 */
		Boolean getShowDetail();

	/**
	 * Gets the show division monthly total.
	 *
	 * @return the show division monthly total
	 */
		Boolean getShowDivisionMonthlyTotal();

	/**
	 * Gets the show division total.
	 *
	 * @return the show division total
	 */
		Boolean getShowDivisionTotal();

	/**
	 * Gets the show hierarchy 1.
	 *
	 * @return the show hierarchy 1
	 */
		Boolean getShowHierarchy1();

	/**
	 * Gets the showierarchy 2.
	 *
	 * @return the showierarchy 2
	 */
		Boolean getShowierarchy2();

	/**
	 * Gets the showierarchy 3.
	 *
	 * @return the showierarchy 3
	 */
		Boolean getShowierarchy3();

	/**
	 * Gets the shwowierarchy 4.
	 *
	 * @return the shwowierarchy 4
	 */
		Boolean getShwowierarchy4();

	/**
	 * Gets the showierarchy 5.
	 *
	 * @return the showierarchy 5
	 */
		Boolean getShowierarchy5();
}
