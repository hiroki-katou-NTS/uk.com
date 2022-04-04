package nts.uk.ctx.at.request.dom.application.common.adapter.record;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.dom.application.common.service.other.output.TimePlaceOutput;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeOfExistMinus;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.attendancetime.TimeLeavingWork;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.breakouting.OutingTimeSheet;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.breakouting.breaking.BreakTimeSheet;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.DeductionTotalTime;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.TimeDivergenceWithCalculation;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.timestamp.WorkTimeInformation;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.shortworktime.ShortWorkingTimeSheet;
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
	private Optional<WorkTimeInformation> startTime1 = Optional.empty();	
	
//	終了時刻1
//	日別実績の出退勤．出退勤．出退勤．勤務NO＝1
	private Optional<WorkTimeInformation> endTime1 = Optional.empty();
	
//	開始時刻2
//	日別実績の出退勤．出退勤．出退勤．勤務NO＝2
	private Optional<WorkTimeInformation> startTime2 = Optional.empty();
	
//	終了時刻2
//	日別実績の出退勤．出退勤．出退勤．勤務NO＝2
	private Optional<WorkTimeInformation> endTime2 = Optional.empty();
	
	
	
//	遅刻時間
	private Optional<AttendanceTime> lateTime1 = Optional.empty();
	
//	早退時間
	private Optional<AttendanceTime> earlyLeaveTime1 = Optional.empty();
	
//	遅刻時間
	private Optional<AttendanceTime> lateTime2 = Optional.empty();
//	早退時間
	private Optional<AttendanceTime> earlyLeaveTime2 = Optional.empty();
	
//	外出時間
	private Optional<AttendanceTime> outTime1 = Optional.empty();
	
//	外出時間
	private Optional<AttendanceTime> outTime2 = Optional.empty();
	
	
	
	
	
//	育児時間
	private DeductionTotalTime totalTime;
	
//	計算フレックス
	private AttendanceTimeOfExistMinus calculateFlex;
	
//	計算残業	
	private Map<Integer, AttendanceTime> overTimeLst;
	
//	計算振替残業
	private Map<Integer, AttendanceTime> calculateTransferOverTimeLst;
	
//	計算休日出勤
	private Map<Integer, AttendanceTime> calculateHolidayLst;
	
//	計算振替
	private Map<Integer, AttendanceTime> calculateTransferLst;
	
	
	
	
	
	
	
//	予定出勤時刻1
	private TimeWithDayAttr scheduledAttendence1;
	
//	予定退勤時刻1
	private TimeWithDayAttr scheduledDeparture1;
	
//	予定出勤時刻2
	private Optional<TimeWithDayAttr> scheduledAttendence2 = Optional.empty();
	
//	予定退勤時刻2
	private Optional<TimeWithDayAttr> scheduledDeparture2 = Optional.empty();
	
	
	
	
	
	
	
	
//	臨時時間帯
	private List<TimeLeavingWork> timeLeavingWorks;
	
//	外出時間帯
	private List<OutingTimeSheet> outHoursLst;
	
//	短時間勤務時間帯
	private List<ShortWorkingTimeSheet> shortWorkingTimeSheets;
	
//	休憩時間帯
	private List<BreakTimeSheet> breakTimeSheets;
	
	
	
	
	
//	残業深夜時間
	private Optional<TimeDivergenceWithCalculation> overTimeMidnight = Optional.empty();
	
//	法内休出深夜時間
	private Optional<TimeDivergenceWithCalculation> midnightOnHoliday = Optional.empty();
	
//	法外休出深夜時間
	private Optional<TimeDivergenceWithCalculation> outOfMidnight = Optional.empty();
	
//	祝日休出深夜時間
	private Optional<TimeDivergenceWithCalculation> midnightPublicHoliday = Optional.empty();
	
	
	// 育児時間帯
	private List<ShortWorkingTimeSheet> childCareShortWorkingTimeList;
	// 介護時間帯
	private List<ShortWorkingTimeSheet> careShortWorkingTimeList;
	
	// 応援時間帯（List）
	private List<TimePlaceOutput> supportTimes;

}
