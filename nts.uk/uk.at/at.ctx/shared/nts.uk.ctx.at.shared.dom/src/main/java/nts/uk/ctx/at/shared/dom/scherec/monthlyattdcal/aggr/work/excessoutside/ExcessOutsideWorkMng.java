package nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.work.excessoutside;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import lombok.Getter;
import lombok.val;
import nts.arc.layer.app.cache.CacheCarrier;
import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.arc.time.calendar.Year;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.shared.dom.WorkInformation;
import nts.uk.ctx.at.shared.dom.adapter.employee.EmployeeImport;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonth;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonthWithMinus;
import nts.uk.ctx.at.shared.dom.scherec.addsettingofworktime.WorkTimeAddtionTimeGetter;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.dailyattendancework.IntegrationOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.MonthlyAggregationErrorInfo;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.flex.FlexAggregateMethod;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.flex.FlexMonthWorkTimeAggrSet;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.flex.SettlePeriod;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.flex.ShortageFlexSetting;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.export.GetSettlementPeriodOfDefor;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.vtotalmethod.DefoAggregateMethodOfMonthly;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.vtotalmethod.FlexAggregateMethodOfMonthly;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.work.MonAggrCompanySettings;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.work.MonAggrEmployeeSettings;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.work.MonthlyCalculatingDailys;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.work.SettingRequiredByDefo;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.work.SettingRequiredByFlex;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.work.SettingRequiredByReg;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.work.premiumtarget.getvacationaddtime.AddSet;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.work.premiumtarget.getvacationaddtime.GetAddSet;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.work.premiumtarget.getvacationaddtime.PremiumAtr;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.work.timeseries.FlexTimeOfTimeSeries;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.AgreementTimeOfManagePeriod;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.AttendanceItemOfMonthly;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.AttendanceTimeOfMonthly;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.calc.AggregateMonthlyValue;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.calc.MonthlyAggregateAtr;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.calc.MonthlyCalculation;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.calc.actualworkingtime.RegularAndIrregularTimeOfMonthly;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.calc.flex.FlexLegalTimeGetter;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.calc.flex.FlexTime;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.calc.flex.FlexTimeOfMonthly;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.calc.totalworkingtime.AggregateTotalWorkingTime;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.excessoutside.ExcessOutsideWorkOfMonthly;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.outsideot.OutsideOTCalMed;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.outsideot.breakdown.OutsideOTBRDItem;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.outsideot.holiday.SuperHD60HConMed;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.outsideot.overtime.Overtime;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.algorithm.monthly.MonthlyFlexStatutoryLaborTime;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.algorithm.monthly.MonthlyStatutoryLaborDivisionService;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.algorithm.monthly.MonthlyStatutoryWorkingHours;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItem;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingSystem;
import nts.uk.ctx.at.shared.dom.workrule.closure.Closure;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureId;
import nts.uk.ctx.at.shared.dom.workrule.closure.service.ClosureService;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.time.calendar.date.ClosureDate;

/**
 * 時間外超過管理
 * @author shuichi_ishida
 */
@Getter
public class ExcessOutsideWorkMng {

	/** 月次明細 */
	private MonthlyDetail monthlyDetail;
	/** 時間外超過明細 */
	private ExcessOutsideWorkDetail excessOutsideWorkDetail;
	/** 時間外超過 */
	private ExcessOutsideWorkOfMonthly excessOutsideWork;
	
	/** 月別実績の月の計算 */
	private MonthlyCalculation monthlyCalculation;
	/** 会社ID */
	private final String companyId;
	/** 社員ID */
	private final String employeeId;
	/** 年月 */
	private final YearMonth yearMonth;
	/** 締めID */
	private final ClosureId closureId;
	/** 締め日付 */
	private final ClosureDate closureDate;
	/** 期間 */
	private final DatePeriod procPeriod;
	/** 労働条件項目 */
	private final WorkingConditionItem workingConditionItem;
	/** 労働制 */
	private final WorkingSystem workingSystem;
	/** 社員 */
	private EmployeeImport employee;
	/** 職場ID */
	private String workplaceId;
	/** 雇用コード */
	private String employmentCd;
	/** 退職月度がどうか */
	private boolean isRetireMonth;
	/** 締め */
	private Optional<Closure> closureOpt;
	
	/** 通常勤務が必要とする設定 */
	private SettingRequiredByReg settingsByReg;
	/** 変形労働勤務が必要とする設定 */
	private SettingRequiredByDefo settingsByDefo;
	/** フレックス勤務が必要とする設定 */
	private SettingRequiredByFlex settingsByFlex;
	/** 月別集計で必要な会社別設定 */
	private MonAggrCompanySettings companySets;
	/** 月別集計で必要な社員別設定 */
	private MonAggrEmployeeSettings employeeSets;

	/** 月の計算中の日別実績データ */
	private MonthlyCalculatingDailys monthlyCalculatingDailys;
	/** 実績の勤務情報リスト */
	private Map<GeneralDate, WorkInformation> workInfoOfRecordMap;
	
	/** 開始週NO */
	private int startWeekNo;
	/** 年度 */
	private Year year;
	/** 管理期間の36協定時間 */
	private AgreementTimeOfManagePeriod agreementTimeOfManagePeriod;
	
	/** 時間外超過累積時間 */
	private AttendanceTimeMonth totalExcessOutside;
	/** エラー情報 */
	private List<MonthlyAggregationErrorInfo> errorInfos;
	
	private AggregateMonthlyValue aggrValue;
	
	/**
	 * コンストラクタ
	 * @param companyId 会社ID
	 * @param employeeId 社員ID
	 * @param yearMonth 年月
	 * @param closureId 締めID
	 * @param closureDate 締め日
	 * @param procPeriod 期間
	 * @param workingSystem 労働制
	 * @param monthlyCalculation 月別実績の月の計算
	 */
	public ExcessOutsideWorkMng(MonthlyCalculation monthlyCalculation){
		
		this.monthlyDetail = new MonthlyDetail();
		this.excessOutsideWorkDetail = new ExcessOutsideWorkDetail();
		this.excessOutsideWork = new ExcessOutsideWorkOfMonthly();
		
		this.monthlyCalculation = monthlyCalculation;
		this.companyId = monthlyCalculation.getCompanyId();
		this.employeeId = monthlyCalculation.getEmployeeId();
		this.yearMonth = monthlyCalculation.getYearMonth();
		this.closureId = monthlyCalculation.getClosureId();
		this.closureDate = monthlyCalculation.getClosureDate();
		this.procPeriod = monthlyCalculation.getProcPeriod();
		this.workingConditionItem = monthlyCalculation.getWorkingConditionItem();
		this.workingSystem = monthlyCalculation.getWorkingSystem();
		this.employee = monthlyCalculation.getEmployee();
		this.workplaceId = monthlyCalculation.getWorkplaceId();
		this.employmentCd = monthlyCalculation.getEmploymentCd();
		this.isRetireMonth = monthlyCalculation.isRetireMonth();
		this.closureOpt = monthlyCalculation.getClosureOpt();
		
		this.settingsByReg = monthlyCalculation.getSettingsByReg();
		this.settingsByDefo = monthlyCalculation.getSettingsByDefo();
		this.settingsByFlex = monthlyCalculation.getSettingsByFlex();
		this.companySets = monthlyCalculation.getCompanySets();
		this.employeeSets = monthlyCalculation.getEmployeeSets();
		
		this.monthlyCalculatingDailys = monthlyCalculation.getMonthlyCalculatingDailys();
		this.workInfoOfRecordMap = monthlyCalculation.getWorkInfoOfRecordMap();
		
		this.startWeekNo = monthlyCalculation.getStartWeekNo();
		this.year = monthlyCalculation.getYear();
		this.agreementTimeOfManagePeriod = new AgreementTimeOfManagePeriod(this.employeeId, this.yearMonth);
		
		this.totalExcessOutside = new AttendanceTimeMonth(0);
		this.errorInfos = new ArrayList<>();
		this.aggrValue = null;
	}
	
