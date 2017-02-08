/**
 * 
 */
package nts.uk.ctx.pr.formula.app.find.formulaeasycondition;

import java.math.BigDecimal;

import lombok.Value;
import nts.uk.ctx.pr.formula.dom.formula.FormulaEasyCondition;

/**
 * @author hungnm
 *
 */
@Value
public class FormulaEasyConditionDto {

	String easyFormulaCode;

	int fixFormulaAtr;

	BigDecimal fixMoney;

	String referenceMasterCode;

	public static FormulaEasyConditionDto fromDomain(FormulaEasyCondition domain) {
		return new FormulaEasyConditionDto(domain.getEasyFormulaCode().v(), domain.getFixFormulaAtr().value,
				domain.getFixMoney().v(), domain.getReferenceMasterCode().v());
	}
}
