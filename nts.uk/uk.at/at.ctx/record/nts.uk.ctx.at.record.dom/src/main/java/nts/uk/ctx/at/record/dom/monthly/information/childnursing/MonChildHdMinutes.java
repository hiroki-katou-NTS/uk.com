package nts.uk.ctx.at.record.dom.monthly.information.childnursing;

import nts.arc.primitive.HalfIntegerPrimitiveValue;
import nts.arc.primitive.constraint.HalfIntegerRange;
/**
 * 
 * @author phongtq
 *
 */
@HalfIntegerRange(min = 0, max = 999.59)
public class MonChildHdMinutes extends HalfIntegerPrimitiveValue<MonChildHdMinutes>{

	private static final long serialVersionUID = 1991334616598114830L;

	public MonChildHdMinutes(Double rawValue){
		super(rawValue);
	}
}
