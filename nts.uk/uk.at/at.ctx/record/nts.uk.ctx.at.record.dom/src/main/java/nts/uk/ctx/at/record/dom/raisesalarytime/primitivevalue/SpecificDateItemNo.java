package nts.uk.ctx.at.record.dom.raisesalarytime.primitivevalue;

import java.math.BigDecimal;

import nts.arc.primitive.DecimalPrimitiveValue;
import nts.arc.primitive.constraint.DecimalMaxValue;
import nts.arc.primitive.constraint.DecimalMinValue;

/**
 * 
 * @author nampt
 * 外出枠NO
 *
 */
@DecimalMinValue("1")
@DecimalMaxValue("10")
public class SpecificDateItemNo extends DecimalPrimitiveValue<SpecificDateItemNo> {
	
	private static final long serialVersionUID = 1L;
	
	public SpecificDateItemNo(BigDecimal rawValue) {
		super(rawValue);
	}

}
