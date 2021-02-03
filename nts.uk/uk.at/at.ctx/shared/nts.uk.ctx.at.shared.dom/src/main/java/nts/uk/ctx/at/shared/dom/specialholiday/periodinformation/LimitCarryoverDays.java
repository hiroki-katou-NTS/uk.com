package nts.uk.ctx.at.shared.dom.specialholiday.periodinformation;

/**
 * 繰越上限日数
 */
import nts.arc.primitive.IntegerPrimitiveValue;
import nts.arc.primitive.constraint.IntegerRange;
@IntegerRange(min = 0, max = 999)
public class LimitCarryoverDays extends IntegerPrimitiveValue<LimitCarryoverDays> {
	/** serialVersionUID */
	private static final long serialVersionUID = 1L;

	public LimitCarryoverDays(Integer rawValue) {
		super(rawValue);
	}

}
