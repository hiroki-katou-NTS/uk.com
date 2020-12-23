package nts.uk.ctx.at.schedule.dom.schedule.workschedule;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.val;
import nts.arc.layer.dom.objecttype.DomainAggregate;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.common.time.TimeSpanForCalc;
import nts.uk.ctx.at.shared.dom.remainingnumber.base.TimezoneToUseHourlyHoliday;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.affiliationinfor.AffiliationInforOfDailyAttd;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.attendancetime.TimeLeavingOfDailyAttd;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.attendancetime.WorkNo;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.breakgoout.OutingTimeOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.breakouting.OutingTimeOfDailyAttd;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.breakouting.breaking.BreakTimeOfDailyAttd;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.earlyleavetime.LeaveEarlyTimeOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.editstate.EditStateOfDailyAttd;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.latetime.LateTimeOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.shortworktime.ShortTimeOfDailyAttd;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.workinfomation.WorkInfoOfDailyAttendance;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.worktime.AttendanceTimeOfDailyAttendance;
import nts.uk.ctx.at.shared.dom.workrule.goingout.GoingOutReason;

/**
 * 勤務予定 root
 * UKDesign.ドメインモデル."NittsuSystem.UniversalK".就業.contexts.勤務予定.勤務予定.勤務予定
 * @author HieuLT
 *
 */
@Getter
@AllArgsConstructor
public class WorkSchedule implements DomainAggregate {

	/** 社員ID(employeeID) */
	private final String employeeID;

	/** 社員ID(年月日(YMD) */
	private final GeneralDate ymd;

	/** 確定区分 */
	private ConfirmedATR confirmedATR;

	/** 勤務情報 */
	private WorkInfoOfDailyAttendance workInfo;

	/** 所属情報 **/
	private AffiliationInforOfDailyAttd affInfo;
	
	/** 休憩時間帯**/
	@Getter
	private Optional<BreakTimeOfDailyAttd> lstBreakTime;
	
	/** 編集状態 **/
	private List<EditStateOfDailyAttd> lstEditState;

	/** 出退勤 */
	private Optional<TimeLeavingOfDailyAttd> optTimeLeaving;

	/** 勤怠時間 */
	private Optional<AttendanceTimeOfDailyAttendance> optAttendanceTime;

	/** 短時間勤務 */
	private Optional<ShortTimeOfDailyAttd> optSortTimeWork;

	/** 外出時間帯 */
	private Optional<OutingTimeOfDailyAttd> outingTime;

	/**
	 * TODO 勤務予定に外出時間帯を追加、あとで直す！！
	 * 外出時間帯を追加したことによってコンパイルエラーが発生するため、
	 * 一旦仮で外出時間帯以外を受け付けるコンストラクタを用意。
	 */
	public WorkSchedule(String sid, GeneralDate date, ConfirmedATR confirmedAtr
			, WorkInfoOfDailyAttendance workInfo, AffiliationInforOfDailyAttd affInfo
			, Optional<BreakTimeOfDailyAttd> breakTime, List<EditStateOfDailyAttd> editState
			, Optional<TimeLeavingOfDailyAttd> timeLeaving, Optional<AttendanceTimeOfDailyAttendance> attendanceTime
			, Optional<ShortTimeOfDailyAttd> sortTimeWork) {

		this.employeeID = sid;
		this.ymd = date;
		this.confirmedATR = confirmedAtr;
		this.workInfo = workInfo;
		this.affInfo = affInfo;
		this.lstBreakTime = breakTime;
		this.lstEditState = editState;
		this.optTimeLeaving = timeLeaving;
		this.optAttendanceTime = attendanceTime;
		this.optSortTimeWork = sortTimeWork;
		this.outingTime = Optional.empty();
	}

	/**
	 * 時間休暇を取得する
	 * @return
	 */
	public Map<TimezoneToUseHourlyHoliday, TimeVacation> getTimeVacation() {
		
		/** 日別勤怠の遅刻時間を取得 */
		val lateTimeMap = this.getLateTimes();
		
		/** 日別勤怠の早退時間を取得*/
		val earlyTimeMap = this.getEarlyTimes();
		
		/** 日別勤怠の外出時間を取得 **/
		val outingTimeMap = this.getOutingTimes();
		
		Map<TimezoneToUseHourlyHoliday, TimeVacation> result = new HashMap<>();
		result.putAll( lateTimeMap );
		result.putAll( earlyTimeMap );
		result.putAll( outingTimeMap );
		
		return result;
	}
	
