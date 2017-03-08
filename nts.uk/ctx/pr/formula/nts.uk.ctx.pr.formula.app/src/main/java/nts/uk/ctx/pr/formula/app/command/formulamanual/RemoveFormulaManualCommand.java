package nts.uk.ctx.pr.formula.app.command.formulamanual;

import java.math.BigDecimal;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author nampt
 *
 */
@Data
@NoArgsConstructor
public class RemoveFormulaManualCommand {
	
	private String companyCode;

	private String formulaCode;

	private String historyId;

	private String formulaContent;

	private BigDecimal referenceMonthAtr;

	private BigDecimal roundAtr;

	private BigDecimal roundDigit;

}
