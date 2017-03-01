package nts.uk.ctx.pr.formula.dom.formula;

import lombok.Getter;
import nts.uk.ctx.pr.formula.dom.primitive.CompanyCode;
import nts.uk.ctx.pr.formula.dom.primitive.EasyFormulaCode;
import nts.uk.ctx.pr.formula.dom.primitive.FormulaCode;
import nts.uk.ctx.pr.formula.dom.primitive.HistoryId;
import nts.uk.ctx.pr.formula.dom.primitive.ReferenceItemCode;

@Getter
public class FormulaEasyStandardItem {

	private String companyCode;

	private FormulaCode formulaCode;

	private HistoryId historyId;

	private EasyFormulaCode easyFormulaCode;

	private ReferenceItemCode referenceItemCode;

	/**
	 * @param companyCode
	 * @param formulaCode
	 * @param historyId
	 * @param easyFormulaCode
	 * @param referenceItemCode
	 */
	public FormulaEasyStandardItem(String companyCode, FormulaCode formulaCode, HistoryId historyId,
			EasyFormulaCode easyFormulaCode, ReferenceItemCode referenceItemCode) {
		super();
		this.companyCode = companyCode;
		this.formulaCode = formulaCode;
		this.historyId = historyId;
		this.easyFormulaCode = easyFormulaCode;
		this.referenceItemCode = referenceItemCode;
	}

	public FormulaEasyStandardItem createFromJavaType(String companyCode, String formulaCode, String historyId,
			String easyFormulaCode, String referenceItemCode) {
		return new FormulaEasyStandardItem(companyCode, new FormulaCode(formulaCode),
				new HistoryId(historyId), new EasyFormulaCode(easyFormulaCode),
				new ReferenceItemCode(referenceItemCode));
	}

}
