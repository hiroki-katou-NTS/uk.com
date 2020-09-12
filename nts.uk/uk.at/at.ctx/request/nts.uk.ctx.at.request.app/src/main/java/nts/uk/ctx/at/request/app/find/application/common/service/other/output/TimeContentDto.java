package nts.uk.ctx.at.request.app.find.application.common.service.other.output;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.ctx.at.request.dom.application.common.service.other.output.TimeContentOutput;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;

/**
 * refactor 4
 * @author Doan Duy Hung
 *
 */
@AllArgsConstructor
@Getter
public class TimeContentDto {
	/**
	 * 早退時間
	 */
	private int earlyLeaveTime;
	
	/**
	 * 早退時間2
	 */
	private int earlyLeaveTime2;
	
	/**
	 * 遅刻時間
	 */
	private int lateTime;
	
	/**
	 * 遅刻時間2
	 */
	private int lateTime2;
	
	public static TimeContentDto fromDomain(TimeContentOutput timeContentOutput) {
		return new TimeContentDto(
				timeContentOutput.getEarlyLeaveTime().v(), 
				timeContentOutput.getEarlyLeaveTime2().v(), 
				timeContentOutput.getLateTime().v(), 
				timeContentOutput.getLateTime2().v());
	}
	
	public TimeContentOutput toDomain() {
		return new TimeContentOutput(
				new AttendanceTime(earlyLeaveTime), 
				new AttendanceTime(earlyLeaveTime2), 
				new AttendanceTime(lateTime), 
				new AttendanceTime(lateTime2));
	}
}
