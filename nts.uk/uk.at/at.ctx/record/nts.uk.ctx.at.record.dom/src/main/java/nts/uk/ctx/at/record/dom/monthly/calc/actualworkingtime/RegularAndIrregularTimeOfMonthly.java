package nts.uk.ctx.at.record.dom.monthly.calc.actualworkingtime;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import lombok.Getter;
import lombok.Setter;
import lombok.val;
import nts.arc.diagnose.stopwatch.concurrent.ConcurrentStopwatches;
import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.record.dom.monthly.calc.AggregateMonthlyValue;
import nts.uk.ctx.at.record.dom.monthly.calc.MonthlyAggregateAtr;
import nts.uk.ctx.at.record.dom.monthly.calc.MonthlyCalculation;
import nts.uk.ctx.at.record.dom.monthly.calc.totalworkingtime.AggregateTotalWorkingTime;
import nts.uk.ctx.at.record.dom.monthlyprocess.aggr.MonthlyAggregationErrorInfo;
import nts.uk.ctx.at.record.dom.monthlyprocess.aggr.work.MonAggrCompanySettings;
import nts.uk.ctx.at.record.dom.monthlyprocess.aggr.work.MonAggrEmployeeSettings;
import nts.uk.ctx.at.record.dom.monthlyprocess.aggr.work.MonthlyCalculatingDailys;
import nts.uk.ctx.at.record.dom.monthlyprocess.aggr.work.RepositoriesRequiredByMonthlyAggr;
import nts.uk.ctx.at.record.dom.monthlyprocess.aggr.work.SettingRequiredByDefo;
import nts.uk.ctx.at.record.dom.monthlyprocess.aggr.work.SettingRequiredByReg;
import nts.uk.ctx.at.record.dom.monthlyprocess.aggr.work.excessoutside.ExcessOutsideWorkMng;
import nts.uk.ctx.at.record.dom.monthlyprocess.aggr.work.premiumtarget.AddedVacationUseTime;
import nts.uk.ctx.at.record.dom.monthlyprocess.aggr.work.premiumtarget.IrregularPeriodCarryforwardsTimeOfCurrent;
import nts.uk.ctx.at.record.dom.monthlyprocess.aggr.work.premiumtarget.TargetPremiumTimeMonthOfRegular;
import nts.uk.ctx.at.record.dom.monthlyprocess.aggr.work.premiumtarget.TargetPrmTimeWeekOfPrevMonLast;
import nts.uk.ctx.at.record.dom.monthlyprocess.aggr.work.premiumtarget.getvacationaddtime.AddSet;
import nts.uk.ctx.at.record.dom.monthlyprocess.aggr.work.premiumtarget.getvacationaddtime.GetAddSet;
import nts.uk.ctx.at.record.dom.monthlyprocess.aggr.work.premiumtarget.getvacationaddtime.PremiumAtr;
import nts.uk.ctx.at.record.dom.weekly.AttendanceTimeOfWeekly;
import nts.uk.ctx.at.record.dom.workrecord.monthcal.export.GetSettlementPeriodOfDefor;
import nts.uk.ctx.at.record.dom.workrecord.workperfor.dailymonthlyprocessing.ErrMessageContent;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonth;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonthWithMinus;
import nts.uk.ctx.at.shared.dom.statutory.worktime.shared.WeekStart;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingSystem;
import nts.uk.ctx.at.shared.dom.workrule.closure.Closure;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureId;
import nts.uk.shr.com.i18n.TextResource;
import nts.uk.shr.com.time.calendar.date.ClosureDate;
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
	
	/** 当月の変形期間繰越時間 */
	private IrregularPeriodCarryforwardsTimeOfCurrent irregularPeriodCarryforwardsTime;
	/** 加算した休暇使用時間 */
	private AddedVacationUseTime addedVacationUseTime;
	/** 週割増処理期間 */
	private DatePeriod weekAggrPeriod;
	/** 前月の最終週の週割増対象時間 */
	private AttendanceTimeMonth weekPremiumTimeOfPrevMonth;
	/** エラー情報 */
	private List<MonthlyAggregationErrorInfo> errorInfos;
	
	/**
	 * コンストラクタ
	 */
	public RegularAndIrregularTimeOfMonthly(){
		
		this.weeklyTotalPremiumTime = new AttendanceTimeMonth(0);
		this.monthlyTotalPremiumTime = new AttendanceTimeMonth(0);
		this.irregularWorkingTime = new IrregularWorkingTimeOfMonthly();
		
		this.irregularPeriodCarryforwardsTime = new IrregularPeriodCarryforwardsTimeOfCurrent();
		this.addedVacationUseTime = new AddedVacationUseTime();
		this.weekAggrPeriod = new DatePeriod(GeneralDate.today(), GeneralDate.today());
		this.weekPremiumTimeOfPrevMonth = new AttendanceTimeMonth(0);
		this.errorInfos = new ArrayList<>();
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
	 * @param closureId 締めID
	 * @param closureDate 締め日付
	 * @param datePeriod 期間
	 * @param workingSystem 労働制
	 * @param closureOpt 締め
	 * @param aggregateAtr 集計区分
	 * @param employmentCd 雇用コード
	 * @param settingsByReg 通常勤務が必要とする設定
	 * @param settingsByDefo 変形労働勤務が必要とする設定
	 * @param aggregateTotalWorkingTime 集計総労働時間
	 * @param excessOutsideWorkMng 時間外超過管理
	 * @param startWeekNo 開始週NO
	 * @param companySets 月別集計で必要な会社別設定
	 * @param employeeSets 月別集計で必要な社員別設定
	 * @param monthlyCalcDailys 月の計算中の日別実績データ
	 * @param repositories 月次集計が必要とするリポジトリ
	 * @return 戻り値：月別実績を集計する
	 */
	public AggregateMonthlyValue aggregateMonthly(
			String companyId,
			String employeeId,
			YearMonth yearMonth,
			ClosureId closureId,
			ClosureDate closureDate,
			DatePeriod datePeriod,
			WorkingSystem workingSystem,
			Optional<Closure> closureOpt,
			MonthlyAggregateAtr aggregateAtr,
			String employmentCd,
			SettingRequiredByReg settingsByReg,
			SettingRequiredByDefo settingsByDefo,
			AggregateTotalWorkingTime aggregateTotalWorkingTime,
			ExcessOutsideWorkMng excessOutsideWorkMng,
			int startWeekNo,
			MonAggrCompanySettings companySets,
			MonAggrEmployeeSettings employeeSets,
			MonthlyCalculatingDailys monthlyCalcDailys,
			RepositoriesRequiredByMonthlyAggr repositories){
		
		List<AttendanceTimeOfWeekly> resultWeeks = new ArrayList<>();
		
		ConcurrentStopwatches.start("12222.1:週開始の取得：");
		
		// 週開始を取得する
		val workTimeSetOpt = companySets.getWorkingTimeSetting(employmentCd,
				employeeSets.getWorkplacesToRoot(datePeriod.end()), workingSystem, employeeSets, repositories);
		if (!workTimeSetOpt.isPresent()){
			this.errorInfos.add(new MonthlyAggregationErrorInfo(
					"005", new ErrMessageContent(TextResource.localize("Msg_1171"))));
			return AggregateMonthlyValue.of(aggregateTotalWorkingTime, excessOutsideWorkMng, resultWeeks);
		}
		WeekStart weekStart = null;
		if (workTimeSetOpt.get().getWeeklyTime() != null){
			weekStart = workTimeSetOpt.get().getWeeklyTime().getStart();
		}
		if (weekStart == null) {
			this.errorInfos.add(new MonthlyAggregationErrorInfo(
					"005", new ErrMessageContent(TextResource.localize("Msg_1171"))));
			return AggregateMonthlyValue.of(aggregateTotalWorkingTime, excessOutsideWorkMng, resultWeeks);
		}

		ConcurrentStopwatches.stop("12222.1:週開始の取得：");
		ConcurrentStopwatches.start("12222.2:前月の最終週：");
		
		this.weekPremiumTimeOfPrevMonth = new AttendanceTimeMonth(0);
		if (weekStart != WeekStart.TighteningStartDate){
			
			// 前月の最終週を集計する
			this.aggregateLastWeekOfPrevMonth(companyId, employeeId, datePeriod, workingSystem,
					aggregateAtr, settingsByReg, settingsByDefo,
					weekStart, startWeekNo, companySets, employeeSets, monthlyCalcDailys, repositories);;
		}

		ConcurrentStopwatches.stop("12222.2:前月の最終週：");
		
		// 期間．開始日を処理日にする
		GeneralDate procDate = datePeriod.start();
		int procWeekNo = startWeekNo;
		
		// 「処理中の週開始日」を期間．開始日にする
		GeneralDate procWeekStartDate = datePeriod.start();
		
		// 処理をする期間の日数分ループ
		val attendanceTimeOfDailyMap = monthlyCalcDailys.getAttendanceTimeOfDailyMap();
		val workInformationOfDailyMap = monthlyCalcDailys.getWorkInfoOfDailyMap();
		while (procDate.beforeOrEquals(datePeriod.end())){
			
			if (attendanceTimeOfDailyMap.containsKey(procDate)){
				val attendanceTimeOfDaily = attendanceTimeOfDailyMap.get(procDate);
				
				// 処理日の職場コードを取得する
				String procWorkplaceId = "empty";
				val workplaceOpt = employeeSets.getWorkplace(procDate);
				if (workplaceOpt.isPresent()){
					procWorkplaceId = workplaceOpt.get().getWorkplaceId();
				}
				
				// 処理日の雇用コードを取得する
				String procEmploymentCd = "empty";
				val employmentOpt = employeeSets.getEmployment(procDate);
				if (employmentOpt.isPresent()){
					procEmploymentCd = employmentOpt.get().getEmploymentCode();
				}
				
				ConcurrentStopwatches.start("12222.3:日別実績の集計：");
				
				// 処理日の勤務情報を取得する
				if (workInformationOfDailyMap.containsKey(procDate)) {
					val workInfo = workInformationOfDailyMap.get(procDate).getRecordInfo();
					
					// 日別実績を集計する　（通常・変形労働時間勤務用）
					aggregateTotalWorkingTime.aggregateDailyForRegAndIrreg(attendanceTimeOfDaily,
							companyId, procWorkplaceId, procEmploymentCd, workingSystem, aggregateAtr,
							workInfo, settingsByReg, settingsByDefo, companySets, employeeSets, repositories);
				}
				
				ConcurrentStopwatches.stop("12222.3:日別実績の集計：");
			}
			
			// 週の集計をする日か確認する
			if (MonthlyCalculation.isAggregateWeek(procDate, weekStart, datePeriod, closureOpt)){
			
				// 週の期間を計算
				this.weekAggrPeriod = new DatePeriod(procWeekStartDate, procDate);
				
				// 翌週の開始日を設定しておく
				procWeekStartDate = procDate.addDays(1);
				
				// 対象の「週別実績の勤怠時間」を作成する
				val newWeek = new AttendanceTimeOfWeekly(employeeId, yearMonth, closureId, closureDate,
						procWeekNo, this.weekAggrPeriod);
				procWeekNo += 1;
				
				// 週別実績を集計する
				{
					ConcurrentStopwatches.start("12222.4:週別実績の集計：");
					
					// 週の計算
					val weekCalc = newWeek.getWeeklyCalculation();
					weekCalc.aggregate(companyId, employeeId, yearMonth, this.weekAggrPeriod,
							workingSystem, aggregateAtr,
							settingsByReg, settingsByDefo, aggregateTotalWorkingTime,
							weekStart, this.weekPremiumTimeOfPrevMonth,
							attendanceTimeOfDailyMap, companySets, repositories);
					resultWeeks.add(newWeek);
					if (weekCalc.getErrorInfos().size() > 0) this.errorInfos.addAll(weekCalc.getErrorInfos());

					ConcurrentStopwatches.stop("12222.4:週別実績の集計：");
					
					// 「前月の最終週の週割増対象時間 」を0にする　（前月の最終週の～は、1回目の週の計算だけで使う）
					this.weekPremiumTimeOfPrevMonth = new AttendanceTimeMonth(0);
					
					// 月の週割増合計へ
					val weekTime = weekCalc.getRegAndIrgTime();
					this.weeklyTotalPremiumTime = this.weeklyTotalPremiumTime.addMinutes(
							weekTime.getWeeklyTotalPremiumTime().v());
					
					// 集計区分を確認する
					if (aggregateAtr == MonthlyAggregateAtr.EXCESS_OUTSIDE_WORK && excessOutsideWorkMng != null){

						ConcurrentStopwatches.start("12222.5:時間外超過の集計：");
						
						// 時間外超過の集計
						newWeek.getExcessOutside().aggregate(weekCalc, companySets);
						
						ConcurrentStopwatches.stop("12222.5:時間外超過の集計：");
						ConcurrentStopwatches.start("12222.6:逆時系列割り当て：");
						
						// 時間外超過の時、週割増時間を逆時系列で割り当てる
						excessOutsideWorkMng.assignWeeklyPremiumTimeByReverseTimeSeries(
								weekTime.getWeekPremiumProcPeriod(), weekTime.getWeeklyTotalPremiumTime(),
								aggregateTotalWorkingTime, repositories);
						
						ConcurrentStopwatches.stop("12222.6:逆時系列割り当て：");
					}
				}
			}
			
			procDate = procDate.addDays(1);
		}
		
		return AggregateMonthlyValue.of(aggregateTotalWorkingTime, excessOutsideWorkMng, resultWeeks);
	}
	
	/**
	 * 前月の最終週を集計する
	 * @param companyId 会社ID
	 * @param employeeId 社員ID
	 * @param datePeriod 期間
	 * @param workingSystem 労働制
	 * @param aggregateAtr 集計区分
	 * @param settingsByReg 通常勤務が必要とする設定
	 * @param settingsByDefo 変形労働勤務が必要とする設定
	 * @param weekStart 週開始
	 * @param startWeekNo 開始週NO
	 * @param companySets 月別集計で必要な会社別設定
	 * @param employeeSets 月別集計で必要な社員別設定
	 * @param monthlyCalcDailys 月の計算中の日別実績データ
	 * @param repositories 月次集計が必要とするリポジトリ
	 */
	private void aggregateLastWeekOfPrevMonth(
			String companyId,
			String employeeId,
			DatePeriod datePeriod,
			WorkingSystem workingSystem,
			MonthlyAggregateAtr aggregateAtr,
			SettingRequiredByReg settingsByReg,
			SettingRequiredByDefo settingsByDefo,
			WeekStart weekStart,
			int startWeekNo,
			MonAggrCompanySettings companySets,
			MonAggrEmployeeSettings employeeSets,
			MonthlyCalculatingDailys monthlyCalcDailys,
			RepositoriesRequiredByMonthlyAggr repositories){
		
		// 前月の最終週を集計するか判断する
		{
			// 労働条件のループの1回目の処理か確認する　（週NOが1以外なら、その月度の1回目ではない）
			if (startWeekNo != 1) return;
			
			// 前月の最終日の労働制を確認する
			val workingConditionItemOpt = repositories.getWorkingConditionItem()
					 .getBySidAndStandardDate(employeeId, datePeriod.start().addDays(-1));
			if (!workingConditionItemOpt.isPresent()) return;
			val prevWorkingSystem = workingConditionItemOpt.get().getLaborSystem();
			
			if (workingSystem == WorkingSystem.REGULAR_WORK){
				if (prevWorkingSystem != WorkingSystem.REGULAR_WORK) return;
			}
			if (workingSystem == WorkingSystem.VARIABLE_WORKING_TIME_WORK){
				if (prevWorkingSystem == WorkingSystem.FLEX_TIME_WORK) return;
			}
		}
		
		// 前月の最終週の期間を求める
		val employee = employeeSets.getEmployee();
		DatePeriod lastWeekPeriod = null;
		if (!MonthlyCalculation.isWeekStart(datePeriod.start(), weekStart)){
			GeneralDate startDate = datePeriod.start().addDays(-1);
			for (int i = 0; i < 6; i++){
				if (MonthlyCalculation.isWeekStart(startDate, weekStart)) break;
				startDate = startDate.addDays(-1);
			}
			GeneralDate endDate = datePeriod.start().addDays(-1);
			lastWeekPeriod = MonthlyCalculation.confirmProcPeriod(
					new DatePeriod(startDate, endDate),
					new DatePeriod(employee.getEntryDate(), employee.getRetiredDate()));
		}
		if (lastWeekPeriod == null) return;
		
		// 最終週用の集計総労働時間を用意する
		AggregateTotalWorkingTime prevTotalWorkingTime = new AggregateTotalWorkingTime();
		
		val attendanceTimeOfDailyMap = monthlyCalcDailys.getAttendanceTimeOfDailyMap();
		val workInformationOfDailyMap = monthlyCalcDailys.getWorkInfoOfDailyMap();
		
		// 共有項目を集計する
		prevTotalWorkingTime.aggregateSharedItem(lastWeekPeriod, attendanceTimeOfDailyMap);

		GeneralDate procDate = lastWeekPeriod.start();
		while (procDate.beforeOrEquals(lastWeekPeriod.end())){
			
			if (attendanceTimeOfDailyMap.containsKey(procDate)){
				val attendanceTimeOfDaily = attendanceTimeOfDailyMap.get(procDate);
				
				// 処理日の職場コードを取得する
				String procWorkplaceId = "empty";
				val affWorkplaceOpt = employeeSets.getWorkplace(procDate);
				if (affWorkplaceOpt.isPresent()){
					procWorkplaceId = affWorkplaceOpt.get().getWorkplaceId();
				}
				
				// 処理日の雇用コードを取得する
				String procEmploymentCd = "empty";
				val employmentOpt = employeeSets.getEmployment(procDate);
				if (employmentOpt.isPresent()){
					procEmploymentCd = employmentOpt.get().getEmploymentCode();
				}
				
				// 処理日の勤務情報を取得する
				if (workInformationOfDailyMap.containsKey(procDate)) {
					val workInfo = workInformationOfDailyMap.get(procDate).getRecordInfo();
					
					// 日別実績を集計する　（通常・変形労働時間勤務用）
					prevTotalWorkingTime.aggregateDailyForRegAndIrreg(attendanceTimeOfDaily,
							companyId, procWorkplaceId, procEmploymentCd, workingSystem, aggregateAtr,
							workInfo, settingsByReg, settingsByDefo, companySets, employeeSets, repositories);
				}
			}
			
			// 処理日を更新する
			procDate = procDate.addDays(1);
		}

		// 加算設定取得　（割増用）
		AddSet addSet = new AddSet();
		if (workingSystem == WorkingSystem.REGULAR_WORK){
			addSet = GetAddSet.get(workingSystem, PremiumAtr.PREMIUM, settingsByReg.getHolidayAdditionMap());
		}
		if (workingSystem == WorkingSystem.VARIABLE_WORKING_TIME_WORK){
			addSet = GetAddSet.get(workingSystem, PremiumAtr.PREMIUM, settingsByDefo.getHolidayAdditionMap());
		}
		if (addSet.getErrorInfo().isPresent()){
			this.errorInfos.add(addSet.getErrorInfo().get());
		}
		
		// 前月の最終週の週割増時間を求める
		val weekPremiumTime = TargetPrmTimeWeekOfPrevMonLast.askPremiumTimeWeek(
				companyId, employeeId, lastWeekPeriod, addSet, prevTotalWorkingTime);
		this.weekPremiumTimeOfPrevMonth = new AttendanceTimeMonth(weekPremiumTime.getTargetTime().v());
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
	 * @param isRetireMonth 退職月かどうか
	 * @param workplaceId 職場ID
	 * @param employmentCd 雇用コード
	 * @param settingsByReg 通常勤務が必要とする設定
	 * @param settingsByDefo 変形労働勤務が必要とする設定
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
			boolean isRetireMonth,
			String workplaceId,
			String employmentCd,
			SettingRequiredByReg settingsByReg,
			SettingRequiredByDefo settingsByDefo,
			AggregateTotalWorkingTime aggregateTotalWorkingTime,
			RepositoriesRequiredByMonthlyAggr repositories){

		// 通常勤務の時
		if (workingSystem == WorkingSystem.REGULAR_WORK){
			
			// 加算設定　取得　（割増用）
			val addSet = GetAddSet.get(workingSystem, PremiumAtr.PREMIUM, settingsByReg.getHolidayAdditionMap());
			if (addSet.getErrorInfo().isPresent()){
				this.errorInfos.add(addSet.getErrorInfo().get());
			}
			
			// 「割増を求める」がtrueの時
			val aggregateTimeSet = settingsByReg.getRegularAggrSet().getAggregateTimeSet();
			if (aggregateTimeSet.getSurchargeWeekMonth()){
			
				// 通常勤務の月単位の時間を集計する
				this.aggregateTimePerMonthOfRegular(companyId, employeeId, yearMonth, datePeriod,
						workplaceId, employmentCd, addSet, aggregateTotalWorkingTime,
						settingsByReg.getStatutoryWorkingTimeMonth(), repositories);
			}
		}
		
		// 変形労働時間勤務の時
		if (workingSystem == WorkingSystem.VARIABLE_WORKING_TIME_WORK){
			
			// 加算設定　取得　（割増用）
			val addSet = GetAddSet.get(workingSystem, PremiumAtr.PREMIUM, settingsByDefo.getHolidayAdditionMap());
			if (addSet.getErrorInfo().isPresent()){
				this.errorInfos.add(addSet.getErrorInfo().get());
			}
			
			// 変形労働勤務の月単位の時間を集計する
			this.aggregateTimePerMonthOfIrregular(companyId, employeeId,
					yearMonth, closureId, closureDate, datePeriod, isRetireMonth,
					settingsByDefo, addSet, aggregateTotalWorkingTime, repositories);
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
	 * @param statutoryWorkingTimeMonth 月間法定労働時間
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
			AttendanceTimeMonth statutoryWorkingTimeMonth,
			RepositoriesRequiredByMonthlyAggr repositories){
		
		// 通常勤務の月割増時間の対象となる時間を求める
		val targetPremiumTimeMonthOfRegular = new TargetPremiumTimeMonthOfRegular();
		this.addedVacationUseTime = targetPremiumTimeMonthOfRegular.askPremiumTimeMonth(
				companyId, employeeId, datePeriod, addSet, aggregateTotalWorkingTime);
		val targetPremiumTimeMonth = targetPremiumTimeMonthOfRegular.getTargetPremiumTimeMonth();
		
		// 通常勤務の月割増対象時間　≦　法定労働時間　なら、処理終了
		if (targetPremiumTimeMonth.lessThanOrEqualTo(statutoryWorkingTimeMonth.v())) return;
		
		// 通常勤務の月割増対象時間が法定労働時間を超えた分を「月割増対象時間超過分」とする
		int excessTargetPremiumMinutes = targetPremiumTimeMonth.v();
		excessTargetPremiumMinutes -= statutoryWorkingTimeMonth.v();
		
		// 月割増対象時間超過分－週割増合計時間を月割増合計時間とする
		this.monthlyTotalPremiumTime = new AttendanceTimeMonth(
				excessTargetPremiumMinutes - this.weeklyTotalPremiumTime.v());
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
	 * @param isRetireMonth 退職月かどうか
	 * @param settingsByDefo 変形労働勤務が必要とする設定
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
			boolean isRetireMonth,
			SettingRequiredByDefo settingsByDefo,
			AddSet addSet,
			AggregateTotalWorkingTime aggregateTotalWorkingTime,
			RepositoriesRequiredByMonthlyAggr repositories){
		
		// 当月の変形期間繰越時間を集計する
		this.irregularPeriodCarryforwardsTime = new IrregularPeriodCarryforwardsTimeOfCurrent();
		this.irregularPeriodCarryforwardsTime.aggregate(companyId, employeeId, datePeriod,
				this.weeklyTotalPremiumTime, settingsByDefo.getHolidayAdditionMap(),
				aggregateTotalWorkingTime, settingsByDefo.getStatutoryWorkingTimeMonth());
		this.addedVacationUseTime.addMinutesToAddTimePerMonth(
				this.irregularPeriodCarryforwardsTime.getAddedVacationUseTime().v());
		
		// 「変形労働時間勤務の法定内集計設定」を取得
		val deforAggrSet = settingsByDefo.getDeforAggrSet();
		
		// 変形労働精算期間の取得
		val setlPeriod = GetSettlementPeriodOfDefor.createFromDeforAggrSet(deforAggrSet);
		
		// 該当精算期間の開始月～前月の変形期間繰越時間を集計する
		val pastIrregularPeriodCarryforwardsTime = this.aggregatePastIrregularPeriodCarryforwardsTime(
				employeeId, yearMonth, closureId, closureDate, setlPeriod, repositories);
		
		// 開始月～当月の変形期間繰越時間を求める
		AttendanceTimeMonthWithMinus totalIrregularPeriodCarryforwardsTime = new AttendanceTimeMonthWithMinus(
				pastIrregularPeriodCarryforwardsTime.v());
		totalIrregularPeriodCarryforwardsTime = totalIrregularPeriodCarryforwardsTime.addMinutes(
				this.irregularPeriodCarryforwardsTime.getTime().v());

		// 精算月か確認する
		if (setlPeriod.isSettlementMonth(yearMonth, isRetireMonth)){
			
			// 精算月の時、月割増合計時間に集計結果を入れる
			this.monthlyTotalPremiumTime = new AttendanceTimeMonth(totalIrregularPeriodCarryforwardsTime.v());
			
			// 変形期間繰越時間を 0 にする
			this.irregularWorkingTime.setIrregularPeriodCarryforwardTime(new AttendanceTimeMonthWithMinus(0));
		}
		else{
			
			// 精算月でない時、複数月変形途中時間・変形期間繰越時間に集計結果を入れる
			this.irregularWorkingTime.setMultiMonthIrregularMiddleTime(totalIrregularPeriodCarryforwardsTime);
			this.irregularWorkingTime.setIrregularPeriodCarryforwardTime(
					new AttendanceTimeMonthWithMinus(this.irregularPeriodCarryforwardsTime.getTime().v()));
		}
	}
	
	/**
	 * 精算期間．開始月～前月までの変形期間繰越時間を集計する
	 * @param employeeId 社員ID
	 * @param yearMonth 年月（度）
	 * @param closureId 締めID
	 * @param closureDate 締め日付
	 * @param setlPeriod 変形労働精算期間
	 * @param repositories 月次集計が必要とするリポジトリ
	 * @return 過去の変形期間繰越時間
	 */
	private AttendanceTimeMonth aggregatePastIrregularPeriodCarryforwardsTime(
			String employeeId, YearMonth yearMonth,
			ClosureId closureId, ClosureDate closureDate,
			GetSettlementPeriodOfDefor setlPeriod,
			RepositoriesRequiredByMonthlyAggr repositories){
		
		AttendanceTimeMonth irregularPeriodCarryforwardsTime = new AttendanceTimeMonth(0);
		
		// 精算期間を取得する
		val pastYearMonths = setlPeriod.getPastSettlementYearMonths(yearMonth);
		
		// 開始月～前月までの変形期間繰越時間を集計する
		for (val pastYearMonth : pastYearMonths){
			val attendanceTimeList = repositories.getAttendanceTimeOfMonthly().findByYearMonthOrderByStartYmd(
					employeeId, pastYearMonth);
			for (val attendanceTime : attendanceTimeList){
				val actualWorkingTime = attendanceTime.getMonthlyCalculation().getActualWorkingTime();
				val irregularWorkingTime = actualWorkingTime.getIrregularWorkingTime();
				val carryforwardTime = irregularWorkingTime.getIrregularPeriodCarryforwardTime();
				
				irregularPeriodCarryforwardsTime =
						irregularPeriodCarryforwardsTime.addMinutes(carryforwardTime.v());
			}
		}
		
		return irregularPeriodCarryforwardsTime;
	}
	
	/**
	 * 総労働対象時間の取得
	 * @return 総労働対象時間
	 */
	public AttendanceTimeMonth getTotalWorkingTargetTime(){
		
		return new AttendanceTimeMonth(this.weeklyTotalPremiumTime.v() + this.monthlyTotalPremiumTime.v() +
				this.irregularWorkingTime.getTotalWorkingTargetTime().v());
	}
	
	/**
	 * 合算する
	 * @param target 加算対象
	 */
	public void sum(RegularAndIrregularTimeOfMonthly target){
		
		this.weeklyTotalPremiumTime = this.weeklyTotalPremiumTime.addMinutes(target.weeklyTotalPremiumTime.v());
		this.monthlyTotalPremiumTime = this.monthlyTotalPremiumTime.addMinutes(target.monthlyTotalPremiumTime.v());
		this.irregularWorkingTime.sum(target.irregularWorkingTime);
	}
}
