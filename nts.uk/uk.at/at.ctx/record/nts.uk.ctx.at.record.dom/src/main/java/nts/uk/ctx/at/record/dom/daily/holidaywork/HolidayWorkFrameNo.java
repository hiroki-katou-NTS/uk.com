package nts.uk.ctx.at.record.dom.daily.holidaywork;

import nts.arc.primitive.IntegerPrimitiveValue;
import nts.arc.primitive.constraint.IntegerRange;

/**
 * 休出枠No
 * 
 * @author keisuke_hoshina
 *
 */
@IntegerRange(min = 0, max = 9)
public class HolidayWorkFrameNo extends IntegerPrimitiveValue<HolidayWorkFrameNo> {
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 *
	 * @param rawValue
	 *            the raw value
	 */
	public HolidayWorkFrameNo(Integer rawValue) {
		super(rawValue);
	}
}
