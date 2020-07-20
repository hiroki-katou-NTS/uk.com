package nts.uk.ctx.at.request.dom.application.common.service.other.output;

import lombok.Getter;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;

/**
 * refactor 4
 * UKDesign.UniversalK.就業.KAF_申請.共通アルゴリズム.実績内容の取得.勤怠時間内容
 * @author Doan Duy Hung
 *
 */
@Getter
public class TimeContentOutput {
	
	/**
	 * 早退時間
	 */
	private AttendanceTime earlyLeaveTime;
	
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
