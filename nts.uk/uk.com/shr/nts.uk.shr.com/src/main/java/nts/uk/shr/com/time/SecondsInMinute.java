package nts.uk.shr.com.time;

import nts.arc.primitive.IntegerPrimitiveValue;
import nts.arc.primitive.constraint.IntegerRange;

/**
 * 時刻としての秒数
 */
@IntegerRange(min = 0, max = 59)
public class SecondsAsTimePoint extends IntegerPrimitiveValue<SecondsAsTimePoint> {

	/** serialVersionUID */
	private static final long serialVersionUID = 1L;

	public SecondsAsTimePoint(Integer rawValue) {
		super(rawValue);
	}
}
