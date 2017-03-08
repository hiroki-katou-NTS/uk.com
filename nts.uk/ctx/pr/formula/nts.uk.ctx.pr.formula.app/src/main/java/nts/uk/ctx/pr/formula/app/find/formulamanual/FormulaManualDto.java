package nts.uk.ctx.pr.formula.app.find.formulamanual;

import java.math.BigDecimal;

import lombok.Value;
import nts.uk.ctx.pr.formula.dom.formula.FormulaManual;

/**
 * @author hungnm
 *
 */
@Value
public class FormulaManualDto {

	String companyCode;

	String formulaCode;

	String historyId;

	String formulaContent;

	BigDecimal referenceMonthAtr;

	BigDecimal roundAtr;

	BigDecimal roundDigit;

	public static FormulaManualDto fromDomain(FormulaManual domain) {
		return new FormulaManualDto(domain.getCompanyCode(), domain.getFormulaCode().v(), domain.getHistoryId(),
				domain.getFormulaContent().v(), new BigDecimal(domain.getReferenceMonthAtr().value),new BigDecimal(domain.getRoundAtr().value),
				new BigDecimal(domain.getRoundDigit().value));
	}
}
