/**
 * 
 */
package nts.uk.ctx.pr.formula.dom.repository;

import java.util.Optional;

import nts.uk.ctx.pr.formula.dom.formula.FormulaEasyCondition;

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
	Optional<FormulaEasyCondition> find(String companyCode, String formulaCode, String historyId, String referenceMasterCode);
}
