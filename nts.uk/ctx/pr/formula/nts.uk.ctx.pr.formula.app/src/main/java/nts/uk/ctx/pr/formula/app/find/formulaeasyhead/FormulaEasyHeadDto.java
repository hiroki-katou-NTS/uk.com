/**
 * 
 */
package nts.uk.ctx.pr.formula.app.find.formulaeasyhead;

import java.math.BigDecimal;

import lombok.Value;
import nts.uk.ctx.pr.formula.dom.formula.FormulaEasyHeader;

/**
 * @author hungnm
 *
 */
@Value
public class FormulaEasyHeadDto {
	
	String companyCode;
	
	String formulaCode;
	
	String historyId;
	
	BigDecimal conditionAtr;

	BigDecimal referenceMasterNo;
	
	public static FormulaEasyHeadDto fromDomain(FormulaEasyHeader domain) {
		return new FormulaEasyHeadDto(domain.getCompanyCode(),
				domain.getFormulaCode().v(),
				domain.getHistoryId(),
				new BigDecimal(domain.getConditionAtr().value),
				new BigDecimal(domain.getReferenceMasterNo().value));
	}
}
