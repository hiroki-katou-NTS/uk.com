package nts.uk.ctx.at.schedule.dom.shift.businesscalendar.specificdate.primitives;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.CharType;
import nts.arc.primitive.constraint.StringCharType;
import nts.arc.primitive.constraint.StringMaxLength;

@StringCharType(CharType.ANY_HALF_WIDTH)
@StringMaxLength(24)
public class SpecificName extends StringPrimitiveValue<SpecificName>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public SpecificName(String rawValue) {
		super(rawValue);
	}

}
