package nts.uk.ctx.at.schedule.dom.shift.shiftcondition.shiftcondition;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

@StringMaxLength(40)
public class ShiftConditionCategoryName extends StringPrimitiveValue<ShiftConditionCategoryName> {

	public ShiftConditionCategoryName(String rawValue) {
		super(rawValue);
	}

	private static final long serialVersionUID = 1L;

}
