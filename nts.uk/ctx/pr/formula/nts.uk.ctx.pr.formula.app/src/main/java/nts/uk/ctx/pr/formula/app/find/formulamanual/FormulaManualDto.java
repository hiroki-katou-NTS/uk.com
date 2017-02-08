package nts.uk.ctx.pr.formula.app.find.formulamanual;

import lombok.Value;
import nts.uk.ctx.pr.formula.dom.formula.FormulaManual;

/**
 * @author hungnm
 *
 */
@Value
public class FormulaManualDto {
	String formulaContent;

	int referenceMonthAtr;

	int roundAtr;

	int roundDigit;

	public static FormulaManualDto fromDomain(FormulaManual domain) {
		return new FormulaManualDto(domain.getFormulaContent().v(), domain.getReferenceMonthAtr().value,
				domain.getRoundAtr().value, domain.getRoundDigit().value);
	}
}
