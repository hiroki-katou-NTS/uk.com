package nts.uk.ctx.hr.develop.dom.announcement.mandatoryretirement.primitiveValue;

import nts.arc.primitive.IntegerPrimitiveValue;
import nts.arc.primitive.constraint.IntegerRange;

/**
 * @author thanhpv
 * 定年年齢
 */
@IntegerRange(min=60, max=99)
public class RetirementAge extends IntegerPrimitiveValue<RetirementAge>{

	/** serialVersionUID */
	private static final long serialVersionUID = 1L;

	public RetirementAge(Integer rawValue) {
		super(rawValue);
	}
}
