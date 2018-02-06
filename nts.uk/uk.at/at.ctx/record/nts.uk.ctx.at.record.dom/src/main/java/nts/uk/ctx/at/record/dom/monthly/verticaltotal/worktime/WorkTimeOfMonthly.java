package nts.uk.ctx.at.record.dom.monthly.verticaltotal.worktime;

import java.util.List;

import lombok.Getter;
import lombok.val;
import nts.uk.ctx.at.record.dom.actualworkinghours.AttendanceTimeOfDailyPerformance;
import nts.uk.ctx.at.record.dom.monthly.verticaltotal.worktime.attdleavegatetime.AttendanceLeaveGateTimeOfMonthly;
import nts.uk.ctx.at.record.dom.monthly.verticaltotal.worktime.bonuspaytime.BonusPayTimeOfMonthly;
import nts.uk.ctx.at.record.dom.monthly.verticaltotal.worktime.breaktime.BreakTimeOfMonthly;
import nts.uk.ctx.at.record.dom.monthly.verticaltotal.worktime.divergencetime.DivergenceTimeOfMonthly;
import nts.uk.ctx.at.record.dom.monthly.verticaltotal.worktime.goout.GoOutOfMonthly;
import nts.uk.ctx.at.record.dom.monthly.verticaltotal.worktime.holidaytime.HolidayTimeOfMonthly;
import nts.uk.ctx.at.record.dom.monthly.verticaltotal.worktime.lateleaveearly.LateLeaveEarlyOfMonthly;
import nts.uk.ctx.at.record.dom.monthly.verticaltotal.worktime.medicaltime.MedicalTimeOfMonthly;
import nts.uk.ctx.at.record.dom.monthly.verticaltotal.worktime.midnighttime.MidnightTimeOfMonthly;
import nts.uk.ctx.at.record.dom.monthly.verticaltotal.worktime.premiumtime.PremiumTimeOfMonthly;
import nts.uk.ctx.at.record.dom.monthly.verticaltotal.worktime.timevarience.BudgetTimeVarienceOfMonthly;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.EmployeeDailyPerError;
import nts.uk.ctx.at.shared.dom.worktime.predset.WorkTimeNightShift;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;

/**
 * 月別実績の勤務時間
 * @author shuichu_ishida
 */
@Getter
public class WorkTimeOfMonthly {

	/** 加給時間 */
	private BonusPayTimeOfMonthly bonusPayTime;
	/** 外出 */
	private GoOutOfMonthly goOut;
	/** 割増時間 */
	private PremiumTimeOfMonthly premiumTime;
	/** 休憩時間 */
	private BreakTimeOfMonthly breakTime;
	/** 休日時間 */
	private HolidayTimeOfMonthly holidayTime;
	/** 深夜時間 */
	private MidnightTimeOfMonthly midnightTime;
	/** 遅刻早退 */
	private LateLeaveEarlyOfMonthly lateLeaveEarly;
	/** 入退門時間 */
	private AttendanceLeaveGateTimeOfMonthly attendanceLeaveGateTime;
	/** 予実差異時間 */
	private BudgetTimeVarienceOfMonthly budgetTimeVarience;
	/** 乖離時間 */
	private DivergenceTimeOfMonthly divergenceTime;
	/** 医療時間 */
	private MedicalTimeOfMonthly medicalTime;
	/** 予約 */
	//reservation
	
	/**
	 * コンストラクタ
	 */
	public WorkTimeOfMonthly(){
		
		this.bonusPayTime = new BonusPayTimeOfMonthly();
		this.goOut = new GoOutOfMonthly();
		this.premiumTime = new PremiumTimeOfMonthly();
		this.breakTime = new BreakTimeOfMonthly();
		this.holidayTime = new HolidayTimeOfMonthly();
		this.midnightTime = new MidnightTimeOfMonthly();
		this.lateLeaveEarly = new LateLeaveEarlyOfMonthly();
		this.attendanceLeaveGateTime = new AttendanceLeaveGateTimeOfMonthly();
		this.budgetTimeVarience = new BudgetTimeVarienceOfMonthly();
		this.divergenceTime = new DivergenceTimeOfMonthly();
		this.medicalTime = new MedicalTimeOfMonthly(WorkTimeNightShift.DAY_SHIFT);
	}

