package nts.uk.ctx.at.shared.dom.worktime.common;



import java.util.List;


import nts.uk.shr.com.enumcommon.NotUseAtr;

public interface RoundingTimeGetMemento {
	
	/**
	 * Gets the attendance minute later calculate.
	 * @return the attendance minute later calculate.
	 */
	NotUseAtr getAttendanceMinuteLaterCalculate();

	/**
	 * Gets the leave work minuteAgo calculate.
	 * @return the leave work minuteAgo calculate.
	 */
	NotUseAtr getLeaveWorkMinuteAgoCalculate();

	/**
	 * Gets the rounding set.
	 * @return the rounding set
	 */
	List<RoundingSet> getRoundingSets();
	

}
