package nts.uk.ctx.at.function.dom.alarm.extractionrange.daily;

import nts.arc.primitive.IntegerPrimitiveValue;
import nts.arc.primitive.constraint.IntegerRange;

@IntegerRange(min = 0, max = 31)
public class Day extends IntegerPrimitiveValue<Day> {

	private static final long serialVersionUID = 1L;

	public Day(Integer rawValue) {
		super(rawValue);
		// TODO Auto-generated constructor stub
	}

	

}
