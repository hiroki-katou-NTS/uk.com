package nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.verticaltotal.worktime;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.Getter;
import lombok.val;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.erroralarm.EmployeeDailyPerError;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.secondorder.medical.MedicalCareTimeOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.worktime.AttendanceTimeOfDailyAttendance;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.verticaltotal.reservation.ReservationOfMonthly;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.verticaltotal.worktime.actual.HolidayUsageOfMonthly;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.verticaltotal.worktime.actual.LaborTimeOfMonthly;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.verticaltotal.worktime.attendanceleave.AttendanceLeaveGateTimeOfMonthly;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.verticaltotal.worktime.bonuspaytime.BonusPayTimeOfMonthly;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.verticaltotal.worktime.breaktime.BreakTimeOfMonthly;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.verticaltotal.worktime.divergencetime.DivergenceTimeOfMonthly;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.verticaltotal.worktime.goout.GoOutOfMonthly;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.verticaltotal.worktime.interval.IntervalTimeOfMonthly;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.verticaltotal.worktime.lateleaveearly.LateLeaveEarlyOfMonthly;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.verticaltotal.worktime.medicaltime.MedicalTimeOfMonthly;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.verticaltotal.worktime.midnighttime.MidnightTimeOfMonthly;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.verticaltotal.worktime.premiumtime.PremiumTimeOfMonthly;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.verticaltotal.worktime.timevarience.BudgetTimeVarienceOfMonthly;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.verticaltotal.worktime.toppage.TopPageDisplayOfMonthly;
import nts.uk.ctx.at.shared.dom.worktime.predset.WorkTimeNightShift;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;

/**
 * 月別実績の勤務時間
 * @author shuichi_ishida
 */
@Getter
public class WorkTimeOfMonthlyVT implements Serializable{

	/** Serializable */
	private static final long serialVersionUID = 1L;

	/** 遅刻早退 */
	private LateLeaveEarlyOfMonthly lateLeaveEarly;
	/** 深夜時間 */
	private MidnightTimeOfMonthly midnightTime;
	/** 休憩時間 */
	private BreakTimeOfMonthly breakTime;
	/** 加給時間 */
	private BonusPayTimeOfMonthly bonusPayTime;
	/** 入退門時間 */
	private AttendanceLeaveGateTimeOfMonthly attendanceLeaveGateTime;
	/** 予約 */
	private ReservationOfMonthly reservation;
	/** 割増時間 */
	private PremiumTimeOfMonthly premiumTime;
	/** 予実差異時間 */
	private BudgetTimeVarienceOfMonthly budgetTimeVarience;
	/** 乖離時間 */
	private DivergenceTimeOfMonthly divergenceTime;
	/** 外出 */
	private GoOutOfMonthly goOut;
	/** 休日時間 -> 廃止 */
//	private HolidayTimeOfMonthly holidayTime;
	/** トップページ表示用時間 */
	private TopPageDisplayOfMonthly topPage;
	/** 医療時間 */
	private Map<WorkTimeNightShift, MedicalTimeOfMonthly> medicalTime;
	/** インターバル時間 */
	private IntervalTimeOfMonthly interval;
	/** 休暇使用時間 */
	private HolidayUsageOfMonthly holidayUseTime;
	/** 労働時間 */
	private LaborTimeOfMonthly laborTime;
	
	/**
	 * コンストラクタ
	 */
	public WorkTimeOfMonthlyVT(){
		
		this.bonusPayTime = new BonusPayTimeOfMonthly();
		this.goOut = new GoOutOfMonthly();
		this.premiumTime = new PremiumTimeOfMonthly();
		this.breakTime = new BreakTimeOfMonthly();
		this.reservation = ReservationOfMonthly.empty();
		this.midnightTime = new MidnightTimeOfMonthly();
		this.lateLeaveEarly = new LateLeaveEarlyOfMonthly();
		this.attendanceLeaveGateTime = new AttendanceLeaveGateTimeOfMonthly();
		this.budgetTimeVarience = new BudgetTimeVarienceOfMonthly();
		this.divergenceTime = new DivergenceTimeOfMonthly();
		this.medicalTime = new HashMap<>();
		this.topPage = TopPageDisplayOfMonthly.empty();
		this.interval = IntervalTimeOfMonthly.empty();
		this.laborTime = LaborTimeOfMonthly.empty();
		this.holidayUseTime = HolidayUsageOfMonthly.empty();
	}

