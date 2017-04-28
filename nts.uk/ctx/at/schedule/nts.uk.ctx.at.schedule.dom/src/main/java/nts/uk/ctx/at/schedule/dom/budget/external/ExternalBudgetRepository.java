package nts.uk.ctx.at.schedule.dom.budget.external;

import java.util.List;

public interface ExternalBudgetRepository {
	/*
	 * get all Item of external budget
	 */
	List<ExternalBudget> findAll(String companyId);

	/*
	 * Insert
	 */
	void insert(ExternalBudget externalBudgetResult);

	/*
	 * Update
	 */
	void update(ExternalBudget externalBudgetResult);

	/*
	 * Delete
	 */
	void delete(String companyId,String externalBudgetCode);
}
