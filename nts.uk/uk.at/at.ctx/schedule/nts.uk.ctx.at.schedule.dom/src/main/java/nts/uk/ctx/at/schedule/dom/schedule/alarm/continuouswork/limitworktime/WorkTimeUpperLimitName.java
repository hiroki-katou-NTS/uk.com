package nts.uk.ctx.at.schedule.dom.schedule.alarm.continuouswork.limitworktime;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;
/**
 * 就業時間帯連続名称
 * @author lan_lt
 *
 */
@StringMaxLength(10)
public class WorkTimeUpperLimitName extends StringPrimitiveValue<WorkTimeUpperLimitName>{

	/** serialVersionUID	 */
	private static final long serialVersionUID = 1L;

	public WorkTimeUpperLimitName(String rawValue) {
		super(rawValue);
	}

}
