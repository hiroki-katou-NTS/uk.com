package nts.uk.ctx.at.record.dom.reservation.bentomenu;

import nts.arc.primitive.IntegerPrimitiveValue;
import nts.arc.primitive.constraint.IntegerRange;

/**
 * 弁当金額
 * @author Doan Duy Hung
 *
 */
@IntegerRange(min = 0, max = 99999)
public class BentoAmount extends IntegerPrimitiveValue<BentoAmount>{

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	public BentoAmount(int rawValue) {
		super(rawValue);
	}

}
