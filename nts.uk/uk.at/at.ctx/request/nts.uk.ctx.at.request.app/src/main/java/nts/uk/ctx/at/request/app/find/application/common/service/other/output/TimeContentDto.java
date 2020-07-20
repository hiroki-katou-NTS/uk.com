package nts.uk.ctx.at.request.app.find.application.common.service.other.output;

import lombok.Getter;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;

/**
 * refactor 4
 * @author Doan Duy Hung
 *
 */
@Getter
public class TimeContentDto {
	/**
	 * 早退時間
	 */
	private int earlyLeaveTime;
	
	/**
	 * 早退時間2
	 */
	private AttendanceTime earlyLeaveTime2;
	
	/**
	 * 遅刻時間
	 */
	private AttendanceTime lateTime;
	
	/**
	 * 遅刻時間2
	 */
	private AttendanceTime lateTime2;
}
