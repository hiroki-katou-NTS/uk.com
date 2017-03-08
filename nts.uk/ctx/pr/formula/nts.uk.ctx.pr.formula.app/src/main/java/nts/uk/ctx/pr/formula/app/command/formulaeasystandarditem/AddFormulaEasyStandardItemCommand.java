package nts.uk.ctx.pr.formula.app.command.formulaeasystandarditem;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author nampt
 *
 */
@Data
@NoArgsConstructor
public class AddFormulaEasyStandardItemCommand {
	
	String companyCode;

	String formulaCode;

	String historyId;

	String easyFormulaCode;

	String referenceItemCode;
	
}
