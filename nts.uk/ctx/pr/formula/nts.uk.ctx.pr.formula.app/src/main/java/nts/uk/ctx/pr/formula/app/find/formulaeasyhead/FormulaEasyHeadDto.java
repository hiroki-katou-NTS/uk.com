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
	
	int conditionAtr;

	int referenceMasterNo;
	
	public static FormulaEasyHeadDto fromDomain(FormulaEasyHead domain) {
		return new FormulaEasyHeadDto(domain.getConditionAtr().value, domain.getReferenceMasterNo().value);
	}
}
