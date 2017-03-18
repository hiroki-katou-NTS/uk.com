package nts.uk.ctx.pr.formula.app.command.formulahistory;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author nampt
 *
 */
@Data
@NoArgsConstructor
public class UpdateFormulaHistoryCommand {
	
	private String formulaCode;
	
	private String historyId;
	
	/* Formula header */
	/**
	 * B_SEL_002
	 * CONDITION_ATR
	 */
	private int conditionAtr;
}
