package nts.uk.ctx.at.record.dom.workrecord.temporarywork;

import nts.arc.primitive.IntegerPrimitiveValue;
import nts.arc.primitive.PrimitiveValue;
import nts.arc.primitive.constraint.IntegerMaxValue;
import nts.arc.primitive.constraint.IntegerMinValue;

/**
 * @author hoangdd
 * 最大利用回数
 */
@IntegerMinValue(0)
@IntegerMaxValue(3)
public class MaxUsage extends IntegerPrimitiveValue<PrimitiveValue<Integer>>{

	/**
	 * @param rawValue
	 */
	public MaxUsage(Integer rawValue) {
		super(rawValue);
		// TODO Auto-generated constructor stub
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 2373974409702043847L;

}

