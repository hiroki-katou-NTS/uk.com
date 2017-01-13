package nts.uk.ctx.pr.formula.dom.formula;

import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.DomainObject;
import nts.uk.ctx.pr.formula.dom.enums.ReferenceMonthAtr;
import nts.uk.ctx.pr.formula.dom.enums.RoundAtr;
import nts.uk.ctx.pr.formula.dom.enums.RoundDigit;
import nts.uk.ctx.pr.formula.dom.primitive.FormulaContent;

/**
 * @author hungnm
 *
 */
public class FormulaManual extends DomainObject {
	@Getter
	private FormulaContent formulaContent;

	@Getter
	private ReferenceMonthAtr referenceMonthAtr;

	@Getter
	private RoundAtr roundAtr;

	@Getter
	private RoundDigit roundDigit;

	/**
	 * @param formulaContent
	 * @param referenceMonthAtr
	 * @param roundAtr
	 * @param roundDigit
	 */
	public FormulaManual(FormulaContent formulaContent, ReferenceMonthAtr referenceMonthAtr, RoundAtr roundAtr,
			RoundDigit roundDigit) {
		super();
		this.formulaContent = formulaContent;
		this.referenceMonthAtr = referenceMonthAtr;
		this.roundAtr = roundAtr;
		this.roundDigit = roundDigit;
	}

	public FormulaManual createFromJavaType(String formulaContent, int referenceMonthAtr, int roundAtr,
			int roundDigit) {
		return new FormulaManual(new FormulaContent(formulaContent),
				EnumAdaptor.valueOf(referenceMonthAtr, ReferenceMonthAtr.class),
				EnumAdaptor.valueOf(roundAtr, RoundAtr.class), EnumAdaptor.valueOf(roundDigit, RoundDigit.class));
	}

}
