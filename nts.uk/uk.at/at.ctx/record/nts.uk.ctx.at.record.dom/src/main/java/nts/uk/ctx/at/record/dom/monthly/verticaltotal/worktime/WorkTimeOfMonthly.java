package nts.uk.ctx.at.record.dom.monthly.verticaltotal.worktime;

import lombok.Getter;
import lombok.val;
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
		this.medicalTime = new MedicalTimeOfMonthly();
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
}
