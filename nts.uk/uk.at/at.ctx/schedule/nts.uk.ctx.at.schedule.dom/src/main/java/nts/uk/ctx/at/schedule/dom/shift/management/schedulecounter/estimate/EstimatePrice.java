package nts.uk.ctx.at.schedule.dom.shift.management.schedulecounter.estimate;

import nts.arc.primitive.IntegerPrimitiveValue;
import nts.arc.primitive.constraint.IntegerRange;
/**
 * 目安金額
 * @author lan_lt
 *
 */
@IntegerRange(min = 0, max = 99999999)
public class EstimatePrice extends IntegerPrimitiveValue<EstimatePrice> {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new estimate price.
	 *
	 * @param rawValue the raw value
	 */
	public EstimatePrice(Integer rawValue) {
		super(rawValue);
	}

}