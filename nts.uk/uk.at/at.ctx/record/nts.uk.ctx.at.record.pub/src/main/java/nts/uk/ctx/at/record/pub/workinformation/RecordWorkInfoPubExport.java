package nts.uk.ctx.at.record.pub.workinformation;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import lombok.Data;
import lombok.Setter;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.pub.workinformation.export.WrTimeLeavingWorkExport;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.breakouting.OutingTimeSheet;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.breakouting.breaking.BreakTimeSheet;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.TimeDivergenceWithCalculation;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.overtimehours.ExcessOverTimeWorkMidNightTime;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.shortworktime.ShortWorkingTimeSheet;

/**
 * 勤務実績
 * @author nampt
 * 
 */
@Data
@Setter
public class RecordWorkInfoPubExport {
	//社員ID
	private String employeeId;
		
	//年月日
	private GeneralDate ymd;

	// 勤務種類コード
	private String workTypeCode;
	
	// 就業時間帯コード
	private String workTimeCode;
	
	// 日別実績の出退勤．出退勤．出勤．打刻 , 勤務NO＝0
	//開始時刻1
	private Integer attendanceStampTimeFirst;
	
	// 日別実績の出退勤．出退勤．退勤．打刻, 勤務NO＝0
	//終了時刻1
	private Integer leaveStampTimeFirst;
	
	// 日別実績の出退勤．出退勤．出勤．打刻 , 勤務NO＝1
	//開始時刻2
	private Integer attendanceStampTimeSecond;
	
	// 日別実績の出退勤．出退勤．退勤．打刻, 勤務NO＝1
	//終了時刻1
	private Integer leaveStampTimeSecond;
	
	/** 日別実績の勤怠時間．実働時間．総労働時間．遅刻時間．遅刻時間 */
	//遅刻時間
	private Integer lateTime1;
	
	/** 日別実績の勤怠時間．実働時間．総労働時間．早退時間．早退時間 */
	//早退時間
	private Integer leaveEarlyTime1;
	
	/** 日別実績の勤怠時間．実働時間．総労働時間．遅刻時間．遅刻時間 */
	//遅刻時間2
	private Integer lateTime2;
	
	/** 日別実績の勤怠時間．実働時間．総労働時間．早退時間．早退時間 */
	//早退時間2
	private Integer leaveEarlyTime2;

	/** 日別実績の勤怠時間．実働時間．総労働時間．短時間勤務時間.育児時間 */
	//育児時間
	private Integer childCareTime;
	
	/** 日別実績の勤怠時間．実働時間．総労働時間.外出時間.計上用合計時間.合計時間.外出時間 私用 */
	//外出時間.私用
	private Integer outingTimePrivate;
	
	/** 日別実績の勤怠時間．実働時間．総労働時間.外出時間.計上用合計時間.合計時間.外出時間 組合 */
	//外出時間.組合
	private Integer outingTimeCombine;

	/** 日別実績の勤怠時間．実績時間．総労働時間．所定外時間．残業時間．フレックス時間．フレックス時間.計算フレックス */
	private Integer flexTime;
	
	/** 日別実績の勤怠時間．実績時間．総労働時間．所定外時間．残業時間．残業枠時間．残業時間.計算残業 */
	/** 日別実績の勤怠時間．実績時間．総労働時間．所定外時間．残業時間．残業枠時間．振替時間.計算振替残業 */
	//計算残業 and 計算振替残業
	private List<CommonTimeSheet> overtimes = new ArrayList<>();
	
	/** 日別実績の勤怠時間．実績時間．総労働時間．所定外時間．休出時間．休出枠時間．休出時間.計算休日出勤 */
	/** 日別実績の勤怠時間．実績時間．総労働時間．所定外時間．休出時間．休出枠時間．振替時間.計算振替 */
	//計算休日出勤 and 計算振替
	private List<CommonTimeSheet> holidayWorks = new ArrayList<>();
	
	/** 計算就業外深夜 */ //K thấy mô tả gì 
	/** 休出深夜 */ //K thấy mô tả gì 
	private Integer midnightTime;
	//休憩時間開始時刻  K thấy mô tả gì 
	//休憩時間終了時刻  K thấy mô tả gì 
	
	//勤務予定時間帯 (予定出勤時刻1~2 予定退勤時刻1~2)
	private List<WrScheduleTimeSheetExport> listWrScheduleTimeSheetExport = new ArrayList<>();
	
	//臨時時間帯 
	private List<WrTimeLeavingWorkExport> listTimeLeavingWorkExport = new ArrayList<>();
	
	//外出時間帯
	private List<OutingTimeSheet> listOutingTimeSheet = new ArrayList<>();
	
	//短時間勤務時間帯
	private List<ShortWorkingTimeSheet> listShortWorkingTimeSheet  = new ArrayList<>();
	
	//休憩時間帯
	private List<BreakTimeSheet> listBreakTimeSheet = new ArrayList<>(); 
	
	//残業深夜時間
	private Optional<TimeDivergenceWithCalculation> excessOverTimeWorkMidNightTime = Optional.empty();
	
	//法内休出深夜時間
	Optional<TimeDivergenceWithCalculation> insideTheLaw = Optional.empty();

	//法外休出深夜時間
	Optional<TimeDivergenceWithCalculation> outrageous = Optional.empty();
	
	//祝日休出深夜時間
	Optional<TimeDivergenceWithCalculation> publicHoliday = Optional.empty();
	public RecordWorkInfoPubExport(String workTypeCode, String workTimeCode, Integer attendanceStampTimeFirst,
			Integer leaveStampTimeFirst, Integer attendanceStampTimeSecond, Integer leaveStampTimeSecond, Integer time1, Integer time2,
			Integer time3, Integer time4, Integer time5) {
		super();
		this.workTypeCode = workTypeCode;
		this.workTimeCode = workTimeCode;
		this.attendanceStampTimeFirst = attendanceStampTimeFirst;
		this.leaveStampTimeFirst = leaveStampTimeFirst;
		this.attendanceStampTimeSecond = attendanceStampTimeSecond;
		this.leaveStampTimeSecond = leaveStampTimeSecond;
		this.lateTime1 = time1;
		this.leaveEarlyTime1 = time2;
		this.lateTime2 = time3;
		this.leaveEarlyTime2 = time4;
		this.childCareTime = time5;
	}
	
	public RecordWorkInfoPubExport(String workTypeCode, String workTimeCode) {
		super();
		this.workTypeCode = workTypeCode;
		this.workTimeCode = workTimeCode;
	}
	
	
}
