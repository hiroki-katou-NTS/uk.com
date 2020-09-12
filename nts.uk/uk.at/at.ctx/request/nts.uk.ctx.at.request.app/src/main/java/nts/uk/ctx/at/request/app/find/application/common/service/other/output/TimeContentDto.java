package nts.uk.ctx.at.request.app.find.application.common.service.other.output;

import java.util.Optional;

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
	private Integer earlyLeaveTime;
	
	/**
	 * 早退時間2
	 */
	private Integer earlyLeaveTime2;
	
	/**
	 * 遅刻時間
	 */
	private Integer lateTime;
	
	/**
	 * 遅刻時間2
	 */
	private Integer lateTime2;
	
	public static TimeContentDto fromDomain(TimeContentOutput timeContentOutput) {
		// with mode schedule , it is null
		if (timeContentOutput == null) {
			return new TimeContentDto(
				null,
				null,
				null,
				null);
		}
		return new TimeContentDto(
				timeContentOutput.getEarlyLeaveTime().map(x -> x.v()).orElse(null), 
				timeContentOutput.getEarlyLeaveTime2().map(x -> x.v()).orElse(null), 
				timeContentOutput.getLateTime().map(x -> x.v()).orElse(null), 
				timeContentOutput.getLateTime2().map(x -> x.v()).orElse(null));
	}
	
	public TimeContentOutput toDomain() {
		return new TimeContentOutput(
				earlyLeaveTime == null ? Optional.empty() : Optional.of(new AttendanceTime(earlyLeaveTime)), 
				earlyLeaveTime2 == null ? Optional.empty() : Optional.of(new AttendanceTime(earlyLeaveTime2)), 
				lateTime == null ? Optional.empty() : Optional.of(new AttendanceTime(lateTime)), 
				lateTime2 == null ? Optional.empty() : Optional.of(new AttendanceTime(lateTime2)));
	}
}
