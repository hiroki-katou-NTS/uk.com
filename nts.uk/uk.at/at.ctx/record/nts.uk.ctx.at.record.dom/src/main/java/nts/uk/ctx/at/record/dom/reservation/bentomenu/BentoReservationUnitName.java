package nts.uk.ctx.at.record.dom.reservation.bentomenu;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

/**
 * 弁当予約単位名
 * @author Doan Duy Hung
 *
 */
@StringMaxLength(2)
public class BentoReservationUnitName extends StringPrimitiveValue<BentoReservationUnitName> {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	public BentoReservationUnitName(String rawValue) {
		super(rawValue);
	}

}
