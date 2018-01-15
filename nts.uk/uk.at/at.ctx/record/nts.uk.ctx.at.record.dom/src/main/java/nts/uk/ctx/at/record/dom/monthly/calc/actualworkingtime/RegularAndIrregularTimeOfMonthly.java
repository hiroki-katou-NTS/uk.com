package nts.uk.ctx.at.record.dom.monthly.calc.actualworkingtime;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.val;
import nts.arc.error.BusinessException;
import nts.arc.error.RawErrorMessage;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.record.dom.actualworkinghours.AttendanceTimeOfDailyPerformance;
import nts.uk.ctx.at.record.dom.monthly.calc.MonthlyAggregateAtr;
import nts.uk.ctx.at.record.dom.monthly.calc.totalworkingtime.AggregateTotalWorkingTime;
import nts.uk.ctx.at.record.dom.monthlyaggrmethod.AggrSettingMonthly;
import nts.uk.ctx.at.record.dom.monthlyaggrmethod.LegalTransferOrderSetOfAggrMonthly;
import nts.uk.ctx.at.record.dom.monthlyaggrmethod.regularandirregular.LegalAggrSetOfIrg;
import nts.uk.ctx.at.record.dom.monthlyprocess.aggr.work.RepositoriesRequiredByMonthlyAggr;
import nts.uk.ctx.at.record.dom.monthlyprocess.aggr.work.premiumtarget.AddedVacationUseTime;
import nts.uk.ctx.at.record.dom.monthlyprocess.aggr.work.premiumtarget.IrregularPeriodCarryforwardsTimeOfCurrent;
import nts.uk.ctx.at.record.dom.monthlyprocess.aggr.work.premiumtarget.TargetPremiumTimeMonthOfRegular;
import nts.uk.ctx.at.record.dom.monthlyprocess.aggr.work.premiumtarget.getvacationaddtime.AddSet;
import nts.uk.ctx.at.record.dom.workinformation.WorkInformation;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonth;
import nts.uk.ctx.at.shared.dom.employment.statutory.worktime.employment.WorkingSystem;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureDate;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureId;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

/**
 * 月別実績の通常変形時間
 * @author shuichi_ishida
 */
@Getter
public class RegularAndIrregularTimeOfMonthly {
	
	/** 週割増合計時間 */
	@Setter
	private AttendanceTimeMonth weeklyTotalPremiumTime;
	/** 月割増合計時間 */
	@Setter
	private AttendanceTimeMonth monthlyTotalPremiumTime;
	/** 変形労働時間 */
	private IrregularWorkingTimeOfMonthly irregularWorkingTime;
	
	/** 加算した休暇使用時間 */
	private AddedVacationUseTime addedVacationUseTime;
	
	/**
	 * コンストラクタ
	 */
	public RegularAndIrregularTimeOfMonthly(){
		
		this.weeklyTotalPremiumTime = new AttendanceTimeMonth(0);
		this.monthlyTotalPremiumTime = new AttendanceTimeMonth(0);
		this.irregularWorkingTime = new IrregularWorkingTimeOfMonthly();
		
		this.addedVacationUseTime = new AddedVacationUseTime();
	}

	/**
	 * ファクトリー
	 * @param weeklyTotalPremiumTime 週割増合計時間
	 * @param monthlyTotalPremiumTime 月割増合計時間
	 * @param irregularWorkingTime 変形労働時間
	 * @return 日別実績の通常変形時間
	 */
	public static RegularAndIrregularTimeOfMonthly of(
			AttendanceTimeMonth weeklyTotalPremiumTime,
			AttendanceTimeMonth monthlyTotalPremiumTime,
			IrregularWorkingTimeOfMonthly irregularWorkingTime){
		
		val domain = new RegularAndIrregularTimeOfMonthly();
		domain.weeklyTotalPremiumTime = weeklyTotalPremiumTime;
		domain.monthlyTotalPremiumTime = monthlyTotalPremiumTime;
		domain.irregularWorkingTime = irregularWorkingTime;
		return domain;
	}
	
