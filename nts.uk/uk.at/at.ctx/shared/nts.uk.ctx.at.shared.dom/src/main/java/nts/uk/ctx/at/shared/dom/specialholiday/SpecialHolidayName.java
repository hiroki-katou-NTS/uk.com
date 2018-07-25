package nts.uk.ctx.at.shared.dom.specialholiday;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

@StringMaxLength(20)
public class SpecialHolidayName extends StringPrimitiveValue<SpecialHolidayName> {
	/** serialVersionUID */
	private static final long serialVersionUID = 1L;
	
	public SpecialHolidayName(String rawValue) {
		super(rawValue);
	}
}
