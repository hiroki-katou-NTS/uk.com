package nts.uk.ctx.at.schedule.dom.schedule.workschedule;

import java.util.List;
import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.layer.dom.objecttype.DomainAggregate;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.affiliationinfor.AffiliationInforOfDailyAttd;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.attendancetime.TimeLeavingOfDailyAttd;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.breakouting.OutingTimeOfDailyAttd;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.breakouting.breaking.BreakTimeOfDailyAttd;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.editstate.EditStateOfDailyAttd;
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
}