	/**
	 * 月別実績を集計する
	 * @param companyId 会社ID
	 * @param employeeId 社員ID
	 * @param yearMonth 年月（度）
	 * @param datePeriod 期間
	 * @param workingSystem 労働制
	 * @param aggregateAtr 集計区分
	 * @param aggrSettingMonthly 月別実績集計設定
	 * @param legalTransferOrderSet 法定内振替順設定
	 * @param attendanceTimeOfDailys リスト：日別実績の勤怠時間
	 * @param aggregateTotalWorkingTime 総労働時間
	 * @param repositories 月次集計が必要とするリポジトリ
	 * @return 集計総労働時間
	 */
	public AggregateTotalWorkingTime aggregateMonthly(
			String companyId,
			String employeeId,
			YearMonth yearMonth,
			DatePeriod datePeriod,
			WorkingSystem workingSystem,
			MonthlyAggregateAtr aggregateAtr,
			AggrSettingMonthly aggrSettingMonthly,
			LegalTransferOrderSetOfAggrMonthly legalTransferOrderSet,
			List<AttendanceTimeOfDailyPerformance> attendanceTimeOfDailys,
			AggregateTotalWorkingTime aggregateTotalWorkingTime,
			RepositoriesRequiredByMonthlyAggr repositories){
		
		// 集計総労働時間　作成　（返却用）
		//*****（メモ）　この方法では、値が正しく戻らないかもしれない（動作検証要）。クラスの必要な値だけコピーしたクローンを作る必要があるかも。
		val returnClass = aggregateTotalWorkingTime;
		
		// 週開始を取得する
		//*****（２次）
		//*****（メモ）　事前に期間の終了日時点の職場ID・雇用コードの確認が必要
		
		// 前月の最終週のループ
		//*****（保留中）

		// 処理をする期間の日数分ループ　（調整後期間内の日次データごと）
		for (val attendanceTimeOfDaily : attendanceTimeOfDailys){
		
			// 処理日の個人履歴関連情報を取得する
			//*****（未）　所属職場履歴から、処理日の職場IDを取る
			String workplaceId = "dummy";
			//*****（未）　所属雇用履歴から、処理日の雇用コードを取る
			String employmentCd = "dummy";
			
			// 処理日の勤務情報を取得する
			WorkInformation workInfo = new WorkInformation("non", "non");
			val workInformationOfDaily =
					repositories.getWorkInformationOfDaily().find(employeeId, attendanceTimeOfDaily.getYmd());
			if (workInformationOfDaily.isPresent()) {
				workInfo = workInformationOfDaily.get().getRecordWorkInformation();
			}
			else {
				String errorMsg = "勤務情報が取得できません。"
						+ "　社員ID：" + employeeId
						+ "　年月日：" + attendanceTimeOfDaily.getYmd().toString("yyyy/M/d");
				throw new BusinessException(new RawErrorMessage(errorMsg));
			}
			
			// 日別実績を集計する　（通常・変形労働時間勤務用）
			returnClass.aggregateDailyForRegAndIrreg(attendanceTimeOfDaily, companyId, workplaceId, employmentCd,
					workingSystem, aggregateAtr, workInfo, aggrSettingMonthly, legalTransferOrderSet, repositories);
			
			// 週の集計をする日か確認する
			//*****（２次）
			
			// 週別実績を集計する
			//*****（２次）
			
			if (aggregateAtr.isExcessOutsideWork()){
			
				// 週割増時間を逆時系列で割り当てる
				//*****（２次）
			}
		}
		
		return returnClass;
	}
	
	/**
	 * 月単位の時間を集計する
	 * @param companyId 会社ID
	 * @param employeeId 社員ID
	 * @param yearMonth 年月（度）
	 * @param closureId 締めID
	 * @param closureDate 締め日付
	 * @param datePeriod 期間
	 * @param workingSystem 労働制
	 * @param aggregateAtr 集計区分
	 * @param workplaceId 職場ID
	 * @param employmentCd 雇用コード
	 * @param aggrSettingMonthly 月別実績集計設定
	 * @param addSet 加算設定
	 * @param aggregateTotalWorkingTime 総労働時間
	 * @param repositories 月次集計が必要とするリポジトリ
	 */
	public void aggregateMonthlyHours(
			String companyId,
			String employeeId,
			YearMonth yearMonth,
			ClosureId closureId,
			ClosureDate closureDate,
			DatePeriod datePeriod,
			WorkingSystem workingSystem,
			MonthlyAggregateAtr aggregateAtr,
			String workplaceId,
			String employmentCd,
			AggrSettingMonthly aggrSettingMonthly,
			AddSet addSet,
			AggregateTotalWorkingTime aggregateTotalWorkingTime,
			RepositoriesRequiredByMonthlyAggr repositories){

		// 通常勤務の時
		if (workingSystem.isRegularWork()){
			
			// 「割増を求める」がtrueの時
			val legalAggrSetOfReg = aggrSettingMonthly.getRegularWork();
			val aggregateTimeSet = legalAggrSetOfReg.getAggregateTimeSet();
			if (aggregateTimeSet.isAskPremium()){
			
				// 通常勤務の月単位の時間を集計する
				this.aggregateTimePerMonthOfRegular(companyId, employeeId, yearMonth, datePeriod,
						workplaceId, employmentCd, addSet, aggregateTotalWorkingTime, repositories);
			}
		}
		
		// 変形労働時間勤務の時
		if (workingSystem.isVariableWorkingTimeWork()){
			
			// 変形労働勤務の月単位の時間を集計する
			this.aggregateTimePerMonthOfIrregular(companyId, employeeId,
					yearMonth, closureId, closureDate, datePeriod,
					aggrSettingMonthly.getIrregularWork(), addSet, aggregateTotalWorkingTime, repositories);
		}
	}
	
