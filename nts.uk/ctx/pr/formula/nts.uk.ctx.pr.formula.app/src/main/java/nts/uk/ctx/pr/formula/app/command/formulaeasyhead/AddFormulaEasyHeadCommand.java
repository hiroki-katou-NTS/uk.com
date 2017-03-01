package nts.uk.ctx.pr.formula.app.command.formulaeasyhead;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AddFormulaEasyHeadCommand {
	
	private String companyCode;
	
	private String formulaCode;
	
	private String historyId;

	private int conditionAtr;

	private int referenceMasterNo;
}
