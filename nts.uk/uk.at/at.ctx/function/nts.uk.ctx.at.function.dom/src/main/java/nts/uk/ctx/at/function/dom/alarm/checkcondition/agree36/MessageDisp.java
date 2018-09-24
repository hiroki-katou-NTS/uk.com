package nts.uk.ctx.at.function.dom.alarm.checkcondition.agree36;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;
/**
 * エラーアラームメッセージ
 * @author yennth
 *
 */
@StringMaxLength(200)
public class MessageDisp extends StringPrimitiveValue<MessageDisp>{

	public MessageDisp(String rawValue) {
		super(rawValue);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
