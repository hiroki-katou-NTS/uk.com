package nts.uk.ctx.pr.formula.dom.formula;

import lombok.Getter;
import lombok.Setter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.YearMonth;
import nts.uk.ctx.pr.formula.dom.enums.DifficultyAtr;
import nts.uk.ctx.pr.formula.dom.primitive.CompanyCode;
import nts.uk.ctx.pr.formula.dom.primitive.FormulaCode;
import nts.uk.ctx.pr.formula.dom.primitive.FormulaName;
import nts.uk.ctx.pr.formula.dom.primitive.HistoryId;

/**
 * @author hungnm
 *
 */
@Getter
public class FormulaMaster extends AggregateRoot {

	private String companyCode;

	private FormulaCode formulaCode;

	private FormulaName formulaName;

	private DifficultyAtr difficultyAtr;

	/**
	 * @param companyCode
	 * @param formulaCode
	 * @param difficultyAtr
	 * @param formulaName
	 */
	public FormulaMaster(String companyCode, FormulaCode formulaCode, DifficultyAtr difficultyAtr,
			FormulaName formulaName) {
		super();
		this.companyCode = companyCode;
		this.formulaCode = formulaCode;
		this.difficultyAtr = difficultyAtr;
		this.formulaName = formulaName;
	}
	
	/**
	 * create basic formula From Java Type
	 * 
	 * @return LayoutMaster
	 */
	public static FormulaMaster createFromJavaType(String companyCode, String formulaCode, int difficultyAtr,
			String formulaName) {
		return new FormulaMaster(companyCode, new FormulaCode(formulaCode), 
				EnumAdaptor.valueOf(difficultyAtr, DifficultyAtr.class),
				new FormulaName(formulaName));
	}
}