	/**
	 * ファクトリー
	 * @param bonusPayTime 加給時間
	 * @param goOut 外出
	 * @param premiumTime 割増時間
	 * @param breakTime 休憩時間
	 * @param reservation 予約
	 * @param midnightTime 深夜時間
	 * @param lateLeaveEarly 遅刻早退
	 * @param attendanceLeaveGateTime 入退門時間
	 * @param budgetTimeVarience 予実差異時間
	 * @param divergenceTime 乖離時間
	 * @param medicalTimeList 医療時間リスト
	 * @param topPage トップページ表示用時間
	 * @param interval インターバル時間
	 * @param holidayUseTime 休暇使用時間
	 * @param laborTime 労働時間
	 * @return 月別実績の勤務時間
	 */
	public static WorkTimeOfMonthlyVT of(
			BonusPayTimeOfMonthly bonusPayTime,
			GoOutOfMonthly goOut,
			PremiumTimeOfMonthly premiumTime,
			BreakTimeOfMonthly breakTime,
			ReservationOfMonthly reservation,
			MidnightTimeOfMonthly midnightTime,
			LateLeaveEarlyOfMonthly lateLeaveEarly,
			AttendanceLeaveGateTimeOfMonthly attendanceLeaveGateTime,
			BudgetTimeVarienceOfMonthly budgetTimeVarience,
			DivergenceTimeOfMonthly divergenceTime,
			List<MedicalTimeOfMonthly> medicalTimeList,
			TopPageDisplayOfMonthly topPage,
			IntervalTimeOfMonthly interval,
			HolidayUsageOfMonthly holidayUseTime,
			LaborTimeOfMonthly laborTime){
		
		val domain = new WorkTimeOfMonthlyVT();
		domain.bonusPayTime = bonusPayTime;
		domain.goOut = goOut;
		domain.premiumTime = premiumTime;
		domain.breakTime = breakTime;
		domain.reservation = reservation;
		domain.midnightTime = midnightTime;
		domain.lateLeaveEarly = lateLeaveEarly;
		domain.attendanceLeaveGateTime = attendanceLeaveGateTime;
		domain.budgetTimeVarience = budgetTimeVarience;
		domain.divergenceTime = divergenceTime;
		for (val medicalTime : medicalTimeList){
			val dayNightAtr = medicalTime.getDayNightAtr();
			domain.medicalTime.putIfAbsent(dayNightAtr, medicalTime);
		}
		domain.topPage = topPage;
		domain.interval = interval;
		domain.laborTime = laborTime;
		domain.holidayUseTime = holidayUseTime;
		return domain;
	}
	
	/**
	 * 集計
	 * @param workType 勤務種類
	 * @param attendanceTime 日別実績の勤怠時間
	 */
	public void aggregate(RequireM1 require, String sid, GeneralDate ymd, 
			WorkType workType, AttendanceTimeOfDailyAttendance attendanceTime){
		
		if (attendanceTime == null) {
			return;
		}
		
		// 遅刻早退の集計（回数・時間）
		this.lateLeaveEarly.aggregate(attendanceTime);
		
		// 深夜時間の集計
		this.midnightTime.aggregate(attendanceTime);
		
		// 休憩時間の集計
		this.breakTime.aggregate(attendanceTime);
		
		// 加給時間の集計
		this.bonusPayTime.aggregate(workType, attendanceTime);
		
		// 入退門関連の項目集計
		this.attendanceLeaveGateTime.aggregate(attendanceTime);

		// ○予約注文の項目集計
		this.reservation.aggregate(require, sid, ymd);

		// 割増時間の集計
		this.premiumTime.aggregate(attendanceTime);
		
		// 差異時間の集計
		this.budgetTimeVarience.aggregate(attendanceTime);
		
		// 乖離時間の集計
		this.divergenceTime.aggregate(attendanceTime);

		// 外出時間の集計（回数・時間）
		this.goOut.aggregate(attendanceTime);

		// ○トップページ表示用時間の集計
		this.topPage.aggregate(attendanceTime);
		
		// 医療項目の集計
		this.aggregateMedicalTime(attendanceTime);

		// ○インターバル時間の集計
		this.interval.aggregate(attendanceTime);
		
		/** ○休暇使用時間 */
		this.holidayUseTime.aggregate(attendanceTime);

		/** ○労働時間 */
		this.laborTime.aggregate(attendanceTime);
	}
	
