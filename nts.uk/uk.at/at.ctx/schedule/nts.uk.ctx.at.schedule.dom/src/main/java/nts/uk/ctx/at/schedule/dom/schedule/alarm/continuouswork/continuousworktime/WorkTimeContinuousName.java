package nts.uk.ctx.at.schedule.dom.schedule.alarm.continuouswork.continuousworktime;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;
/**
 * 就業時間帯連続名称
 * @author lan_lt
 *
 */
@StringMaxLength(20)
public class WorkTimeContinuousName extends StringPrimitiveValue<WorkTimeContinuousName>{

	/** serialVersionUID	 */
	private static final long serialVersionUID = 1L;

	public WorkTimeContinuousName(String rawValue) {
		super(rawValue);
	}

}
