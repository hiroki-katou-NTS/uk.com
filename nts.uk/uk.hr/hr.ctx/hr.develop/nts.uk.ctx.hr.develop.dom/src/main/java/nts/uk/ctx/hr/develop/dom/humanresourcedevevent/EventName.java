package nts.uk.ctx.hr.develop.dom.humanresourcedevevent;

import nts.arc.primitive.constraint.CharType;
import nts.arc.primitive.constraint.StringCharType;
import nts.arc.primitive.constraint.StringMaxLength;
import nts.uk.shr.com.primitive.CodePrimitiveValue;
/**
 * イベント名
 * @author yennth
 *
 */
@StringCharType(CharType.ALPHA_NUMERIC)
@StringMaxLength(50)
public class EventName extends CodePrimitiveValue<EventName>{
	public EventName(String rawValue) {
		super(rawValue);
	}

	private static final long serialVersionUID = 1L;

}
