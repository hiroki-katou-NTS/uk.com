package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.premiumitem;

import nts.arc.primitive.IntegerPrimitiveValue;
import nts.arc.primitive.constraint.IntegerRange;

@IntegerRange(min = 0, max = 9999999)
public class PriceUnit extends IntegerPrimitiveValue<PriceUnit> {

	public static final PriceUnit ZERO = new PriceUnit(0);
	/***/
	private static final long serialVersionUID = 1L;

	public PriceUnit(Integer rawValue) {
		super(rawValue);
	}
}
