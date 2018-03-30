package nts.uk.ctx.at.record.dom.workrecord.goout;

import nts.arc.primitive.IntegerPrimitiveValue;
import nts.arc.primitive.constraint.IntegerMaxValue;
import nts.arc.primitive.constraint.IntegerMinValue;

/**
 * The Class MaxGoOut.
 */
@IntegerMinValue(0)
@IntegerMaxValue(10)
public class MaxGoOut extends IntegerPrimitiveValue<MaxGoOut> {

	/**
	 * Instantiates a new max go out.
	 *
	 * @param rawValue the raw value
	 */
	public MaxGoOut(Integer rawValue) {
		super(rawValue);
		// TODO Auto-generated constructor stub
	}

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
}

