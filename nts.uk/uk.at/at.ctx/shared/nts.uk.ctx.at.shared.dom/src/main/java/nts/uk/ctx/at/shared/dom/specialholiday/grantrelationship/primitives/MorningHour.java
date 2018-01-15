package nts.uk.ctx.at.shared.dom.specialholiday.grantrelationship.primitives;

import nts.arc.primitive.IntegerPrimitiveValue;
import nts.arc.primitive.PrimitiveValue;
import nts.arc.primitive.constraint.IntegerRange;
/**
 * 
 * @author yennth
 *
 */
@IntegerRange(max=10, min=1)
public class MorningHour extends IntegerPrimitiveValue<PrimitiveValue<Integer>>{
	private static final long serialVersionUID = 1L;
	public MorningHour(Integer rawValue) {
		super(rawValue);
	}
}
