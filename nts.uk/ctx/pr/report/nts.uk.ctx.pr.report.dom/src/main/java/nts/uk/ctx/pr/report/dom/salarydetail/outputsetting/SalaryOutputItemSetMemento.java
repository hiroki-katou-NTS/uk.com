package nts.uk.ctx.pr.report.dom.salarydetail.outputsetting;

import nts.uk.ctx.pr.report.dom.salarydetail.SalaryItemType;

/**
 * The Interface SalaryOutputItemSetMemento.
 */
public interface SalaryOutputItemSetMemento {

	/**
	 * Sets the linkage code.
	 *
	 * @param linkageCode the new linkage code
	 */
	 void setLinkageCode(String linkageCode);

	/**
	 * Sets the type.
	 *
	 * @param salaryItemType the new type
	 */
	void setType(SalaryItemType salaryItemType);
}
