package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.personcostcalc.premiumitem;

import nts.arc.primitive.IntegerPrimitiveValue;
import nts.arc.primitive.constraint.IntegerRange;
/**
 * 
 * @author Doan Duy Hung
 *
 */
@IntegerRange(max=999,min=1)
public class PremiumRate extends IntegerPrimitiveValue<PremiumRate>{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4727391399987061483L;

	public PremiumRate(Integer rawValue) {
		super(rawValue);
	}

}
