/**
 * 
 */
package nts.uk.ctx.pr.formula.dom.repository;

import java.util.Optional;

import nts.uk.ctx.pr.formula.dom.formula.FormulaEasyHead;

/**
 * @author hungnm
 *
 */
public interface FormulaEasyHeaderRepository {
	/**
	 * @param companyCode
	 * @param formulaCode
	 * @param historyId
	 * @param referenceMasterNo
	 * @return a formulaEasyHead
	 */
	Optional<FormulaEasyHead> find(String companyCode, String formulaCode, String historyId, String referenceMasterNo);
	
	void add(FormulaEasyHead formulaEasyHead);
}
