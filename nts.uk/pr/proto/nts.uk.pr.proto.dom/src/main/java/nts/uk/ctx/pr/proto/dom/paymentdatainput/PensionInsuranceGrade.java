package nts.uk.ctx.pr.proto.dom.paymentdatainput;

import nts.arc.primitive.IntegerPrimitiveValue;

/**
 * 厚生年金保険等級
 */
@IntegerRangeValue(min = 0, max = 99)
public class PensionInsuranceGrade extends IntegerPrimitiveValue<PensionInsuranceGrade> {

	/** serialVersionUID */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructs.
	 * 
	 * @param rawValue
	 *            raw value
	 */
	public PensionInsuranceGrade(Integer rawValue) {
		super(rawValue);
	}

}
