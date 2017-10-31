package nts.uk.ctx.at.schedule.dom.shift.workpairpattern;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

/**
 * 勤務ペアパターングループ名称
 * 
 * @author sonnh1
 *
 */
@StringMaxLength(12)
public class WorkPairPatternGroupName extends StringPrimitiveValue<WorkPairPatternGroupName> {
	private static final long serialVersionUID = 1L;

	public WorkPairPatternGroupName(String rawValue) {
		super(rawValue);
	}
}
