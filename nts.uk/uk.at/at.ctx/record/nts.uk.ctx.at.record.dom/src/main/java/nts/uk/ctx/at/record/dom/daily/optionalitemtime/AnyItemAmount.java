package nts.uk.ctx.at.record.dom.daily.optionalitemtime;


import nts.arc.primitive.DecimalPrimitiveValue;
import nts.arc.primitive.IntegerPrimitiveValue;
import nts.arc.primitive.constraint.IntegerRange;

@IntegerRange(min = -999999, max = 999999)
public class AnyItemAmount extends IntegerPrimitiveValue<AnyItemAmount>{

	private static final long serialVersionUID = 1L;
	
	public AnyItemAmount(Integer rawValue) {
		super(rawValue);
	}
}