	/**
	 * 集計
	 * 時間外超過
	 */
	public void aggregate(RequireM5 require, CacheCarrier cacheCarrier){
		// 労働制を確認する
		if (this.workingSystem == WorkingSystem.REGULAR_WORK || this.workingSystem == WorkingSystem.VARIABLE_WORKING_TIME_WORK){
			
			// 通常・変形労働時間勤務の時間外超過を集計する
			this.aggregateExcessOutsideWork(require, cacheCarrier, null);
		} else if (this.workingSystem == WorkingSystem.FLEX_TIME_WORK){
			
			// フレックス集計方法を確認する
			val flexAggregateMethod = this.settingsByFlex.getFlexAggrSet().getAggrMethod();
			if (flexAggregateMethod == FlexAggregateMethod.PRINCIPLE){
				
				// 原則集計で時間外超過を集計する
				this.aggregateExcessOutsideWork(require, cacheCarrier, FlexAggregateMethod.PRINCIPLE);
			} else if (flexAggregateMethod == FlexAggregateMethod.FOR_CONVENIENCE){
				
				// 便宜上集計で時間外超過を集計する
				this.aggregateExcessOutsideWork(require, cacheCarrier, FlexAggregateMethod.FOR_CONVENIENCE);
			}
		}
		
		/** 超有休の付与時間を計算 */
		val superHD60HConMed = require.superHD60HConMed(companyId);
		superHD60HConMed.ifPresent(c -> {
			c.calcTimeSuperHD(require, excessOutsideWork);
		});
		
		/**　週別実績の時間外超過を更新する　*/
		if(this.aggrValue != null) {
			
			this.monthlyCalculation.getAttendanceTimeWeeks().stream().forEach(w -> {
				
				this.aggrValue.getAttendanceTimeWeeks().stream().filter(wg -> w.getWeekNo() == wg.getWeekNo())
					.findFirst().ifPresent(wg -> {
						w.setExcessOutside(wg.getExcessOutside());
					});
			});
		}
	}
	
	/**
	 * 時間外超過を集計する
	 * @param flexAggregateMethod フレックス集計方法
	 */
	private void aggregateExcessOutsideWork(RequireM5 require, CacheCarrier cacheCarrier,
			FlexAggregateMethod flexAggregateMethod){
		
		// 時間外超過設定を確認する
		OutsideOTCalMed calcMethod = this.companySets.getOutsideOverTimeSet().getCalculationMethod();
		if (calcMethod == OutsideOTCalMed.DECISION_AFTER){
			
			// 集計後に求める
			this.askAfterAggregate(require, cacheCarrier, flexAggregateMethod);
		}
		if (calcMethod == OutsideOTCalMed.TIME_SERIES){
			
			// 時系列で求める
			this.askbyTimeSeries(require, cacheCarrier, flexAggregateMethod);
		}
	}
	
	/**
	 * 集計後に求める
	 * @param flexAggregateMethod フレックス集計方法
	 */
	private void askAfterAggregate(RequireM5 require, CacheCarrier cacheCarrier, FlexAggregateMethod flexAggregateMethod){
		
		// 集計結果　初期化
		val regAndIrgTime = new RegularAndIrregularTimeOfMonthly();
		val flexTime = new FlexTimeOfMonthly();
		val aggregateTotalWorkingTime = new AggregateTotalWorkingTime();
		aggregateTotalWorkingTime.copySharedItem(this.monthlyCalculation.getAggregateTime());
		
		// 労働制を確認する
		if (workingSystem == WorkingSystem.REGULAR_WORK || workingSystem == WorkingSystem.VARIABLE_WORKING_TIME_WORK){
			
			// 通常・変形労働時間勤務の月別実績を集計する
			this.aggrValue = regAndIrgTime.aggregateMonthly(require,
					this.companyId, this.employeeId, this.yearMonth, this.closureId, this.closureDate,
					this.procPeriod, this.workingSystem, this.closureOpt,
					MonthlyAggregateAtr.EXCESS_OUTSIDE_WORK,
					this.employmentCd, this.settingsByReg, this.settingsByDefo,
					aggregateTotalWorkingTime, this, this.startWeekNo,
					this.companySets, this.employeeSets, this.monthlyCalculatingDailys);
			
			// 通常・変形労働時間勤務の月単位の時間を集計する
			regAndIrgTime.aggregateMonthlyHours(require, cacheCarrier,
					this.companyId, this.employeeId, this.yearMonth, this.closureId, this.closureDate,
					this.procPeriod, this.workingSystem, MonthlyAggregateAtr.EXCESS_OUTSIDE_WORK,
					this.isRetireMonth, this.workplaceId, this.employmentCd, this.settingsByReg, 
					this.settingsByDefo, this.aggrValue.getAggregateTotalWorkingTime(), this.monthlyCalculatingDailys, 
					this.monthlyCalculation.getStatutoryWorkingTime());
		}
		if (workingSystem == WorkingSystem.FLEX_TIME_WORK){
			
			// フレックス勤務の月別実績を集計する
			this.aggrValue = flexTime.aggregateMonthly(require, cacheCarrier, 
					this.companyId, this.employeeId, this.yearMonth, this.closureId, this.closureDate,
					this.procPeriod, this.workingSystem, MonthlyAggregateAtr.EXCESS_OUTSIDE_WORK,
					this.closureOpt, flexAggregateMethod, this.settingsByFlex,
					aggregateTotalWorkingTime, this, this.startWeekNo,
					this.companySets, this.employeeSets, this.monthlyCalculatingDailys);
			
			// フレックス勤務の月単位の時間を集計する
			flexTime.aggregateMonthlyHours(require, cacheCarrier,
					this.companyId, this.employeeId, this.yearMonth, this.closureId, this.closureDate,
					this.procPeriod, MonthlyAggregateAtr.EXCESS_OUTSIDE_WORK, flexAggregateMethod,
					this.workingConditionItem, this.employmentCd, this.companySets,
					this.employeeSets, this.settingsByFlex, this.aggrValue.getAggregateTotalWorkingTime(),
					this.monthlyCalculatingDailys);
			
			// 時間外超過のフレックス時間を反映する
			this.monthlyCalculation.getFlexTime().setFlexTimeOfExcessOutsideTime(
					flexTime.getFlexTimeOfExcessOutsideTime());
		}
		
		if (this.aggrValue != null){
			
			// 実働時間の集計
			this.aggrValue.getAggregateTotalWorkingTime().aggregateActualWorkingTime(
					this.procPeriod, this.workingSystem, regAndIrgTime, flexTime);
			
			// 時間外超過明細．丸め後合計時間に移送する
			this.excessOutsideWorkDetail.setTotalTimeAfterRound(
					this.aggrValue.getAggregateTotalWorkingTime(), regAndIrgTime, flexTime,
					this.settingsByFlex.getFlexAggrSet(), this.companySets.getOutsideOTBDItems(),
					this.companySets.getRoundingSet(), isMultiMonthMode(this.monthlyCalculation));
			
			// 月別実績の時間外超過に移送する
			this.excessOutsideWork.setFromAggregateTime(
					this.excessOutsideWorkDetail.getTotalTimeAfterRound(), regAndIrgTime.getIrregularWorkingTime());
			
			// 時間外超過内訳に割り当てる　（集計後）
			this.assignExcessOutsideWorkBreakdownForAfterAggregate();
		}
	}
	
