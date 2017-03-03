package nts.uk.ctx.pr.formula.app.command.formulaeasycondition;

import java.math.BigDecimal;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author nampt
 *
 */
@Data
@NoArgsConstructor
public class RemoveFormulaEasyConditionCommand {
	
	String companyCode;
	
	String formulaCode;
	
	String historyId;

	String easyFormulaCode;

	int fixFormulaAtr;

	BigDecimal fixMoney;

	String referenceMasterCode;
}
