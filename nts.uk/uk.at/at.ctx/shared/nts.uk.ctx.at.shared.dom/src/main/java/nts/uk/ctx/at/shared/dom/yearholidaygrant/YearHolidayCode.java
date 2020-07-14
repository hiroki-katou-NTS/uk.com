package nts.uk.ctx.at.shared.dom.yearholidaygrant;

import java.io.Serializable;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.CharType;
import nts.arc.primitive.constraint.StringCharType;
import nts.arc.primitive.constraint.StringMaxLength;

@StringCharType(CharType.ALPHA_NUMERIC)
@StringMaxLength(2)
public class YearHolidayCode extends StringPrimitiveValue<YearHolidayCode> implements Serializable{

	/**
	 * Serializable
	 */
	private static final long serialVersionUID = 1L;

	public YearHolidayCode(String rawValue) {
		super(rawValue);
		// TODO Auto-generated constructor stub
	}

}
