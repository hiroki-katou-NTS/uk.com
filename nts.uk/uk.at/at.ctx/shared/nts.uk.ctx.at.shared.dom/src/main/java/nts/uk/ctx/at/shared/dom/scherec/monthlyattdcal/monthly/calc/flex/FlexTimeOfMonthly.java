package nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.calc.flex;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import lombok.Getter;
import lombok.Setter;
import lombok.val;
import nts.arc.diagnose.stopwatch.concurrent.ConcurrentStopwatches;
import nts.arc.error.BusinessException;
import nts.arc.layer.app.cache.CacheCarrier;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.arc.time.calendar.period.DatePeriod;
import nts.gul.serialize.binary.SerializableWithOptional;
import nts.uk.ctx.at.shared.dom.common.MonthlyEstimateTime;
import nts.uk.ctx.at.shared.dom.common.days.AttendanceDaysMonth;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonth;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonthWithMinus;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeYear;
import nts.uk.ctx.at.shared.dom.scherec.addsettingofworktime.WorkFlexAdditionSet;
import nts.uk.ctx.at.shared.dom.scherec.addsettingofworktime.WorkTimeAddtionTimeGetter;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.MonthlyAggregationErrorInfo;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.WorkingSystemChangeCheckService;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.WorkingSystemChangeCheckService.WorkingSystemChangeState;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.flex.AggregateSetting;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.flex.CarryforwardSetInShortageFlex;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.flex.FlexAggregateMethod;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.flex.FlexMonthWorkTimeAggrSet;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.flex.SettlePeriod;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.flex.SettlePeriodOfFlex;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.flex.ShortageFlexSetting;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.work.MonAggrCompanySettings;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.work.MonAggrEmployeeSettings;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.work.MonthlyCalculatingDailys;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.work.SettingRequiredByFlex;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.work.excessoutside.ExcessOutsideWorkMng;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.work.excessoutside.StandardFlexTime;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.work.premiumtarget.AddedVacationUseTime;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.work.premiumtarget.getvacationaddtime.AddSet;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.work.premiumtarget.getvacationaddtime.GetAddSet;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.work.premiumtarget.getvacationaddtime.GetVacationAddTime;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.work.premiumtarget.getvacationaddtime.PremiumAtr;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.AttendanceItemOfMonthly;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.AttendanceTimeOfMonthly;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.TimeMonthWithCalculationAndMinus;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.calc.AggregateMonthlyValue;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.calc.DeductDaysAndTime;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.calc.MonthlyAggregateAtr;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.calc.MonthlyCalculation;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.calc.totalworkingtime.AggregateTotalWorkingTime;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.erroralarm.Flex;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.workform.flex.MonthlyAggrSetOfFlex;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.weekly.AttendanceTimeOfWeekly;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.weekly.WeeklyCalculation;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.algorithm.monthly.MonthlyFlexStatutoryLaborTime;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.algorithm.monthly.MonthlyStatutoryLaborDivisionService;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.algorithm.monthly.MonthlyStatutoryWorkingHours;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItem;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingSystem;
import nts.uk.ctx.at.shared.dom.workrecord.workperfor.dailymonthlyprocessing.ErrMessageContent;
import nts.uk.ctx.at.shared.dom.workrule.closure.Closure;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureId;
import nts.uk.ctx.at.shared.dom.workrule.weekmanage.WeekStart;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.enumcommon.NotUseAtr;
import nts.uk.shr.com.i18n.TextResource;
import nts.uk.shr.com.time.calendar.date.ClosureDate;

/**
 * 月別実績のフレックス時間
 * @author shuichi_ishida
 */
@Getter
public class FlexTimeOfMonthly implements SerializableWithOptional{

	/** Serializable */
	private static final long serialVersionUID = 1L;

	/** フレックス時間 */
	private FlexTime flexTime;
	/** フレックス超過時間 */
	private AttendanceTimeMonth flexExcessTime;
	/** フレックス不足時間 */
	private AttendanceTimeMonth flexShortageTime;
	/** フレックス繰越時間 */
	private FlexCarryforwardTime flexCarryforwardTime;
	/** 時間外超過のフレックス時間 */
	@Setter
	private FlexTimeOfExcessOutsideTime flexTimeOfExcessOutsideTime;
	/** フレックス不足控除時間 */
	private FlexShortDeductTime flexShortDeductTime;
	/** 当月精算フレックス時間 */
	private AttendanceTimeMonthWithMinus flexSettleTime;
	
	/** フレックス時間勤務の月の集計設定 */
	private FlexMonthWorkTimeAggrSet flexAggrSet;
	/** 休暇加算時間設定 */
	private Map<String, AggregateRoot> holidayAdditionMap;
	/** 月次法定内のみ加算 */
	private boolean addMonthlyWithinStatutory;
	/** フレックス勤務の月別集計設定 */
	private Optional<MonthlyAggrSetOfFlex> monthlyAggrSetOfFlexOpt;
	/** 加算した休暇使用時間 */
	private AddedVacationUseTime addedVacationUseTime;
	/** 控除の日数と時間 */
	private DeductDaysAndTime deductDaysAndTime;
	/** 控除前の年休控除時間 */
	private AttendanceTimeMonth annualLeaveTimeBeforeDeduct;
	/** エラー情報リスト */
	private List<MonthlyAggregationErrorInfo> errorInfos;
	/** 社員の月別実績のエラー詳細リスト */
	private List<Flex> perErrors; 

	private void writeObject(ObjectOutputStream stream){
		writeObjectWithOptional(stream);
	}
	
	private void readObject(ObjectInputStream stream){
		readObjectWithOptional(stream);
	}
	
	/**
	 * コンストラクタ
	 */
	public FlexTimeOfMonthly(){
		
		this.flexTime = new FlexTime();
		this.flexExcessTime = new AttendanceTimeMonth(0);
		this.flexShortageTime = new AttendanceTimeMonth(0);
		this.flexCarryforwardTime = new FlexCarryforwardTime();
		this.flexTimeOfExcessOutsideTime = new FlexTimeOfExcessOutsideTime();
		this.flexShortDeductTime = new FlexShortDeductTime();
		this.flexSettleTime = new AttendanceTimeMonthWithMinus(0);
		
		this.flexAggrSet = null;
		this.holidayAdditionMap = new HashMap<>();
		this.addMonthlyWithinStatutory = false;
		this.monthlyAggrSetOfFlexOpt = Optional.empty();
		this.addedVacationUseTime = new AddedVacationUseTime();
		this.deductDaysAndTime = new DeductDaysAndTime(
				new AttendanceDaysMonth(0.0), new AttendanceTimeMonth(0));
		this.annualLeaveTimeBeforeDeduct = new AttendanceTimeMonth(0);
		this.errorInfos = new ArrayList<>();
		this.perErrors = new ArrayList<>();
	}

	/**
	 * ファクトリー
	 * @param flexTime フレックス時間
	 * @param flexExcessTime フレックス超過時間
	 * @param flexShortageTime フレックス不足時間
	 * @param flexCarryforwardTime フレックス繰越時間
	 * @param flexTimeOfExcessOutsideWork 時間外超過のフレックス時間
	 * @param flexShortDeductTime フレックス不足控除時間
	 * @param flexSettleTime 当月精算フレックス時間
	 * @return 月別実績のフレックス時間
	 */
	public static FlexTimeOfMonthly of(
			FlexTime flexTime,
			AttendanceTimeMonth flexExcessTime,
			AttendanceTimeMonth flexShortageTime,
			FlexCarryforwardTime flexCarryforwardTime,
			FlexTimeOfExcessOutsideTime flexTimeOfExcessOutsideWork,
			FlexShortDeductTime flexShortDeductTime,
			AttendanceTimeMonthWithMinus flexSettleTime){
		
		val domain = new FlexTimeOfMonthly();
		domain.flexTime = flexTime;
		domain.flexExcessTime = flexExcessTime;
		domain.flexShortageTime = flexShortageTime;
		domain.flexCarryforwardTime = flexCarryforwardTime;
		domain.flexTimeOfExcessOutsideTime = flexTimeOfExcessOutsideWork;
		domain.flexShortDeductTime = flexShortDeductTime;
		domain.flexSettleTime = flexSettleTime;
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
	 * @param aggregateAtr 集計区分
	 * @param closureOpt 締め
	 * @param flexAggregateMethod フレックス集計方法
	 * @param settingsByFlex フレックス勤務が必要とする設定
	 * @param aggregateTotalWorkingTime 集計総労働時間
	 * @param excessOutsideWorkMng 時間外超過管理
	 * @param startWeekNo 開始週NO
	 * @param companySets 月別集計で必要な会社別設定
	 * @param employeeSets 月別集計で必要な社員別設定
	 * @param monthlyCalcDailys 月の計算中の日別実績データ
	 * @return 戻り値：月別実績を集計する
	 */
	public AggregateMonthlyValue aggregateMonthly(RequireM6 require, CacheCarrier cacheCarrier, String companyId, 
			String employeeId, YearMonth yearMonth, ClosureId closureId, ClosureDate closureDate, 
			DatePeriod datePeriod, WorkingSystem workingSystem,
			MonthlyAggregateAtr aggregateAtr, Optional<Closure> closureOpt, FlexAggregateMethod flexAggregateMethod,
			SettingRequiredByFlex settingsByFlex, AggregateTotalWorkingTime aggregateTotalWorkingTime,
			ExcessOutsideWorkMng excessOutsideWorkMng, int startWeekNo, MonAggrCompanySettings companySets,
			MonAggrEmployeeSettings employeeSets, MonthlyCalculatingDailys monthlyCalcDailys){
		
		this.flexAggrSet = settingsByFlex.getFlexAggrSet();
		this.monthlyAggrSetOfFlexOpt = settingsByFlex.getMonthlyAggrSetOfFlexOpt();
		this.holidayAdditionMap = settingsByFlex.getHolidayAdditionMap();
		
		List<AttendanceTimeOfWeekly> resultWeeks = new ArrayList<>();
		
		// 「月次法定内のみ加算」を確認する
		this.addMonthlyWithinStatutory = false;
		if (this.holidayAdditionMap.containsKey("flexWork")){
			val setOfFlex = (WorkFlexAdditionSet) this.holidayAdditionMap.get("flexWork");
			this.addMonthlyWithinStatutory = setOfFlex.getAddSetOfWorkingTime().getAddSetOfWorkTime().isAdditionWithinMonthlyStatutory();
		}
		else {
			this.errorInfos.add(new MonthlyAggregationErrorInfo(
					"012", new ErrMessageContent(TextResource.localize("Msg_1233"))));
		}
		
		// 「月次法定内のみ加算」を確認する
		AddSet addSet = new AddSet();
		if (!this.addMonthlyWithinStatutory) { // 加算しない
			
			// 加算設定　取得　（割増用）
			addSet = GetAddSet.get(WorkingSystem.FLEX_TIME_WORK, PremiumAtr.PREMIUM, this.holidayAdditionMap);
		} else { // 加算する
			
			// 加算設定　取得　（法定内のみ用）
			addSet = GetAddSet.get(WorkingSystem.FLEX_TIME_WORK, PremiumAtr.ONLY_LEGAL, this.holidayAdditionMap);
		}
		addSet.getErrorInfo().ifPresent(er -> this.errorInfos.add(er));
		
		// 「集計区分」を確認する
		StandardFlexTime standFlexTime = new StandardFlexTime();
		if (aggregateAtr == MonthlyAggregateAtr.EXCESS_OUTSIDE_WORK && excessOutsideWorkMng != null){
			
			// 清算期間内の基準時間を集計する
			standFlexTime = excessOutsideWorkMng.aggrStandardTime(require, cacheCarrier, yearMonth, 
					datePeriod, this.flexAggrSet, Optional.of(aggregateTotalWorkingTime), 
					companySets.getVerticalTotalMethod().getFlexAggregateMethod());
		}
		
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
				String workplaceId = "empty";
				val affWorkplaceOpt = employeeSets.getWorkplace(procDate);
				if (affWorkplaceOpt.isPresent()){
					workplaceId = affWorkplaceOpt.get().getWorkplaceId();
				}
				
				// 処理日の雇用コードを取得する
				String employmentCd = "empty";
				val employmentOpt = employeeSets.getEmployment(procDate);
				if (employmentOpt.isPresent()){
					employmentCd = employmentOpt.get().getEmploymentCode();
				}
			
				ConcurrentStopwatches.start("12222.3:日別実績の集計：");
				
				// 処理日の勤務情報を取得する
				if (workInformationOfDailyMap.containsKey(procDate)) {
					val workInfo = workInformationOfDailyMap.get(procDate).getRecordInfo();
					
					// 日別実績を集計する　（フレックス時間勤務用）
					val flexTimeDaily = aggregateTotalWorkingTime.aggregateDailyForFlex(require, procDate, attendanceTimeOfDaily,
							companyId, workplaceId, employmentCd, workingSystem, aggregateAtr, workInfo,
							settingsByFlex, companySets);
					
					// フレックス時間への集計結果を取得する
					for (val timeSeriesWork : flexTimeDaily.getTimeSeriesWorks().values()){
						this.flexTime.getTimeSeriesWorks().put(timeSeriesWork.getYmd(), timeSeriesWork);
					}
				}

				ConcurrentStopwatches.stop("12222.3:日別実績の集計：");
				
				// フレックス時間を集計する
				this.flexTime.aggregate(procDate, attendanceTimeOfDaily);
			}

			// 週の集計をする日か確認する
			if (MonthlyCalculation.isAggregateWeek(procDate, WeekStart.TighteningStartDate, datePeriod, closureOpt)){
			
				// 週の期間を計算
				DatePeriod weekAggrPeriod = new DatePeriod(procWeekStartDate, procDate);
				
				// 翌週の開始日を設定しておく
				procWeekStartDate = procDate.addDays(1);
				
				// 対象の「週別実績の勤怠時間」を作成する
				val newWeek = new AttendanceTimeOfWeekly(employeeId, yearMonth, closureId, closureDate,
						procWeekNo, weekAggrPeriod);
				procWeekNo += 1;
				
				// 週別実績を集計する
				{
					ConcurrentStopwatches.start("12222.4:週別実績の集計：");
					
					// 週の計算
					val weekCalc = newWeek.getWeeklyCalculation();
					weekCalc.aggregate(require, companyId, employeeId, yearMonth, weekAggrPeriod,
							datePeriod, workingSystem, aggregateAtr, null, null, aggregateTotalWorkingTime,
							WeekStart.TighteningStartDate, new AttendanceTimeMonth(0), attendanceTimeOfDailyMap, 
							companySets, monthlyCalcDailys);
					resultWeeks.add(newWeek);
					if (weekCalc.getErrorInfos().size() > 0) this.errorInfos.addAll(weekCalc.getErrorInfos());

					ConcurrentStopwatches.stop("12222.4:週別実績の集計：");
					
					// 集計区分を確認する
					if (aggregateAtr == MonthlyAggregateAtr.EXCESS_OUTSIDE_WORK && excessOutsideWorkMng != null){
					
						ConcurrentStopwatches.start("12222.5:時間外超過の集計：");
						
						// 時間外超過の集計
						newWeek.getExcessOutside().aggregate(weekCalc, companySets);
						
						ConcurrentStopwatches.stop("12222.5:時間外超過の集計：");
					}
				}
			}
			
			/** ○「集計区分」を確認する */
			if (aggregateAtr == MonthlyAggregateAtr.EXCESS_OUTSIDE_WORK && excessOutsideWorkMng != null){

				ConcurrentStopwatches.start("12222.6:超過時間割り当て：");
				
				// 時間外超過の時、フレックス超過時間を割り当てる
				excessOutsideWorkMng.assignFlexExcessTime(require, cacheCarrier, companyId, datePeriod, flexAggregateMethod,
						procDate, this.flexAggrSet, aggregateTotalWorkingTime, this.flexTime,
						settingsByFlex, addSet, standFlexTime, monthlyCalcDailys);
				
				ConcurrentStopwatches.stop("12222.6:超過時間割り当て：");
			}
			
			procDate = procDate.addDays(1);
		}
		
