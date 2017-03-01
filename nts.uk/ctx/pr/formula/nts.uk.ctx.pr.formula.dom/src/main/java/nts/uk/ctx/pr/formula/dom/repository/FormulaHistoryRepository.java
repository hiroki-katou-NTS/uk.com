package nts.uk.ctx.pr.formula.dom.repository;

import java.util.List;

import nts.uk.ctx.pr.formula.dom.formula.FormulaHistory;

public interface FormulaHistoryRepository {
	
	/**
	 * @param companyCode
	 * @return list of formula history in a company
	 */
	List<FormulaHistory> findAll(String companyCode);
	
	void add (FormulaHistory formulaHistory);
	
}