	/**
	 * 通常勤務の月単位の時間を集計する
	 * @param companyId 会社ID
	 * @param employeeId 社員ID
	 * @param yearMonth 年月（度）
	 * @param datePeriod 期間
	 * @param workplaceId 職場ID
	 * @param employmentCd 雇用コード
	 * @param addSet 加算設定
	 * @param aggregateTotalWorkingTime 集計総労働時間
	 * @param repositories 月次集計が必要とするリポジトリ
	 */
	private void aggregateTimePerMonthOfRegular(
			String companyId,
			String employeeId,
			YearMonth yearMonth,
			DatePeriod datePeriod,
			String workplaceId,
			String employmentCd,
			AddSet addSet,
			AggregateTotalWorkingTime aggregateTotalWorkingTime,
			RepositoriesRequiredByMonthlyAggr repositories){
		
		// 通常勤務の月割増時間の対象となる時間を求める
		val targetPremiumTimeMonthOfRegular = new TargetPremiumTimeMonthOfRegular();
		this.addedVacationUseTime = targetPremiumTimeMonthOfRegular.askPremiumTimeMonth(
				companyId, employeeId, datePeriod, addSet, aggregateTotalWorkingTime);
		val targetPremiumTimeMonth = targetPremiumTimeMonthOfRegular.getTargetPremiumTimeMonth();
		
		// 月の法定労働時間を取得する
		//*****（未）　月の計算（総労働時間にメンバを置いたほうが便利？）で確認して、貰ってくる。
		AttendanceTimeMonth statutoryWorkTime = new AttendanceTimeMonth(0);
		
		// 通常勤務の月割増対象時間　≦　法定労働時間　なら、処理終了
		if (targetPremiumTimeMonth.lessThanOrEqualTo(statutoryWorkTime.v())) return;
		
		// 通常勤務の月割増対象時間が法定労働時間を超えた分を「月割増対象時間超過分」とする
		val excessTargetPremiumTimeMonth = new AttendanceTimeMonth(targetPremiumTimeMonth.v());
		excessTargetPremiumTimeMonth.minusMinutes(statutoryWorkTime.v());
		
		// 月割増対象時間超過分－週割増合計時間を月割増合計時間とする
		this.monthlyTotalPremiumTime = new AttendanceTimeMonth(excessTargetPremiumTimeMonth.v());
		this.monthlyTotalPremiumTime.minusMinutes(this.weeklyTotalPremiumTime.v());
		if (this.monthlyTotalPremiumTime.lessThan(0)) {
			this.monthlyTotalPremiumTime = new AttendanceTimeMonth(0);
		}
	}
	
	
	/**
	 * 変形労働勤務の月単位の時間を集計する
	 * @param companyId 会社ID
	 * @param employeeId 社員ID
	 * @param yearMonth 年月（度）
	 * @param closureId 締めID
	 * @param closureDate 締め日付
	 * @param datePeriod 期間
	 * @param legalAggrSetOfIrg 変形労働時間勤務の法定内集計設定
	 * @param addSet 加算設定
	 * @param aggregateTotalWorkingTime 集計総労働時間
	 * @param repositories 月次集計が必要とするリポジトリ
	 */
	private void aggregateTimePerMonthOfIrregular(
			String companyId,
			String employeeId,
			YearMonth yearMonth,
			ClosureId closureId,
			ClosureDate closureDate,
			DatePeriod datePeriod,
			LegalAggrSetOfIrg legalAggrSetOfIrg,
			AddSet addSet,
			AggregateTotalWorkingTime aggregateTotalWorkingTime,
			RepositoriesRequiredByMonthlyAggr repositories){
		
		// 当月の変形期間繰越時間を集計する
		val irregularPeriodCarryforwardsTime = new IrregularPeriodCarryforwardsTimeOfCurrent();
		this.addedVacationUseTime = irregularPeriodCarryforwardsTime.aggregate(
				companyId, employeeId, datePeriod,
				this.weeklyTotalPremiumTime, addSet, aggregateTotalWorkingTime);
		
		// 該当精算期間の開始月～前月の変形期間繰越時間を集計する
		val pastIrregularPeriodCarryforwardsTime = this.aggregatePastIrregularPeriodCarryforwardsTime(
				employeeId, yearMonth, closureId, closureDate, legalAggrSetOfIrg, repositories);
		
		// 開始月～当月の変形期間繰越時間を求める
		AttendanceTimeMonth totalIrregularPeriodCarryforwardsTime =
				new AttendanceTimeMonth(pastIrregularPeriodCarryforwardsTime.v());
		totalIrregularPeriodCarryforwardsTime =
				totalIrregularPeriodCarryforwardsTime.addMinutes(irregularPeriodCarryforwardsTime.getTime().v());

		// 精算月か確認する
		boolean isSettlementMonth = false;
		if (legalAggrSetOfIrg.isSameSettlementEndMonth(yearMonth)){
			
			isSettlementMonth = true;
		}
		else{
			//*****（未）　退職者なら、その月に精算。退職者かどうかを取る方法の確認要。
			//if (退職者なら)　{
			//	isSettlementMonth = true;
			//}
		}

		if (isSettlementMonth){
			
			// 精算月の時、月割増合計時間に集計結果を入れる
			this.monthlyTotalPremiumTime = new AttendanceTimeMonth(totalIrregularPeriodCarryforwardsTime.v());
			
			// 変形期間繰越時間を 0 にする
			this.irregularWorkingTime.setIrregularPeriodCarryforwardTime(new AttendanceTimeMonth(0));
		}
		else{
			
			// 精算月でない時、複数月変形途中時間・変形期間繰越時間に集計結果を入れる
			this.irregularWorkingTime.setMultiMonthIrregularMiddleTime(
					new AttendanceTimeMonth(totalIrregularPeriodCarryforwardsTime.v()));
			this.irregularWorkingTime.setIrregularPeriodCarryforwardTime(
					new AttendanceTimeMonth(irregularPeriodCarryforwardsTime.getTime().v()));
		}
	}
	
