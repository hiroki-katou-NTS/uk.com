package nts.uk.ctx.at.record.dom.monthly.calc;

import java.util.List;

import lombok.Getter;
import lombok.val;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.record.dom.actualworkinghours.AttendanceTimeOfDailyPerformance;
import nts.uk.ctx.at.record.dom.monthly.calc.actualworkingtime.RegularAndIrregularTimeOfMonthly;
import nts.uk.ctx.at.record.dom.monthly.calc.flex.FlexTimeOfMonthly;
import nts.uk.ctx.at.record.dom.monthly.calc.totalworkingtime.AggregateTotalWorkingTime;
import nts.uk.ctx.at.record.dom.monthlyaggrmethod.AggrSettingMonthly;
import nts.uk.ctx.at.record.dom.monthlyaggrmethod.flex.AggrSettingMonthlyOfFlx;
import nts.uk.ctx.at.record.dom.monthlyaggrmethod.legaltransferorder.LegalTransferOrderSetOfAggrMonthly;
import nts.uk.ctx.at.record.dom.monthlyaggrmethod.regularandirregular.LegalAggrSetOfIrg;
import nts.uk.ctx.at.record.dom.monthlyaggrmethod.regularandirregular.LegalAggrSetOfReg;
import nts.uk.ctx.at.record.dom.monthlyprocess.aggr.work.RepositoriesRequiredByMonthlyAggr;
import nts.uk.ctx.at.record.dom.monthlyprocess.aggr.work.premiumtarget.getvacationaddtime.AddSet;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonth;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingSystem;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureDate;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureId;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

/**
 * 月別実績の月の計算
 * @author shuichi_ishida
 */
@Getter
public class MonthlyCalculation {

	/** 実働時間 */
	private RegularAndIrregularTimeOfMonthly actualWorkingTime;
	/** フレックス時間 */
	private FlexTimeOfMonthly flexTime;
	/** 法定労働時間 */
	private AttendanceTimeMonth statutoryWorkingTime;
	/** 総労働時間 */
	private AggregateTotalWorkingTime totalWorkingTime;
	/** 総拘束時間 */
	private AggregateTotalTimeSpentAtWork totalTimeSpentAtWork;
	
	/** リポジトリ：日別実績の勤怠時間 */
	//AttendanceTimeOfDailyPerformanceRepository
	
	/**
	 * コンストラクタ
	 */
	public MonthlyCalculation(){

		this.actualWorkingTime = new RegularAndIrregularTimeOfMonthly();
		this.flexTime = new FlexTimeOfMonthly();
		this.statutoryWorkingTime = new AttendanceTimeMonth(0);
		this.totalWorkingTime = new AggregateTotalWorkingTime();
		this.totalTimeSpentAtWork = new AggregateTotalTimeSpentAtWork();
	}

	/**
	 * ファクトリー
	 * @param actualWorkingTime 実働時間
	 * @param flexTime フレックス時間
	 * @param statutoryWorkingTime 法定労働時間
	 * @param totalWorkingTime 総労働時間
	 * @param totalTimeSpentAtWork 総拘束時間
	 * @return 月別実績の月の計算
	 */
	public static MonthlyCalculation of(
			RegularAndIrregularTimeOfMonthly actualWorkingTime,
			FlexTimeOfMonthly flexTime,
			AttendanceTimeMonth statutoryWorkingTime,
			AggregateTotalWorkingTime totalWorkingTime,
			AggregateTotalTimeSpentAtWork totalTimeSpentAtWork){
		
		val domain = new MonthlyCalculation();
		domain.actualWorkingTime = actualWorkingTime;
		domain.flexTime = flexTime;
		domain.statutoryWorkingTime = statutoryWorkingTime;
		domain.totalWorkingTime = totalWorkingTime;
		domain.totalTimeSpentAtWork = totalTimeSpentAtWork;
		return domain;
	}
	
