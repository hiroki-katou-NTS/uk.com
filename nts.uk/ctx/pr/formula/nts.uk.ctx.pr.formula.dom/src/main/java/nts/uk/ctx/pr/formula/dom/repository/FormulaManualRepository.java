/**
 * 
 */
package nts.uk.ctx.pr.formula.dom.repository;

import java.util.Optional;

import nts.uk.ctx.pr.formula.dom.formula.FormulaManual;

/**
 * @author hungnm
 *
 */
public interface FormulaManualRepository {
	/**
	 * @param companyCode
	 * @param formulaCode
	 * @param historyId
	 * @return a formula manual
	 */
	Optional<FormulaManual> find(String companyCode, String formulaCode, String historyId);
}
