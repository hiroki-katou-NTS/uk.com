package nts.uk.ctx.at.record.dom.divergence.time.service.attendance;

import java.util.List;

/**
 * get attendance name
 * @author dudt
 *
 */
public interface AttendanceNameDivergenceAdapter {
	List<AttendanceNameDivergenceDto> getDailyAttendanceItemName(List<Integer> dailyAttendanceItemIds);
}
