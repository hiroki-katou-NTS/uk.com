package nts.uk.ctx.at.function.dom.alarm.checkcondition.appapproval;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

@StringMaxLength(400)
public class ErrorAlarmMessage extends StringPrimitiveValue<ErrorAlarmMessage> {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	
	public ErrorAlarmMessage(String rawValue) {
		super(rawValue);
	}

}
