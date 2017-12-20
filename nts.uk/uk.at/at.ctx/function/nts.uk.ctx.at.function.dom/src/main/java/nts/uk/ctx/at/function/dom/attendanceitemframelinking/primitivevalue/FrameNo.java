package nts.uk.ctx.at.function.dom.attendanceitemframelinking.primitivevalue;

import nts.arc.primitive.IntegerPrimitiveValue;
import nts.arc.primitive.PrimitiveValue;
import nts.arc.primitive.constraint.IntegerMaxValue;

/**
 * 
 * @author nampt
 *
 */
@IntegerMaxValue(9999)
public class FrameNo extends IntegerPrimitiveValue<PrimitiveValue<Integer>>{
	
	/** serialVersionUID */
	private static final long serialVersionUID = 1L;
	
	public FrameNo(Integer rawValue) {
		super(rawValue);
	}

}
