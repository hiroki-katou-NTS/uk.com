/**
 * 
 */
package nts.uk.ctx.pr.formula.dom.repository;

import java.util.Optional;

import nts.uk.ctx.pr.formula.dom.formula.FormulaEasyDetail;

/**
 * @author hungnm
 *
 */
public interface FormulaEasyDetailRepository {
	/**
	 * @param companyCode
	 * @param formulaCode
	 * @param historyId
	 * @param easyFormulaCode
	 * @return formulaEasyDetail
	 */
	Optional<FormulaEasyDetail> find (String companyCode, String formulaCode, String historyId, String easyFormulaCode);
}
