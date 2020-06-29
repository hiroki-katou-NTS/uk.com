package nts.uk.ctx.at.schedule.dom.schedule.workschedule;

import java.util.List;
import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.layer.dom.objecttype.DomainAggregate;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.dailyattdcal.dailyattendance.affiliationinfor.AffiliationInforOfDailyAttd;
import nts.uk.ctx.at.shared.dom.dailyattdcal.dailyattendance.attendancetime.TimeLeavingOfDailyAttd;
import nts.uk.ctx.at.shared.dom.dailyattdcal.dailyattendance.breakouting.breaking.BreakTimeOfDailyAttd;
import nts.uk.ctx.at.shared.dom.dailyattdcal.dailyattendance.editstate.EditStateOfDailyAttd;
import nts.uk.ctx.at.shared.dom.dailyattdcal.dailyattendance.shortworktime.ShortTimeOfDailyAttd;
import nts.uk.ctx.at.shared.dom.dailyattdcal.dailyattendance.workinfomation.WorkInfoOfDailyAttendance;
import nts.uk.ctx.at.shared.dom.dailyattdcal.dailyattendance.worktime.AttendanceTimeOfDailyAttendance;

/**
 * 勤務予定
 * UKDesign.ドメインモデル."NittsuSystem.UniversalK".就業.contexts.勤務予定.勤務予定.勤務予定
 * @author HieuLT
 *
 */
@AllArgsConstructor
public class WorkSchedule implements DomainAggregate {
	
	/** 社員ID(employeeID)**/
	@Getter
	private final String employeeID;
	
	/** 社員ID(年月日(YMD))**/
	@Getter
	private final GeneralDate ymd;
	
	/** 予定確定区分 **/
	@Getter
	private ConfirmedATR ConfirmedATR;
	
	/** 日別勤怠の勤務情報 **/
	@Getter
	private WorkInfoOfDailyAttendance workInfo;
	
	/** 日別勤怠の所属情報 **/
	@Getter
	private AffiliationInforOfDailyAttd affInfo;
	
	/** 日別勤怠の休憩時間帯 **/
	@Getter
	private List<BreakTimeOfDailyAttd> lstBreakTime;
	
	/** 日別勤怠の編集状態 **/
	@Getter
	private List<EditStateOfDailyAttd> lstEditState;
	
		/** 日別勤怠の出退勤**/
	@Getter
	private Optional<TimeLeavingOfDailyAttd> optTimeLeaving;
	
	/** 日別勤怠の勤怠時間 **/
	@Getter
	private Optional<AttendanceTimeOfDailyAttendance> optAttendanceTime;
	
	/** 日別勤怠の短時間勤務時間帯**/
	@Getter
	private Optional<ShortTimeOfDailyAttd> optSortTimeWork;
	
	
}