	/**
	 * 医療項目の集計
	 * @param attendanceTimeOfDaily 日別実績の勤怠時間
	 */
	private void aggregateMedicalTime(AttendanceTimeOfDailyAttendance attendanceTimeOfDaily){

		// 日別実績の医療時間を取得する
		//*****（未）　日別実績の医療時間が、リスト化実装された後、処理の調整要。仮に、空リストで処理実装。
		List<MedicalCareTimeOfDaily> medicalTimeList = new ArrayList<>();
		for (val medicalTime : medicalTimeList){
			val dayNightAtr = medicalTime.getDayNightAtr();
			
			// 日別実績の医療時間（勤務時間、控除時間、申送時間）を集計する
			this.medicalTime.putIfAbsent(dayNightAtr, new MedicalTimeOfMonthly(dayNightAtr));
			val targetMedicalTime = this.medicalTime.get(dayNightAtr);
			targetMedicalTime.addMinutesToWorkTime(medicalTime.getWorkTime().v());
			targetMedicalTime.addMinutesToDeducationTime(medicalTime.getDeductionTime().v());
			targetMedicalTime.addMinutesToTakeOverTime(medicalTime.getTakeOverTime().v());
		}
	}
	
	/**
	 * 乖離フラグの集計
	 * @param employeeDailyPerErrors 社員の日別実績エラー一覧リスト
	 */
	public void aggregateDivergenceAtr(List<EmployeeDailyPerError> employeeDailyPerErrors){
		
		this.divergenceTime.aggregateDivergenceAtr(employeeDailyPerErrors);
	}
	
	/**
	 * 合算する
	 * @param target 加算対象
	 */
	public void sum(WorkTimeOfMonthlyVT target){
		
		this.lateLeaveEarly.sum(target.lateLeaveEarly);
		this.midnightTime.sum(target.midnightTime);
		this.breakTime.sum(target.breakTime);
		this.bonusPayTime.sum(target.bonusPayTime);
		this.attendanceLeaveGateTime.sum(target.attendanceLeaveGateTime);
		this.reservation.sum(target.reservation);
		this.premiumTime.sum(target.premiumTime);
		this.budgetTimeVarience.sum(target.budgetTimeVarience);
		this.divergenceTime.sum(target.divergenceTime);
		this.goOut.sum(target.goOut);
		this.topPage.sum(target.topPage);
		
		for (val medicalValue : this.medicalTime.values()){
			val dayNightAtr = medicalValue.getDayNightAtr();
			if (target.medicalTime.containsKey(dayNightAtr)){
				val targetMedicalValue = target.medicalTime.get(dayNightAtr);
				medicalValue.addMinutesToWorkTime(targetMedicalValue.getWorkTime().v());
				medicalValue.addMinutesToDeducationTime(targetMedicalValue.getDeducationTime().v());
				medicalValue.addMinutesToTakeOverTime(targetMedicalValue.getTakeOverTime().v());
			}
		}
		for (val targetMedicalValue : target.medicalTime.values()){
			val dayNightAtr = targetMedicalValue.getDayNightAtr();
			this.medicalTime.putIfAbsent(dayNightAtr, targetMedicalValue);
		}

		this.interval.sum(target.interval);
		this.holidayUseTime.sum(target.holidayUseTime);
		this.laborTime.sum(target.laborTime);
	}

	public static interface RequireM1 extends ReservationOfMonthly.RequireM1 {}
}
