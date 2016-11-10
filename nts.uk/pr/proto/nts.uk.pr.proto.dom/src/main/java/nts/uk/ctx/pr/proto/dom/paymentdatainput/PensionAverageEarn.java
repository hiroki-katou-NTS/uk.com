package nts.uk.ctx.pr.proto.dom.paymentdatainput;

import nts.arc.primitive.IntegerPrimitiveValue;

/**
 * 厚年標準報酬
 */
@IntegerRangeValue(min = 0, max = 99999999)
public class PensionAverageEarn extends IntegerPrimitiveValue<PensionAverageEarn> {

	/** serialVersionUID */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructs.
	 * 
	 * @param rawValue
	 *            raw value
	 */
	public PensionAverageEarn(Integer rawValue) {
		super(rawValue);
	}

}
