/**
 * 
 */
package nts.uk.ctx.pr.formula.app.find.formulaeasyhead;

import lombok.Value;
import nts.uk.ctx.pr.formula.dom.formula.FormulaEasyHead;

/**
 * @author hungnm
 *
 */
@Value
public class FormulaEasyHeadDto {
	
	String companyCode;
	
	String formulaCode;
	
	String historyId;
	
	int conditionAtr;

	int referenceMasterNo;
	
	public static FormulaEasyHeadDto fromDomain(FormulaEasyHead domain) {
		return new FormulaEasyHeadDto(domain.getCompanyCode(),
				domain.getFormulaCode().v(),
				domain.getHistoryId().v(),
				domain.getConditionAtr().value,
				domain.getReferenceMasterNo().value);
	}
}
