package nts.uk.ctx.pr.formula.app.command.formulahistory;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author nampt
 *
 */
@Data
@NoArgsConstructor
public class AddFormulaHistoryCommand {
	
	private String formulaCode;

	private String historyId;
	
	private int startDate;
	
	private int endDate;
	
	private int settingFormula;
	
	private int conditionAtr;

	private int referenceMasterNo;

}
