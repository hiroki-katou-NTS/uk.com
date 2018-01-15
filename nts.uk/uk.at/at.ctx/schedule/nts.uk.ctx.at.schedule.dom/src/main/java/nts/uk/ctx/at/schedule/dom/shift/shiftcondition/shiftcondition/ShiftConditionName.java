package nts.uk.ctx.at.schedule.dom.shift.shiftcondition.shiftcondition;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

/**
 * 
 * @author sonnh1
 *
 */
@StringMaxLength(40)
public class ShiftConditionName extends StringPrimitiveValue<ShiftConditionName> {

	public ShiftConditionName(String rawValue) {
		super(rawValue);
	}

	private static final long serialVersionUID = 1L;
}