	private boolean isMultiMonthMode(MonthlyCalculation monthlyCalculation) {
		
		if (monthlyCalculation.getWorkingSystem() == WorkingSystem.FLEX_TIME_WORK) {
			
			return monthlyCalculation.getSettingsByFlex().getFlexAggrSet().isMultiMonthSettlePeriod();
			
		} else if (monthlyCalculation.getWorkingSystem() == WorkingSystem.VARIABLE_WORKING_TIME_WORK) {
			
			return monthlyCalculation.getSettingsByDefo().getDeforAggrSet().isMultiMonthSettlePeriod(monthlyCalculation.getYearMonth());
		}
		
		return false;
	}
	
	/**
	 * 時系列で求める
	 * @param flexAggregateMethod フレックス集計方法
	 */
	private void askbyTimeSeries(RequireM5 require, CacheCarrier cacheCarrier, FlexAggregateMethod flexAggregateMethod){
		
		// 集計結果　初期化
		val regAndIrgTime = new RegularAndIrregularTimeOfMonthly();
		val flexTime = new FlexTimeOfMonthly();
		val aggregateTotalWorkingTime = new AggregateTotalWorkingTime();
		aggregateTotalWorkingTime.copySharedItem(this.monthlyCalculation.getAggregateTime());
		
		// 労働制を確認する
		if (this.workingSystem == WorkingSystem.REGULAR_WORK ||
			this.workingSystem == WorkingSystem.VARIABLE_WORKING_TIME_WORK){
			
			// 通常・変形労働時間勤務の月別実績を集計する
			this.aggrValue = regAndIrgTime.aggregateMonthly(require,
					this.companyId, this.employeeId, this.yearMonth, this.closureId, this.closureDate,
					this.procPeriod, this.workingSystem, this.closureOpt,
					MonthlyAggregateAtr.EXCESS_OUTSIDE_WORK,
					this.employmentCd, this.settingsByReg, this.settingsByDefo,
					aggregateTotalWorkingTime, this, this.startWeekNo,
					this.companySets, this.employeeSets, this.monthlyCalculatingDailys);
			
			// 通常・変形労働時間勤務の月単位の時間を集計する
			regAndIrgTime.aggregateMonthlyHours(require, cacheCarrier,
					this.companyId, this.employeeId, this.yearMonth, this.closureId, this.closureDate,
					this.procPeriod, this.workingSystem, MonthlyAggregateAtr.EXCESS_OUTSIDE_WORK,
					this.isRetireMonth, this.workplaceId, this.employmentCd, this.settingsByReg, 
					this.settingsByDefo, this.aggrValue.getAggregateTotalWorkingTime(), this.monthlyCalculatingDailys, 
					this.monthlyCalculation.getStatutoryWorkingTime());
			
			// 月次明細に計算結果をコピーする
			this.monthlyDetail.setFromAggregateTotalWorkingTime(this.aggrValue.getAggregateTotalWorkingTime());
			
			// 通常・変形労働勤務の逆時系列割り当て
			this.assignReverseTimeSeriesOfRegAndIrg(require, cacheCarrier,
					regAndIrgTime, this.aggrValue.getAggregateTotalWorkingTime());
		}
		if (this.workingSystem == WorkingSystem.FLEX_TIME_WORK){
			
			// フレックス勤務の月別実績を集計する
			this.aggrValue = flexTime.aggregateMonthly(require, cacheCarrier,
					this.companyId, this.employeeId, this.yearMonth, this.closureId, this.closureDate,
					this.procPeriod, this.workingSystem, MonthlyAggregateAtr.EXCESS_OUTSIDE_WORK,
					this.closureOpt, flexAggregateMethod, this.settingsByFlex,
					aggregateTotalWorkingTime, this, this.startWeekNo,
					this.companySets, this.employeeSets, this.monthlyCalculatingDailys);
			
			// フレックス勤務の月単位の時間を集計する
			flexTime.aggregateMonthlyHours(require, cacheCarrier,
					this.companyId, this.employeeId, this.yearMonth, this.closureId, this.closureDate,
					this.procPeriod, MonthlyAggregateAtr.EXCESS_OUTSIDE_WORK, flexAggregateMethod,
					this.workingConditionItem, this.employmentCd, this.companySets,
					this.employeeSets, this.settingsByFlex, this.aggrValue.getAggregateTotalWorkingTime(),
					this.monthlyCalculatingDailys);
			
			// 時間外超過のフレックス時間を反映する
			this.monthlyCalculation.getFlexTime().setFlexTimeOfExcessOutsideTime(
					flexTime.getFlexTimeOfExcessOutsideTime());
			
			// 月次明細に計算結果をコピーする
			this.monthlyDetail.setFromAggregateTotalWorkingTime(this.aggrValue.getAggregateTotalWorkingTime());
			
			// フレックス時間勤務の逆時系列割り当て
			if (this.settingsByFlex.getFlexAggrSet().getInsufficSet().getSettlePeriod() == SettlePeriod.SINGLE_MONTH){
				if (flexAggregateMethod == FlexAggregateMethod.FOR_CONVENIENCE){
					// 単月、便宜上集計の時
					// 便宜上集計の逆時系列割り当て
					this.assignReverseTimeSeriesForConvenience(flexTime);
				}
			}
		}
		
		if (this.aggrValue != null){
		
			// 時間外超過明細の作成
			this.excessOutsideWorkDetail.assignRoundTime(this.monthlyDetail, this.procPeriod, 
														this.companySets.getRoundingSet(), this.workingSystem);
			
			// 時間外超過内訳に割り当てる
			this.assignExcessOutsideWorkBreakdownForTimeSeries();
		}
	}
	
	/**
	 * 週割増時間を逆時系列で割り当てる
	 * @param weekPermiumProcPeriod 週割増処理期間
	 * @param weekPremiumTime 週単位の週割増時間
	 * @param aggregateTotalWorkingTime 集計総労働時間
	 */
	public void assignWeeklyPremiumTimeByReverseTimeSeries(RequireM4 require, DatePeriod weekPermiumProcPeriod, 
			AttendanceTimeMonth weekPremiumTime, AggregateTotalWorkingTime aggregateTotalWorkingTime, String cid, 
			String sid, MonthlyAggregateAtr aggregateAtr, WorkingSystem workingSystem, MonthlyCalculatingDailys monthlyCalcDailys) {

		// 「週割増・月割増を求める」を取得する
		boolean isAskPremium = false;
		if (this.workingSystem == WorkingSystem.REGULAR_WORK){
			isAskPremium = this.settingsByReg.getRegularAggrSet().getExcessOutsideTimeSet().isSurchargeWeekMonth();
		}
		if (this.workingSystem == WorkingSystem.VARIABLE_WORKING_TIME_WORK){
			isAskPremium = this.settingsByDefo.getDeforAggrSet().getExcessOutsideTimeSet().isSurchargeWeekMonth();
		}
		if (!isAskPremium) return;
		
		// 「時間外超過設定」を確認する　（計算方法が「時系列」以外なら割り当てない）
		if (this.companySets.getOutsideOverTimeSet().getCalculationMethod() != OutsideOTCalMed.TIME_SERIES) return;
		
		/** ○内訳項目一覧に週増合計時間が設定されているか確認する */
		boolean isHaveWeekPremiumItem = this.companySets.getOutsideOverTimeSet().getBreakdownItems().stream().map(c -> c.getAttendanceItemIds())
				.flatMap(List::stream).anyMatch(c -> c == AttendanceItemOfMonthly.WEEKLY_TOTAL_PREMIUM_TIME.value);
		if (!isHaveWeekPremiumItem) return;
		
		// 「週単位の週割増時間」を「逆時系列割り当て用の週割増時間」にコピーする
		AttendanceTimeMonthWithMinus weeklyPTForAssign = new AttendanceTimeMonthWithMinus(weekPremiumTime.v());
		
		// 「週単位の週割増時間」を「月別実績の時間外超過」に加算する
		this.excessOutsideWork.addMinutesToWeeklyTotalPremiumTime(weekPremiumTime.v());
		
		// 「期間．終了日」を処理日にする
		GeneralDate procDate = weekPermiumProcPeriod.end();
		while (procDate.afterOrEquals(weekPermiumProcPeriod.start())){
			
			// 週割増時間を日単位で割り当てる
			weeklyPTForAssign = this.monthlyDetail.assignWeeklyPremiumTimeByDayUnit(require, procDate, weeklyPTForAssign,
					aggregateTotalWorkingTime, this.workInfoOfRecordMap, this, cid, sid, aggregateAtr,
					workingSystem, monthlyCalcDailys);
			
			// 時間外超過明細の更新
			this.excessOutsideWorkDetail = this.monthlyDetail.getExcessOutsideWorkMng().getExcessOutsideWorkDetail();
			
			if (weeklyPTForAssign.lessThanOrEqualTo(0)) break;
			
			// 処理日を更新する
			procDate = procDate.addDays(-1);
		}
	}
	
