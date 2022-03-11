package nts.uk.ctx.at.record.dom.stamp.application;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

@StringMaxLength(40)
public class IntervalMessage extends StringPrimitiveValue<IntervalMessage> {

	private static final long serialVersionUID = 1L;

	public IntervalMessage(String rawValue) {
		super(rawValue);
	}

}