		return AggregateMonthlyValue.of(aggregateTotalWorkingTime, excessOutsideWorkMng, resultWeeks);
	}
	
	/**
	 * 月単位の時間を集計する
	 * @param require Require
	 * @param cacheCarrier Cache
	 * @param companyId 会社ID
	 * @param employeeId 社員ID
	 * @param yearMonth 年月（度）
	 * @param closureId 締めID
	 * @param closureDate 締め日
	 * @param datePeriod 期間
	 * @param aggregateAtr 集計区分
	 * @param flexAggregateMethod フレックス集計方法
	 * @param workingConditionItem 労働条件項目
	 * @param employmentCd 雇用コード
	 * @param companySets 月別集計で必要な会社別設定
	 * @param employeeSets 月別集計で必要な社員別設定
	 * @param settingsByFlex フレックス勤務が必要とする設定
	 * @param aggregateTotalWorkingTime 集計総労働時間
	 * @param monthlyCalculatingDailys 月の計算中の日別実績データ
	 * @param repositories 月次集計が必要とするリポジトリ
	 */
	public void aggregateMonthlyHours(RequireM5 require, CacheCarrier cacheCarrier, String companyId, String employeeId,
			YearMonth yearMonth, ClosureId closureId, ClosureDate closureDate, DatePeriod datePeriod, MonthlyAggregateAtr aggregateAtr,
			FlexAggregateMethod flexAggregateMethod, WorkingConditionItem workingConditionItem, String employmentCd,
			MonAggrCompanySettings companySets, MonAggrEmployeeSettings employeeSets, SettingRequiredByFlex settingsByFlex, 
			AggregateTotalWorkingTime aggregateTotalWorkingTime, MonthlyCalculatingDailys monthlyCalculatingDailys){

		this.flexAggrSet = settingsByFlex.getFlexAggrSet();
		this.holidayAdditionMap = settingsByFlex.getHolidayAdditionMap();
		this.monthlyAggrSetOfFlexOpt = settingsByFlex.getMonthlyAggrSetOfFlexOpt();
		
		// 「月次法定内のみ加算」を確認する
		this.addMonthlyWithinStatutory = false;
		if (this.holidayAdditionMap.containsKey("flexWork")){
			val setOfFlex = (WorkFlexAdditionSet)this.holidayAdditionMap.get("flexWork");
			this.addMonthlyWithinStatutory = setOfFlex.getAddSetOfWorkingTime().getAddSetOfWorkTime().isAdditionWithinMonthlyStatutory();
		} else {
			this.errorInfos.add(new MonthlyAggregationErrorInfo("012", new ErrMessageContent(TextResource.localize("Msg_1233"))));
			return;
		}

		// 「不足設定．清算期間」を確認する
		if (this.flexAggrSet.getInsufficSet().getSettlePeriod() == SettlePeriod.SINGLE_MONTH){
			
			// 前月からのフレ不足を繰越
			this.carryforwardFlex(require, employeeId, yearMonth);
		} else{

			// 前月からのフレを繰越（複数月）
			this.carryforwardFlexForMulti(require, cacheCarrier, employeeId, datePeriod, yearMonth, aggregateAtr);
		}
		
		/** ○「集計方法」を確認する */
		if (flexAggregateMethod == FlexAggregateMethod.FOR_CONVENIENCE){
			// 便宜上集計をする
			this.aggregateForConvenience(require, cacheCarrier,
					companyId, employeeId, yearMonth, closureId, closureDate, datePeriod, aggregateAtr, employmentCd,
					companySets, employeeSets, settingsByFlex, aggregateTotalWorkingTime, monthlyCalculatingDailys);
			
		} else if (flexAggregateMethod == FlexAggregateMethod.PRINCIPLE){
			// 原則集計をする
			this.aggregatePrinciple(require, cacheCarrier, companyId, employeeId, yearMonth, datePeriod,
					closureId, closureDate, employmentCd, aggregateAtr, companySets, employeeSets,
					settingsByFlex, aggregateTotalWorkingTime, monthlyCalculatingDailys);
		}
		
		// 「不足設定．清算期間」を確認する
		if (this.flexAggrSet.getInsufficSet().getSettlePeriod() == SettlePeriod.MULTI_MONTHS) {
			
			// 清算処理をする
			this.settleProc(require, cacheCarrier, companyId, employeeId, yearMonth, datePeriod, closureId,
					closureDate, aggregateAtr, companySets, employeeSets, settingsByFlex,
					aggregateTotalWorkingTime, monthlyCalculatingDailys);
		}
		
		// 大塚モードの判断
		if (!AppContexts.optionLicense().customize().ootsuka()) 
			return;
		
		// 大塚カスタマイズ（フレ永続繰越）
		{
			// フレックス繰越不足時間が計算されているか確認
			int carryforwardShortageMinutes = this.flexCarryforwardTime.getFlexCarryforwardShortageTime().v();
			if (carryforwardShortageMinutes > 0){
				
				// フレ不足時間←フレ繰越不足時間を加算
				this.flexShortageTime = this.flexShortageTime.addMinutes(carryforwardShortageMinutes);
				
				// フレックス時間←フレ繰越不足時間を減算
				this.flexTime.getFlexTime().addFlexTime(new AttendanceTimeMonthWithMinus(-carryforwardShortageMinutes));
				
				// フレ繰越不足時間←0:00
				this.flexCarryforwardTime.setFlexCarryforwardShortageTime(new AttendanceTimeMonth(0));
			}
		}
		
		// 年休控除と欠勤控除のパラメータを確認する　（実際は、計算の早い段階（前月データ確認のタイミング）で確認している）
		this.deductDaysAndTime = new DeductDaysAndTime(
				this.flexShortDeductTime.getAnnualLeaveDeductDays(),
				this.flexShortDeductTime.getAbsenceDeductTime());
		
		// 年休控除する
		this.deductAnnualLeave(require, companyId, employeeId, datePeriod, flexAggregateMethod, workingConditionItem);
		
		// 欠勤控除する
		this.deductAbsence();
		
		// フレックス補填のエラーチェック
		this.checkErrorForInsufficientFlex(settingsByFlex);
	}
	
	/**
	 * 前月からのフレ不足を繰越
	 */
	private void carryforwardFlex(RequireM3 require, String employeeId, YearMonth yearMonth){
		
		// フレックス不足時の繰越設定を確認する　→　翌月繰越の時
		if (this.flexAggrSet.getInsufficSet().getCarryforwardSet() == CarryforwardSetInShortageFlex.NEXT_MONTH_CARRYFORWARD){
	
			// 前月の「月別実績の勤怠時間」を取得する
			val prevYearMonth = yearMonth.previousMonth();
			val prevAttendanceTimeList = require.attendanceTimeOfMonthly(employeeId, prevYearMonth);
			
			// 前月のフレックス不足時間を取得する　（開始日が最も大きい日のデータ）
			// 前月のフレックス繰越不可時間を取得する
			AttendanceTimeMonth prevFlexShortageTime = new AttendanceTimeMonth(0);
			AttendanceTimeMonth prevFlexNotCarryforwardTime = new AttendanceTimeMonth(0);
			if (!prevAttendanceTimeList.isEmpty()){
				val prevAttendanceTime = prevAttendanceTimeList.get(prevAttendanceTimeList.size() - 1);
				val prevFlexTime = prevAttendanceTime.getMonthlyCalculation().getFlexTime();
				prevFlexShortageTime = new AttendanceTimeMonth(prevFlexTime.getFlexShortageTime().v());
				prevFlexNotCarryforwardTime = new AttendanceTimeMonth(
						prevFlexTime.getFlexCarryforwardTime().getFlexNotCarryforwardTime().v());
			}
			
			// 当月のフレックス繰越時間を計算する　（前月のフレックス不足時間－前月のフレックス繰越不可時間）
			int prevShortageMinutes = prevFlexShortageTime.v() - prevFlexNotCarryforwardTime.v();
			if (prevShortageMinutes < 0) prevShortageMinutes = 0;
			// ※　マイナス値に変えて入れる
			this.flexCarryforwardTime.setFlexCarryforwardTime(
					new AttendanceTimeMonthWithMinus(-prevShortageMinutes));
		}
	}
	
	/**
	 * 前月からのフレを繰越（複数月）
	 */
	private void carryforwardFlexForMulti(RequireM3 require, CacheCarrier cacheCarrier, String employeeId,
			DatePeriod period, YearMonth yearMonth, MonthlyAggregateAtr aggregateAtr){
		
		// フレックス清算期間の取得
		SettlePeriodOfFlex settlePeriod = this.flexAggrSet.getInsufficSet().getSettlePeriod(require, cacheCarrier, employeeId, period, yearMonth);
		
		// 「開始月かどうか」を確認する
		if (settlePeriod.getIsStartYm()){

			// 当月のフレックス繰越時間　←　０
			this.flexCarryforwardTime.setFlexCarryforwardTime(new AttendanceTimeMonthWithMinus(0));
		} else{
			
			// 前月の「月別実績の勤怠時間」を取得する
			val prevYearMonth = yearMonth.previousMonth();

			val prevAttendanceTimeList = require.attendanceTimeOfMonthly(employeeId, prevYearMonth);

			// 計算繰越時間を計算する
			int calcCarryforwardMinutes = 0;		// 計算繰越時間
			if (!prevAttendanceTimeList.isEmpty()){
				val prevAttendanceTime = prevAttendanceTimeList.get(prevAttendanceTimeList.size() - 1);
				val prevFlexTime = prevAttendanceTime.getMonthlyCalculation().getFlexTime();
				FlexTimeCurrentMonth prevCurrentMonth = prevFlexTime.getFlexTime().getFlexTimeCurrentMonth();
				if (aggregateAtr == MonthlyAggregateAtr.EXCESS_OUTSIDE_WORK){
					// 時間外超過の時、時間外超過のフレックス時間．当月フレックス時間を利用する
					prevCurrentMonth = prevFlexTime.getFlexTimeOfExcessOutsideTime().getFlexTimeCurrentMonth();
				}
				// 前月のフレックス繰越時間＋前月の当月フレックス時間．フレックス時間－前月の当月清算フレックス時間
				calcCarryforwardMinutes += prevFlexTime.getFlexCarryforwardTime().getFlexCarryforwardTime().v();
				calcCarryforwardMinutes += prevCurrentMonth.getFlexTime().getFlexTime().getTime().v();
				calcCarryforwardMinutes -= prevFlexTime.getFlexSettleTime().v();
			}
			
			// 当月のフレックス繰越時間に、前月のフレックス時間を反映する
			this.flexCarryforwardTime.setFlexCarryforwardTime(new AttendanceTimeMonthWithMinus(calcCarryforwardMinutes));
		}
	}
	
	/**
	 * 便宜上集計をする
	 * @param require Require
	 * @param cacheCarrier Cache
	 * @param companyId 会社ID
	 * @param employeeId 社員ID
	 * @param yearMonth 年月(度)
	 * @param closureId 締めID
	 * @param closureDate 締め日
	 * @param datePeriod 期間
	 * @param aggregateAtr 集計区分
	 * @param employmentCode 雇用コード
	 * @param companySets 月別集計で必要な会社別設定
	 * @param employeeSets 月別集計で必要な社員別設定
	 * @param settingsByFlex フレックス勤務が必要とする設定
	 * @param aggregateTotalWorkingTime 集計総労働時間
	 * @param monthlyCalculatingDailys 月の計算中の日別実績データ
	 */
	private void aggregateForConvenience(RequireM4 require, CacheCarrier cacheCarrier, String companyId,
			String employeeId, YearMonth yearMonth, ClosureId closureId, ClosureDate closureDate,
			DatePeriod datePeriod, MonthlyAggregateAtr aggregateAtr, String employmentCode,
			MonAggrCompanySettings companySets, MonAggrEmployeeSettings employeeSets,
			SettingRequiredByFlex settingsByFlex, AggregateTotalWorkingTime aggregateTotalWorkingTime,
			MonthlyCalculatingDailys monthlyCalculatingDailys) {
		
		// 「不足設定．清算期間」を確認する
		if (this.flexAggrSet.getInsufficSet().getSettlePeriod() == SettlePeriod.SINGLE_MONTH){
			// フレックス時間の計算（便宜上集計）
			
			// フレックス時間を取得する　→　繰越時間相殺前に入れる
			val carryforwardTimeBeforeOffset = this.flexTime.getTimeSeriesTotalFlexTime(datePeriod, false);
			
			/** ○所定労働時間を基準時間に入れる */
			val standardTime = aggregateTotalWorkingTime.getPrescribedWorkingTime().getTotalRecordPrescribedWorkingTime(datePeriod);
			this.flexTime.getFlexTimeCurrentMonth().setStandardTime(standardTime);
			if (carryforwardTimeBeforeOffset.greaterThan(0)){
				
				// フレックス超過の処理をする　（便宜上）
				this.flexExcessForConvenience(require, cacheCarrier, carryforwardTimeBeforeOffset, 
						companyId, employeeId, yearMonth, datePeriod, aggregateAtr, companySets, employeeSets, settingsByFlex,
						aggregateTotalWorkingTime, closureId, closureDate, monthlyCalculatingDailys, this.flexTime.getFlexTime(),
						this.flexTime.getFlexTimeCurrentMonth());
			} else {
				
				// フレックス不足の処理をする　（便宜上）
				this.flexShortageForConvenience(carryforwardTimeBeforeOffset);
				
				// フレックス繰越不可時間を求める
				this.askNotCarryforwardTime(require, cacheCarrier, companyId, employeeId, yearMonth, datePeriod, closureId,
						companySets, employeeSets, settingsByFlex);
			}
		}
		else{
			// 複数月フレックス時間の計算（便宜上集計）
			this.calcFlexMultiForConvenience(require, cacheCarrier,
					companyId, employeeId, yearMonth, closureId, closureDate, datePeriod, aggregateAtr, employmentCode,
					companySets, employeeSets, settingsByFlex, aggregateTotalWorkingTime, monthlyCalculatingDailys);
		}
		
		// 計算フレックス時間の計算（便宜上集計）
		{
			// 計算フレックス時間を取得する　→　繰越時間相殺前
			val totalCalcFlexTime = this.flexTime.getTimeSeriesTotalCalcFlexTime(datePeriod, false);
			if (totalCalcFlexTime.greaterThan(0)){
				
				// 計算フレックス超過の処理をする　（便宜上）
				this.calcFlexExcessForConvenience(totalCalcFlexTime);
			}
			else {
				
				// 計算フレックス不足の処理をする　（便宜上）
				this.calcFlexShortageForConvenience(totalCalcFlexTime);
			}
		}
	}

	/**
	 * フレックス超過の処理をする　（便宜上）
	 * @param carryforwardTimeBeforeOffset 繰越時間相殺前
	 */
	private void flexExcessForConvenience(RequireM4 require, CacheCarrier cacheCarrier, 
			AttendanceTimeMonthWithMinus carryforwardTimeBeforeOffset,
			String companyId, String employeeId, YearMonth yearMonth, DatePeriod datePeriod, MonthlyAggregateAtr aggregateAtr,
			MonAggrCompanySettings companySets, MonAggrEmployeeSettings employeeSets, SettingRequiredByFlex settingsByFlex,
			AggregateTotalWorkingTime aggregateTotalWorkingTime, ClosureId closureId, ClosureDate closureDate, 
			MonthlyCalculatingDailys monthlyCalculatingDailys, FlexTimeTotalTimeMonth flexTime,
			FlexTimeCurrentMonth flexTimeCurrentMonth) {

		// フレックス繰越時間を取得する
		AttendanceTimeMonthWithMinus carryforwardTime = this.flexCarryforwardTime.getFlexCarryforwardTime();
		// （不足分をプラスにして取り出す）
		if (carryforwardTime.lessThan(0)){
			carryforwardTime = new AttendanceTimeMonthWithMinus(-carryforwardTime.v());
		}
		else{
			carryforwardTime = new AttendanceTimeMonthWithMinus(0);
		}
		val carryforwardWorkTime = this.flexCarryforwardTime.getFlexCarryforwardWorkTime();
		val carryforwardShortageTime = this.flexCarryforwardTime.getFlexCarryforwardShortageTime();
		
		if (carryforwardTime.lessThan(carryforwardTimeBeforeOffset.v())){
			
			// フレックス繰越時間をフレックス繰越勤務時間に加算する
			this.flexCarryforwardTime.setFlexCarryforwardWorkTime(
					carryforwardWorkTime.addMinutes(carryforwardTime.v()));
			
			// 繰越時間相殺前とフレックス繰越時間の差分をフレックス超過時間・フレックス時間に加算する
			val difference = carryforwardTimeBeforeOffset.minusMinutes(carryforwardTime.v());
			this.flexExcessTime = this.flexExcessTime.addMinutes(difference.v());
			this.flexTime.getFlexTime().addFlexTime(difference);

			// 法定内・法定外フレックス時間を求める
			this.calcLegalFlexTime(require, cacheCarrier, companyId, employeeId, yearMonth, datePeriod,
					aggregateAtr, flexTime.getFlexTime().getTime(),companySets, employeeSets, settingsByFlex, 
					aggregateTotalWorkingTime, closureId, closureDate, monthlyCalculatingDailys, flexTime,
					flexTimeCurrentMonth);
			
			// 法定外フレックス時間にフレックス時間をセットする
//			this.flexTime.getFlexTime().allFlexAsIllegal(new AttendanceTimeMonth(
//					this.flexTime.getFlexTime().getFlexTime().getTime().valueAsMinutes()));
//			this.flexTime.setLegalFlexTime(new AttendanceTimeMonthWithMinus(0));
		} else {
			
			// 繰越時間相殺前をフレックス繰越勤務時間に加算する
			this.flexCarryforwardTime.setFlexCarryforwardWorkTime(
					carryforwardWorkTime.addMinutes(carryforwardTimeBeforeOffset.v()));
			
			// フレックス繰越時間と繰越時間相殺前の差分をフレックス繰越不足時間に加算する
			val difference = carryforwardTime.minusMinutes(carryforwardTimeBeforeOffset.v());
			this.flexCarryforwardTime.setFlexCarryforwardShortageTime(
					carryforwardShortageTime.addMinutes(difference.v()));
			
			// フレックス時間を 0 にする
			this.flexTime.getFlexTime().clearFlexTime();
		}
	}
	
	/**
	 * フレックス不足の処理をする　（便宜上）
	 * @param carryforwardTimeBeforeOffset 繰越時間相殺前
	 */
	private void flexShortageForConvenience(AttendanceTimeMonthWithMinus carryforwardTimeBeforeOffset){
		
		// フレックス繰越時間を取得する
		AttendanceTimeMonthWithMinus carryforwardTime = this.flexCarryforwardTime.getFlexCarryforwardTime();
		// （不足分をプラスにして取り出す）
		if (carryforwardTime.lessThan(0)){
			carryforwardTime = new AttendanceTimeMonthWithMinus(-carryforwardTime.v());
		}
		else{
			carryforwardTime = new AttendanceTimeMonthWithMinus(0);
		}
		val carryforwardShortageTime = this.flexCarryforwardTime.getFlexCarryforwardShortageTime();
		
		// フレックス繰越時間をフレックス繰越不足時間に加算する
		this.flexCarryforwardTime.setFlexCarryforwardShortageTime(
				carryforwardShortageTime.addMinutes(carryforwardTime.v()));
		
		// 繰越時間相殺前をフレックス不足時間に加算する
		// ※　繰越時間相殺前はマイナス値なので、減算により、加算扱いにする。
		this.flexShortageTime = this.flexShortageTime.minusMinutes(carryforwardTimeBeforeOffset.v());
		
		// 繰越時間相殺前をフレックス時間に加算する
		this.flexTime.getFlexTime().addFlexTime(carryforwardTimeBeforeOffset);
	}

	/**
	 * 複数月フレックス時間の計算（便宜上集計）
	 * @param require Require
	 * @param cacheCarrier Cache
	 * @param companyId 会社ID
	 * @param employeeId 社員ID
	 * @param yearMonth 年月(度)
	 * @param closureId 締めID
	 * @param closureDate 締め日
	 * @param period 期間
	 * @param aggregateAtr 集計区分
	 * @param employmentCode 雇用コード
	 * @param companySets 月別集計で必要な会社別設定
	 * @param employeeSets 月別集計で必要な社員別設定
	 * @param settingsByFlex フレックス勤務が必要とする設定
	 * @param aggregateTotalWorkingTime 集計総労働時間
	 * @param monthlyCalculatingDailys 月の計算中の日別実績データ
	 */
	private void calcFlexMultiForConvenience(RequireM4 require, CacheCarrier cacheCarrier, String companyId,
			String employeeId, YearMonth yearMonth, ClosureId closureId, ClosureDate closureDate,
			DatePeriod period, MonthlyAggregateAtr aggregateAtr, String employmentCode, MonAggrCompanySettings companySets,
			MonAggrEmployeeSettings employeeSets, SettingRequiredByFlex settingsByFlex, 
			AggregateTotalWorkingTime aggregateTotalWorkingTime, MonthlyCalculatingDailys monthlyCalculatingDailys) {
		
		// フレックス時間合計を取得する
		AttendanceTimeMonthWithMinus totalFlexTime = this.flexTime.getTimeSeriesTotalFlexTime(period, false);
		
		// フレックス対象時間を集計する
		AttendanceTimeMonthWithMinus flexTargetTime = this.aggregateFlexTargetTime(require, companyId, employeeId, aggregateAtr,
				monthlyCalculatingDailys, period, aggregateTotalWorkingTime, PremiumAtr.PREMIUM);

		// 当月フレックス時間を計算する　（フレックス時間合計－週平均超過時間）
		FlexTimeCurrentMonth flexTimeCurrentMonth = this.flexTime.getFlexTimeCurrentMonth();
		if (aggregateAtr == MonthlyAggregateAtr.EXCESS_OUTSIDE_WORK){
			// 時間外超過の時、時間外超過のフレックス時間を利用する
			flexTimeCurrentMonth = this.flexTimeOfExcessOutsideTime.getFlexTimeCurrentMonth();
		}
		
		// 週平均超過時間を計算する
		this.calcExcessWeekAveTime(require, yearMonth, period, closureId, aggregateAtr, flexTargetTime, settingsByFlex, flexTimeCurrentMonth);
		
		flexTimeCurrentMonth.getFlexTime().addFlexTime(totalFlexTime.minusMinutes(flexTimeCurrentMonth.getExcessWeekAveTime().valueAsMinutes()));
		
		// 法定労働時間を取得する（フレックス用）
		MonthlyFlexStatutoryLaborTime statLaborTime = FlexLegalTimeGetter.getFlexStatutoryLaborTime(
				require, cacheCarrier, companySets, employeeSets, settingsByFlex,
				true, yearMonth, companyId, employmentCode, employeeId, period.end(), Optional.of(period),
				closureId, closureDate, Optional.of(aggregateTotalWorkingTime), monthlyCalculatingDailys);
		// 基準時間　←　取得結果.所定労働時間
		flexTimeCurrentMonth.setStandardTime(new AttendanceTimeMonth(statLaborTime.getSpecifiedSetting().valueAsMinutes()));
	}
	
	/**
	 * 計算フレックス超過の処理をする　（便宜上）
	 * @param carryforwardTimeBeforeOffset 繰越時間相殺前
	 */
	private void calcFlexExcessForConvenience(AttendanceTimeMonthWithMinus carryforwardTimeBeforeOffset){

		// フレックス繰越時間を取得する
		AttendanceTimeMonthWithMinus carryforwardTime = this.flexCarryforwardTime.getFlexCarryforwardTime();
		// （不足分をプラスにして取り出す）
		if (carryforwardTime.lessThan(0)){
			carryforwardTime = new AttendanceTimeMonthWithMinus(-carryforwardTime.v());
		}
		else{
			carryforwardTime = new AttendanceTimeMonthWithMinus(0);
		}
		
		if (carryforwardTime.lessThan(carryforwardTimeBeforeOffset.v())){
			
			// 繰越時間相殺前とフレックス繰越時間の差分を計算フレックス時間に加算する
			this.flexTime.getFlexTime().addFlexCalcTime(carryforwardTimeBeforeOffset.minusMinutes(carryforwardTime.v()));
		}
		else {
			
			// 計算フレックス時間を 0 にする
			this.flexTime.getFlexTime().clearFlexCalcTime();
		}
	}
	
	/**
	 * 計算フレックス不足の処理をする　（便宜上）
	 * @param carryforwardTimeBeforeOffset 繰越時間相殺前
	 */
	private void calcFlexShortageForConvenience(AttendanceTimeMonthWithMinus carryforwardTimeBeforeOffset){
		
		// 繰越時間相殺前を計算フレックス時間に加算する
		this.flexTime.getFlexTime().addFlexCalcTime(carryforwardTimeBeforeOffset);
	}
	
	/**
	 * 原則集計をする
	 * @param require Require
	 * @param cacheCarrier Cache
	 * @param companyId 会社ID
	 * @param employeeId 社員ID
	 * @param yearMonth 年月（度）
	 * @param datePeriod 期間
	 * @param closureId 締めID
	 * @param closureDate 締め日
	 * @param employmentCode 雇用コード
	 * @param aggregateAtr 集計区分
	 * @param companySets 月別集計で必要な会社別設定
	 * @param employeeSets 月別集計で必要な社員別設定
	 * @param settingsByFlex フレックス勤務が必要とする設定
	 * @param aggregateTotalWorkingTime 総労働時間
	 * @param monthlyCalculatingDailys 月の計算中の日別実績データ
	 */
	private void aggregatePrinciple(RequireM4 require, CacheCarrier cacheCarrier, String companyId, String employeeId,
			YearMonth yearMonth, DatePeriod datePeriod, ClosureId closureId, ClosureDate closureDate, String employmentCode,
			MonthlyAggregateAtr aggregateAtr, MonAggrCompanySettings companySets, MonAggrEmployeeSettings employeeSets,
			SettingRequiredByFlex settingsByFlex, AggregateTotalWorkingTime aggregateTotalWorkingTime,
			MonthlyCalculatingDailys monthlyCalculatingDailys) {

		// 「不足設定．清算期間」を確認する
		if (this.flexAggrSet.getInsufficSet().getSettlePeriod() == SettlePeriod.SINGLE_MONTH)
		{
			// フレックス時間の計算（原則集計）
			this.calcFlexTimeForPrinciple(require, cacheCarrier, companyId, employeeId, yearMonth, datePeriod,
					closureId, closureDate, employmentCode, aggregateAtr, companySets, employeeSets,
					settingsByFlex, aggregateTotalWorkingTime, monthlyCalculatingDailys);
		} else {
			// 複数月フレックス時間の計算（原則集計）
			this.calcFlexMultiForPrinciple(require, cacheCarrier, companyId, employeeId, yearMonth, datePeriod,
					closureId, closureDate, employmentCode, aggregateAtr, companySets, employeeSets,
					settingsByFlex, aggregateTotalWorkingTime, monthlyCalculatingDailys);
		}
		// 計算フレックス時間の計算（原則集計）
		this.calcCalcFlexTime(require, cacheCarrier, companyId, employeeId, yearMonth, datePeriod,
				closureId, closureDate, employmentCode, aggregateAtr, companySets, employeeSets, 
				settingsByFlex, aggregateTotalWorkingTime, monthlyCalculatingDailys);
	}

	/**
	 * フレックス時間の計算（原則集計）
	 */
	private void calcFlexTimeForPrinciple(RequireM4 require, CacheCarrier cacheCarrier, String companyId, String employeeId,
			YearMonth yearMonth, DatePeriod datePeriod, ClosureId closureId, ClosureDate closureDate, String employmentCode,
			MonthlyAggregateAtr aggregateAtr, MonAggrCompanySettings companySets, MonAggrEmployeeSettings employeeSets,
			SettingRequiredByFlex settingsByFlex, AggregateTotalWorkingTime aggregateTotalWorkingTime, 
			MonthlyCalculatingDailys monthlyCalculatingDailys) {
		
		// フレックス対象時間を集計する
		val flexTargetTime = this.aggregateFlexTargetTime(require, companyId, employeeId, aggregateAtr,
				monthlyCalculatingDailys, datePeriod, aggregateTotalWorkingTime, PremiumAtr.PREMIUM);
		
		// 所定労働時間（代休控除後）を求める
		AttendanceTimeMonth compLeaveAfterDeduct = this.getPrescribedTime(
				require, cacheCarrier, companyId, employeeId, yearMonth, datePeriod, closureId, closureDate,
				employmentCode, companySets, employeeSets, settingsByFlex,
				aggregateTotalWorkingTime, monthlyCalculatingDailys);
		// 繰越時間相殺前を求める
		val carryforwardTimeBeforeOffset = flexTargetTime.minusMinutes(compLeaveAfterDeduct.valueAsMinutes());
		// 所定労働時間（代休控除後）を基準時間に入れる
		int standMinutes = compLeaveAfterDeduct.valueAsMinutes();
		if (standMinutes < 0) standMinutes = 0;
		if (aggregateAtr == MonthlyAggregateAtr.MONTHLY){
			this.flexTime.getFlexTimeCurrentMonth().setStandardTime(
					new AttendanceTimeMonth(standMinutes));
		} else{
			// 時間外超過の時、時間外超過のフレックス時間側に入れる
			this.flexTimeOfExcessOutsideTime.getFlexTimeCurrentMonth().setStandardTime(
					new AttendanceTimeMonth(standMinutes));
		}
		
		if (carryforwardTimeBeforeOffset.greaterThan(0)) {
			FlexTimeCurrentMonth flexTimeCurrentMonth = this.flexTime.getFlexTimeCurrentMonth();
			if (aggregateAtr == MonthlyAggregateAtr.EXCESS_OUTSIDE_WORK){
				// 時間外超過の時、時間外超過のフレックス時間を利用する
				flexTimeCurrentMonth = this.flexTimeOfExcessOutsideTime.getFlexTimeCurrentMonth();
			}
			// フレックス超過の処理をする
			this.flexExcessTime = this.flexExcessPrinciple(require, cacheCarrier, carryforwardTimeBeforeOffset,
					companyId, employeeId, yearMonth, datePeriod, aggregateAtr, companySets, employeeSets, settingsByFlex,
					aggregateTotalWorkingTime, closureId, closureDate, monthlyCalculatingDailys, this.flexTime.getFlexTime(), 
					this.flexExcessTime, this.flexCarryforwardTime, flexTimeCurrentMonth);
		} else {
			// フレックス不足の処理をする
			val addedTimeForShortage = this.flexShortagePrinciple(require, companyId, employeeId, aggregateAtr,
					monthlyCalculatingDailys, datePeriod, carryforwardTimeBeforeOffset, aggregateTotalWorkingTime);
			this.addedVacationUseTime.addMinutesToAddTimePerMonth(addedTimeForShortage.getAddTimePerMonth().v());
			
			// フレックス繰越不可時間を求める
			this.askNotCarryforwardTime(require, cacheCarrier, companyId, employeeId, yearMonth, datePeriod, closureId,
					companySets, employeeSets, settingsByFlex);
		}
	}

	/**
	 * 所定労働時間を求める
	 */
	private AttendanceTimeMonth getPrescribedTime(RequireM4 require, CacheCarrier cacheCarrier, String companyId,
			String employeeId, YearMonth yearMonth, DatePeriod datePeriod, ClosureId closureId, ClosureDate closureDate,
			String employmentCode, MonAggrCompanySettings companySets, MonAggrEmployeeSettings employeeSets,
			SettingRequiredByFlex settingsByFlex, AggregateTotalWorkingTime aggregateTotalWorkingTime,
			MonthlyCalculatingDailys monthlyCalculatingDailys) {
		
		// 法定労働時間を取得する（フレックス用）
		MonthlyFlexStatutoryLaborTime statLaborTime = FlexLegalTimeGetter.getFlexStatutoryLaborTime(
				require, cacheCarrier, companySets, employeeSets, settingsByFlex,
				true, yearMonth, companyId, employmentCode, employeeId, datePeriod.end(), Optional.of(datePeriod),
				closureId, closureDate, Optional.of(aggregateTotalWorkingTime), monthlyCalculatingDailys);
		// 所定労働時間を返す
		return new AttendanceTimeMonth(statLaborTime.getSpecifiedSetting().valueAsMinutes());
	}
	
	/**
	 * 複数月フレックス時間の計算（原則集計）
	 * @param require Require
	 * @param cacheCarrier Cache
	 * @param companyId 会社ID
	 * @param employeeId 社員ID
	 * @param yearMonth 年月(度)
	 * @param datePeriod 期間
	 * @param closureId 締めID
	 * @param closureDate 締め日
	 * @param employmentCode 雇用コード
	 * @param aggregateAtr 集計区分
	 * @param companySets 月別集計で必要な会社別設定
	 * @param employeeSets 月別集計で必要な社員別設定
	 * @param settingsByFlex フレックス勤務が必要とする設定
	 * @param aggregateTotalWorkingTime 総労働時間
	 * @param monthlyCalculatingDailys 月の計算中の日別実績データ
	 */
	private void calcFlexMultiForPrinciple(RequireM4 require, CacheCarrier cacheCarrier, String companyId, String employeeId,
			YearMonth yearMonth, DatePeriod datePeriod, ClosureId closureId, ClosureDate closureDate, String employmentCode,
			MonthlyAggregateAtr aggregateAtr, MonAggrCompanySettings companySets, MonAggrEmployeeSettings employeeSets,
			SettingRequiredByFlex settingsByFlex, AggregateTotalWorkingTime aggregateTotalWorkingTime, 
			MonthlyCalculatingDailys monthlyCalculatingDailys) {
		
		// フレックス対象時間を集計する
		AttendanceTimeMonthWithMinus flexTargetTime = this.aggregateFlexTargetTime(require, companyId, employeeId, aggregateAtr,
				monthlyCalculatingDailys, datePeriod, aggregateTotalWorkingTime, PremiumAtr.PREMIUM);

		// 当月フレックス時間を計算する　（フレックス時間合計－所定労働時間－週平均超過時間）
		FlexTimeCurrentMonth flexTimeCurrentMonth = this.flexTime.getFlexTimeCurrentMonth();
		if (aggregateAtr == MonthlyAggregateAtr.EXCESS_OUTSIDE_WORK){
			// 時間外超過の時、時間外超過のフレックス時間を利用する
			flexTimeCurrentMonth = this.flexTimeOfExcessOutsideTime.getFlexTimeCurrentMonth();
		}
		
		// 週平均超過時間を計算する
		this.calcExcessWeekAveTime(require, yearMonth, datePeriod, closureId, aggregateAtr, flexTargetTime, settingsByFlex, flexTimeCurrentMonth);
		// 所定労働時間（代休控除後）を求める
		AttendanceTimeMonth compLeaveAfterDeduct = this.getPrescribedTime(
				require, cacheCarrier, companyId, employeeId, yearMonth, datePeriod, closureId, closureDate,
				employmentCode, companySets, employeeSets, settingsByFlex,
				aggregateTotalWorkingTime, monthlyCalculatingDailys);
		
		int predWorkMinutes = compLeaveAfterDeduct.valueAsMinutes();
		int excessWeekAveMinutes = flexTimeCurrentMonth.getExcessWeekAveTime().valueAsMinutes();
		
		/** フレックス時間を計算する */
		val flexTime = flexTargetTime.valueAsMinutes() - predWorkMinutes - excessWeekAveMinutes;

		/** 基準時間をセットする */
		flexTimeCurrentMonth.setStandardTime(new AttendanceTimeMonth(compLeaveAfterDeduct.valueAsMinutes()));

		/** フレックス時間をセットする */
		flexTimeCurrentMonth.getFlexTime().setFlex(new TimeMonthWithCalculationAndMinus(
															new AttendanceTimeMonthWithMinus(flexTime), 
															new AttendanceTimeMonthWithMinus(0)));
		
		if (flexTime > 0) {
			
			// 法定内・法定外フレックス時間を求める
			this.calcLegalFlexTime(require, cacheCarrier, companyId, employeeId, yearMonth, datePeriod,
					aggregateAtr, flexTimeCurrentMonth.getFlexTime().getFlexTime().getTime(),companySets,
					employeeSets, settingsByFlex, aggregateTotalWorkingTime, closureId, closureDate, 
					monthlyCalculatingDailys, flexTimeCurrentMonth.getFlexTime(), flexTimeCurrentMonth);

		}
	}
	
	/**
	 * フレックス対象時間を集計する
	 * @return フレックス対象時間
	 */
	private AttendanceTimeMonthWithMinus aggregateFlexTargetTime(RequireM8 require,
			String cid, String sid, MonthlyAggregateAtr aggregateAtr,
			MonthlyCalculatingDailys monthlyCalcDailys, DatePeriod datePeriod,
			AggregateTotalWorkingTime aggregateTotalWorkingTime, PremiumAtr premiumAtr){
		
		AttendanceTimeMonthWithMinus flexTargetTime = new AttendanceTimeMonthWithMinus(0);
		
		/** ○加算設定を取得する */
		AddSet addSet = getAddSet(premiumAtr);
		
		// 集計対象時間を取得する
		val totalLegalTime = aggregateTotalWorkingTime.getWorkTime().getAggregateTargetTime(datePeriod, addSet);
		
		// フレックス対象時間に集計対象時間を加算する
		flexTargetTime = flexTargetTime.addMinutes(totalLegalTime.v());
				
		// 合計フレックス時間を取得する
		val totalFlexTime = this.flexTime.getTimeSeriesTotalFlexTime(datePeriod, true);
		
		// フレックス対象時間にフレックス時間を加算する
		flexTargetTime = flexTargetTime.addMinutes(totalFlexTime.v());
		
		// 休暇加算時間を取得する
		val dailies = monthlyCalcDailys.getDailyWorks(sid, datePeriod);
		AttendanceTimeMonth vacationAddTime = WorkTimeAddtionTimeGetter.getForPremiumTime(require,
				dailies, WorkingSystem.FLEX_TIME_WORK, cid, aggregateAtr, Optional.of(addSet));

		// フレックス対象時間に休暇加算時間を加算する
		flexTargetTime = flexTargetTime.addMinutes(vacationAddTime.v());

		// 「休暇加算時間」を「加算した休暇使用時間」に退避しておく
		this.addedVacationUseTime.addMinutesToAddTimePerMonth(vacationAddTime.v());
		
		return flexTargetTime;
	}

	/**
	 * 週平均超過時間を計算する
	 * @param aggregateAtr 集計区分
	 * @param flexTargetTime フレックス対象時間
	 * @param settingsByFlex フレックス勤務が必要とする設定
	 */
	private void calcExcessWeekAveTime(RequireM4 require, YearMonth yearMonth, DatePeriod datePeriod, ClosureId closureId, 
			MonthlyAggregateAtr aggregateAtr, AttendanceTimeMonthWithMinus flexTargetTime, SettingRequiredByFlex settingsByFlex,
			FlexTimeCurrentMonth flexTimeCurrentMonth) {
		
		int excessWeekAveMinutes = 0;	// 週平均超過時間（分）

		// 週平均基準時間（週平均時間）を取得する
		int weekAveMinutes = this.getWeekAverageTime(
				require, yearMonth, datePeriod, closureId, settingsByFlex).valueAsMinutes();
		if (weekAveMinutes > 0){
			if (flexTargetTime.greaterThan(weekAveMinutes)){
				
				// 週平均超過時間を計算する　（フレックス対象時間－週平均基準時間）
				excessWeekAveMinutes = flexTargetTime.v() - weekAveMinutes;
			}
		}
		flexTimeCurrentMonth.setExcessWeekAveTime(new AttendanceTimeMonth(excessWeekAveMinutes));
	}
	
	/**
	 * 週平均基準時間（週平均時間）を取得する
	 * @param require Require
	 * @param yearMonth 年月(度)
	 * @param datePeriod 期間
	 * @param closureId 締めID
	 * @param settingsByFlex フレックス勤務が必要とする設定
	 * @return 週平均基準時間
	 */
	public AttendanceTimeMonth getWeekAverageTime(RequireM4 require, YearMonth yearMonth, DatePeriod datePeriod,
			ClosureId closureId, SettingRequiredByFlex settingsByFlex) {
		
		// 在籍日数で按分した時間を取得する
		MonthlyEstimateTime newPres = settingsByFlex.getFlexAggregateMethodMonthly().getTimeDivisionByWorkingDays(
				require, yearMonth, new MonthlyEstimateTime(settingsByFlex.getWeekAverageTime().valueAsMinutes()),
				datePeriod, closureId);
		
		return new AttendanceTimeMonth(newPres.valueAsMinutes());
	}

	/**
	 * 計算フレックス時間の計算（原則集計）
	 * @param require Require
	 * @param cacheCarrier Cache
	 * @param companyId 会社ID
	 * @param employeeId 社員ID
	 * @param yearMonth 年月(度)
	 * @param datePeriod 期間
	 * @param closureId 締めID
	 * @param closureDate 締め日
	 * @param employmentCode 雇用コード
	 * @param companySets 月別集計で必要な会社別設定
	 * @param employeeSets 月別集計で必要な社員別設定
	 * @param settingsByFlex フレックス勤務が必要とする設定
	 * @param aggregateTotalWorkingTime 総労働時間
	 * @param monthlyCalculatingDailys 月の計算中の日別実績データ
	 */
	private void calcCalcFlexTime(RequireM4 require, CacheCarrier cacheCarrier, String companyId, String employeeId,
			YearMonth yearMonth, DatePeriod datePeriod, ClosureId closureId, ClosureDate closureDate, String employmentCode,
			MonthlyAggregateAtr aggregateAtr, MonAggrCompanySettings companySets, MonAggrEmployeeSettings employeeSets, 
			SettingRequiredByFlex settingsByFlex, AggregateTotalWorkingTime aggregateTotalWorkingTime, 
			MonthlyCalculatingDailys monthlyCalculatingDailys) {
		
		// 計算フレックス対象時間を求める
		int calcflexTargetMinutes = this.aggregateCalcFlexTargetTime(require, companyId, employeeId, aggregateAtr, 
				monthlyCalculatingDailys, datePeriod, aggregateTotalWorkingTime, PremiumAtr.PREMIUM).v();
		
		// 所定労働時間（代休控除後）を求める
		AttendanceTimeMonth compLeaveAfterDeduct = this.getPrescribedTime(require, cacheCarrier, companyId, 
				employeeId, yearMonth, datePeriod, closureId, closureDate, employmentCode, companySets, 
				employeeSets, settingsByFlex, aggregateTotalWorkingTime, monthlyCalculatingDailys);
		
		// 繰越時間相殺前を求める
		int carryforwardTimeBeforeOffset = calcflexTargetMinutes - compLeaveAfterDeduct.valueAsMinutes();
		if (carryforwardTimeBeforeOffset > 0){
			// フレックス超過の処理をする
			this.calcFlexExcessPrinciple(new AttendanceTimeMonthWithMinus(carryforwardTimeBeforeOffset));
		} else {
			// フレックス不足の処理をする
			this.calcFlexShortagePrinciple(require, companyId, employeeId, aggregateAtr, monthlyCalculatingDailys, datePeriod,
					new AttendanceTimeMonthWithMinus(carryforwardTimeBeforeOffset), aggregateTotalWorkingTime);
		}
	}
	
	/**
	 * 計算フレックス対象時間を求める
	 * @return フレックス対象時間
	 */
	private AttendanceTimeMonthWithMinus aggregateCalcFlexTargetTime(RequireM8 require, String cid, String sid,
			MonthlyAggregateAtr aggregateAtr, MonthlyCalculatingDailys monthlyCalcDailys, DatePeriod datePeriod,
			AggregateTotalWorkingTime aggregateTotalWorkingTime, PremiumAtr premiumAtr){
		
		int calcFlexTargetMinutes = 0;

		// 「月次法定内のみ加算」を確認する
		AddSet addSet = getAddSet(premiumAtr);
		
		// 集計対象時間を取得する
		val totalLegalTime = aggregateTotalWorkingTime.getWorkTime().getAggregateTargetTime(datePeriod, addSet);
		
		// 計算フレックス対象時間に集計対象時間を加算する
		calcFlexTargetMinutes += totalLegalTime.v();
				
		// 合計計算フレックス時間を取得する
		int totalCalcFlexMinutes = this.flexTime.getTimeSeriesTotalCalcFlexTime(datePeriod, true).v();
		
		// 計算フレックス対象時間に合計計算フレックス時間を加算する
		calcFlexTargetMinutes += totalCalcFlexMinutes;
		
		// 休暇加算時間を取得する
		val dailies = monthlyCalcDailys.getDailyWorks(sid, datePeriod);
		AttendanceTimeMonth vacationAddTime = WorkTimeAddtionTimeGetter.getForPremiumTime(require,
				dailies, WorkingSystem.FLEX_TIME_WORK, cid, aggregateAtr, Optional.of(addSet));

		// 計算フレックス対象時間に休暇加算時間を加算する
		calcFlexTargetMinutes += vacationAddTime.valueAsMinutes();
		
		return new AttendanceTimeMonthWithMinus(calcFlexTargetMinutes);
	}

	/** ○加算設定を取得する */
	private AddSet getAddSet(PremiumAtr premiumAtr) {
		AddSet addSet = new AddSet();
		if (!this.addMonthlyWithinStatutory){
			// 加算しない
			
			// 加算設定　取得　（割増用）
			addSet = GetAddSet.get(WorkingSystem.FLEX_TIME_WORK, premiumAtr, this.holidayAdditionMap);
			if (addSet.getErrorInfo().isPresent()){
				this.errorInfos.add(addSet.getErrorInfo().get());
			}
		}
		else {
			// 加算する
			
			// 加算設定　取得　（法定内のみ用）
			addSet = GetAddSet.get(WorkingSystem.FLEX_TIME_WORK, PremiumAtr.ONLY_LEGAL, this.holidayAdditionMap);
			if (addSet.getErrorInfo().isPresent()){
				this.errorInfos.add(addSet.getErrorInfo().get());
			}
		}
		return addSet;
	}

	/**
	 * フレックス超過の処理をする　（原則）
	 */
	private AttendanceTimeMonth flexExcessPrinciple(RequireM4 require, CacheCarrier cacheCarrier, 
			AttendanceTimeMonthWithMinus carryforwardTimeBeforeOffset,
			String companyId, String employeeId, YearMonth yearMonth, DatePeriod datePeriod, MonthlyAggregateAtr aggregateAtr,
			MonAggrCompanySettings companySets, MonAggrEmployeeSettings employeeSets, SettingRequiredByFlex settingsByFlex,
			AggregateTotalWorkingTime aggregateTotalWorkingTime, ClosureId closureId, ClosureDate closureDate, 
			MonthlyCalculatingDailys monthlyCalculatingDailys, FlexTimeTotalTimeMonth flexTime, AttendanceTimeMonth flexExcessTime,
			FlexCarryforwardTime flexCarryforwardTime, FlexTimeCurrentMonth flexTimeCurrentMonth) {

		// 繰越不足時間を取得する
		AttendanceTimeMonthWithMinus carryforwardTime = flexCarryforwardTime.getFlexCarryforwardTime();
		if (carryforwardTime.lessThan(0)){
			carryforwardTime = new AttendanceTimeMonthWithMinus(-carryforwardTime.v());
		} else{
			carryforwardTime = new AttendanceTimeMonthWithMinus(0);
		}
		
		// フレックス繰越時間を取得する
		val carryforwardWorkTime = flexCarryforwardTime.getFlexCarryforwardWorkTime();
		val carryforwardShortageTime = flexCarryforwardTime.getFlexCarryforwardShortageTime();
		
		if (carryforwardTime.lessThan(carryforwardTimeBeforeOffset.v())){
			
			// 繰越不足時間をフレックス繰越勤務時間に加算する
			flexCarryforwardTime.setFlexCarryforwardWorkTime(
					carryforwardWorkTime.addMinutes(carryforwardTime.v()));
			
			// 繰越時間相殺前と繰越不足時間の差分をフレックス超過時間・フレックス時間に加算する
			val difference = carryforwardTimeBeforeOffset.minusMinutes(carryforwardTime.v());
			flexExcessTime = flexExcessTime.addMinutes(difference.v());
			flexTime.addFlexTime(difference);

			// 法定内・法定外フレックス時間を求める
			this.calcLegalFlexTime(require, cacheCarrier, companyId, employeeId, yearMonth, datePeriod,
					aggregateAtr, flexTime.getFlexTime().getTime(),companySets, employeeSets, settingsByFlex, 
					aggregateTotalWorkingTime, closureId, closureDate, monthlyCalculatingDailys, flexTime, flexTimeCurrentMonth);
		} else {
			
			// 繰越時間相殺前をフレックス繰越勤務時間に加算する
			flexCarryforwardTime.setFlexCarryforwardWorkTime(
					carryforwardWorkTime.addMinutes(carryforwardTimeBeforeOffset.v()));
			
			// 繰越不足時間と繰越時間相殺前の差分をフレックス繰越不足時間に加算する
			val difference = carryforwardTime.minusMinutes(carryforwardTimeBeforeOffset.v());
			flexCarryforwardTime.setFlexCarryforwardShortageTime(
					carryforwardShortageTime.addMinutes(difference.v()));
			
			// フレックス時間を 0 にする
			flexTime.clearFlexTime();
		}
		
		return flexExcessTime;
	}
	
	/**
	 * フレックス不足の処理をする　（原則）
	 * @param datePeriod 期間
	 * @param carryforwardTimeBeforeOffset 繰越時間相殺前
	 * @param aggregateTotalWorkingTime 総労働時間
	 * @param addSet 加算設定
	 * @return 加算した休暇使用時間
	 */
	private AddedVacationUseTime flexShortagePrinciple(RequireM8 require, String cid, String sid, 
			MonthlyAggregateAtr aggregateAtr, MonthlyCalculatingDailys monthlyCalcDailys, DatePeriod datePeriod,
			AttendanceTimeMonthWithMinus carryforwardTimeBeforeOffset, AggregateTotalWorkingTime aggregateTotalWorkingTime) {
		
		AddedVacationUseTime addedVacationUseTime = new AddedVacationUseTime();
		
		// フレックス繰越時間を取得する
		AttendanceTimeMonthWithMinus carryforwardTime = this.flexCarryforwardTime.getFlexCarryforwardTime();
		// （不足分をプラスにして取り出す）
		if (carryforwardTime.lessThan(0)){
			carryforwardTime = new AttendanceTimeMonthWithMinus(-carryforwardTime.v());
		}
		else{
			carryforwardTime = new AttendanceTimeMonthWithMinus(0);
		}
		val carryforwardShortageTime = this.flexCarryforwardTime.getFlexCarryforwardShortageTime();
		
		// フレックス繰越時間をフレックス繰越不足時間に加算する
		this.flexCarryforwardTime.setFlexCarryforwardShortageTime(
				carryforwardShortageTime.addMinutes(carryforwardTime.v()));
		
		// 繰越時間相殺前を休暇加算後にコピーする
		AttendanceTimeMonthWithMinus afterAddVacation =
				new AttendanceTimeMonthWithMinus(carryforwardTimeBeforeOffset.v());
		
		// 休暇加算後＜ 0 なら、不足分を加算する
		if (afterAddVacation.lessThan(0)){
			
			// 「月次法定内のみ加算」を確認する
			if (!this.addMonthlyWithinStatutory) { // 「加算しない」時、不足分を加算する

				/** 不足分を加算する(原則) */
				addedVacationUseTime = getShortageAddTime(require, cid, sid, aggregateAtr, monthlyCalcDailys, datePeriod);
					
				// 休暇加算後に休暇加算時間を加算する
				afterAddVacation = afterAddVacation.addMinutes(addedVacationUseTime.getAddTimePerMonth().v());
				if (afterAddVacation.greaterThan(0)){
					
					// 休暇加算後が0を超える時、休暇加算後を 0 とし、休暇加算後と相殺前の差分を「加算した休暇使用時間」とする
					afterAddVacation = new AttendanceTimeMonthWithMinus(0);
					val difference = afterAddVacation.minusMinutes(carryforwardTimeBeforeOffset.v());
					addedVacationUseTime = AddedVacationUseTime.of(new AttendanceTimeMonth(difference.v()));
				}

			}
		}
		
		// 休暇加算後をフレックス不足時間に加算する
		// ※　休暇加算後はマイナス値なので、減算により、加算扱いにする。
		this.flexShortageTime = this.flexShortageTime.minusMinutes(afterAddVacation.v());

		// 休暇加算後をフレックス時間に加算する
		this.flexTime.getFlexTime().addFlexTime(afterAddVacation);
		
		return addedVacationUseTime;
	}

	/**
	 * 法定内・法定外フレックス時間を求める
	 */
	private void calcLegalFlexTime(RequireM4 require, CacheCarrier cacheCarrier, String companyId, String employeeId,
			YearMonth yearMonth, DatePeriod period, MonthlyAggregateAtr aggregateAtr, AttendanceTimeMonthWithMinus flexTimeAfterSettle,
			MonAggrCompanySettings companySets, MonAggrEmployeeSettings employeeSets, SettingRequiredByFlex settingsByFlex,
			AggregateTotalWorkingTime aggregateTotalWorkingTime, ClosureId closureId, ClosureDate closureDate,
			MonthlyCalculatingDailys monthlyCalculatingDailys, FlexTimeTotalTimeMonth flexTime, FlexTimeCurrentMonth flexTimeCurrentMonth) {
		
		// 「法定内集計設定．集計設定」を確認する
		val aggrSet = this.flexAggrSet.getLegalAggrSet().getAggregateSet();
		if (aggrSet == AggregateSetting.INCLUDE_ALL_OUTSIDE_TIME_IN_FLEX_TIME){		// 時間外は全てフレ時間
			
			// 全て法定外に入れる
			flexTime.allFlexAsIllegal(new AttendanceTimeMonth(flexTimeAfterSettle.valueAsMinutes()));
			return;
		}
		
		// フレ時間の内訳を管理する
		// 法定内として扱う時間を求める
		AttendanceTimeMonth treatLegalMinutes = FlexLegalTimeGetter.askTreatLegalTime(require, cacheCarrier, companyId, employeeId,
				period, yearMonth, aggregateAtr, companySets, employeeSets, settingsByFlex, closureId, closureDate,
				Optional.of(aggregateTotalWorkingTime), monthlyCalculatingDailys, this.flexCarryforwardTime, flexTimeCurrentMonth);
		
		/** 大塚モードかを確認する */
		// 「月次法定内のみ加算」を確認する
		if (AppContexts.optionLicense().customize().ootsuka() && this.addMonthlyWithinStatutory){
			
			// 大塚モードで求める
			this.calcLegalFlexTimeForOtuka(require, cacheCarrier, employeeId, yearMonth, period, flexTimeAfterSettle,
					treatLegalMinutes, settingsByFlex, aggregateTotalWorkingTime, flexTime, companyId, aggregateAtr,
					monthlyCalculatingDailys);
		} else{
			
			// 「法定内として扱う時間」と「清算後フレックス時間」を比較する
			flexTime.calcIllegalWith(flexTimeAfterSettle, treatLegalMinutes);
		}
	}

	/**
	 * 法定内・法定外フレックス時間を求める　（大塚モードで求める）
	 */
	private void calcLegalFlexTimeForOtuka(RequireM3 require, CacheCarrier cacheCarrier, String employeeId,
			YearMonth yearMonth, DatePeriod period, AttendanceTimeMonthWithMinus flexTimeAfterSettle,
			AttendanceTimeMonth treatLegalTime, SettingRequiredByFlex settingsByFlex,
			AggregateTotalWorkingTime aggregateTotalWorkingTime, FlexTimeTotalTimeMonth flexTime,
			String cid, MonthlyAggregateAtr aggregateAtr, MonthlyCalculatingDailys monthlyCalcDailys) {
		
		// 休暇加算時間合計を求める
		int totalVacationAddMinutes = this.askTotalVacationAddTime(require, cacheCarrier, employeeId, yearMonth, period,
				PremiumAtr.ONLY_LEGAL, settingsByFlex, aggregateAtr, aggregateTotalWorkingTime, cid, monthlyCalcDailys).v();
		
		// 加算設定を取得する
		val addSet = GetAddSet.get(WorkingSystem.FLEX_TIME_WORK, PremiumAtr.ONLY_LEGAL, this.holidayAdditionMap);
		if (addSet.isSpecialHoliday()){
			
			// 特別休暇使用時間合計を求める
			int totalSpecialLeaveUseMinutes = this.askTotalSpecialHolidayUseTime(require, cacheCarrier, employeeId, yearMonth, period,
					settingsByFlex, aggregateTotalWorkingTime).v();
			
			// 「休暇加算時間合計」に「特別休暇使用時間合計」を加算する
			totalVacationAddMinutes += totalSpecialLeaveUseMinutes;
		}
		
		// 「清算後フレックス時間」と「休暇加算時間合計」を比較する
		if (flexTimeAfterSettle.v() <= totalVacationAddMinutes){
			
			// 全て法定内に入れる
			flexTime.allFlexAsLegal(new AttendanceTimeMonth(flexTimeAfterSettle.v()));
		}
		else {
			
			// 「清算後フレックス時間差分」を求める
			int diffMinutes = flexTimeAfterSettle.v() - totalVacationAddMinutes;
			
			// 「清算後フレックス時間差分」と「法定内として扱う時間」を比較する
			if (diffMinutes <= treatLegalTime.v()){
				
				// 全て法定内に入れる
				flexTime.allFlexAsLegal(new AttendanceTimeMonth(flexTimeAfterSettle.v()));
			}
			else{
				
				// 法定内と法定外に分けて入れる
				flexTime.setFlexIllegal(new AttendanceTimeMonth(diffMinutes - treatLegalTime.v()));
				flexTime.setFlexLegal(new AttendanceTimeMonth(totalVacationAddMinutes + treatLegalTime.v()));
			}
		}
	}


	/**
	 * 休暇加算時間合計を求める
	 * @param employeeId 社員ID
	 * @param yearMonth 年月（度）
	 * @param period 期間
	 * @param premiumAtr 割増区分
	 * @param settingsByFlex フレックス勤務が必要とする設定
	 * @param aggregateTotalWorkingTime 総労働時間（当月分）
	 */
	private AttendanceTimeYear askTotalVacationAddTime(RequireM3 require, CacheCarrier cacheCarrier, String employeeId, YearMonth yearMonth,
			DatePeriod period, PremiumAtr premiumAtr, SettingRequiredByFlex settingsByFlex, MonthlyAggregateAtr aggregateAtr,
			AggregateTotalWorkingTime aggregateTotalWorkingTime, String cid, MonthlyCalculatingDailys monthlyCalcDailys){
		
		int result = 0;
		val flexAggrSet = settingsByFlex.getFlexAggrSet();
		
		// フレックス清算期間の取得
		SettlePeriodOfFlex settlePeriod = flexAggrSet.getInsufficSet().getSettlePeriod(require, cacheCarrier, employeeId, period, yearMonth);

		// 加算設定　取得
		val addSet = GetAddSet.get(WorkingSystem.FLEX_TIME_WORK, premiumAtr, this.holidayAdditionMap);
		if (addSet.getErrorInfo().isPresent()){
			this.errorInfos.add(addSet.getErrorInfo().get());
		}
		
		// 開始月～「年月」を年月ごとにループする
		YearMonth indexYm = settlePeriod.getStartYm();
		for (; indexYm.lessThanOrEqualTo(yearMonth); indexYm = indexYm.nextMonth()){
			
			int vacationAddMinutes = 0;		// 休暇加算時間
			
			// ループ中の年月と「年月」を比較する
			if (indexYm.lessThan(yearMonth)){	// 前月以前
				
				// ループ中の年月の「月別実績の勤怠時間」を取得する
				val prevAttendanceTimeList = require.attendanceTimeOfMonthly(employeeId, indexYm);

				// 休暇加算時間を取得する（過去分）
				if (!prevAttendanceTimeList.isEmpty()){
					val prevAttendanceTime = prevAttendanceTimeList.get(prevAttendanceTimeList.size() - 1);
					val vacationUseTime = prevAttendanceTime.getMonthlyCalculation().getAggregateTime().getVacationUseTime();
					vacationAddMinutes = GetVacationAddTime.getTimeForPast(vacationUseTime, addSet).v();
				}
			} else {		// 当月
				
				// 休暇加算時間を取得する
				val dailies = monthlyCalcDailys.getDailyWorks(employeeId, period);
				vacationAddMinutes = WorkTimeAddtionTimeGetter.getForPremiumTime(require,
						dailies, WorkingSystem.FLEX_TIME_WORK, cid, aggregateAtr, Optional.of(addSet)).valueAsMinutes();
			}
			
			// 「休暇加算時間合計」に「休暇加算時間」を加算する
			result += vacationAddMinutes;
		}
		
		// 休暇加算時間合計を返す
		return new AttendanceTimeYear(result);
	}

	/**
	 * 特別休暇使用時間合計を求める
	 * @param employeeId 社員ID
	 * @param yearMonth 年月（度）
	 * @param period 期間
	 * @param settingsByFlex フレックス勤務が必要とする設定
	 * @param aggregateTotalWorkingTime 総労働時間（当月分）
	 */
	private AttendanceTimeYear askTotalSpecialHolidayUseTime(RequireM3 require, CacheCarrier cacheCarrier, String employeeId,
			YearMonth yearMonth, DatePeriod period, SettingRequiredByFlex settingsByFlex,
			AggregateTotalWorkingTime aggregateTotalWorkingTime){
		
		int result = 0;
		val flexAggrSet = settingsByFlex.getFlexAggrSet();
		
		// フレックス清算期間の取得
		SettlePeriodOfFlex settlePeriod = flexAggrSet.getInsufficSet().getSettlePeriod(require, cacheCarrier, employeeId, period, yearMonth);
		
		// 開始月～「年月」を年月ごとにループする
		YearMonth indexYm = settlePeriod.getStartYm();
		for (; indexYm.lessThanOrEqualTo(yearMonth); indexYm = indexYm.nextMonth()){
			
			int useMinutes = 0;		// 使用時間
			
			// ループ中の年月と「年月」を比較する
			if (indexYm.lessThan(yearMonth)){	// 前月以前
				
				// ループ中の年月の「月別実績の勤怠時間」を取得する
				val prevAttendanceTimeList = require.attendanceTimeOfMonthly(employeeId, indexYm);

				// 「特別休暇．使用時間」　→　使用時間
				if (!prevAttendanceTimeList.isEmpty()){
					val prevAttendanceTime = prevAttendanceTimeList.get(prevAttendanceTimeList.size() - 1);
					val vacationUseTime = prevAttendanceTime.getMonthlyCalculation().getAggregateTime().getVacationUseTime();
					useMinutes = vacationUseTime.getSpecialHoliday().getUseTime().v();
				}
			}
			else{		// 当月
				
				// 処理中年月の使用時間の合計　→　使用時間
				val vacationUseTime = aggregateTotalWorkingTime.getVacationUseTime();
				useMinutes = vacationUseTime.getSpecialHoliday().getTotalUseTime(period).v();
			}
			
			// 「特別休暇使用時間合計」に使用時間を加算する
			result += useMinutes;
		}
		
		// 特別休暇使用時間合計を返す
		return new AttendanceTimeYear(result);
	}
	
	
	/**
	 * 計算フレックス超過の処理をする　（原則）
	 * @param carryforwardTimeBeforeOffset 繰越時間相殺前
	 */
	private void calcFlexExcessPrinciple(AttendanceTimeMonthWithMinus carryforwardTimeBeforeOffset){

		// フレックス繰越時間を取得する
		int carryforwardMinutes = this.flexCarryforwardTime.getFlexCarryforwardTime().v();
		// （不足分をプラスにして取り出す）
		if (carryforwardMinutes < 0){
			carryforwardMinutes = -carryforwardMinutes;
		} else{
			carryforwardMinutes = 0;
		}
		
		if (carryforwardMinutes < carryforwardTimeBeforeOffset.v()){
			
			// 繰越時間相殺前とフレックス繰越時間の差分を計算フレックス時間に加算する
			int difference = carryforwardTimeBeforeOffset.v() - carryforwardMinutes;
			this.flexTime.getFlexTime().addFlexCalcTime(new AttendanceTimeMonthWithMinus(difference));
		} else {
			
			// 計算フレックス時間を 0 にする
			this.flexTime.getFlexTime().clearFlexCalcTime();
		}
	}


	/**
	 * 計算フレックス不足の処理をする　（原則）
	 * @param datePeriod 期間
	 * @param carryforwardTimeBeforeOffset 繰越時間相殺前
	 * @param aggregateTotalWorkingTime 総労働時間
	 * @return 加算した休暇使用時間
	 */
	private AddedVacationUseTime calcFlexShortagePrinciple(RequireM8 require, String cid, String sid, 
			MonthlyAggregateAtr aggregateAtr, MonthlyCalculatingDailys monthlyCalcDailys, DatePeriod datePeriod,
			AttendanceTimeMonthWithMinus carryforwardTimeBeforeOffset, AggregateTotalWorkingTime aggregateTotalWorkingTime) {
		
		AddedVacationUseTime addedVacationUseTime = new AddedVacationUseTime();
		
		// 休暇加算前を確認する　←　繰越時間相殺前
		int beforeAddVacation = carryforwardTimeBeforeOffset.v();
		
		// 休暇加算前＜ 0 なら、不足分を加算する
		if (beforeAddVacation < 0){
			
			// 「月次法定内のみ加算」を確認する
			if (!this.addMonthlyWithinStatutory){
				// 「加算しない」時、不足分を加算する
				
				/** ○不足分を加算する(原則・計算) */
				addedVacationUseTime = getShortageAddTime(require, cid, sid, aggregateAtr, monthlyCalcDailys, datePeriod);
			}
		}
		
		// 繰越時間相殺前を計算フレックス時間に加算する
		this.flexTime.getFlexTime().addFlexCalcTime(carryforwardTimeBeforeOffset);
		
		return addedVacationUseTime;
	}
	
	/** 不足分を加算する(原則) */
	private AddedVacationUseTime getShortageAddTime(RequireM8 require, String cid, String sid, 
			MonthlyAggregateAtr aggregateAtr, MonthlyCalculatingDailys monthlyCalcDailys, DatePeriod datePeriod) {

		/** 「フレックス勤務の加算設定」を取得する */
		val flexAddSet = require.workFlexAdditionSet(cid);
		
		return flexAddSet.map(c -> {
			
			if (c.isAddVacation(nts.uk.ctx.at.shared.dom.PremiumAtr.Premium) == NotUseAtr.NOT_USE) {

				// 加算設定　取得　（不足時用）
				val addSetWhenShortage = GetAddSet.get(
						WorkingSystem.FLEX_TIME_WORK, PremiumAtr.WHEN_SHORTAGE, this.holidayAdditionMap);
				if (addSetWhenShortage.getErrorInfo().isPresent()){
					this.errorInfos.add(addSetWhenShortage.getErrorInfo().get());
				}
				
				// 休暇加算時間を取得する
				val dailies = monthlyCalcDailys.getDailyWorks(sid, datePeriod);
				AttendanceTimeMonth vacationAddTime = WorkTimeAddtionTimeGetter.getForPremiumTime(require,
						dailies, WorkingSystem.FLEX_TIME_WORK, cid, aggregateAtr, Optional.of(addSetWhenShortage));
				
				// 加算した「休暇加算時間」を「加算した休暇使用時間」に退避しておく
				return AddedVacationUseTime.of(vacationAddTime);
			} 
			return new AddedVacationUseTime();
		}).orElseGet(() -> new AddedVacationUseTime());
	}

	/**
	 * フレックス繰越不可時間を求める
	 * @param companyId 会社ID
	 * @param employeeId 社員ID
	 * @param yearMonth 年月（度）
	 * @param closureId 締めID
	 * @param companySets 月別集計で必要な会社別設定
	 * @param employeeSets 月別集計で必要な社員別設定
	 * @param settingsByFlex フレックス勤務が必要とする設定
	 */
	private void askNotCarryforwardTime(RequireM2 require, CacheCarrier cacheCarrier, String companyId, 
			String employeeId, YearMonth yearMonth, DatePeriod period, ClosureId closureId, MonAggrCompanySettings companySets, 
			MonAggrEmployeeSettings employeeSets, SettingRequiredByFlex settingsByFlex){
		
		val flexAggrSet = settingsByFlex.getFlexAggrSet();
		
		// フレックス繰越不可時間　←　０
		this.flexCarryforwardTime.setFlexNotCarryforwardTime(new AttendanceTimeMonth(0));
		
		// 「不足設定．繰越設定」を確認する
		if (flexAggrSet.getInsufficSet().getCarryforwardSet() == CarryforwardSetInShortageFlex.CURRENT_MONTH_INTEGRATION)
			return;	// 当月精算
		
		/** 月別実績の時間項目を丸める */
		int flexShortageMinutes = companySets.getRoundingSet().itemRound(AttendanceItemOfMonthly.FLEX_TIME.value, this.flexShortageTime).v();
		
		// フレックス不足時間を確認する
		if (flexShortageMinutes <= 0) return;
		
		/** 次の集計期間で同じ労働制で集計するかを確認する */
		if(WorkingSystemChangeCheckService.isSameWorkingSystemWithNextAggrPeriod(require, cacheCarrier, employeeId, period, WorkingSystem.FLEX_TIME_WORK) == WorkingSystemChangeState.CHANGED) {
			/** フレックス繰越不可時間　←　フレックス不足時間(丸め後) */
			this.flexCarryforwardTime.setFlexNotCarryforwardTime(new AttendanceTimeMonth(flexShortageMinutes));
			return;
		}
		
		// 翌月の所定と法定の差を求める
		int diffNextMonth = 0;
		{
			// 翌月末時点の雇用コードを確認する
			if (companySets.getClosureMap().containsKey(closureId.value)){
				val closure = companySets.getClosureMap().get(closureId.value);
				val closurePeriods = closure.getPeriodByYearMonth(yearMonth.nextMonth());
				if (closurePeriods.size() > 0){
					DatePeriod nextPeriod = closurePeriods.get(closurePeriods.size() - 1);	// 翌月締め期間
					val employmentOpt = employeeSets.getEmployment(nextPeriod.end());
					// ※　翌月末時点の雇用コード
					if (employmentOpt.isPresent()){
						String employmentCd = employmentOpt.get().getEmploymentCode();
						
						// 法定労働時間を取得する
						val flexStatTime = MonthlyStatutoryWorkingHours.flexMonAndWeekStatutoryTime(require, cacheCarrier, 
								companyId, employmentCd, employeeId, nextPeriod.end(), yearMonth.nextMonth());
						// 翌月時間の確認　（マスタから参照用）
						int nextStatMinutes = flexStatTime.getStatutorySetting().v();	// 翌月法定
						int nextPredMinutes = flexStatTime.getSpecifiedSetting().v();	// 翌月所定
						
						/** 翌月の所定と法定の差　＝　EMPTYの時 →　「フレックス不足時間(丸め後)　≦　翌月の所定と法定の差」と同様にする*/
						if (nextStatMinutes == 0) 
							return;
						
						// 「フレックス勤務所定労働時間取得．参照先」を確認する
						if (!companySets.getComFlexSetOpt().map(c -> c.isWithinTimeUsageAttr()).orElseThrow(() -> new BusinessException("Msg_2242"))){	// 実績から参照
								
							// 処理中の年月の翌月の「月別実績の勤怠時間」を取得する
							val nextYearMonth = yearMonth.nextMonth();
							val nextAttendanceTimeList = require.attendanceTimeOfMonthly(employeeId, nextYearMonth);
							if (!nextAttendanceTimeList.isEmpty()){
								val nextAttendanceTime = nextAttendanceTimeList.get(nextAttendanceTimeList.size() - 1);
								val nextAggrTime = nextAttendanceTime.getMonthlyCalculation().getAggregateTime();
								
								// 「実績から参照」　かつ　翌月の実績から取得出来たら、その実績所定労働時間を採用する
								nextPredMinutes = nextAggrTime.getPrescribedWorkingTime().getRecordPrescribedWorkingTime().v();
							}
						}
						
						// 翌月の所定と法定の差を求める　（法定労働時間－所定労働時間）
						diffNextMonth = nextStatMinutes - nextPredMinutes;
						if (diffNextMonth < 0) diffNextMonth = 0;
					}
				}
			}
		}
		
		// フレックス繰越不可時間を計算する
		if (flexShortageMinutes > diffNextMonth) {
			this.flexCarryforwardTime.setFlexNotCarryforwardTime(new AttendanceTimeMonth(
					flexShortageMinutes - diffNextMonth));
		}
	}
	
	/**
	 * 清算処理をする
	 * @param require Require
	 * @param cacheCarrier Cache
	 * @param companyId 会社ID
	 * @param employeeId 社員ID
	 * @param yearMonth 年月（度）
	 * @param period 期間
	 * @param closureId 締めID
	 * @param closureDate 締め日
	 * @param aggregateAtr 集計区分
	 * @param companySets 月別集計で必要な会社別設定
	 * @param employeeSets 月別集計で必要な社員別設定
	 * @param settingsByFlex フレックス勤務が必要とする設定
	 * @param aggregateTotalWorkingTime 総労働時間（当月分）
	 * @param monthlyCalculatingDailys 月の計算中の日別実績データ
	 */
	private void settleProc(RequireM4 require, CacheCarrier cacheCarrier, String companyId, String employeeId,  YearMonth yearMonth,
			DatePeriod period, ClosureId closureId, ClosureDate closureDate, MonthlyAggregateAtr aggregateAtr, 
			MonAggrCompanySettings companySets, MonAggrEmployeeSettings employeeSets, SettingRequiredByFlex settingsByFlex, 
			AggregateTotalWorkingTime aggregateTotalWorkingTime, MonthlyCalculatingDailys monthlyCalculatingDailys) {

		val flexAggrSet = settingsByFlex.getFlexAggrSet();
		
		// フレックス清算期間の取得
		SettlePeriodOfFlex settlePeriod = flexAggrSet.getInsufficSet().getSettlePeriod(
				require, cacheCarrier, employeeId, period, yearMonth);
		
		// 「清算月かどうか」を確認する
		if (settlePeriod.getIsSettleYm() == false){
			
			// フレックス時間　←　０
			this.flexTime.getFlexTime().clearFlexTime();
			this.flexTime.getFlexTime().setFlexLegal(new AttendanceTimeMonth(0));
			this.flexTime.getFlexTime().setFlexIllegal(new AttendanceTimeMonth(0));
			this.flexShortageTime = new AttendanceTimeMonth(0);
			this.flexExcessTime = new AttendanceTimeMonth(0);
			return;
		}
		
		// 清算後フレックス時間を計算する　（フレックス繰越時間＋当月フレックス時間．フレックス時間－当月精算フレックス時間）
		FlexTimeCurrentMonth flexTimeCurrentMonth = this.flexTime.getFlexTimeCurrentMonth();
		if (aggregateAtr == MonthlyAggregateAtr.EXCESS_OUTSIDE_WORK){
			// 時間外超過の時、時間外超過のフレックス時間を利用する
			flexTimeCurrentMonth = this.flexTimeOfExcessOutsideTime.getFlexTimeCurrentMonth();
		}
		int afterSettleMinutes = this.flexCarryforwardTime.getFlexCarryforwardTime().v();
		afterSettleMinutes += flexTimeCurrentMonth.getFlexTime().getFlexTime().getTime().v();
		afterSettleMinutes -= this.flexSettleTime.v();
		
		// 清算後フレックス時間を確認する
		if (afterSettleMinutes < 0){
			
			// フレックス不足の処理をする（清算）
			this.flexShortageSettle(require, cacheCarrier, employeeId, yearMonth, period,
					new AttendanceTimeMonthWithMinus(afterSettleMinutes),settingsByFlex, 
					aggregateTotalWorkingTime, aggregateAtr, companyId, monthlyCalculatingDailys);
		}
		else{
			
			// フレックス超過の処理をする（清算）
			this.flexExcessSettle(require, cacheCarrier, companyId, employeeId, yearMonth, period,
					closureId, closureDate, aggregateAtr, new AttendanceTimeMonthWithMinus(afterSettleMinutes),
					companySets, employeeSets, settingsByFlex, aggregateTotalWorkingTime, monthlyCalculatingDailys);
		}
	}
	
	/**
	 * フレックス不足の処理をする（清算）
	 * @param employeeId 社員ID
	 * @param yearMonth 年月（度）
	 * @param period 期間
	 * @param flexTimeAfterSettle 清算後フレックス時間
	 * @param settingsByFlex フレックス勤務が必要とする設定
	 * @param aggregateTotalWorkingTime 総労働時間（当月分）
	 */
	private void flexShortageSettle(RequireM3 require, CacheCarrier cacheCarrier, String employeeId, YearMonth yearMonth,
			DatePeriod period, AttendanceTimeMonthWithMinus flexTimeAfterSettle, SettingRequiredByFlex settingsByFlex,
			AggregateTotalWorkingTime aggregateTotalWorkingTime, MonthlyAggregateAtr aggregateAtr, String cid, 
			MonthlyCalculatingDailys monthlyCalcDailys){

		val flexAggrSet = settingsByFlex.getFlexAggrSet();
		int afterSettleMinutes = flexTimeAfterSettle.v();	// 清算後フレックス時間（分）
		
		// 「集計方法」を確認する
		if (flexAggrSet.getAggrMethod() == FlexAggregateMethod.PRINCIPLE){
			
			// 「月次法定内のみ加算」を確認する
			if (this.addMonthlyWithinStatutory == false){
				
				// 不足分を加算する（清算）
				{
					// 休暇加算時間合計を求める
					int totalVacationAddMinutes = this.askTotalVacationAddTime(require, cacheCarrier, employeeId, yearMonth, period,
							PremiumAtr.WHEN_SHORTAGE, settingsByFlex, aggregateAtr, aggregateTotalWorkingTime, cid, monthlyCalcDailys).v();
					
					// 「清算後フレックス時間」に「休暇加算合計時間」を加算する
					afterSettleMinutes += totalVacationAddMinutes;
				}
			}
		}
		
		// フレックス時間　←　清算後フレックス時間
		this.flexTime.getFlexTime().setFlex(new TimeMonthWithCalculationAndMinus(
				new AttendanceTimeMonthWithMinus(afterSettleMinutes),
				this.flexTime.getFlexTime().getFlexTime().getCalcTime()));
		this.flexTime.getFlexTime().setFlexLegal(new AttendanceTimeMonth(0));
		this.flexTime.getFlexTime().setFlexIllegal(new AttendanceTimeMonth(0));
		this.flexShortageTime = new AttendanceTimeMonth(-afterSettleMinutes);
		this.flexExcessTime = new AttendanceTimeMonth(0);
	}
	
	/**
	 * フレックス超過の処理をする（清算）
	 * @param require Require
	 * @param cacheCarrier Cache
	 * @param companyId 会社ID
	 * @param employeeId 社員ID
	 * @param yearMonth 年月（度）
	 * @param period 期間
	 * @param closureId 締めID
	 * @param closureDate 締め日
	 * @param aggregateAtr 集計区分
	 * @param flexTimeAfterSettle 清算後フレックス時間
	 * @param companySets 月別集計で必要な会社別設定
	 * @param employeeSets 月別集計で必要な社員別設定
	 * @param settingsByFlex フレックス勤務が必要とする設定
	 * @param aggregateTotalWorkingTime 総労働時間（当月分）
	 * @param monthlyCalculatingDailys 月の計算中の日別実績データ
	 */
	private void flexExcessSettle(RequireM4 require, CacheCarrier cacheCarrier, String companyId, String employeeId, 
			YearMonth yearMonth, DatePeriod period, ClosureId closureId, ClosureDate closureDate, MonthlyAggregateAtr aggregateAtr, 
			AttendanceTimeMonthWithMinus flexTimeAfterSettle, MonAggrCompanySettings companySets, 
			MonAggrEmployeeSettings employeeSets, SettingRequiredByFlex settingsByFlex, 
			AggregateTotalWorkingTime aggregateTotalWorkingTime, MonthlyCalculatingDailys monthlyCalculatingDailys){
		
//		val flexAggrSet = settingsByFlex.getFlexAggrSet();
		int afterSettleMinutes = flexTimeAfterSettle.v();	// 清算後フレックス時間（分）
		
		// 「集計方法」を確認する
//		if (flexAggrSet.getAggrMethod() == FlexAggregateMethod.PRINCIPLE){
			
			// 「清算後フレックス時間」を確認する
			if (afterSettleMinutes > 0){
				
				// フレックス時間　←　清算後フレックス時間
				this.flexTime.getFlexTime().setFlex(new TimeMonthWithCalculationAndMinus(
						new AttendanceTimeMonthWithMinus(afterSettleMinutes),
						this.flexTime.getFlexTime().getFlexTime().getCalcTime()));
				this.flexShortageTime = new AttendanceTimeMonth(0);
				this.flexExcessTime = new AttendanceTimeMonth(afterSettleMinutes);
				
				// 法定内・法定外フレックス時間を求める
				this.calcLegalFlexTime(require, cacheCarrier, companyId, employeeId, yearMonth, period, aggregateAtr,
						new AttendanceTimeMonthWithMinus(afterSettleMinutes), companySets, employeeSets, settingsByFlex,
						aggregateTotalWorkingTime, closureId, closureDate, monthlyCalculatingDailys, this.flexTime.getFlexTime(),
						this.flexTime.getFlexTimeCurrentMonth());
				
//				return;
			}
//		}
		
		// フレックス時間　←　清算後フレックス時間
//		this.flexTime.getFlexTime().setFlex(new TimeMonthWithCalculationAndMinus(
//				new AttendanceTimeMonthWithMinus(afterSettleMinutes),
//				this.flexTime.getFlexTime().getFlexTime().getCalcTime()));
//		this.flexTime.getFlexTime().setFlexLegal(new AttendanceTimeMonth(0));
//		this.flexTime.getFlexTime().setFlexIllegal(new AttendanceTimeMonth(afterSettleMinutes));
//		this.flexShortageTime = new AttendanceTimeMonth(0);
//		this.flexExcessTime = new AttendanceTimeMonth(afterSettleMinutes);
	}
	
	/**
	 * フレックス勤務の就業時間を求める
	 * @param require Require
	 * @param cacheCarrier Cache
	 * @param companyId 会社ID
	 * @param employeeId 社員ID
	 * @param yearMonth 年月(度)
	 * @param datePeriod 期間
	 * @param closureId 締めID
	 * @param closureDate 締め日
	 * @param employmentCode 雇用コード
	 * @param companySets 月別集計で必要な会社別設定
	 * @param employeeSets 月別集計で必要な社員別設定
	 * @param settingsByFlex フレックス勤務が必要とする設定
	 * @param aggregateTotalWorkingTime 総労働時間
	 * @param monthlyCalculatingDailys 月の計算中の日別実績データ
	 * @return 就業時間
	 */
	public void askWorkTimeOfFlex(RequireM4 require, CacheCarrier cacheCarrier,
			String companyId, String employeeId, YearMonth yearMonth, DatePeriod datePeriod, ClosureId closureId,
			ClosureDate closureDate, MonthlyAggregateAtr aggregateAtr, String employmentCode,
			MonAggrCompanySettings companySets, MonAggrEmployeeSettings employeeSets,
			SettingRequiredByFlex settingsByFlex,  AggregateTotalWorkingTime aggregateTotalWorkingTime, 
			MonthlyCalculatingDailys monthlyCalculatingDailys) {
		
		// 「フレックス集計方法」を確認する　（原則集計かどうか）
		FlexAggregateMethod flexAggregateMethod = settingsByFlex.getFlexAggrSet().getAggrMethod();
		if (flexAggregateMethod == FlexAggregateMethod.PRINCIPLE){
			
			/** フレックス時間発生するかを確認する */
			if (this.checkIsFlexTimeOccur(settingsByFlex.getFlexAggrSet())){
				
				// 設定上の所定労働時間を確認する
				// 所定労働時間を求める(代休使用控除する)
				val prescribedWorkingTime = this.getPrescribedTime(
						require, cacheCarrier, companyId, employeeId, yearMonth, datePeriod, closureId, closureDate,
						employmentCode, companySets, employeeSets, settingsByFlex,
						aggregateTotalWorkingTime, monthlyCalculatingDailys);
				
				/** ○就業時間←所定労働時間 */
				aggregateTotalWorkingTime.getWorkTime().setWorkTime(prescribedWorkingTime);
			} else {
				/** ○「フレ不足時間」を確認 */
				if (this.flexShortageTime.greaterThan(0)) {
					
					/** ○フレックス対象時間を集計する */
					int predMinutes = this.aggregateFlexTargetTime(require, companyId, employeeId, aggregateAtr,
							monthlyCalculatingDailys, datePeriod, aggregateTotalWorkingTime, PremiumAtr.WHEN_SHORTAGE).v();
					if (predMinutes < 0) predMinutes = 0;
	
					/** ○就業時間←フレックス対象時間 */
					aggregateTotalWorkingTime.getWorkTime().setWorkTime(new AttendanceTimeMonth(predMinutes));
				}
			}
		} else {
			
			/** ○就業時間←就業合計時間 */
			aggregateTotalWorkingTime.getWorkTime().totalizeWorkTime(datePeriod);
			val workTime = aggregateTotalWorkingTime.getWorkTime().getWorkTime();
			
			/** フレックス時間発生するかを確認する */
			if (this.checkIsFlexTimeOccur(settingsByFlex.getFlexAggrSet())) {

				/** 日単位のフレックス不足時間を合計する */
				val flexShortage = this.flexTime.getMinusFlexTime(); 
				
				/** ○就業時間←就業合計時間 */
				aggregateTotalWorkingTime.getWorkTime().setWorkTime(workTime.addMinutes(flexShortage.valueAsMinutes()));
				
			} else {

				/** 日単位のフレックス超過時間を合計する */
				val flexOver = this.flexTime.getPlusFlexTime();
				
				/** ○就業時間←就業合計時間　+　フレックス超過時間 */
				aggregateTotalWorkingTime.getWorkTime().setWorkTime(workTime.addMinutes(flexOver.valueAsMinutes()));
			}
		}
	}
	
	/** フレックス時間発生するかを確認する */
	public boolean checkIsFlexTimeOccur(FlexMonthWorkTimeAggrSet flexAggrSet) {
		
		/** 複数月かを確認する */
		if (!flexAggrSet.isMultiMonthSettlePeriod()) {
			/** ○「フレ超過時間」 OR 「フレ繰越勤務時間」に値が入っているか確認する */
			if (this.flexExcessTime.greaterThan(0) ||
					this.flexCarryforwardTime.getFlexCarryforwardWorkTime().greaterThan(0)) {
				/** 「発生する」を返す */
				return true;
			}
			/** 「発生しない」を返す */
			return false;
		} else {
			if (this.flexTime.getFlexTimeCurrentMonth().getFlexTime().getFlexTime().getTime().greaterThan(0)) {
				/** 「発生する」を返す */
				return true;
			}
			/** 「発生しない」を返す */
			return false;
		}
	}
	
	/**
	 * 年休控除する
	 * @param companyId 会社ID
	 * @param employeeId 社員ID
	 * @param period 期間
	 * @param flexAggregateMethod フレックス集計方法
	 * @param workingConditionItem 労働条件項目
	 * @param flexAggrSet フレックス時間勤務の月の集計設定
	 */
	private void deductAnnualLeave(RequireM1 require, String companyId, String employeeId, DatePeriod period,
			FlexAggregateMethod flexAggregateMethod, WorkingConditionItem workingConditionItem){
		
		// 「控除前のフレックス不足時間」を入れておく
		this.flexShortDeductTime.setFlexShortTimeBeforeDeduct(this.flexShortageTime);
		
		// 年休控除日数を時間換算する
		this.deductDaysAndTime.timeConversionOfDeductAnnualLeaveDays(require,
				companyId, employeeId, period, workingConditionItem);
		if (this.deductDaysAndTime.getErrorInfos().size() > 0){
			this.errorInfos.addAll(this.deductDaysAndTime.getErrorInfos());
			return;
		}
		
		// 年休控除日数に値が入っているか確認する
		if (this.flexShortDeductTime.getAnnualLeaveDeductDays().lessThanOrEqualTo(0.0)) return;
		
		// 控除前の年休控除時間を保存する
		this.annualLeaveTimeBeforeDeduct = this.deductDaysAndTime.getAnnualLeaveDeductTime();
		
		// 「フレックス繰越不足時間」を年休控除する
		val flexCarryforwardShortTime = this.flexCarryforwardTime.getFlexCarryforwardShortageTime();
		if (flexCarryforwardShortTime.greaterThan(0)){
			AttendanceTimeMonth subtractTime = this.deductDaysAndTime.getAnnualLeaveDeductTime();
			if (subtractTime.greaterThan(flexCarryforwardShortTime.v())){
				subtractTime = flexCarryforwardShortTime;
			}
			// フレックス繰越不足時間から年休控除時間を引く
			this.flexCarryforwardTime.setFlexCarryforwardShortageTime(
					flexCarryforwardShortTime.minusMinutes(subtractTime.v()));
			// 引いた分を年休控除時間から引く
			this.deductDaysAndTime.minusMinutesToAnnualLeaveDeductTime(subtractTime.v());
			// 引いた分をフレックス繰越勤務時間に足す
			this.flexCarryforwardTime.setFlexCarryforwardWorkTime(new AttendanceTimeMonth(
					this.flexCarryforwardTime.getFlexCarryforwardWorkTime().v() + subtractTime.v()));
		}
		
		// 年休控除時間が残っているか確認する
		if (this.deductDaysAndTime.getAnnualLeaveDeductTime().lessThanOrEqualTo(0)) return;
		
		// フレックス不足時間を年休控除する
		if (this.flexShortageTime.greaterThan(0)){
			AttendanceTimeMonth subtractTime = this.deductDaysAndTime.getAnnualLeaveDeductTime();
			if (subtractTime.greaterThan(this.flexShortageTime.v())){
				subtractTime = this.flexShortageTime;
			}
			// フレックス不足時間から年休控除時間を引く
			this.flexShortageTime = this.flexShortageTime.minusMinutes(subtractTime.v());
			// 引いた分を年休控除時間から引く
			this.deductDaysAndTime.minusMinutesToAnnualLeaveDeductTime(subtractTime.v());
			// 引いた分をフレックス時間に足す
			int flexMinutes = this.flexTime.getFlexTime().getFlexTime().getTime().v();
			if (flexMinutes < 0){
				int addMinutes = subtractTime.v();
				if (addMinutes > -flexMinutes) addMinutes = -flexMinutes;
				this.flexTime.getFlexTime().addFlexTime(new AttendanceTimeMonthWithMinus(addMinutes));
			}
		}
		
		// 年休控除時間が残っているか確認する
		if (this.deductDaysAndTime.getAnnualLeaveDeductTime().lessThanOrEqualTo(0)) return;
		
		// 法定内・法定外フレックス時間に加算する
		{
			// 「フレックス集計方法」を確認すす
			if (flexAggregateMethod == FlexAggregateMethod.PRINCIPLE){
				val aggrSet = this.flexAggrSet.getLegalAggrSet().getAggregateSet();
				if (aggrSet == AggregateSetting.INCLUDE_ALL_OUTSIDE_TIME_IN_FLEX_TIME){
					// 年休控除時間を法定外フレックス時間に加算する
					this.flexTime.getFlexTime().addFlexIllegalTime(this.deductDaysAndTime.getAnnualLeaveDeductTime());
				}
				if (aggrSet == AggregateSetting.MANAGE_DETAIL){
					// 年休控除時間を法定内フレックス時間に加算する
					this.flexTime.getFlexTime().addFlexLegalTime(this.deductDaysAndTime.getAnnualLeaveDeductTime());
				}
			}
			// 年休控除時間をフレックス時間に加算する
			int addMinutes = this.deductDaysAndTime.getAnnualLeaveDeductTime().v();
			this.flexTime.getFlexTime().addFlexTime(new AttendanceTimeMonthWithMinus(addMinutes));
		}
		
		int flexMinutes = this.flexTime.getFlexTime().getFlexTime().getTime().v();
		if (flexMinutes > 0){
			
			// フレックス時間をフレックス超過時間へ移送
			this.flexExcessTime = new AttendanceTimeMonth(flexMinutes);
		}
	}
	
	/**
	 * 欠勤控除する
	 */
	private void deductAbsence(){
		
		// フレックス繰越不足時間を欠勤控除する
		val flexCarryforwardShortTime = this.flexCarryforwardTime.getFlexCarryforwardShortageTime();
		if (flexCarryforwardShortTime.greaterThan(0)){
			AttendanceTimeMonth subtractTime = this.deductDaysAndTime.getAbsenceDeductTime();
			if (subtractTime.greaterThan(flexCarryforwardShortTime.v())){
				subtractTime = flexCarryforwardShortTime;
			}
			// フレックス繰越不足時間から欠勤控除時間を引く
			this.flexCarryforwardTime.setFlexCarryforwardShortageTime(
					flexCarryforwardShortTime.minusMinutes(subtractTime.v()));
			// 引いた分を欠勤控除時間から引く
			this.deductDaysAndTime.minusMinutesToAbsenceDeductTime(subtractTime.v());
		}
		
		// 欠勤控除時間が残っているか確認する
		if (this.deductDaysAndTime.getAbsenceDeductTime().lessThanOrEqualTo(0)) return;
		
		// フレックス不足時間を欠勤控除する
		if (this.flexShortageTime.greaterThan(0)){
			AttendanceTimeMonth subtractTime = this.deductDaysAndTime.getAbsenceDeductTime();
			if (subtractTime.greaterThan(this.flexShortageTime.v())){
				subtractTime = this.flexShortageTime;
			}
			// フレックス不足時間から欠勤控除時間を引く
			this.flexShortageTime = this.flexShortageTime.minusMinutes(subtractTime.v());
			// 引いた分を欠勤控除時間から引く
			this.deductDaysAndTime.minusMinutesToAbsenceDeductTime(subtractTime.v());
			// 引いた分をフレックス時間に足す
			int flexMinutes = this.flexTime.getFlexTime().getFlexTime().getTime().v();
			if (flexMinutes < 0){
				int addMinutes = subtractTime.v();
				if (addMinutes > -flexMinutes) addMinutes = -flexMinutes;
				this.flexTime.getFlexTime().addFlexTime(new AttendanceTimeMonthWithMinus(addMinutes));
			}
		}
	}
	
	/**
	 * フレックス補填のエラーチェック
	 * @param settingsByFlex フレックス勤務が必要とする設定
	 */
	private void checkErrorForInsufficientFlex(SettingRequiredByFlex settingsByFlex){
		
		// フレックス不足の年休補填管理を取得
		val insufficientFlexOpt = settingsByFlex.getInsufficientFlexOpt();
		if (insufficientFlexOpt.isPresent()){
			val insufficientFlex = insufficientFlexOpt.get();
			
			// 年休補填時間のエラーチェック
			val deductDays = new AttendanceDaysMonth(this.flexShortDeductTime.getAnnualLeaveDeductDays().v());
			if (insufficientFlex.checkErrorForSupplementableDays(deductDays)){
				
				// 社員の月別実績のエラーを作成する
				if (!this.perErrors.contains(Flex.FLEX_YEAR_HOLIDAY_DEDUCTIBLE_DAYS)){
					this.perErrors.add(Flex.FLEX_YEAR_HOLIDAY_DEDUCTIBLE_DAYS);
				}
			}
		}
		
		// フレックス不足時間のエラーチェック
		boolean shortageError = false;
		{
			// 社員のフレックス繰越上限時間を求める
			int limitTime = 15 * 60;		// 繰越上限時間
			{
				val flexShortageLimitOpt = settingsByFlex.getFlexShortageLimitOpt();
				if (flexShortageLimitOpt.isPresent()){
					limitTime = flexShortageLimitOpt.get().getLimitTime().v();
				}
				int possibleTime = settingsByFlex.getCanNextCarryforwardTimeMonth().v();	// 翌月繰越可能時間
				if (limitTime > possibleTime) limitTime = possibleTime;
			}
			
			// 「フレックス不足時間」と繰越上限時間を比較
			if (this.flexShortageTime.v() > limitTime) shortageError = true;
			
			if (shortageError){
				
				// 社員の月別実績のエラーを作成する
				if (!this.perErrors.contains(Flex.FLEX_EXCESS_CARRYOVER_TIME)){
					this.perErrors.add(Flex.FLEX_EXCESS_CARRYOVER_TIME);
				}
			}
		}
		
		// 前月繰越の補填未完了のエラーチェック
		val flexCarryforwardShortTime = this.flexCarryforwardTime.getFlexCarryforwardShortageTime();
		if (flexCarryforwardShortTime.v() > 0){
			
			// 社員の月別実績のエラーを作成する
			if (!this.perErrors.contains(Flex.FLEX_INCOMPLETE_OF_CARRYFORWARD)){
				this.perErrors.add(Flex.FLEX_INCOMPLETE_OF_CARRYFORWARD);
			}
		}
	}
	
	/**
	 * 総労働対象時間の取得
	 * @return 総労働対象時間
	 */
	public AttendanceTimeMonth getTotalWorkingTargetTime(FlexMonthWorkTimeAggrSet flexAggrSet){
		
		/** ○「集計方法」を確認する */
		if (this.flexAggrSet.getAggrMethod() == FlexAggregateMethod.FOR_CONVENIENCE) { /** 便宜上集計 */
			
			/** フレックス発生するか確認する　*/
			if (checkIsFlexTimeOccur(flexAggrSet)) {

				/** 日単位のフレックス超過時間を合計する - 日単位のフレックス不足時間を合計する */
				return this.flexTime.getPlusFlexTime().addMinutes(this.flexTime.getMinusFlexTime().valueAsMinutes());
			}
			
			return new AttendanceTimeMonth(0);
		}

		if (this.flexAggrSet.isMultiMonthSettlePeriod()) { 
			int flexTime = this.flexTime.getFlexTimeCurrentMonth().getFlexTime().getFlexTime().getTime().valueAsMinutes() 
							+ this.flexTime.getFlexTimeCurrentMonth().getExcessWeekAveTime().valueAsMinutes();
			return new AttendanceTimeMonth(flexTime);
		}
		
		/** 原則集計 */
		/** 総労働対象時間を計算する  */
		return new AttendanceTimeMonth(this.flexExcessTime.v() +
				this.flexCarryforwardTime.getFlexCarryforwardWorkTime().v());
	}
	
	/** フレックス時間の再計算 */
	public void recalcFlexTime() {
		
		/**　フレックス時間を計算する　*/
		val flexTime = this.flexExcessTime.valueAsMinutes() - this.flexShortageTime.valueAsMinutes();
		this.flexTime.getFlexTime().getFlexTime().setTime(new AttendanceTimeMonthWithMinus(flexTime));
	}
	
	/**
	 * 合算する
	 * @param target 加算対象
	 */
	public void sum(FlexTimeOfMonthly target){
		
		this.flexTime.sum(target.flexTime);
		this.flexExcessTime = this.flexExcessTime.addMinutes(target.flexExcessTime.v());
		this.flexShortageTime = this.flexShortageTime.addMinutes(target.flexShortageTime.v());
		this.flexCarryforwardTime.sum(target.flexCarryforwardTime);
		this.flexTimeOfExcessOutsideTime.sum(target.flexTimeOfExcessOutsideTime);
		this.flexShortDeductTime.sum(target.flexShortDeductTime);
		this.flexSettleTime = this.flexSettleTime.addMinutes(target.flexSettleTime.v());
	}
	
	public static interface RequireM8 extends WorkTimeAddtionTimeGetter.Require {}
	
	// 年休控除する
	public static interface RequireM1 extends DeductDaysAndTime.RequireM1 {}
	
	// 法定内として扱う時間を求める
	public static interface RequireM2 extends MonthlyStatutoryWorkingHours.RequireM1, RequireM3 {}
	
	public static interface RequireM3 extends ShortageFlexSetting.Require, AttendanceTimeOfMonthly.Require,
		WorkTimeAddtionTimeGetter.Require {}
	
	public static interface RequireM4 extends RequireM2, RequireM3, RequireM8,
		MonthlyStatutoryLaborDivisionService.Require, FlexLegalTimeGetter.Require {}
	
	public static interface RequireM5 extends RequireM1, RequireM4 {}
	
	public static interface RequireM6 extends AggregateTotalWorkingTime.RequireM1,
		ExcessOutsideWorkMng.RequireM1, ExcessOutsideWorkMng.RequireM7, WeeklyCalculation.Require {}
}
