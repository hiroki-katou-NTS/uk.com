package nts.uk.ctx.at.function.dom.alarm.checkcondition.agree36;

import nts.arc.primitive.IntegerPrimitiveValue;
import nts.arc.primitive.constraint.IntegerRange;
/**
 * 
 * @author yennth
 *
 */
@IntegerRange(max = 12, min = 0)
public class Number extends IntegerPrimitiveValue<Number>{

	public Number(Integer rawValue) {
		super(rawValue);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
