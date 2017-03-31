/**
 * 
 */
package nts.uk.ctx.pr.formula.app.find.formulaeasycondition;

import java.math.BigDecimal;
import java.util.List;

import lombok.Data;

/**
 * @author hungnm
 *
 */
@Data
public class FormulaEasyConditionDto {
	
	int fixFormulaAtr;
	
	String easyFormulaCode;

	BigDecimal fixMoney;

	String referenceMasterCode;
	
}
