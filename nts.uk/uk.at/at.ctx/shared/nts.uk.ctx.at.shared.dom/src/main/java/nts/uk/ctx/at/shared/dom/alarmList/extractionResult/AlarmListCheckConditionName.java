package nts.uk.ctx.at.shared.dom.alarmList.extractionResult;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

/**
 * アラームチェック条件名称
 * @author do_dt
 *
 */
@StringMaxLength(20)
public class AlarmListCheckConditionName extends StringPrimitiveValue<AlarmListCheckConditionName> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public AlarmListCheckConditionName(String rawValue) {
		super(rawValue);
		// TODO Auto-generated constructor stub
	}

}
