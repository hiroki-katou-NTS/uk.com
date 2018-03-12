package nts.uk.ctx.at.function.dom.processexecution.personalschedule;

import nts.arc.primitive.IntegerPrimitiveValue;
import nts.arc.primitive.constraint.IntegerRange;

/**
 * 作成期間
 */
@IntegerRange(max = 3, min = 1)
public class CreationPeriod extends IntegerPrimitiveValue<CreationPeriod>{
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	public CreationPeriod(Integer rawValue) {
		super(rawValue);
	}
}
