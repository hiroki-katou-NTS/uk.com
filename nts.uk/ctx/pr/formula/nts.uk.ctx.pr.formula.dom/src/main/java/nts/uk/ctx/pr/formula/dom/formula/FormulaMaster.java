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
public class FormulaMaster extends AggregateRoot {
	@Getter
	private CompanyCode companyCode;

	@Getter
	@Setter
	private FormulaCode formulaCode;

	@Getter
	@Setter
	private HistoryId historyId;

	@Getter
	@Setter
	private DifficultyAtr difficultyAtr;

	@Getter
	@Setter
	private YearMonth startYm;

	@Getter
	@Setter
	private YearMonth endYm;

	@Getter
	@Setter
	private FormulaName formulaName;
	
	@Getter
	@Setter
	private FormulaManual formulaManual;
	
	@Getter
	@Setter
	private FormulaEasyHead formulaEasy;

	/**
	 * @param companyCode
	 * @param formulaCode
	 * @param historyId
	 * @param difficultyAtr
	 * @param startYm
	 * @param endYm
	 * @param formulaName
	 */
	public FormulaMaster(CompanyCode companyCode, FormulaCode formulaCode, HistoryId historyId,
			DifficultyAtr difficultyAtr, YearMonth startYm, YearMonth endYm, FormulaName formulaName) {
		super();
		this.companyCode = companyCode;
		this.formulaCode = formulaCode;
		this.historyId = historyId;
		this.difficultyAtr = difficultyAtr;
		this.startYm = startYm;
		this.endYm = endYm;
		this.formulaName = formulaName;
	}

	/**
	 * create basic formula From Java Type
	 * 
	 * @return LayoutMaster
	 */
	public FormulaMaster createFromJavaType(String companyCode, String formulaCode, String historyId, int difficultyAtr,
			int startYm, int endYm, String formulaName) {
		return new FormulaMaster(new CompanyCode(companyCode), new FormulaCode(formulaCode), new HistoryId(historyId),
				EnumAdaptor.valueOf(difficultyAtr, DifficultyAtr.class), new YearMonth(startYm), new YearMonth(endYm),
				new FormulaName(formulaName));
	}
	
}
