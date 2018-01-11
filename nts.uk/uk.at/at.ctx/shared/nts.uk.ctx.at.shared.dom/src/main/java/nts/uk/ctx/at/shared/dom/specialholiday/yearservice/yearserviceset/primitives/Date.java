package nts.uk.ctx.at.shared.dom.specialholiday.yearservice.yearserviceset.primitives;

import nts.arc.primitive.IntegerPrimitiveValue;
import nts.arc.primitive.PrimitiveValue;
import nts.arc.primitive.constraint.IntegerRange;
@IntegerRange(max = 99, min = 0)
public class Date extends IntegerPrimitiveValue<PrimitiveValue<Integer>>{
	/** serialVersionUID */
	private static final long serialVersionUID = 1L;
	public Date(Integer rawValue){
		super(rawValue);
	}
}
