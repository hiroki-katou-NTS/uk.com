package nts.uk.ctx.at.schedule.dom.budget.external;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

@StringMaxLength(10)
public class ExternalBudgetName extends StringPrimitiveValue<ExternalBudgetCd> {

	/** serialVersionUID */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructs.
	 * 
	 * @param rawValue
	 */
	public ExternalBudgetName(String rawValue) {
		super(rawValue);
	}
}
