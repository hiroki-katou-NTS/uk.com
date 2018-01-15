package nts.uk.ctx.at.schedule.dom.budget.premium.service;

import java.util.List;
/**
 * get name attendance item
 * @author dudt
 *
 */
public interface AttendanceNamePriniumAdapter {
	List<AttendanceNamePriniumDto> getDailyAttendanceItemName(List<Integer> dailyAttendanceItemIds);
}
