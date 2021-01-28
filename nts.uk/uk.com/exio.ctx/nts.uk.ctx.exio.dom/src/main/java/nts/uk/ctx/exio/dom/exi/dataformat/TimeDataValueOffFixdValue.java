package nts.uk.ctx.exio.dom.exi.dataformat;

import nts.arc.primitive.IntegerPrimitiveValue;
import nts.arc.primitive.constraint.HalfIntegerRange;
@HalfIntegerRange(min = 0, max = 12)
public class TimeDataValueOffFixdValue extends IntegerPrimitiveValue<TimeDataValueOffFixdValue>{

	public TimeDataValueOffFixdValue(Integer rawValue) {
		super(rawValue);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
