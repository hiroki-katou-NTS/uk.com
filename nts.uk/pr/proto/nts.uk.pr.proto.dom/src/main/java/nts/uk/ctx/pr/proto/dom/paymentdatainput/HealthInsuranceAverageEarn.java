package nts.uk.ctx.pr.proto.dom.paymentdatainput;

import nts.arc.primitive.IntegerPrimitiveValue;

/**
 * 健保標準報酬
 */
@IntegerRangeValue(min = 0, max = 99999999)
public class HealthInsuranceAverageEarn extends IntegerPrimitiveValue<HealthInsuranceAverageEarn> {

	/** serialVersionUID */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructs.
	 * 
	 * @param rawValue
	 *            raw value
	 */
	public HealthInsuranceAverageEarn(Integer rawValue) {
		super(rawValue);
	}

}
