package nts.uk.ctx.at.schedule.dom.budget.external;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.CharType;
import nts.arc.primitive.constraint.StringCharType;
import nts.arc.primitive.constraint.StringMaxLength;

@StringCharType(CharType.NUMERIC)
@StringMaxLength(3)
public class ExternalBudgetCd extends StringPrimitiveValue<ExternalBudgetCd> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	public ExternalBudgetCd(String rawValue) {
		super(rawValue);
	}
}
