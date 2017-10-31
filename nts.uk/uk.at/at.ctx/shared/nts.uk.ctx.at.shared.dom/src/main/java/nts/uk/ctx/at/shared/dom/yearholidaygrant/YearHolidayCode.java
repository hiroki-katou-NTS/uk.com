package nts.uk.ctx.at.shared.dom.yearholidaygrant;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;
import nts.uk.shr.com.primitive.ZeroPaddedCode;

@StringMaxLength(2)
@ZeroPaddedCode
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