	/**
	 * ファクトリー
	 * @param bonusPayTime 加給時間
	 * @param goOut 外出
	 * @param premiumTime 割増時間
	 * @param breakTime 休憩時間
	 * @param holidayTime 休日時間
	 * @param midnightTime 深夜時間
	 * @param lateLeaveEarly 遅刻早退
	 * @param attendanceLeaveGateTime 入退門時間
	 * @param budgetTimeVarience 予実差異時間
	 * @param divergenceTime 乖離時間
	 * @param medicalTime 医療時間
	 * @return 月別実績の勤務時間
	 */
	public static WorkTimeOfMonthly of(
			BonusPayTimeOfMonthly bonusPayTime,
			GoOutOfMonthly goOut,
			PremiumTimeOfMonthly premiumTime,
			BreakTimeOfMonthly breakTime,
			HolidayTimeOfMonthly holidayTime,
			MidnightTimeOfMonthly midnightTime,
			LateLeaveEarlyOfMonthly lateLeaveEarly,
			AttendanceLeaveGateTimeOfMonthly attendanceLeaveGateTime,
			BudgetTimeVarienceOfMonthly budgetTimeVarience,
			DivergenceTimeOfMonthly divergenceTime,
			MedicalTimeOfMonthly medicalTime){
		
		val domain = new WorkTimeOfMonthly();
		domain.bonusPayTime = bonusPayTime;
		domain.goOut = goOut;
		domain.premiumTime = premiumTime;
		domain.breakTime = breakTime;
		domain.holidayTime = holidayTime;
		domain.midnightTime = midnightTime;
		domain.lateLeaveEarly = lateLeaveEarly;
		domain.attendanceLeaveGateTime = attendanceLeaveGateTime;
		domain.budgetTimeVarience = budgetTimeVarience;
		domain.divergenceTime = divergenceTime;
		domain.medicalTime = medicalTime;
		return domain;
	}
	
	/**
	 * 集計
	 * @param workType 勤務種類
	 * @param attendanceTimeOfDaily 日別実績の勤怠時間
	 */
	public void aggregate(
			WorkType workType,
			AttendanceTimeOfDailyPerformance attendanceTimeOfDaily){
		
		// 加給時間の集計
		//*****（未）　加給時間のクラス利用が不整合になっていて、正しいメンバが参照できない。
		this.bonusPayTime.aggregate(workType, attendanceTimeOfDaily);
		
		// 外出時間の集計（回数・時間）
		//*****（未）　日別実績の外出時間・短時間勤務時間のクラスの作成待ち。集計処理は、未実装。
		this.goOut.aggregate(attendanceTimeOfDaily);
		
		// 割増時間の集計
		this.premiumTime.aggregate(attendanceTimeOfDaily);
		
		// 休憩時間の集計
		this.breakTime.aggregate(attendanceTimeOfDaily);
		
		// 深夜時間の集計
		this.midnightTime.aggregate(attendanceTimeOfDaily);
		
		// 遅刻早退の集計（回数・時間）
		this.lateLeaveEarly.aggregate(attendanceTimeOfDaily);
		
		// 入退門関連の項目集計
		this.attendanceLeaveGateTime.aggregate(attendanceTimeOfDaily);
		
		// 差異時間の集計
		this.budgetTimeVarience.aggregate(attendanceTimeOfDaily);
		
		// 乖離時間の集計
		this.divergenceTime.aggregate(attendanceTimeOfDaily);
		
		// 医療項目の集計
		//*****（未）　医療時間クラスをリスト管理に設計変更要。
		this.medicalTime.aggregate(attendanceTimeOfDaily);
		
		// 予約データの集計
		
	}
	
	/**
	 * 乖離フラグの集計
	 * @param employeeDailyPerErrors 社員の日別実績エラー一覧リスト
	 */
	public void aggregateDivergenceAtr(List<EmployeeDailyPerError> employeeDailyPerErrors){
		
		this.divergenceTime.aggregateDivergenceAtr(employeeDailyPerErrors);
	}
}
