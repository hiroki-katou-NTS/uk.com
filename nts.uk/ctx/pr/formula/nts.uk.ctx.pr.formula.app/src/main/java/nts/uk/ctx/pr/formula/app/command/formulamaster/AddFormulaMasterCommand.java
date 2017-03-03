package nts.uk.ctx.pr.formula.app.command.formulamaster;

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

	private int difficultyAtr;
	
}
