package nts.uk.ctx.at.schedule.dom.shift.management.schedulecounter.estimate;

import nts.arc.primitive.IntegerPrimitiveValue;
import nts.arc.primitive.PrimitiveValue;
import nts.arc.primitive.constraint.IntegerMaxValue;
import nts.arc.primitive.constraint.IntegerMinValue;
/**
 * 目安金額枠NO
 * @author lan_lt
 *
 */
@IntegerMinValue(1)
@IntegerMaxValue(5)
public class EstimateAmountNo  extends IntegerPrimitiveValue<PrimitiveValue<Integer>>{

	private static final long serialVersionUID = 1L;
	
	public EstimateAmountNo(Integer rawValue) {
		super(rawValue);
	}
	
}
