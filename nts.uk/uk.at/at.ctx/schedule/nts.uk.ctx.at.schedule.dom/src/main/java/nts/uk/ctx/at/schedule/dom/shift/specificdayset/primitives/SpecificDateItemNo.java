package nts.uk.ctx.at.schedule.dom.shift.specificdayset.primitives;

import java.math.BigDecimal;

import nts.arc.primitive.DecimalPrimitiveValue;
import nts.arc.primitive.constraint.CharType;
import nts.arc.primitive.constraint.StringCharType;
import nts.arc.primitive.constraint.StringMaxLength;

@StringCharType(CharType.NUMERIC)
@StringMaxLength(2)
public class SpecificDateItemNo extends DecimalPrimitiveValue<SpecificDateItemNo> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public SpecificDateItemNo(BigDecimal rawValue) {
		super(rawValue);
	}
}
