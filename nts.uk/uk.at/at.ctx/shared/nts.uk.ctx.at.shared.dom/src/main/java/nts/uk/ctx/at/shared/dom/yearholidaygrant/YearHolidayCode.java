package nts.uk.ctx.at.shared.dom.yearholidaygrant;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

@StringMaxLength(2)
public class YearHolidayCode extends StringPrimitiveValue<YearHolidayCode> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public YearHolidayCode(String rawValue) {
		super(rawValue);
		// TODO Auto-generated constructor stub
	}

}
