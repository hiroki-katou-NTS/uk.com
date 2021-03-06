package nts.uk.ctx.at.request.dom.application.overtime;

import java.util.Collections;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;

/**
 * Refactor5
 * @author hoangnd
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
//申請就業外深夜時間
public class OverTimeShiftNight {
	// 休出深夜時間
	private List<HolidayMidNightTime> midNightHolidayTimes = Collections.emptyList();
	// 合計外深夜時間
	private AttendanceTime midNightOutSide;
	// 残業深夜時間
	private AttendanceTime overTimeMidNight;
}
