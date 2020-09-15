package nts.uk.ctx.at.schedule.dom.schedule.alarm.simultaneousattendance.ban;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;
/**
 * 
 * @author lan_lt
 *
 */
@StringMaxLength(10)
public class SimultaneousAttendanceBanName extends StringPrimitiveValue<SimultaneousAttendanceBanName>{

	/**	serialVersionUID */
	private static final long serialVersionUID = 1L;
	

	public SimultaneousAttendanceBanName(String rawValue) {
		super(rawValue);
	}


}
