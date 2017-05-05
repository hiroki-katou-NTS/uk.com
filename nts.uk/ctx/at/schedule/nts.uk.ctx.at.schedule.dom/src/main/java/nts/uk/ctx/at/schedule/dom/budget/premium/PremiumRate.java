package nts.uk.ctx.at.schedule.dom.budget.premium;

import java.math.BigDecimal;

import nts.arc.primitive.DecimalPrimitiveValue;
import nts.arc.primitive.constraint.DecimalRange;
/**
 * 
 * @author Doan Duy Hung
 *
 */
@DecimalRange(max="100",min="0")
public class PremiumRate extends DecimalPrimitiveValue<PremiumRate>{

	public PremiumRate(BigDecimal rawValue) {
		super(rawValue);
	}

}
