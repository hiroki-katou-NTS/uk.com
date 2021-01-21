package nts.uk.ctx.at.schedule.dom.shift.specificdayset.primitives;

import nts.arc.primitive.IntegerPrimitiveValue;
import nts.arc.primitive.constraint.CharType;
import nts.arc.primitive.constraint.StringCharType;
import nts.arc.primitive.constraint.StringMaxLength;

@StringCharType(CharType.NUMERIC)
@StringMaxLength(2)
public class SpecificDateItemNo extends IntegerPrimitiveValue<SpecificDateItemNo> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public SpecificDateItemNo(Integer rawValue) {
		super(rawValue);
	}
}
