package nts.uk.ctx.at.schedule.app.export.budgetexcel;

import java.util.List;

import nts.uk.ctx.at.schedule.dom.budget.external.ExternalBudget;
/**
 * 
 * @author Hoidd
 *
 */
public interface BudgetExcelRepo {
	/**
	 * 
	 * @param companyId
	 * @return
	 */
	List<ExternalBudget> findAll(String companyId);
}
