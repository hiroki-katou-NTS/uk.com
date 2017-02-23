package nts.uk.ctx.pr.report.dom.salarydetail.outputsetting;

import java.util.List;

import nts.uk.ctx.pr.report.dom.salarydetail.SalaryCategory;

/**
 * The Interface SalaryCategorySettingGetMemento.
 */
public interface SalaryCategorySettingGetMemento {

	/**
	 * Gets the salary category.
	 *
	 * @return the salary category
	 */
	 SalaryCategory getSalaryCategory();

	/**
	 * Gets the salary output items.
	 *
	 * @return the salary output items
	 */
	 List<SalaryOutputItem> getSalaryOutputItems();
}
