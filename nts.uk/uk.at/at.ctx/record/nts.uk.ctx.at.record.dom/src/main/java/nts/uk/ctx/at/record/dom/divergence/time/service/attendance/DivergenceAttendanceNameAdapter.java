package nts.uk.ctx.at.record.dom.divergence.time.service.attendance;

import java.util.List;

/**
 * The Interface DivergenceAttendanceNameAdapter.
 */
public interface DivergenceAttendanceNameAdapter {

	List<DivergenceAttendanceNameDto> getDailyAttendanceItemName(List<Integer> dailyAttendanceItemIds);
}
