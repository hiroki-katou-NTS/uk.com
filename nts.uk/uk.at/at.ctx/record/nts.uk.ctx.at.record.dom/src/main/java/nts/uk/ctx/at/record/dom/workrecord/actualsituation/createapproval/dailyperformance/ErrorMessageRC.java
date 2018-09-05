package nts.uk.ctx.at.record.dom.workrecord.actualsituation.createapproval.dailyperformance;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

@StringMaxLength(1000)
public class ErrorMessageRC extends StringPrimitiveValue<ErrorMessageRC> {

	public ErrorMessageRC(String rawValue) {
		super(rawValue);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
