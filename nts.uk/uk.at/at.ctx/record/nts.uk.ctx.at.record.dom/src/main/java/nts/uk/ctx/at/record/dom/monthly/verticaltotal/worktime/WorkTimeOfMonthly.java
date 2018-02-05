package nts.uk.ctx.at.record.dom.monthly.verticaltotal.worktime;

import java.util.List;
import java.util.Map;

import lombok.Getter;
import lombok.val;
import nts.arc.time.GeneralDate;
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
import nts.uk.ctx.at.record.dom.raisesalarytime.SpecificDateAttrOfDailyPerfor;
import nts.uk.ctx.at.record.dom.workinformation.WorkInfoOfDailyPerformance;
import nts.uk.ctx.at.shared.dom.worktime.predset.WorkTimeNightShift;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

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
	 * @param datePeriod 期間
	 * @param workInfoOfDailyMap 日別実績の勤務情報リスト
	 * @param attendanceTimeOfDailys 日別実績の勤怠時間リスト
	 * @param specificDateAtrOfDailys 日別実績の特定日区分リスト
	 * @param workTypeMap 勤務種類リスト
	 */
	public void aggregate(
			DatePeriod datePeriod,
			Map<GeneralDate, WorkInfoOfDailyPerformance> workInfoOfDailyMap,
			List<AttendanceTimeOfDailyPerformance> attendanceTimeOfDailys,
			List<SpecificDateAttrOfDailyPerfor> specificDateAtrOfDailys,
			Map<String, WorkType> workTypeMap){
		
		// 加給時間の集計
		this.bonusPayTime.aggregate(datePeriod, workInfoOfDailyMap, attendanceTimeOfDailys,
				specificDateAtrOfDailys, workTypeMap);
		
		// 外出時間の集計（回数・時間）
		//*****（未）　日別実績の外出時間・短時間勤務時間のクラスの作成待ち。集計処理は、未実装。
		this.goOut.aggregate(datePeriod, attendanceTimeOfDailys);
		
		// 割増時間の集計
		this.premiumTime.aggregate(datePeriod, attendanceTimeOfDailys);
		
		// 休憩時間の集計
		this.breakTime.aggregate(datePeriod, attendanceTimeOfDailys);
		
		// 深夜時間の集計
		this.midnightTime.aggregate(datePeriod, attendanceTimeOfDailys);
		
		// 遅刻早退の集計（回数・時間）
		this.lateLeaveEarly.aggregate(datePeriod, attendanceTimeOfDailys);
		
		// 入退門関連の項目集計
		//*****（未）　集計方法不明。設計確認要。
		
		// 差異時間の集計
		this.budgetTimeVarience.aggregate(datePeriod, attendanceTimeOfDailys);
		
		// 乖離時間・乖離フラグの集計
		this.divergenceTime.aggregate(datePeriod, attendanceTimeOfDailys);
		
		// 医療項目の集計
		this.medicalTime.aggregate(datePeriod, attendanceTimeOfDailys);
		
		// 予約データの集計
		
	}
}
