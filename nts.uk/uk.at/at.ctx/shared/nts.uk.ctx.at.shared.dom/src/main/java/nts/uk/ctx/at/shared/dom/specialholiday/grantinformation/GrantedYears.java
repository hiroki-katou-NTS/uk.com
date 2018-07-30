package nts.uk.ctx.at.shared.dom.specialholiday.grantinformation;

import nts.arc.primitive.IntegerPrimitiveValue;
import nts.arc.primitive.constraint.IntegerRange;

/**
 * 周期
 * 
 * @author tanlv
 *
 */
@IntegerRange(min = 0, max = 99)
public class GrantedYears extends IntegerPrimitiveValue<GrantedYears> {
	/** serialVersionUID */
	private static final long serialVersionUID = 1L;

	public GrantedYears(int rawValue) {
		super(rawValue);
	}
}
