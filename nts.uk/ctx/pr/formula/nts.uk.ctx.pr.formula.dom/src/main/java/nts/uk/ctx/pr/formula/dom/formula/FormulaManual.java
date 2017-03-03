package nts.uk.ctx.pr.formula.dom.formula;

import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.DomainObject;
import nts.uk.ctx.pr.formula.dom.enums.ReferenceMonthAtr;
import nts.uk.ctx.pr.formula.dom.enums.RoundDigit;
import nts.uk.ctx.pr.formula.dom.enums.RoundMethod;
import nts.uk.ctx.pr.formula.dom.primitive.FormulaCode;
import nts.uk.ctx.pr.formula.dom.primitive.FormulaContent;

/**
 * @author hungnm
 *
 */
@Getter
public class FormulaManual extends DomainObject {
	
	private String companyCode;
	
	private FormulaCode formulaCode;
	
	private String historyId;

	private FormulaContent formulaContent;

	private ReferenceMonthAtr referenceMonthAtr;

	private RoundMethod roundAtr;

	private RoundDigit roundDigit;
	
	/**
	 * @param companyCode
	 * @param formulaCode
	 * @param historyId
	 * @param formulaContent
	 * @param referenceMonthAtr
	 * @param roundAtr
	 * @param roundDigit
	 */
	public FormulaManual(String companyCode, FormulaCode formulaCode, String historyId,
			FormulaContent formulaContent, ReferenceMonthAtr referenceMonthAtr, RoundMethod roundAtr,
			RoundDigit roundDigit) {
		super();
		this.companyCode = companyCode;
		this.formulaCode = formulaCode;
		this.historyId = historyId;
		this.formulaContent = formulaContent;
		this.referenceMonthAtr = referenceMonthAtr;
		this.roundAtr = roundAtr;
		this.roundDigit = roundDigit;
	}

	public FormulaManual createFromJavaType(String companyCode, String formulaCode, String historyId, String formulaContent, int referenceMonthAtr, int roundAtr,
			int roundDigit) {
		return new FormulaManual(companyCode, new FormulaCode(formulaCode), historyId, new FormulaContent(formulaContent),
				EnumAdaptor.valueOf(referenceMonthAtr, ReferenceMonthAtr.class),
				EnumAdaptor.valueOf(roundAtr, RoundMethod.class), EnumAdaptor.valueOf(roundDigit, RoundDigit.class));
	}
}
