package nts.uk.ctx.pr.formula.app.command.formulamaster;

import java.math.BigDecimal;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author nampt
 *
 */
@Data
@NoArgsConstructor
public class AddFormulaMasterCommand {
	
	private String ccd;

	private String formulaCode;
	
	private String formulaName;

	private BigDecimal difficultyAtr;
	
	private int startDate;
	
	private int endDate;
	
	private int conditionAtr;
	
	private int refMasterNo;
	
}
