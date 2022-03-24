package nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timedifferencemanagement;

import nts.arc.primitive.IntegerPrimitiveValue;
import nts.arc.primitive.PrimitiveValue;
import nts.arc.primitive.constraint.IntegerMaxValue;
import nts.arc.primitive.constraint.IntegerMinValue;

/**
 * 地域コード
 * @author chungnt
 *
 */

@IntegerMinValue(0)
@IntegerMaxValue(99)
public class RegionCode extends IntegerPrimitiveValue<PrimitiveValue<Integer>>{
	
	/**
	 * @param rawValue
	 */
	public RegionCode(Integer rawValue) {
		super(rawValue);
		// TODO Auto-generated constructor stub
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 2373974409702043847L;
}


