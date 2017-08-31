package nts.uk.ctx.at.record.dom.dailyperformanceformat.primitivevalue;

import nts.arc.primitive.IntegerPrimitiveValue;
import nts.arc.primitive.PrimitiveValue;
import nts.arc.primitive.constraint.IntegerMaxValue;

@IntegerMaxValue(9999)
public class OrderSorted extends IntegerPrimitiveValue<PrimitiveValue<Integer>>{

	private static final long serialVersionUID = 1L;
	
	public OrderSorted(Integer rawValue) {
		super(rawValue);
	}

}
