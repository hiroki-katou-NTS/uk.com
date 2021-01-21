package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.erroralarm;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

/**
 * The Class ErrorAlarmMessage.
 */
// エラーアラームメッセージ
@StringMaxLength(200)
public class ErrorAlarmMessage extends StringPrimitiveValue<ErrorAlarmMessage> {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new error alarm message.
	 *
	 * @param rawValue
	 *            the raw value
	 */
	public ErrorAlarmMessage(String rawValue) {
		super(rawValue);
	}

}
