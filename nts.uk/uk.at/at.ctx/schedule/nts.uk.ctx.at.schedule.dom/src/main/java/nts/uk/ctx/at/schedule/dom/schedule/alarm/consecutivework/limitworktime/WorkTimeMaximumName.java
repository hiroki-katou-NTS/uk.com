package nts.uk.ctx.at.schedule.dom.schedule.alarm.consecutivework.limitworktime;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;
/**
 * 就業時間帯連続名称
 * @author lan_lt
 *
 */
@StringMaxLength(20)
public class WorkTimeMaximumName extends StringPrimitiveValue<WorkTimeMaximumName>{
	/** serialVersionUID	 */
	private static final long serialVersionUID = 1L;

	public WorkTimeMaximumName(String rawValue) {
		super(rawValue);
	}

}
