/**
 * 
 */
package nts.uk.ctx.pr.formula.app.find.formulaeasystandarditem;

import lombok.Value;
import nts.uk.ctx.pr.formula.dom.formula.FormulaEasyStandardItem;

/**
 * @author hungnm, nampt
 *
 */
@Value
public class FormulaEasyStandardItemDto {

	String companyCode;

	String formulaCode;

	String historyId;

	String easyFormulaCode;

	String referenceItemCode;

	public static FormulaEasyStandardItemDto fromDomain(FormulaEasyStandardItem domain) {
		return new FormulaEasyStandardItemDto(domain.getCompanyCode(), domain.getFormulaCode().v(),
				domain.getHistoryId(), domain.getEasyFormulaCode().v(), domain.getReferenceItemCode().v());
	}
}
