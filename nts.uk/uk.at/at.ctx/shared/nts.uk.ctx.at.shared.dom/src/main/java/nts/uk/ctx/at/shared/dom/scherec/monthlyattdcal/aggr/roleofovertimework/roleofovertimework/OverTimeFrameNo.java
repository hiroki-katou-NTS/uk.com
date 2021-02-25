package nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.roleofovertimework.roleofovertimework;

import nts.arc.primitive.IntegerPrimitiveValue;
import nts.arc.primitive.PrimitiveValue;
import nts.arc.primitive.constraint.IntegerRange;

/**
 * The Class OverTimeFrameNo.
 */
// 残業枠NO
@IntegerRange(min = 1, max = 10)
public class OverTimeFrameNo extends IntegerPrimitiveValue<PrimitiveValue<Integer>>{

	/**
	 * Instantiates a new over time frame no.
	 *
	 * @param rawValue the raw value
	 */
	public OverTimeFrameNo(Integer rawValue) {
		super(rawValue);
		// TODO Auto-generated constructor stub
	}

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

}