	/**
	 * フレックス超過時間を割り当てる
	 * @param require Require
	 * @param cacheCarrier Cache
	 * @param datePeriod 期間
	 * @param flexAggregateMethod フレックス集計方法
	 * @param procDate 処理日
	 * @param flexAggrSet フレックス時間勤務の月の集計設定
	 * @param aggregateTotalWorkingTime 集計総労働時間
	 * @param flexTime フレックス時間
	 * @param setFlex フレックス勤務が必要とする設定
	 * @param addSet 加算設定
	 * @param standFlexTime 基準フレックス時間
	 */
	public void assignFlexExcessTime(RequireM7 require, CacheCarrier cacheCarrier, String cid, 
			DatePeriod datePeriod, FlexAggregateMethod flexAggregateMethod, GeneralDate procDate, 
			FlexMonthWorkTimeAggrSet flexAggrSet, AggregateTotalWorkingTime aggregateTotalWorkingTime, 
			FlexTime flexTime, SettingRequiredByFlex setFlex, AddSet addSet, StandardFlexTime standFlexTime, MonthlyCalculatingDailys monthlyCalcDailys) {
		
		// 「不足設定．清算期間」を確認する
		if (flexAggrSet.getInsufficSet().getSettlePeriod() == SettlePeriod.MULTI_MONTHS){
			// フレックス超過時間を割り当てる（複数月）
			this.assignFlexExcessTimeForMulti(require, datePeriod, procDate, aggregateTotalWorkingTime, 
					flexTime, addSet, standFlexTime, monthlyCalcDailys);
			return;
		}
		// 「フレックス集計方法」を確認する
		if (flexAggregateMethod == FlexAggregateMethod.FOR_CONVENIENCE) return;
		// フレックス超過対象時間を求める
		val targetFlexExcessTime = this.askTargetFlexExcessTime(require, 
				new DatePeriod(datePeriod.start(), procDate), aggregateTotalWorkingTime, flexTime, addSet, monthlyCalcDailys);

		// 法定労働時間を取得する（フレックス用）
		MonthlyFlexStatutoryLaborTime monStatTime = FlexLegalTimeGetter.getFlexStatutoryLaborTime(
				require, cacheCarrier, this.companySets, this.employeeSets, this.settingsByFlex,
				true, this.yearMonth, cid, this.employmentCd, this.employeeId,
				datePeriod.end(), Optional.of(datePeriod), this.closureId, this.closureDate,
				Optional.of(aggregateTotalWorkingTime), monthlyCalcDailys);
		// 法定内フレックス時間を含めるか判断する
		AttendanceTimeMonthWithMinus excessTimeUntilDay = new AttendanceTimeMonthWithMinus(0);
		if (ExcessOutsideWorkMng.isIncludeLegalFlexTime(this.companySets.getOutsideOTBDItems())){
			// 法定内フレックスを含んで当日までの超過時間を求める
			excessTimeUntilDay = this.askExcessTimeUntilDayIncludeLegalFlex(targetFlexExcessTime, monStatTime);
		} else {
			// 法定外フレックスのみで当日までの超過時間を求める
			excessTimeUntilDay = this.askExcessTimeUntilDayOnlyillegalFlex(targetFlexExcessTime, monStatTime);
		}
		// 前日までの超過時間を求める
		AttendanceTimeMonthWithMinus excessTimeUntilPrevDay = new AttendanceTimeMonthWithMinus(0);
		DatePeriod periodUntilPrevDay = new DatePeriod(datePeriod.start(), procDate.addDays(-1)); 
		if (!periodUntilPrevDay.isReversed()){
			excessTimeUntilPrevDay = this.excessOutsideWorkDetail.getTotalFlexExcessTime(periodUntilPrevDay);
		}
		// 当日の超過時間を計算する
		val excessTimeOfDay = excessTimeUntilDay.minusMinutes(excessTimeUntilPrevDay.v());
		if (excessTimeOfDay.greaterThan(0)){
			// 当日の超過時間＞0 の時、当日の超過時間を時間外超過明細に加算する
			val flexExcessTime = this.excessOutsideWorkDetail.getFlexExcessTime();
			flexExcessTime.putIfAbsent(procDate, new FlexTimeOfTimeSeries(procDate));
			val targetTimeSeries = flexExcessTime.get(procDate);
			targetTimeSeries.addMinutesToFlexTimeInFlexTime(excessTimeOfDay.v());
		}
	}

	/**
	 * フレックス超過時間を割り当てる（複数月）
	 * @param datePeriod 期間
	 * @param procDate 処理日
	 * @param aggregateTotalWorkingTime 集計総労働時間
	 * @param flexTime フレックス時間
	 * @param addSet 加算設定
	 * @param standFlexTime 基準フレックス時間
	 */
	public void assignFlexExcessTimeForMulti(RequireM8 require, DatePeriod datePeriod, GeneralDate procDate,
			AggregateTotalWorkingTime aggregateTotalWorkingTime, FlexTime flexTime,
			AddSet addSet, StandardFlexTime standFlexTime, MonthlyCalculatingDailys monthlyCalcDailys) {
		
		// 前日までのフレックス超過対象時間を求める　→　前日累計対象時間
		AttendanceTimeMonthWithMinus prevSumTargetTime = this.askTargetFlexExcessTime(require, 
				new DatePeriod(datePeriod.start(), procDate.addDays(-1)), aggregateTotalWorkingTime, 
				flexTime, addSet, monthlyCalcDailys);
		
		// 当日分のフレックス超過対象時間を求める　→　当日対象時間
		AttendanceTimeMonthWithMinus currTargetTime = this.askTargetFlexExcessTime(require, 
				new DatePeriod(procDate, procDate), aggregateTotalWorkingTime, flexTime, addSet, monthlyCalcDailys);
		
		// 「当日対象時間」を確認する
		if (currTargetTime.greaterThan(0)){
			
			// 週平均超過時間を割り当てる
			currTargetTime = this.assignExcessWeekAveTime(procDate, standFlexTime, prevSumTargetTime, currTargetTime);
			
			// 清算時間を割り当てる
			this.assignSettleTime(procDate, standFlexTime, prevSumTargetTime, currTargetTime);
		}
	}
	
