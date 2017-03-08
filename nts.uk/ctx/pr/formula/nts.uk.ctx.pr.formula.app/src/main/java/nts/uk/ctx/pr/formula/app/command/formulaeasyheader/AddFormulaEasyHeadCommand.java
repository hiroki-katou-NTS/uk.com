package nts.uk.ctx.pr.formula.app.command.formulaeasyheader;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author nampt
 *
 */

@Data
@NoArgsConstructor
public class AddFormulaEasyHeadCommand {
	
	private String companyCode;
	
	private String formulaCode;
	
	private String historyId;

	private int conditionAtr;

	private int referenceMasterNo;
}
