/**
 * 
 */
package nts.uk.ctx.pr.formula.dom.repository;

import java.util.Optional;

import nts.uk.ctx.pr.formula.dom.formula.FormulaEasyDetail;
import nts.uk.ctx.pr.formula.dom.primitive.EasyFormulaCode;
import nts.uk.ctx.pr.formula.dom.primitive.FormulaCode;

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
	Optional<FormulaEasyDetail> findByPriKey (String companyCode, FormulaCode formulaCode, String historyId, EasyFormulaCode easyFormulaCode);
	
	void remove(FormulaEasyDetail formulaEasyDetail);
}