	/**
	 * 週平均超過時間を割り当てる
	 * @param procDate 処理日
	 * @param standFlexTime 基準フレックス時間
	 * @param prevSumTargetTime 前日累計対象時間
	 * @param currTargetTime 当日対象時間
	 * @return 当日対象時間
	 */
	public AttendanceTimeMonthWithMinus assignExcessWeekAveTime(
			GeneralDate procDate,
			StandardFlexTime standFlexTime,
			AttendanceTimeMonthWithMinus prevSumTargetTime,
			AttendanceTimeMonthWithMinus currTargetTime){

		int result = currTargetTime.v();	// 当日対象時間（分）
		
		// 「当日累計対象時間」を求める
		int currSumTargetMinutes = prevSumTargetTime.v() + currTargetTime.v();
		
		// 「当日累計週超過時間」を求める
		int currExcessSumWeekAveMinutes = currSumTargetMinutes - standFlexTime.getCurrWeekAveTime().v();
		if (currExcessSumWeekAveMinutes < 0) currExcessSumWeekAveMinutes = 0;
		if (currExcessSumWeekAveMinutes > 0){
		
			// 当日の「時間外超過明細．フレックス超過明細」の取得
			val flexExcessTime = this.excessOutsideWorkDetail.getFlexExcessTime();
			flexExcessTime.putIfAbsent(procDate, new FlexTimeOfTimeSeries(procDate));
			val targetTimeSeries = flexExcessTime.get(procDate);
			
			// 「当日対象時間」と「当日累計週超過時間」を比較する
			if (result > currExcessSumWeekAveMinutes){
				
				// 「当日累計週超過時間」を時間外超過明細に加算する
				targetTimeSeries.addMinutesToFlexTimeInFlexTime(currExcessSumWeekAveMinutes);
				
				// 「当日対象時間」から「当日累計週超過時間」を引く
				result -= currExcessSumWeekAveMinutes;
			}
			else{
				
				// 「当日対象時間」を時間外超過明細に加算する
				targetTimeSeries.addMinutesToFlexTimeInFlexTime(result);
				
				// 「当日対象時間」を０にする
				result = 0;
			}
		}
		
		// 「当日対象時間」を返す
		return new AttendanceTimeMonthWithMinus(result);
	}
	
	/**
	 * 清算時間を割り当てる
	 * @param procDate 処理日
	 * @param standFlexTime 基準フレックス時間
	 * @param prevSumTargetTime 前日累計対象時間
	 * @param currTargetTime 当日対象時間
	 * @return 当日対象時間
	 */
	public void assignSettleTime(GeneralDate procDate, StandardFlexTime standFlexTime,
			AttendanceTimeMonthWithMinus prevSumTargetTime, AttendanceTimeMonthWithMinus currTargetTime) {

		int currTargetMinutes = currTargetTime.v();		// 当日対象時間（分）
		
		// 法定内フレックス時間を含めるか判断する
		int standMinutes = 0;
		if (ExcessOutsideWorkMng.isIncludeLegalFlexTime(this.companySets.getOutsideOTBDItems())){
			
			// 「基準時間」に「清算基準時間」を入れる
			standMinutes = standFlexTime.getSettleStandTime().v();
		} else {
			
			// 「基準時間」に「清算法定時間」を入れる
			standMinutes = standFlexTime.getSettleStatTime().v();
		}
		
		// 「当日清算対象時間」を求める　（前日累計対象時間＋当日対象時間）
		int currSettleTargetMinutes = prevSumTargetTime.v() + currTargetMinutes;
		
		// 「当日累計清算時間」を求める　（当日清算対象時間－基準時間）
		int currSumSettleMinutes = currSettleTargetMinutes - standMinutes;
		if (currSumSettleMinutes < 0) currSumSettleMinutes = 0;
		
		// 当日の「時間外超過明細．フレックス超過明細」の取得
		val flexExcessTime = this.excessOutsideWorkDetail.getFlexExcessTime();
		flexExcessTime.putIfAbsent(procDate, new FlexTimeOfTimeSeries(procDate));
		val targetTimeSeries = flexExcessTime.get(procDate);
		
		// 「当日対象時間」と「当日累計清算時間」を比較する
		if (currTargetMinutes >= currSumSettleMinutes){
			
			// 「当日累計清算時間」を時間外超過明細に加算する
			targetTimeSeries.addMinutesToFlexTimeInFlexTime(currSumSettleMinutes);
		}
		else{
			
			// 「当日対象時間」を時間外超過明細に加算する
			targetTimeSeries.addMinutesToFlexTimeInFlexTime(currTargetMinutes);
		}
	}
	
	/**
	 * 法定内フレックス時間を含めるか判断する
	 * @return true:含める（法定内フレックスを含む）、false:含めない（法定外フレックスのみ）
	 */
	public static boolean isIncludeLegalFlexTime(List<OutsideOTBRDItem> outsideOTBDItems){

		// 内訳項目一覧を取得
		boolean isExistLegalFlex = false;
		boolean isExistIllegalFlex = false;
		for (val outsideOTBDItem : outsideOTBDItems){
			for (val attendanceItemId : outsideOTBDItem.getAttendanceItemIds()){
				if (attendanceItemId == AttendanceItemOfMonthly.FLEX_LEGAL_TIME.value) isExistLegalFlex = true;
				if (attendanceItemId == AttendanceItemOfMonthly.FLEX_ILLEGAL_TIME.value) isExistIllegalFlex = true;
			}
		}
		
		// 法定内フレックス時間を含めるかどうか判断
		if (isExistIllegalFlex == true && isExistLegalFlex == false) return false;
		return true;
	}
	
	/**
	 * フレックス超過対象時間を求める
	 * @param datePeriod 期間
	 * @param aggregateTotalWorkingTime 集計総労働時間
	 * @param flexTime フレックス時間
	 * @param addSet 加算設定
	 * @return フレックス超過対象時間
	 */
	private AttendanceTimeMonthWithMinus askTargetFlexExcessTime(RequireM8 require, 
			DatePeriod datePeriod, AggregateTotalWorkingTime aggregateTotalWorkingTime,
			FlexTime flexTime, AddSet addSet, MonthlyCalculatingDailys monthlyCalcDailys) {
		
		AttendanceTimeMonthWithMinus targetFlexExcessTime = new AttendanceTimeMonthWithMinus(0);

		// 「月別実績の就業時間」を取得する
		val workTime = aggregateTotalWorkingTime.getWorkTime();
		
		// 集計対象時間を集計する
		AttendanceTimeMonthWithMinus totalWorkTime =
				new AttendanceTimeMonthWithMinus(workTime.getAggregateTargetTime(datePeriod, addSet).v());
		
		// 累計フレックス時間を集計する
		AttendanceTimeMonthWithMinus totalFlexTime =
				new AttendanceTimeMonthWithMinus(flexTime.getTimeSeriesTotalFlexTime(datePeriod, true).v());
		
		/** 割増時間の加算時間を計算する */
		List<IntegrationOfDaily> dailies = monthlyCalcDailys.getDailyWorks(this.employeeId, datePeriod);
		val addTime = WorkTimeAddtionTimeGetter.getForPremiumTime(require, dailies,
				WorkingSystem.FLEX_TIME_WORK, this.companyId, MonthlyAggregateAtr.EXCESS_OUTSIDE_WORK, Optional.of(addSet));
		
		// 「フレックス超過対象時間」を計算する
		targetFlexExcessTime = totalWorkTime.addMinutes(totalFlexTime.v()).addMinutes(addTime.valueAsMinutes());
		
		return targetFlexExcessTime;
	}
	
	/**
	 * 法定外フレックスのみで当日までの超過時間を求める
	 * @param targetFlexExcessTime フレックス超過対象時間
	 * @param monStatTime 月の法定労働時間
	 * @return 当日までの超過時間
	 */
	private AttendanceTimeMonthWithMinus askExcessTimeUntilDayOnlyillegalFlex(
		AttendanceTimeMonthWithMinus targetFlexExcessTime, MonthlyFlexStatutoryLaborTime monStatTime){
		
		AttendanceTimeMonthWithMinus excessTimeUntilDay = new AttendanceTimeMonthWithMinus(0);

		// 法定労働時間(代休控除後)を確認する
		int afterDeduction = monStatTime.getStatutorySetting().valueAsMinutes();
		// 当日までの超過時間を求める
		excessTimeUntilDay = new AttendanceTimeMonthWithMinus(targetFlexExcessTime.v() - afterDeduction);
		
		return excessTimeUntilDay;
	}
	