	/**
	 * ［時間休暇を取得する］　の　［日別勤怠の遅刻時間を取得］
	 * @return
	 */
	private Map<TimezoneToUseHourlyHoliday, TimeVacation> getLateTimes() {
		
		// 勤怠時間 or 出退勤 empty
		if ( !this.optAttendanceTime.isPresent() || !this.optTimeLeaving.isPresent() ) {
			return Collections.emptyMap();
		}
		
		// 遅刻時間を取得する
		val lateTimes = this.optAttendanceTime.get().getLateTimeOfDaily();

		Map<TimezoneToUseHourlyHoliday, TimeVacation> result = new HashMap<>();
		
		for (LateTimeOfDaily lateTime : lateTimes) {
			
			// 出勤の勤務NOを指定
			val lateType = TimezoneToUseHourlyHoliday.getBeforeWorking(WorkNo.converFromOtherWorkNo(lateTime.getWorkNo()));

			// @出退勤.勤務開始の休暇時間帯を取得する
			Optional<TimeSpanForCalc> leavingTimeSpan = this.optTimeLeaving.get().getStartTimeVacations(lateTime.getWorkNo());
			if ( !leavingTimeSpan.isPresent() ) {
				continue;
			}
			
			//時間休暇
			val timeVacation = new TimeVacation( Arrays.asList(leavingTimeSpan.get()), lateTime.getTimePaidUseTime() );

			result.put(lateType, timeVacation);
		}
		
		return result;
	}
	
	/**
	 * ［時間休暇を取得する］　の　［日別勤怠の早退時間を取得］
	 * @return
	 */
	private Map<TimezoneToUseHourlyHoliday, TimeVacation> getEarlyTimes() {
		
		if ( !this.optAttendanceTime.isPresent() || !this.optTimeLeaving.isPresent() ) {
			return Collections.emptyMap();
		}
		
		/** 日別勤怠の早退時間を取得*/
		List<LeaveEarlyTimeOfDaily> earlyTimes = this.optAttendanceTime.get().getLeaveEarlyTimeOfDaily();
		
		Map<TimezoneToUseHourlyHoliday, TimeVacation> result = new HashMap<>();
		
		for(LeaveEarlyTimeOfDaily earlyTime : earlyTimes) {
			
			// 退勤の勤務NOを指定
			val earlyType = TimezoneToUseHourlyHoliday.getAfterWorking(WorkNo.converFromOtherWorkNo(earlyTime.getWorkNo()));
			
			// @出退勤.勤務終了の休暇時間帯を取得する ($.勤務NO)
			Optional<TimeSpanForCalc> leavingTimeSpan = this.optTimeLeaving.get().getEndTimeVacations(earlyTime.getWorkNo());
			if ( !leavingTimeSpan.isPresent() ) {
				continue;
			}
			
			//時間休暇
			val timeVacation = new TimeVacation( Arrays.asList(leavingTimeSpan.get()), earlyTime.getTimePaidUseTime() );
			
			result.put(earlyType, timeVacation);
		}
		
		return result;
		
	}
	
	/**
	 * ［時間休暇を取得する］　の　［【日別勤怠の外出時間を取得］
	 * @return
	 */
	private Map<TimezoneToUseHourlyHoliday, TimeVacation> getOutingTimes() {
		
		if ( !this.optAttendanceTime.isPresent() || !this.outingTime.isPresent() ) {
			return Collections.emptyMap();
		}
		
		//$外出時間 = @勤怠時間.外出時間を取得する () : filter $.外出理由 in (私用, 組合)
		List<OutingTimeOfDaily> outingTimes = this.optAttendanceTime.get().getOutingTimeOfDaily().stream()
				.filter(c -> c.getReason() == GoingOutReason.PRIVATE || c.getReason() == GoingOutReason.UNION)
				.collect(Collectors.toList());
		
		Map<TimezoneToUseHourlyHoliday, TimeVacation> result = new HashMap<>();
		
		for(OutingTimeOfDaily outingTime : outingTimes) {
			
			val type = TimezoneToUseHourlyHoliday.getDuringWorking(outingTime.getReason());
			
			// 外出理由を指定して時間帯を取得する
			val timeZones = this.outingTime.get().getTimeZoneByGoOutReason(outingTime.getReason());
			val timeLeave = new TimeVacation( timeZones, outingTime.getTimeVacationUseOfDaily() );
			
			result.put(type, timeLeave);
		}
		
		return result;
	}

}
