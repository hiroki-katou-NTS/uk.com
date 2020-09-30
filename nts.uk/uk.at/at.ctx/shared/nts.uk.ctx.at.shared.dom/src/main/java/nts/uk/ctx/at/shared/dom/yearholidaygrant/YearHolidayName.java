package nts.uk.ctx.at.shared.dom.yearholidaygrant;

import java.io.Serializable;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

@StringMaxLength(20)
public class YearHolidayName extends StringPrimitiveValue<YearHolidayName> implements Serializable{

	/**
	 * Serializable
	 */
	private static final long serialVersionUID = 1L;

	public YearHolidayName(String rawValue) {
		super(rawValue);
		// TODO Auto-generated constructor stub
	}

}
