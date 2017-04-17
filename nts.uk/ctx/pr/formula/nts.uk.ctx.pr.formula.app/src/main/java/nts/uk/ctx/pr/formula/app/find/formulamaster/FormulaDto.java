/**
 * 
 */
package nts.uk.ctx.pr.formula.app.find.formulamaster;

import lombok.Value;
import nts.uk.ctx.pr.formula.dom.formula.FormulaMaster;

/**
 * @author hungnm
 *
 */
@Value
public class FormulaDto {
	
	private String ccd;

	private String formulaCode;
	
	private String formulaName;

	private int difficultyAtr;

	public static FormulaDto fromDomain(FormulaMaster domain) {
		return new FormulaDto(domain.getCompanyCode(), domain.getFormulaCode().v(),
				domain.getFormulaName().v(), domain.getDifficultyAtr().value);
	}
}