	/**
	 * 履歴ごとに月別実績を集計する
	 * @param companyId 会社ID
	 * @param employeeId 社員ID
	 * @param yearMonth 年月（度）
	 * @param closureId 締めID
	 * @param closureDate 締め日付
	 * @param datePeriod 期間
	 * @param workingSystem 労働制
	 * @param repositories 月次集計が必要とするリポジトリ
	 */
	public void aggregate(String companyId, String employeeId, YearMonth yearMonth,
			ClosureId closureId, ClosureDate closureDate,
			DatePeriod datePeriod, WorkingSystem workingSystem,
			RepositoriesRequiredByMonthlyAggr repositories){
		
		// 日別実績の勤怠時間　取得
		// ※　取得期間を　開始日-6日～終了日　とする　（開始週の集計のため）
		DatePeriod findPeriod = new DatePeriod(datePeriod.start().addDays(-6), datePeriod.end());
		List<AttendanceTimeOfDailyPerformance> attendanceTimeOfDailys =
				repositories.getAttendanceTimeOfDaily().findByPeriodOrderByYmd(employeeId, findPeriod);
		
		// 期間終了日時点の職場コードを取得する
		String workplaceId = "empty";
		val affWorkplaceOpt = repositories.getAffWorkplaceAdapter().findBySid(employeeId, datePeriod.end());
		if (affWorkplaceOpt.isPresent()){
			workplaceId = affWorkplaceOpt.get().getWorkplaceId();
		}
		
		// 期間終了日時点の雇用コードを取得する
		String employmentCd = "empty";
		val syEmploymentOpt = repositories.getSyEmployment().findByEmployeeId(
				companyId, employeeId, datePeriod.end());
		if (syEmploymentOpt.isPresent()){
			employmentCd = syEmploymentOpt.get().getEmploymentCode();
		}
		
		// 月別実績集計設定　取得　（基準：期間終了日）　（設定確認不可時は、空設定を適用）
		AggrSettingMonthly aggrSettingMonthly = AggrSettingMonthly.of(
				new LegalAggrSetOfReg(), new LegalAggrSetOfIrg(), new AggrSettingMonthlyOfFlx());
		val aggrSettingMonthlyOpt = repositories.getAggrSettingMonthly().get(
				companyId, workplaceId, employmentCd, employeeId);
		if (aggrSettingMonthlyOpt.isPresent()){
			aggrSettingMonthly = aggrSettingMonthlyOpt.get();
		}

		// 法定内振替順設定　取得
		val legalTransferOrderSetOpt = repositories.getLegalTransferOrderSetOfAggrMonthly().find(companyId);
		LegalTransferOrderSetOfAggrMonthly legalTransferOrderSet = new LegalTransferOrderSetOfAggrMonthly(companyId);
		if (legalTransferOrderSetOpt.isPresent()){
			legalTransferOrderSet = legalTransferOrderSetOpt.get();
		}
		
		// 週間、月間法定労働時間　取得
		//*****（未）　日次での実装位置を確認して、合わせて実装する。
		//*****（未）　参考（日次用）。このクラスか、別のクラスに、月・週用のメソッドを追加。仮に0設定。
		/*
		repositories.getGetOfStatutoryWorkTime().getDailyTimeFromStaturoyWorkTime(WorkingSystem.RegularWork,
				companyId, workplaceId, employmentCd, employeeId, datePeriod.end());
		*/
		AttendanceTimeMonth statutoryWorkingTimeWeek = new AttendanceTimeMonth(0);
		AttendanceTimeMonth statutoryWorkingTimeMonth = new AttendanceTimeMonth(0);
		
		// （休暇）加算設定　取得
		//*****（未）　休暇加算設定は、会社単位なので、ここで取得し、必要な処理に引き継ぐ。仮に空設定。
		AddSet addSet = new AddSet();
		
		// 共有項目を集計する
		this.totalWorkingTime.aggregateSharedItem(datePeriod, attendanceTimeOfDailys);
		
		// 通常勤務　or　変形労働　の時
		if (workingSystem == WorkingSystem.REGULAR_WORK ||
			workingSystem == WorkingSystem.VARIABLE_WORKING_TIME_WORK){
			
			// 通常・変形労働勤務の月別実績を集計する
			this.totalWorkingTime = this.actualWorkingTime.aggregateMonthly(companyId, employeeId,
					yearMonth, datePeriod, workingSystem, MonthlyAggregateAtr.MONTHLY,
					aggrSettingMonthly, legalTransferOrderSet, addSet, attendanceTimeOfDailys,
					this.totalWorkingTime, statutoryWorkingTimeWeek, repositories);
			
			// 通常・変形労働勤務の月単位の時間を集計する
			this.actualWorkingTime.aggregateMonthlyHours(companyId, employeeId,
					yearMonth, closureId, closureDate, datePeriod, workingSystem,
					MonthlyAggregateAtr.MONTHLY, workplaceId, employmentCd, aggrSettingMonthly,
					addSet, this.totalWorkingTime, statutoryWorkingTimeMonth, repositories);
		}
		// フレックス時間勤務　の時
		else if (workingSystem == WorkingSystem.FLEX_TIME_WORK){
			
			// フレックス集計方法を取得する
			val aggrSetOfFlex = aggrSettingMonthly.getFlexWork();

			// フレックス勤務の月別実績を集計する
			this.totalWorkingTime = this.flexTime.aggregateMonthly(companyId, employeeId,
					yearMonth, datePeriod, workingSystem, MonthlyAggregateAtr.MONTHLY,
					aggrSetOfFlex, attendanceTimeOfDailys, this.totalWorkingTime, repositories);
			
			// フレックス勤務の月単位の時間を集計する
			this.flexTime.aggregateMonthlyHours(companyId, employeeId, yearMonth, datePeriod,
					workplaceId, employmentCd, aggrSetOfFlex, addSet, this.totalWorkingTime, repositories);
		}

		// 実働時間の集計
		this.totalWorkingTime.aggregateActualWorkingTime(datePeriod, workingSystem,
				this.actualWorkingTime, this.flexTime);
	}
}
