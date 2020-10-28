package nts.uk.ctx.at.schedule.dom.schedule.workschedule;
//
import java.util.ArrayList;
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
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.TimevacationUseTimeOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.affiliationinfor.AffiliationInforOfDailyAttd;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.attendancetime.TimeLeavingOfDailyAttd;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.attendancetime.WorkNo;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.breakgoout.OutingTimeOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.breakouting.GoingOutReason;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.breakouting.OutingTimeOfDailyAttd;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.breakouting.breaking.BreakTimeOfDailyAttd;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.earlyleavetime.LeaveEarlyTimeOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.editstate.EditStateOfDailyAttd;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.latetime.LateTimeOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.shortworktime.ShortTimeOfDailyAttd;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.workinfomation.WorkInfoOfDailyAttendance;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.worktime.AttendanceTimeOfDailyAttendance;

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

	/** 休憩時間帯 */
	private List<BreakTimeOfDailyAttd> lstBreakTime;

	/** 編集状態 */
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
			, List<BreakTimeOfDailyAttd> breakTime, List<EditStateOfDailyAttd> editState
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
	public Map<TimezoneToUseHourlyHoliday, TimeVacation> getTimeVacation(){

		if (!this.optAttendanceTime.isPresent()) {
			return Collections.emptyMap();
		}

		Map<TimezoneToUseHourlyHoliday, TimeVacation> result = new HashMap<>();

		/** 日別勤怠の遅刻時間を取得 */
		val lateTimes = this.optAttendanceTime.get().getLateTimeOfDaily();

		for (LateTimeOfDaily lateTime : lateTimes) {
				// @出退勤.勤務開始の休暇時間帯を取得する ($.勤務NO)
				val useTimeStart = TimezoneToUseHourlyHoliday.getBeforeWorking(WorkNo.converFromOtherWorkNo(lateTime.getWorkNo()));
				// $.休暇使用時間
				Optional<TimeSpanForCalc> leavingTime = this.optTimeLeaving.isPresent()?
						this.optTimeLeaving.get().getStartTimeVacations(lateTime.getWorkNo()): Optional.empty();
				//時間休暇
				val timeVacation = new TimeVacation(
						  leavingTime.isPresent() ? Arrays.asList(leavingTime.get()) : new ArrayList<>()
						, lateTime.getTimePaidUseTime());

				result.put(useTimeStart, timeVacation);
		}


		/** 日別勤怠の早退時間を取得*/
		List<LeaveEarlyTimeOfDaily> earlyTimes = this.optAttendanceTime.get().getLeaveEarlyTimeOfDaily();
		for(LeaveEarlyTimeOfDaily earlyTime : earlyTimes) {
			// @出退勤.勤務終了の休暇時間帯を取得する ($.勤務NO)
			val useTimeEnd = TimezoneToUseHourlyHoliday.getAfterWorking(WorkNo.converFromOtherWorkNo(earlyTime.getWorkNo()));
			// $.休暇使用時間
			Optional<TimeSpanForCalc> leavingTime = this.optTimeLeaving.isPresent()?
					this.optTimeLeaving.get().getStartTimeVacations(earlyTime.getWorkNo()): Optional.empty();
			//時間休暇
			val timeVacation = new TimeVacation(leavingTime.isPresent() ? Arrays.asList(leavingTime.get()) : new ArrayList<>(),
					earlyTime.getTimePaidUseTime());
			result.put(useTimeEnd, timeVacation);
		}

		/** 日別勤怠の外出時間を取得 **/
		//$外出時間 = @勤怠時間.外出時間を取得する () : filter $.外出理由 in (私用, 組合)
		List<OutingTimeOfDaily> outingTimes = this.optAttendanceTime.get().getOutingTimeOfDaily().stream()
				.filter(c -> c.getReason() == GoingOutReason.PRIVATE || c.getReason() == GoingOutReason.UNION)
				.collect(Collectors.toList());

		for(OutingTimeOfDaily outingTime : outingTimes) {
			val outTime = convertTimeOffTypeFromGoOutReason(outingTime.getReason());
			if(this.outingTime.isPresent()){
				val timeZones = this.outingTime.get().getTimeZoneByGoOutReason();
				val timeLeave = new TimeVacation( timeZones, outingTime.getTimeVacationUseOfDaily());
				result.put(outTime, timeLeave);
			}
		}

		return result;
	}

	/**
	 * 外出理由から時間休暇種類への変換
	 * @param goOutReason 外出理由
	 * @return
	 */
	private TimezoneToUseHourlyHoliday convertTimeOffTypeFromGoOutReason(GoingOutReason goOutReason) {
		switch(goOutReason) {
		case PRIVATE:
			return TimezoneToUseHourlyHoliday.GOINGOUT_PRIVATE;
		case UNION:
			return TimezoneToUseHourlyHoliday.GOINGOUT_UNION;
		default:
			throw new RuntimeException("時間休暇は私用、組合しかない。");
		}
	}

	/**
	 * 時間休暇
	 * @author lan_lt
	 *
	 */
	@AllArgsConstructor
	@Getter
	public class TimeVacation{
		/** 時間帯リスト */
		private List<TimeSpanForCalc> timeList;

		/** 使用時間 */
		private TimevacationUseTimeOfDaily useTime;


	}
}
