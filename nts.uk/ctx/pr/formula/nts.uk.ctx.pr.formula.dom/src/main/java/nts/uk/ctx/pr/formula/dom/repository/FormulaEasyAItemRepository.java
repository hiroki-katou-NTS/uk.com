package nts.uk.ctx.pr.formula.dom.repository;

import java.util.List;

import nts.uk.ctx.pr.formula.dom.primitive.EasyFormulaCode;
import nts.uk.ctx.pr.formula.dom.primitive.FormulaCode;
import nts.uk.ctx.pr.formula.dom.primitive.ItemCode;

/**
 * @author hungnm
 *
 */
public interface FormulaEasyAItemRepository {
	
	/**
	 * @param companyCode
	 * @param formulaCode
	 * @param historyId
	 * @param easyFormulaCode
 	 * @return list item code of formula easy
	 */
	List<ItemCode> findAll(String companyCode, FormulaCode formulaCode, String historyId, EasyFormulaCode easyFormulaCode);
}
