package nts.uk.ctx.at.record.dom.divergencetime.service.attendance;

import java.util.List;

/**
 * get attendance name
 * @author dudt
 *
 */
public interface AttendanceNameDivergenceAdapter {
	List<AttendanceNameDivergenceDto> getDailyAttendanceItemName(List<Integer> dailyAttendanceItemIds);
}
