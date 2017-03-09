package nts.uk.ctx.pr.formula.app.command.formulaeasycondition;

import java.math.BigDecimal;
import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author nampt
 *
 */
@Data
@NoArgsConstructor
public class AddFormulaEasyConditionCommand {
	
	String companyCode;
	
	String formulaCode;
	
	String historyId;

	List<EasyConditionDto> easyFormulaCode;

	int fixFormulaAtr;

	BigDecimal fixMoney;

	String referenceMasterCode;

}
