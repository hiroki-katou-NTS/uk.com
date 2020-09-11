package nts.uk.ctx.at.request.dom.application.common.adapter.record;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeOfExistMinus;
import nts.uk.ctx.at.shared.dom.dailyattdcal.dailyattendance.attendancetime.TimeLeavingWork;
import nts.uk.ctx.at.shared.dom.dailyattdcal.dailyattendance.breakouting.OutingTimeSheet;
import nts.uk.ctx.at.shared.dom.dailyattdcal.dailyattendance.breakouting.breaking.BreakTimeSheet;
import nts.uk.ctx.at.shared.dom.dailyattdcal.dailyattendance.common.TimeDivergenceWithCalculation;
import nts.uk.ctx.at.shared.dom.dailyattdcal.dailyattendance.common.timestamp.WorkTimeInformation;
import nts.uk.ctx.at.shared.dom.dailyattdcal.dailyattendance.shortworktime.ShortWorkingTimeSheet;
import nts.uk.ctx.at.shared.dom.dailyattdcal.dailycalprocess.calculation.other.DeductionTotalTime;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeCode;
import nts.uk.shr.com.time.TimeWithDayAttr;

/**
 * refactor 4
 * @author Doan Duy Hung
 *
 */
@AllArgsConstructor
@Getter
public class RecordWorkInfoImport {
//	社員ID
	private String employeeId;
	
//	年月日
	private GeneralDate date;
	
//	勤務種類コード
	private WorkTypeCode workTypeCode;
	
//	就業時間帯コード
	private WorkTimeCode workTimeCode;
	
//	開始時刻1
//	日別実績の出退勤．出退勤．出退勤．勤務NO＝1
	private WorkTimeInformation startTime1;	
	
//	終了時刻1
//	日別実績の出退勤．出退勤．出退勤．勤務NO＝1
	private WorkTimeInformation endTime1;
	
//	開始時刻2
//	日別実績の出退勤．出退勤．出退勤．勤務NO＝2
	private WorkTimeInformation startTime2;
	
//	終了時刻2
//	日別実績の出退勤．出退勤．出退勤．勤務NO＝2
	private WorkTimeInformation endTime2;
	
	
	
//	遅刻時間
	private AttendanceTime lateTime1;
	
//	早退時間
	private AttendanceTime earlyLeaveTime1;
	
//	遅刻時間
	private AttendanceTime lateTime2;
//	早退時間
	private AttendanceTime earlyLeaveTime2;
	
//	外出時間
	private AttendanceTime outTime1;
	
//	外出時間
	private AttendanceTime outTime2;
	
	
	
	
	
//	育児時間
	private DeductionTotalTime totalTime;
	
//	計算フレックス
	private AttendanceTimeOfExistMinus calculateFlex;
	
//	計算残業	
	private List<AttendanceTime> overTimeLst;
	
//	計算振替残業
	private List<AttendanceTime> calculateTransferOverTimeLst;
	
//	計算休日出勤
	private List<AttendanceTime> calculateHolidayLst;
	
//	計算振替
	private List<AttendanceTime> calculateTransferLst;
	
	
	
	
	
	
	
//	予定出勤時刻1
	private TimeWithDayAttr scheduledAttendence1;
	
//	予定退勤時刻1
	private TimeWithDayAttr scheduledDeparture1;
	
//	予定出勤時刻2
	private TimeWithDayAttr scheduledAttendence2;
	
//	予定退勤時刻2
	private TimeWithDayAttr scheduledDeparture2;
	
	
	
	
	
	
	
	
//	臨時時間帯
	private List<TimeLeavingWork> timeLeavingWorks;
	
//	外出時間帯
	private List<OutingTimeSheet> outHoursLst;
	
//	短時間勤務時間帯
	private List<ShortWorkingTimeSheet> shortWorkingTimeSheets;
	
//	休憩時間帯
	private List<BreakTimeSheet> breakTimeSheets;
	
	
	
	
	
//	残業深夜時間
	private TimeDivergenceWithCalculation overTimeMidnight;
	
//	法内休出深夜時間
	private TimeDivergenceWithCalculation midnightOnHoliday;
	
//	法外休出深夜時間
	private TimeDivergenceWithCalculation outOfMidnight;
	
//	祝日休出深夜時間
	private TimeDivergenceWithCalculation midnightPublicHoliday;
}
