package nts.uk.ctx.at.function.dom.scheduledailytable;

import nts.arc.primitive.constraint.CharType;
import nts.arc.primitive.constraint.StringCharType;
import nts.arc.primitive.constraint.StringMaxLength;
import nts.uk.shr.com.primitive.CodePrimitiveValue;

@StringCharType(CharType.NUMERIC)
@StringMaxLength(2)
public class ScheduleDailyTableCode extends CodePrimitiveValue<ScheduleDailyTableCode> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5929240406372373059L;

	public ScheduleDailyTableCode(String rawValue) {
		super(rawValue);
	}

}
