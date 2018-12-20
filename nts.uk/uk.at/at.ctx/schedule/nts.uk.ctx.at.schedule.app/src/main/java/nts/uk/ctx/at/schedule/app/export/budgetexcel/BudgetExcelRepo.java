package nts.uk.ctx.at.schedule.app.export.budgetexcel;

import java.util.List;

import nts.uk.ctx.at.schedule.dom.budget.external.ExternalBudget;

public interface BudgetExcelRepo {
	
	List<ExternalBudget> findAll(String companyId);
}