	/**
	 * 精算期間．開始月～前月までの変形期間繰越時間を集計する
	 * @param employeeId 社員ID
	 * @param yearMonth 年月（度）
	 * @param closureId 締めID
	 * @param closureDate 締め日付
	 * @param legalAggrSetOfIrg 変形労働時間勤務の法定内集計設定
	 * @param repositories 月次集計が必要とするリポジトリ
	 * @return 過去の変形期間繰越時間
	 */
	private AttendanceTimeMonth aggregatePastIrregularPeriodCarryforwardsTime(
			String employeeId, YearMonth yearMonth,
			ClosureId closureId, ClosureDate closureDate,
			LegalAggrSetOfIrg legalAggrSetOfIrg,
			RepositoriesRequiredByMonthlyAggr repositories){
		
		AttendanceTimeMonth irregularPeriodCarryforwardsTime = new AttendanceTimeMonth(0);
		
		// 精算期間を取得する
		val pastYearMonths = legalAggrSetOfIrg.getPastSettlementYearMonths(yearMonth);
		
		// 開始月～前月までの変形期間繰越時間を集計する
		for (val pastYearMonth : pastYearMonths){
			val attendanceTimeOpt = repositories.getAttendanceTimeOfMonthly().find(
					employeeId, pastYearMonth, closureId, closureDate);
			if (!attendanceTimeOpt.isPresent()) continue;
			
			val attendanceTime = attendanceTimeOpt.get();
			val actualWorkingTime = attendanceTime.getMonthlyCalculation().getActualWorkingTime();
			val irregularWorkingTime = actualWorkingTime.getIrregularWorkingTime();
			val carryforwardTime = irregularWorkingTime.getIrregularPeriodCarryforwardTime();
			
			irregularPeriodCarryforwardsTime = irregularPeriodCarryforwardsTime.addMinutes(carryforwardTime.v());
		}
		
		return irregularPeriodCarryforwardsTime;
	}
}
