package nts.uk.ctx.at.function.dom.alarm.checkcondition.mastercheck;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

@StringMaxLength(400)
public class ErrorAlarmMessageMSTCHK extends StringPrimitiveValue<ErrorAlarmMessageMSTCHK> {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	
	public ErrorAlarmMessageMSTCHK(String rawValue) {
		super(rawValue);
	}

}
