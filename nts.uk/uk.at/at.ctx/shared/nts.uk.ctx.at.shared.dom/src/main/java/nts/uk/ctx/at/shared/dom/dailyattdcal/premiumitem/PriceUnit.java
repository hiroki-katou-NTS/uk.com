package nts.uk.ctx.at.shared.dom.dailyattdcal.premiumitem;

import nts.arc.primitive.IntegerPrimitiveValue;
import nts.arc.primitive.constraint.IntegerRange;

@IntegerRange(min = 0, max = 9999999)
public class PriceUnit extends IntegerPrimitiveValue<PriceUnit> {

	/***/
	private static final long serialVersionUID = 1L;

	public PriceUnit(Integer rawValue) {
		super(rawValue);
	}

}
