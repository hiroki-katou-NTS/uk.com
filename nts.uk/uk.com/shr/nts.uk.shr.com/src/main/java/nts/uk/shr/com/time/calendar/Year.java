package nts.uk.shr.com.time.calendar;

import nts.arc.primitive.IntegerPrimitiveValue;
import nts.gul.util.value.DiscreteValue;

public class Year extends IntegerPrimitiveValue<Year>
		implements DiscreteValue<Year> {

	/** serialVersionUID */
	private static final long serialVersionUID = 1L;

	public Year(Integer rawValue) {
		super(rawValue);
	}

	@Override
	public Year nextValue(boolean isIncrement) {
		return new Year(this.v() + (isIncrement ? 1 : -1));
	}

}
