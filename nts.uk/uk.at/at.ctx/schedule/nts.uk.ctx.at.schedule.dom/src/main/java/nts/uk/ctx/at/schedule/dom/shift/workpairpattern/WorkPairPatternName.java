package nts.uk.ctx.at.schedule.dom.shift.workpairpattern;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

/**
 * 勤務ペアパターン名称
 * 
 * @author sonnh1
 *
 */
@StringMaxLength(12)
public class WorkPairPatternName extends StringPrimitiveValue<WorkPairPatternName> {
	private static final long serialVersionUID = 1L;

	public WorkPairPatternName(String rawValue) {
		super(rawValue);
	}
}
