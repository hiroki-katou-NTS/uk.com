package nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timedifferencemanagement;

import nts.arc.primitive.IntegerPrimitiveValue;
import nts.arc.primitive.PrimitiveValue;
import nts.arc.primitive.constraint.CharType;
import nts.arc.primitive.constraint.IntegerMaxValue;
import nts.arc.primitive.constraint.IntegerMinValue;
import nts.arc.primitive.constraint.StringCharType;

/**
 * 地域時差
 * @author chungnt
 *
 */

@IntegerMinValue(-9999)
@IntegerMaxValue(9999)
@StringCharType(CharType.NUMERIC)
public class RegionalTime extends IntegerPrimitiveValue<PrimitiveValue<Integer>> {

	/**
	 * @param rawValue
	 */
	public RegionalTime(Integer rawValue) {
		super(rawValue);
		// TODO Auto-generated constructor stub
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 2373974409702043847L;
	
}
