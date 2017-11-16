package nts.uk.ctx.at.schedule.dom.shift.shiftcondition.shiftcondition;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

/**
 * 条件エラーメッセージ
 * 
 * @author sonnh1
 *
 */
@StringMaxLength(100)
public class ShiftConditionErrorMessage extends StringPrimitiveValue<ShiftConditionErrorMessage> {

	public ShiftConditionErrorMessage(String rawValue) {
		super(rawValue);
	}

	private static final long serialVersionUID = 1L;
}