	/**
	 * 法定内フレックスを含んで当日までの超過時間を求める
	 * @param targetFlexExcessTime フレックス超過対象時間
	 * @param monStatTime 月の法定労働時間
	 * @return 当日までの超過時間
	 */
	private AttendanceTimeMonthWithMinus askExcessTimeUntilDayIncludeLegalFlex(
			AttendanceTimeMonthWithMinus targetFlexExcessTime, MonthlyFlexStatutoryLaborTime monStatTime){
		
		AttendanceTimeMonthWithMinus excessTimeUntilDay = new AttendanceTimeMonthWithMinus(0);

		// 所定労働時間(代休控除後)を確認する
		int afterDeduction = monStatTime.getSpecifiedSetting().valueAsMinutes();

		// 当日までの超過時間を求める
		excessTimeUntilDay = new AttendanceTimeMonthWithMinus(targetFlexExcessTime.v() - afterDeduction);
		
		return excessTimeUntilDay;
	}
	
	/**
	 * 通常・変形労働勤務の逆時系列割り当て
	 * @param regAndIrgTime 月別実績の通常変形時間
	 * @param aggregateTotalWorkingTime 総労働時間
	 * @param repositories 月次集計が必要とするリポジトリ
	 */
	private void assignReverseTimeSeriesOfRegAndIrg(
			RequireM3 require, CacheCarrier cacheCarrier,
			RegularAndIrregularTimeOfMonthly regAndIrgTime,
			AggregateTotalWorkingTime aggregateTotalWorkingTime){
		
		// 内訳項目一覧に週割増合計時間が設定されているか確認する
		val outsideOTSet = this.companySets.getOutsideOverTimeSet();
		boolean isExistWeekTotalPT = false;
		for (val outsideOTBDItem : outsideOTSet.getBreakdownItems()){
			for (val attendanceItemId : outsideOTBDItem.getAttendanceItemIds()){
				if (attendanceItemId == AttendanceItemOfMonthly.MONTHLY_TOTAL_PREMIUM_TIME.value){
					isExistWeekTotalPT = true;
					break;
				}
			}
		}
		if (!isExistWeekTotalPT) return;
		
		// 「労働制」を確認する
		if (this.workingSystem == WorkingSystem.REGULAR_WORK){
			
			// 通常勤務の月割増時間を逆時系列で割り当てる
			this.assignMonthlyPremiumTimeByReverseTimeSeriesOfReg(require, 
					regAndIrgTime, aggregateTotalWorkingTime);
		}
		if (this.workingSystem == WorkingSystem.VARIABLE_WORKING_TIME_WORK){
			
			// 変形労働勤務の月割増時間を逆時系列で割り当てる
			this.assignMonthlyPremiumTimeByReverseTimeSeriesOfIrg(
					require, cacheCarrier, regAndIrgTime, aggregateTotalWorkingTime);
		}
	}
	
	/**
	 * 通常勤務の月割増時間を逆時系列で割り当てる
	 * @param regAndIrgTime 月別実績の通常変形時間
	 * @param aggregateTotalWorkingTime 総労働時間
	 */
	private void assignMonthlyPremiumTimeByReverseTimeSeriesOfReg(RequireM2 require,
			RegularAndIrregularTimeOfMonthly regAndIrgTime,
			AggregateTotalWorkingTime aggregateTotalWorkingTime){
		
		// 「週割増・月割増を求める」を取得する
		boolean isAskPremium = false;
		if (this.workingSystem == WorkingSystem.REGULAR_WORK){
			isAskPremium = this.settingsByReg.getRegularAggrSet().getExcessOutsideTimeSet().isSurchargeWeekMonth();
		}
		if (this.workingSystem == WorkingSystem.VARIABLE_WORKING_TIME_WORK){
			isAskPremium = this.settingsByDefo.getDeforAggrSet().getExcessOutsideTimeSet().isSurchargeWeekMonth();
		}
		if (!isAskPremium) return;
		
		// 「月別実績の通常変形時間」から「逆時系列割り当て用の月割増時間」にコピーする
		val monthPremiumTime = regAndIrgTime.getMonthlyTotalPremiumTime();
		AttendanceTimeMonthWithMinus monthlyPTForAssign = new AttendanceTimeMonthWithMinus(monthPremiumTime.v());
		
		// 「月別実績の通常変形時間」から「月別実績の時間外超過」にコピーする
		this.excessOutsideWork.addMinutesToMonthlyTotalPremiumTime(monthPremiumTime.v());
		
		// 加算設定を取得する　（割増）
		AddSet addSet = new AddSet();
		if (this.workingSystem == WorkingSystem.REGULAR_WORK){
			addSet = GetAddSet.get(this.workingSystem, PremiumAtr.PREMIUM, this.settingsByReg.getHolidayAdditionMap());
		}
		if (this.workingSystem == WorkingSystem.VARIABLE_WORKING_TIME_WORK){
			addSet = GetAddSet.get(this.workingSystem, PremiumAtr.PREMIUM, this.settingsByDefo.getHolidayAdditionMap());
		}
		if (addSet.getErrorInfo().isPresent()){
			this.errorInfos.add(addSet.getErrorInfo().get());
		}
		
		// 「期間．終了日」を処理日にする
		GeneralDate procDate = this.procPeriod.end();
		while (procDate.afterOrEquals(this.procPeriod.start())){
			
			// 月割増時間を日単位で割り当てる
			monthlyPTForAssign = this.monthlyDetail.assignMonthlyPremiumTimeByDayUnit(require, procDate, monthlyPTForAssign,
					addSet, aggregateTotalWorkingTime, this.workInfoOfRecordMap, this);
			
			// 時間外超過明細の更新
			this.excessOutsideWorkDetail = this.monthlyDetail.getExcessOutsideWorkMng().getExcessOutsideWorkDetail();
			
			if (monthlyPTForAssign.lessThanOrEqualTo(0)) break;
			
			// 処理日を更新する
			procDate = procDate.addDays(-1);
		}
	}
	
	/**
	 * 変形労働勤務の月割増時間を逆時系列で割り当てる
	 * @param regAndIrgTime 月別実績の通常変形時間
	 * @param aggregateTotalWorkingTime 総労働時間
	 */
	private void assignMonthlyPremiumTimeByReverseTimeSeriesOfIrg(RequireM3 require, CacheCarrier cacheCarrier,
			RegularAndIrregularTimeOfMonthly regAndIrgTime,
			AggregateTotalWorkingTime aggregateTotalWorkingTime){
		
		// 処理月が含まれる精算期間が単月か複数月か確認する
		val settlementPeriod = GetSettlementPeriodOfDefor.createFromDeforAggrSet(this.settingsByDefo.getDeforAggrSet());
		if (settlementPeriod.isSingleMonth(this.yearMonth)){
			
			// 単月の時、変形繰越時間を 0 にする
			this.excessOutsideWork.setDeformationCarryforwardTime(new AttendanceTimeMonthWithMinus(0));
			
			// 精算月の月割増時間を逆時系列で割り当てる　（単月）　※　通常勤務の方式と同じ
			this.assignMonthlyPremiumTimeByReverseTimeSeriesOfReg(require, 
					regAndIrgTime, aggregateTotalWorkingTime);
		}
		else {
			
			/** ○変形繰越時間をセットする */
			val carryForward = regAndIrgTime.getIrregularWorkingTime().getIrregularPeriodCarryforwardTime().valueAsMinutes();
			val carryForwardPlus = new AttendanceTimeMonthWithMinus(carryForward > 0 ? carryForward : 0);
			this.excessOutsideWork.setDeformationCarryforwardTime(carryForwardPlus);
				
			/** 月割増時間を逆時系列で割り当てる */
			this.assignMonthlyPremiumTimeByReverseTimeSeries(require, carryForwardPlus, aggregateTotalWorkingTime);
		}
	}
	
