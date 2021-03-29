package nts.uk.ctx.at.shared.dom.worktime.common;

import nts.uk.shr.com.enumcommon.NotUseAtr;
import java.util.List;


public interface RoundingTimeSetMemento {
	
	/**
	 * Sets the attendance minute later calculate.
	 * @return the attendance minute later calculate.
	 */
	void setAttendanceMinuteLaterCalculate(NotUseAtr attendanceMinutelater);

	/**
	 * Sets the leave work minuteAgo calculate.
	 * @return the leave work minuteAgo calculate.
	 */
	void setLeaveWorkMinuteAgoCalculate(NotUseAtr leaveMinutelater);

	/**
	 * Sets the rounding set.
	 * @return the rounding set
	 */
	void getRoundingSets(List<RoundingSet> roundingSets);

}
