package nts.uk.ctx.at.record.dom.reservation.bento;

import nts.arc.primitive.IntegerPrimitiveValue;
import nts.arc.primitive.constraint.IntegerRange;

/**
 * 弁当予約個数
 * @author Doan Duy Hung
 *
 */
@IntegerRange(min = 1, max = 99)
public class BentoReservationCount extends IntegerPrimitiveValue<BentoReservationCount>{

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	public BentoReservationCount(int rawValue) {
		super(rawValue);
	}

}
