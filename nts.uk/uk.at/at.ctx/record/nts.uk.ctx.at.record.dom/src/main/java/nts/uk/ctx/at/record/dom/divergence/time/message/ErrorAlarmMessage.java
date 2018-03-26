package nts.uk.ctx.at.record.dom.divergence.time.message;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

/* エラーアラームメッセージ */
@StringMaxLength(100)
public class ErrorAlarmMessage extends StringPrimitiveValue<ErrorAlarmMessage> {

	private static final long serialVersionUID = 1L;
	
	public ErrorAlarmMessage(String rawValue) {
		super(rawValue);
	}

}
