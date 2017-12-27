package nts.uk.ctx.at.function.dom.alarm.checkcondition;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

/**
 * 
 * @author HungTT - アラームチェック条件名称
 *
 */

@StringMaxLength(20)
public class AlarmCheckConditionName extends StringPrimitiveValue<AlarmCheckConditionName> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public AlarmCheckConditionName(String rawValue) {
		super(rawValue);
	}

}
