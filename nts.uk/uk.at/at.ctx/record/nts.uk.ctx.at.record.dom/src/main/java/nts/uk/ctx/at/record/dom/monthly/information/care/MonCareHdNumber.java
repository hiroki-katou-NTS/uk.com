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

	@Override
	protected Double reviseRawValue(Double rawValue) {
		if (rawValue == null) return super.reviseRawValue(rawValue);
		if (rawValue > 999.5) rawValue = 999.5;
		if (rawValue < 0.0) rawValue = 0.0;
		return super.reviseRawValue(rawValue);
	}
}
