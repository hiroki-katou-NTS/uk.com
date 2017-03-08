package nts.uk.ctx.pr.formula.app.command.formulahistory;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author nampt
 *
 */
@Data
@NoArgsConstructor
public class RemoveFormulaHistoryCommand {
	
	String companyCode;

	String formulaCode;

	String historyId;
	
	int startDate;
	
	int endDate;

}
