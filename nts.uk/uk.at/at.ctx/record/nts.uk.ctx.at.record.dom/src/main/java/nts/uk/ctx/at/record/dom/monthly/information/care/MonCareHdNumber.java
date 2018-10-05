package nts.uk.ctx.at.record.dom.monthly.information.care;

import nts.arc.primitive.HalfIntegerPrimitiveValue;
import nts.arc.primitive.constraint.HalfIntegerRange;
/**
 * 
 * @author phongtq
 *
 */
@HalfIntegerRange(min = 0, max = 999.5)
public class MonCareHdNumber extends HalfIntegerPrimitiveValue<MonCareHdNumber>{

	private static final long serialVersionUID = 1835543330888872672L;
	
	public MonCareHdNumber(Double rawValue) {
		super(rawValue);
	}

}