	/**
	 * 変形労働勤務の月割増時間を逆時系列で割り当てる
	 * @param monthlyPTForAssign 逆時系列割り当て用の月割増時間
	 * @param aggregateTotalWorkingTime 総労働時間
	 */
	private void assignMonthlyPremiumTimeByReverseTimeSeries(RequireM2 require,
			AttendanceTimeMonthWithMinus monthlyPTForAssign, AggregateTotalWorkingTime aggregateTotalWorkingTime) {
		
		// 加算設定を取得する　（割増）
		val addSet = GetAddSet.get(this.workingSystem, PremiumAtr.PREMIUM, this.settingsByDefo.getHolidayAdditionMap());
		if (addSet.getErrorInfo().isPresent()){
			this.errorInfos.add(addSet.getErrorInfo().get());
		}
		
		// 「期間．終了日」を処理日にする
		GeneralDate procDate = this.procPeriod.end();
		while (procDate.afterOrEquals(this.procPeriod.start())){
			
			// 月割増時間を日単位で割り当てる
			monthlyPTForAssign = this.monthlyDetail.assignMonthlyPremiumTimeByDayUnit(require, procDate, monthlyPTForAssign,
					addSet, aggregateTotalWorkingTime, this.workInfoOfRecordMap, this);
			
			// 時間外超過明細の更新
			this.excessOutsideWorkDetail = this.monthlyDetail.getExcessOutsideWorkMng().getExcessOutsideWorkDetail();
			
			if (monthlyPTForAssign.lessThanOrEqualTo(0)) break;
			
			// 処理日を更新する
			procDate = procDate.addDays(-1);
		}
	}
	
	/**
	 * 便宜上集計の逆時系列割り当て　（フレックス）
	 * @param flexTimeOfMonthly 月別実績のフレックス時間
	 */
	private void assignReverseTimeSeriesForConvenience(FlexTimeOfMonthly flexTimeOfMonthly){

		// 法定内フレックス時間を含めるか判断する
		AttendanceTimeMonthWithMinus flexExcessTargetTime = new AttendanceTimeMonthWithMinus(0);
		if (ExcessOutsideWorkMng.isIncludeLegalFlexTime(this.companySets.getOutsideOTBDItems())){
			
			// フレックス超過対象時間を求める（法定内フレックスを含む）
			int flexExcessTargetMinutes = flexTimeOfMonthly.getFlexExcessTime().v();
			flexExcessTargetMinutes += flexTimeOfMonthly.getFlexCarryforwardTime().getFlexCarryforwardWorkTime().v();
			flexExcessTargetTime = new AttendanceTimeMonthWithMinus(flexExcessTargetMinutes);
		}
		else {
			
			// フレックス超過対象時間を求める（法定外フレックスのみ）
			int flexExcessTargetMinutes = flexTimeOfMonthly.getFlexTime().getFlexTime().getIllegalFlexTime().v();
			flexExcessTargetMinutes += flexTimeOfMonthly.getFlexCarryforwardTime().getFlexCarryforwardWorkTime().v();
			flexExcessTargetTime = new AttendanceTimeMonthWithMinus(flexExcessTargetMinutes);
		}
		
		// 「期間．終了日」を処理日にする
		GeneralDate procDate = this.procPeriod.end();
		while (procDate.afterOrEquals(this.procPeriod.start())){
			
			// フレックス超過対象時間を日単位で割り当てる
			flexExcessTargetTime = this.excessOutsideWorkDetail.assignFlexExcessTargetTimeByDayUnit(
					procDate, flexExcessTargetTime, flexTimeOfMonthly);
			
			if (flexExcessTargetTime.lessThanOrEqualTo(0)) break;
			
			// 処理日を更新する
			procDate = procDate.addDays(-1);
		}
	}
	
	/**
	 * 時間外超過内訳に割り当てる　（集計後）
	 */
	private void assignExcessOutsideWorkBreakdownForAfterAggregate(){
		
		// 「時間外超過累積時間」を作成する
		this.totalExcessOutside = new AttendanceTimeMonth(0);
		
		// 「時間外超過の内訳項目」を取得する
		for (val outsideOTBDItem : this.companySets.getOutsideOTBDItems()){
			
			// 内訳項目に設定されている項目の値を取得する
			val totalTime = this.excessOutsideWorkDetail.getTotalTimeAfterRound();
			AttendanceTimeMonth breakdownItemTime = new AttendanceTimeMonth(0);
			boolean addedFlexExcessTime = false;	// フレックス超過時間を足したか
			for (val attendanceItemId : outsideOTBDItem.getAttendanceItemIds()){
				
				// 法定内フレックス時間・法定外フレックス時間がある時、フレックス超過時間を1回だけ足す
				if (attendanceItemId == AttendanceItemOfMonthly.FLEX_LEGAL_TIME.value ||
					attendanceItemId == AttendanceItemOfMonthly.FLEX_ILLEGAL_TIME.value){
					if (addedFlexExcessTime) continue;
					breakdownItemTime = breakdownItemTime.addMinutes(
							totalTime.getTimeOfAttendanceItemId(AttendanceItemOfMonthly.FLEX_EXCESS_TIME.value).v());
					addedFlexExcessTime = true;
					continue;
				}
				
				breakdownItemTime = breakdownItemTime.addMinutes(
						totalTime.getTimeOfAttendanceItemId(attendanceItemId).v());
			}
			if (breakdownItemTime.greaterThan(0)){
			
				// 内訳項目ごとに時間外超過の値を求める
				val breakdownItemNo =  outsideOTBDItem.getBreakdownItemNo().value;
				this.askExcessOutsideWorkEachBreakdown(breakdownItemTime, breakdownItemNo);
			}
		}
	}
	
	/**
	 * 時間外超過内訳に割り当てる　（時系列用）
	 */
	private void assignExcessOutsideWorkBreakdownForTimeSeries(){
		
		// 「時間外超過累積時間」を作成する
		this.totalExcessOutside = new AttendanceTimeMonth(0);
		
		// 「期間．開始日」を処理日にする
		GeneralDate procDate = this.procPeriod.start();
		while (procDate.beforeOrEquals(this.procPeriod.end())){
			
			// 日ごとに時間外超過の値を求める
			this.askExcessOutsideWorkEachDay(procDate);
			
			// 処理日を更新する
			procDate = procDate.addDays(1);
		}
		
		// 丸め差分時間を時間外超過の値に割り当てる
		{
			// 「時間外超過の内訳項目」を取得する
			for (val outsideOTBDItem : this.companySets.getOutsideOTBDItems()){
				
				// 内訳項目に設定されている項目の値を取得する
				val roundDiffTime = this.excessOutsideWorkDetail.getRoundDiffTime();
				AttendanceTimeMonth breakdownItemTime = new AttendanceTimeMonth(0);
				boolean addedFlexExcessTime = false;	// フレックス超過時間を足したか
				for (val attendanceItemId : outsideOTBDItem.getAttendanceItemIds()){
					
					// 法定内フレックス時間・法定外フレックス時間がある時、フレックス超過時間を1回だけ足す
					if (attendanceItemId == AttendanceItemOfMonthly.FLEX_LEGAL_TIME.value ||
						attendanceItemId == AttendanceItemOfMonthly.FLEX_ILLEGAL_TIME.value){
						if (addedFlexExcessTime) continue;
						breakdownItemTime = breakdownItemTime.addMinutes(
								roundDiffTime.getTimeOfAttendanceItemId(AttendanceItemOfMonthly.FLEX_EXCESS_TIME.value).v());
						addedFlexExcessTime = true;
						continue;
					}
					
					breakdownItemTime = breakdownItemTime.addMinutes(
							roundDiffTime.getTimeOfAttendanceItemId(attendanceItemId).v());
				}
				if (breakdownItemTime.greaterThan(0)){
				
					// 内訳項目ごとに時間外超過の値を求める
					val breakdownItemNo =  outsideOTBDItem.getBreakdownItemNo().value;
					this.askExcessOutsideWorkEachBreakdown(breakdownItemTime, breakdownItemNo);
				}
			}
		}
	}
	
