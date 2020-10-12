package nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.roleofovertimework.roleopenperiod;

import nts.arc.primitive.IntegerPrimitiveValue;
import nts.arc.primitive.PrimitiveValue;
import nts.arc.primitive.constraint.IntegerRange;

/**
 * The Class BreakoutFrameNo.
 */
// 休出枠NO
@IntegerRange(min = 1, max = 10)
public class BreakoutFrameNo extends IntegerPrimitiveValue<PrimitiveValue<Integer>>{

	public BreakoutFrameNo(Integer rawValue) {
		super(rawValue);
		// TODO Auto-generated constructor stub
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
