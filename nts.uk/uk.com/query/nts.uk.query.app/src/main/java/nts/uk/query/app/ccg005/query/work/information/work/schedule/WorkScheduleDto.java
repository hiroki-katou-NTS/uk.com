package nts.uk.query.app.ccg005.query.work.information.work.schedule;

import java.util.List;

import lombok.Builder;
import lombok.Data;
import nts.arc.time.GeneralDate;
import nts.uk.query.app.ccg005.query.work.information.work.performance.dto.WorkInfoOfDailyAttendanceDto;
import nts.uk.query.app.ccg005.query.work.information.work.schedule.dto.AffiliationInforOfDailyAttdDto;
import nts.uk.query.app.ccg005.query.work.information.work.schedule.dto.AttendanceTimeOfDailyAttendanceDto;
import nts.uk.query.app.ccg005.query.work.information.work.schedule.dto.BreakTimeOfDailyAttdDto;
import nts.uk.query.app.ccg005.query.work.information.work.schedule.dto.EditStateOfDailyAttdDto;
import nts.uk.query.app.ccg005.query.work.information.work.schedule.dto.OutingTimeOfDailyAttdDto;
import nts.uk.query.app.ccg005.query.work.information.work.schedule.dto.ShortTimeOfDailyAttdDto;
import nts.uk.query.app.ccg005.query.work.information.work.schedule.dto.TimeLeavingOfDailyAttdDto;

@Data
@Builder
public class WorkScheduleDto {
	/** 社員ID(employeeID) */
	private String employeeID;

	/** 社員ID(年月日(YMD) */
	private GeneralDate ymd;

	/** 確定区分 */
	private Integer confirmedATR;

	/** 勤務情報 */
	private WorkInfoOfDailyAttendanceDto workInfo;

	/** 所属情報 **/
	private AffiliationInforOfDailyAttdDto affInfo;
	
	/** 休憩時間帯**/
	private BreakTimeOfDailyAttdDto lstBreakTime;
	
	/** 編集状態 **/
	private List<EditStateOfDailyAttdDto> lstEditState;

	/** 出退勤 */
	private TimeLeavingOfDailyAttdDto optTimeLeaving;

	/** 勤怠時間 */
	private AttendanceTimeOfDailyAttendanceDto optAttendanceTime;

	/** 短時間勤務 */
	private ShortTimeOfDailyAttdDto optSortTimeWork;

	/** 外出時間帯 */
	private OutingTimeOfDailyAttdDto outingTime;
}
