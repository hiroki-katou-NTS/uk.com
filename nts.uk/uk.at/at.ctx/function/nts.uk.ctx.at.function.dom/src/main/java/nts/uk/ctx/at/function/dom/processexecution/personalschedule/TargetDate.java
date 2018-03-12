package nts.uk.ctx.at.function.dom.processexecution.personalschedule;

import nts.arc.primitive.IntegerPrimitiveValue;
import nts.arc.primitive.constraint.IntegerRange;

/**
 * 対象日
 */
@IntegerRange(max = 31, min = 1)
public class TargetDate extends IntegerPrimitiveValue<TargetDate>{
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	public TargetDate(Integer rawValue) {
		super(rawValue);
	}
}
