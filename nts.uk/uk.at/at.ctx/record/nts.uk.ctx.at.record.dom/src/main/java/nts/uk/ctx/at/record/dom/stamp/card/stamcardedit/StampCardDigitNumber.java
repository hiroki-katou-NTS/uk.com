package nts.uk.ctx.at.record.dom.stamp.card.stamcardedit;

import nts.arc.primitive.IntegerPrimitiveValue;
import nts.arc.primitive.constraint.IntegerRange;


@IntegerRange(min = 0, max = 20)
public class StampCardDigitNumber extends IntegerPrimitiveValue<StampCardDigitNumber>{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public StampCardDigitNumber(Integer rawValue) {
		super(rawValue);
		// TODO Auto-generated constructor stub
	}

}
