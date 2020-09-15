package nts.uk.ctx.at.schedule.dom.schedule.alarm.simultaneousattendance.ban;

import nts.arc.primitive.constraint.CharType;
import nts.arc.primitive.constraint.StringCharType;
import nts.arc.primitive.constraint.StringMaxLength;
import nts.uk.shr.com.primitive.CodePrimitiveValue;
@StringCharType(CharType.ALPHA_NUMERIC)
@StringMaxLength(4)
public class SimultaneousAttendanceBanCode extends CodePrimitiveValue<SimultaneousAttendanceBanCode>{
	/** serialVersionUID */
	private static final long serialVersionUID = 1L;
	
	public SimultaneousAttendanceBanCode(String rawValue) {
		super(rawValue);
	}

}
