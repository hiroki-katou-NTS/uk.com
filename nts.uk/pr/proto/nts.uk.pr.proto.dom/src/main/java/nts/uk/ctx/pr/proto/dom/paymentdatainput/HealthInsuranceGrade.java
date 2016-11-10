package nts.uk.ctx.pr.proto.dom.paymentdatainput;

import nts.arc.primitive.IntegerPrimitiveValue;

/**
 * 健康保険等級
 */
@IntegerRangeValue(min = 0, max = 99)
public class HealthInsuranceGrade extends IntegerPrimitiveValue<HealthInsuranceGrade> {

	/** serialVersionUID */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructs.
	 * 
	 * @param rawValue
	 *            raw value
	 */
	public HealthInsuranceGrade(Integer rawValue) {
		super(rawValue);
	}

}
