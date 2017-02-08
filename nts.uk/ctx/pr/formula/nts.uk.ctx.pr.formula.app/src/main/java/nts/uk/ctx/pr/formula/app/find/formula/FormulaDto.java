/**
 * 
 */
package nts.uk.ctx.pr.formula.app.find.formula;

import lombok.Value;
import nts.uk.ctx.pr.formula.dom.formula.FormulaMaster;

/**
 * @author hungnm
 *
 */
@Value
public class FormulaDto {
	String ccd;

	String formulaCode;

	String histId;

	String formulaName;

	int strYm;

	int endYm;

	int difficultyAtr;

	public static FormulaDto fromDomain(FormulaMaster domain) {
		return new FormulaDto(domain.getCompanyCode().v(), domain.getFormulaCode().v(), domain.getHistoryId().v(),
				domain.getFormulaName().v(), domain.getStartYm().v(), domain.getEndYm().v(),
				domain.getDifficultyAtr().value);
	}
}
