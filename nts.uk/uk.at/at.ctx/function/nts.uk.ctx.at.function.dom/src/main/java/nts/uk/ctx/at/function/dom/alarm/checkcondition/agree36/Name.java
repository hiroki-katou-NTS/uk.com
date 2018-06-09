package nts.uk.ctx.at.function.dom.alarm.checkcondition.agree36;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;
/**
 * 36協定エラーアラーム名称
 * @author yennth
 *
 */
@StringMaxLength(20)
public class Name extends StringPrimitiveValue<MessageDisp>{
	private static final long serialVersionUID = 1L;

	public Name(String rawValue) {
		super(rawValue);
	}
}
