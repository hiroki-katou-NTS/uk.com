package nts.uk.ctx.at.schedule.dom.budget.premium;

import java.math.BigDecimal;

import nts.arc.primitive.DecimalPrimitiveValue;
import nts.arc.primitive.IntegerPrimitiveValue;
import nts.arc.primitive.constraint.DecimalRange;
import nts.arc.primitive.constraint.IntegerRange;
/**
 * 
 * @author Doan Duy Hung
 *
 */
@IntegerRange(max=100,min=0)
public class PremiumRate extends IntegerPrimitiveValue<PremiumRate>{

	public PremiumRate(Integer rawValue) {
		super(rawValue);
	}

}
