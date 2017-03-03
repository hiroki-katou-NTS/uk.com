/**
 * 
 */
package nts.uk.ctx.pr.formula.dom.repository;

import java.util.Optional;

import nts.uk.ctx.pr.formula.dom.formula.FormulaEasyCondition;
import nts.uk.ctx.pr.formula.dom.primitive.FormulaCode;
import nts.uk.ctx.pr.formula.dom.primitive.HistoryId;
import nts.uk.ctx.pr.formula.dom.primitive.ReferenceMasterCode;

/**
 * @author hungnm
 *
 */
public interface FormulaEasyConditionRepository {
	
	/**
	 * @param companyCode
	 * @param formulaCode
	 * @param historyId
	 * @param referenceMasterCode
	 * @return a formula easy condition
	 */
	Optional<FormulaEasyCondition> find(String companyCode, FormulaCode formulaCode, String historyId, ReferenceMasterCode referenceMasterCode);
	
	void add(FormulaEasyCondition formulaEasyCondition);
	
	void remove(FormulaEasyCondition formulaEasyCondition);
}
