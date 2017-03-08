package nts.uk.ctx.pr.formula.dom.repository;

import java.util.List;

import nts.uk.ctx.pr.formula.dom.formula.FormulaEasyStandardItem;
import nts.uk.ctx.pr.formula.dom.primitive.EasyFormulaCode;
import nts.uk.ctx.pr.formula.dom.primitive.FormulaCode;
import nts.uk.ctx.pr.formula.dom.primitive.ItemCode;

/**
 * @author hungnm
 *
 */
public interface FormulaEasyStandardItemRepository {
	
	/**
	 * @param companyCode
	 * @param formulaCode
	 * @param historyId
	 * @param easyFormulaCode
 	 * @return list item code of formula easy
	 */
	List<FormulaEasyStandardItem> findAll(String companyCode, FormulaCode formulaCode, String historyId, EasyFormulaCode easyFormulaCode);
	
	void remove(FormulaEasyStandardItem formulaEasyStandardItem);
	
	void add(FormulaEasyStandardItem formulaEasyStandardItem);
}
