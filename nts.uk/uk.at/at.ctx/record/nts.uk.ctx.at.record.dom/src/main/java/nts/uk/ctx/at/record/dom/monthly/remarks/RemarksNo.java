package nts.uk.ctx.at.record.dom.monthly.remarks;

import nts.arc.primitive.IntegerPrimitiveValue;
import nts.arc.primitive.constraint.IntegerMaxValue;
import nts.arc.primitive.constraint.IntegerMinValue;
/**
 * 
 * @author phongtq
 *
 */
@IntegerMinValue(1)
@IntegerMaxValue(5)
public class RemarksNo extends IntegerPrimitiveValue<RemarksNo>{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public RemarksNo(int rawValue) {
		super(rawValue);
		// TODO Auto-generated constructor stub
	}
}
