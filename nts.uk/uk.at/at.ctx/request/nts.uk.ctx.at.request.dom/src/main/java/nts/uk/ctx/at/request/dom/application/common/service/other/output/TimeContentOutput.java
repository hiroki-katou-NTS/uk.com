package nts.uk.ctx.at.request.dom.application.common.service.other.output;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;

/**
 * refactor 4
 * UKDesign.UniversalK.就業.KAF_申請.共通アルゴリズム.実績内容の取得.勤怠時間内容
 * @author Doan Duy Hung
 *
 */
@AllArgsConstructor
@Getter
public class TimeContentOutput {
	
	/**
	 * 早退時間
	 */
	private Optional<AttendanceTime> earlyLeaveTime = Optional.empty();
	
	/**
	 * 早退時間2
	 */
	private Optional<AttendanceTime> earlyLeaveTime2 = Optional.empty();
	
	/**
	 * 遅刻時間
	 */
	private Optional<AttendanceTime> lateTime = Optional.empty();
	
	/**
	 * 遅刻時間2
	 */
	private Optional<AttendanceTime> lateTime2 = Optional.empty();
	
}
