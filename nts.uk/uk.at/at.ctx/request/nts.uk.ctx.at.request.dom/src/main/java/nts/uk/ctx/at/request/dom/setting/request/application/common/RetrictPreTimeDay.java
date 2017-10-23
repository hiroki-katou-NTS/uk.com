package nts.uk.ctx.at.request.dom.setting.request.application.common;

import nts.arc.primitive.IntegerPrimitiveValue;
import nts.arc.primitive.constraint.IntegerRange;

//@StringCharType(CharType.NUMERIC)
//@StringMaxLength(4)
@IntegerRange(max=9999,min=1)
public class RetrictPreTimeDay extends IntegerPrimitiveValue<RetrictPreTimeDay> {

	private static final long serialVersionUID = 1L;

	public RetrictPreTimeDay(Integer rawValue) {
		super(rawValue);
	}
}
