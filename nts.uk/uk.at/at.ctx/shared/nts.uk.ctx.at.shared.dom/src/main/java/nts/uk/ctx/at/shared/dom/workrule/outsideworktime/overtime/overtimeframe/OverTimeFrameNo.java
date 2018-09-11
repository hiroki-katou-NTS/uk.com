package nts.uk.ctx.at.shared.dom.workrule.outsideworktime.overtime.overtimeframe;

import nts.arc.primitive.IntegerPrimitiveValue;
import nts.arc.primitive.constraint.IntegerRange;
/**
 * 残業枠No
 * @author keisuke_hoshina
 *
 */

@IntegerRange(min = 1, max = 10)
public class OverTimeFrameNo extends IntegerPrimitiveValue<OverTimeFrameNo>{
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 *
	 * @param rawValue
	 *            the raw value
	 */
	public OverTimeFrameNo(Integer rawValue) {
		super(rawValue);
	}
}