	/**
	 * 日ごとに時間外超過の値を求める
	 * @param procDate 処理日
	 */
	private void askExcessOutsideWorkEachDay(GeneralDate procDate){
		
		// 「時間外超過の内訳項目」を取得する
		for (val outsideOTBDItem : this.companySets.getOutsideOTBDItems()){
			
			// 内訳項目に設定されている項目の値を取得する
			AttendanceTimeMonth breakdownItemTime = new AttendanceTimeMonth(0);
			boolean addedFlexExcessTime = false;	// フレックス超過時間を足したか
			for (val attendanceItemId : outsideOTBDItem.getAttendanceItemIds()){
				
				/** TODO: 当月の法定内・外も？*/
				// 法定内フレックス時間・法定外フレックス時間がある時、フレックス超過時間を1回だけ足す
				if (attendanceItemId == AttendanceItemOfMonthly.FLEX_LEGAL_TIME.value ||
					attendanceItemId == AttendanceItemOfMonthly.FLEX_ILLEGAL_TIME.value){
					if (addedFlexExcessTime) continue;
					breakdownItemTime = breakdownItemTime.addMinutes(
							this.excessOutsideWorkDetail.getTimeOfAttendanceItemId(
									AttendanceItemOfMonthly.FLEX_EXCESS_TIME.value, procDate).v());
					addedFlexExcessTime = true;
					continue;
				}
				
				breakdownItemTime = breakdownItemTime.addMinutes(
						this.excessOutsideWorkDetail.getTimeOfAttendanceItemId(attendanceItemId, procDate).v());
			}
			if (breakdownItemTime.greaterThan(0)){
			
				// 内訳項目ごとに時間外超過の値を求める
				val breakdownItemNo =  outsideOTBDItem.getBreakdownItemNo().value;
				this.askExcessOutsideWorkEachBreakdown(breakdownItemTime, breakdownItemNo);
			}
		}
	}
	
	/**
	 * 内訳項目ごとに時間外超過の値を求める
	 * @param breakdownItemTime 内訳項目時間
	 * @param breakdownItemNo 内訳項目NO
	 */
	private void askExcessOutsideWorkEachBreakdown(AttendanceTimeMonth breakdownItemTime, int breakdownItemNo){
		
		// 「超過時間一覧」を取得する
		val overTimes = this.companySets.getOutsideOTOverTimes();
		
		for (int ixOverTime = 0; ixOverTime < overTimes.size(); ixOverTime++){
			val overTime = overTimes.get(ixOverTime);
			
			// 時間外超過累積時間＋内訳項目時間と超過時間を比較する
			if (this.totalExcessOutside.v() + breakdownItemTime.v() <= overTime.getOvertime().v()) break;
			
			// 次の超過時間を取得する
			Overtime nextOverTime = null;
			if (ixOverTime + 1 < overTimes.size()) nextOverTime = overTimes.get(ixOverTime + 1);
			
			// 時間外超過の時間に加算する
			this.excessOutsideWork.addTimeFromBreakdownItemTime(
					this.totalExcessOutside, breakdownItemTime, overTime, nextOverTime, breakdownItemNo);
		}
		
		// 「内訳項目時間」を「時間外超過累積時間」に加算する
		this.totalExcessOutside = this.totalExcessOutside.addMinutes(breakdownItemTime.v());
	}
	
	/**
	 * エラー情報の取得
	 * @return エラー情報リスト
	 */
	public List<MonthlyAggregationErrorInfo> getErrorInfos(){
		
		List<MonthlyAggregationErrorInfo> results = new ArrayList<>();
		results.addAll(this.errorInfos);
		results.addAll(this.monthlyDetail.getErrorInfos());
		return results;
	}
	
	/**
	 * 清算期間内の基準時間を集計する
	 * @param yearMonth 年月（度）
	 * @param period 期間
	 * @param flexAggrSet フレックス時間勤務の月の集計設定
	 * @return 基準フレックス時間
	 */
	public StandardFlexTime aggrStandardTime(RequireM1 require, CacheCarrier cacheCarrier, YearMonth yearMonth,
			DatePeriod period, FlexMonthWorkTimeAggrSet flexAggrSet, 
			Optional<AggregateTotalWorkingTime> laborTime, FlexAggregateMethodOfMonthly flexAggregateMethod){
		
		String cid = AppContexts.user().companyId();
		StandardFlexTime result = new StandardFlexTime();
		
		ShortageFlexSetting insufficSet = flexAggrSet.getInsufficSet();
		
		// 「清算期間」を確認する
		if (insufficSet.getSettlePeriod() == SettlePeriod.SINGLE_MONTH) return result;
		
		/** 法定労働時間を取得する */
		MonthlyFlexStatutoryLaborTime monStatTime = FlexLegalTimeGetter.getFlexStatutoryLaborTime(
				require, cacheCarrier, this.companySets, this.employeeSets, this.settingsByFlex,
				true, yearMonth, cid, this.employmentCd, this.employeeId, period.end(), Optional.of(period),
				this.closureId, this.closureDate, laborTime, this.monthlyCalculatingDailys);
		
		// 「清算法定時間」に「法定労働時間」を加算する
		result.addSettleStatTime(monStatTime.getStatutorySetting().valueAsMinutes());
		
		/** ○「清算基準時間」に「所定労働時間」を加算する */
		result.addSettleStandTime(monStatTime.getSpecifiedSetting().valueAsMinutes());
				
		// 代休使用時間を求める
		val vacationUseTime = this.monthlyCalculation.getAggregateTime().getVacationUseTime();
		val compensatoryLeave = vacationUseTime.getCompensatoryLeave();
		val compensatoryLeaveTime = compensatoryLeave.getTotalUseTime(period);
		
		// 「清算基準時間」から代休使用時間を引く
		result.addSettleStandTime(-compensatoryLeaveTime.v());
		
		// 「当月週平均時間」に「週平均時間」を入れる
		result.setCurrWeekAveTime(new AttendanceTimeMonth(monStatTime.getWeekAveSetting().v()));
		
		// 「基準フレックス時間」を返す
		return result;
	}
	
	public static interface RequireM6 extends MonthlyStatutoryLaborDivisionService.Require {
		
		Optional<AttendanceTimeOfMonthly> attendanceTimeOfMonthly(String employeeId, YearMonth yearMonth, ClosureId closureId, ClosureDate closureDate);
	}
	
	public static interface RequireM0 {

		List<AttendanceTimeOfMonthly> attendanceTimeOfMonthly(String employeeId, YearMonth yearMonth);
	}

	public static interface RequireM5 extends FlexTimeOfMonthly.RequireM6, FlexTimeOfMonthly.RequireM5, RequireM3, 
		RegularAndIrregularTimeOfMonthly.RequireM1, RegularAndIrregularTimeOfMonthly.RequireM3, SuperHD60HConMed.RequireM1 {
		
		Optional<SuperHD60HConMed> superHD60HConMed(String cid);
	}
	
	public static interface RequireM4 extends MonthlyDetail.RequireM5 {}
	
	public static interface RequireM3 extends RequireM0, MonthlyStatutoryWorkingHours.RequireM4, RequireM2,
		GetSettlementPeriodOfDefor.Require, DefoAggregateMethodOfMonthly.Require, ClosureService.RequireM1 {}
	
	public static interface RequireM2 extends MonthlyDetail.RequireM3 {}
	
	public static interface RequireM1 extends RequireM0, MonthlyStatutoryWorkingHours.RequireM1, 
		ShortageFlexSetting.Require, RequireM6, FlexLegalTimeGetter.RequireM1 {}
	
	public static interface RequireM7 extends FlexLegalTimeGetter.Require, RequireM8 {}
	
	public static interface RequireM8 extends WorkTimeAddtionTimeGetter.Require {}
}
