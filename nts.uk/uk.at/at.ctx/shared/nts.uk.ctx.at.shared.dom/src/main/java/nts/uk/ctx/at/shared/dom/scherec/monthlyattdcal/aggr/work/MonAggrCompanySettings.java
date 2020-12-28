package nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.work;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.CopyOnWriteArrayList;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.val;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.flex.com.ComFlexMonthActCalSet;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.other.com.ComDeforLaborMonthActCalSet;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.other.com.ComRegulaMonthActCalSet;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.legaltransferorder.LegalTransferOrderSetOfAggrMonthly;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.roleofovertimework.roleofovertimework.RoleOvertimeWork;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.roundingset.RoundingSetOfMonthly;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.vtotalmethod.PayItemCountOfMonthly;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.vtotalmethod.AggregateMethodOfMonthly;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.setting.AgreementOperationSetting;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.flexshortage.FlexShortageLimit;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.flexshortage.InsufficientFlexHolidayMnt;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.verticaltotal.GetVacationAddSet;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.verticaltotal.VacationAddSet;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.workform.flex.MonthlyAggrSetOfFlex;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.outsideot.OutsideOTSetting;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.outsideot.UseClassification;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.outsideot.breakdown.OutsideOTBRDItem;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.outsideot.overtime.Overtime;
import nts.uk.ctx.at.shared.dom.scherec.optitem.OptionalItem;
import nts.uk.ctx.at.shared.dom.scherec.optitem.applicable.EmpCondition;
import nts.uk.ctx.at.shared.dom.scherec.optitem.calculation.Formula;
import nts.uk.ctx.at.shared.dom.scherec.optitem.calculation.disporder.FormulaDispOrder;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.UsageUnitSetting;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.flex.GetFlexPredWorkTime;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.week.WorkingTimeSetting;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.week.defor.DeforLaborTimeCom;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.week.defor.DeforLaborTimeEmp;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.week.defor.DeforLaborTimeWkp;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.week.regular.RegularLaborTimeCom;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.week.regular.RegularLaborTimeEmp;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.week.regular.RegularLaborTimeWkp;
import nts.uk.ctx.at.shared.dom.scherec.totaltimes.TotalTimes;
import nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.AnnualPaidLeaveSetting;
import nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.OperationStartSetDailyPerform;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.CompensatoryLeaveComSetting;
import nts.uk.ctx.at.shared.dom.vacation.setting.retentionyearly.EmptYearlyRetentionSetting;
import nts.uk.ctx.at.shared.dom.vacation.setting.retentionyearly.RetentionYearlySetting;
import nts.uk.ctx.at.shared.dom.vacation.setting.subst.ComSubstVacation;
import nts.uk.ctx.at.shared.dom.workdayoff.frame.WorkdayoffFrame;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingSystem;
import nts.uk.ctx.at.shared.dom.workrecord.workperfor.dailymonthlyprocessing.ErrMessageContent;
import nts.uk.ctx.at.shared.dom.workrule.closure.Closure;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureClassification;
import nts.uk.ctx.at.shared.dom.worktime.algorithm.getcommonset.GetCommonSet;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimezoneCommonSet;
import nts.uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSetting;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.ctx.at.shared.dom.yearholidaygrant.GrantHdTblSet;
import nts.uk.ctx.at.shared.dom.yearholidaygrant.LengthServiceTbl;
import nts.uk.shr.com.i18n.TextResource;

/**
 * 月別集計で必要な会社別設定
 * @author shuichi_ishida
 */
@Setter
@NoArgsConstructor
public class MonAggrCompanySettings {

	/** 会社ID */
	@Getter
	private String companyId;
	/** 勤務種類 */
	private ConcurrentMap<String, Object> workTypeMap;
	/** 就業時間帯：共通設定 */
	private ConcurrentMap<String, Object> workTimeCommonSetMap;
	/** 所定時間設定 */
	private ConcurrentMap<String, Object> predetermineTimeSetMap;
	/** 締め */
	@Getter
	private ConcurrentMap<Integer, Closure> closureMap;
	/** 締め当月の期間 */
	@Getter
	private ConcurrentMap<Integer, DatePeriod> currentMonthPeriodMap;
	/** 法定内振替順設定 */
	@Getter
	private LegalTransferOrderSetOfAggrMonthly legalTransferOrderSet;
	/** 残業枠の役割 */
	@Getter
	private CopyOnWriteArrayList<RoleOvertimeWork> roleOverTimeFrameList;
	/** 休出枠 */
	@Getter
	private CopyOnWriteArrayList<WorkdayoffFrame> workDayoffFrameList;
	/** 休暇時間加算設定 */
	@Getter
	private ConcurrentMap<String, AggregateRoot> holidayAdditionMap;
	/** 労働時間と日数の設定の利用単位の設定 */
	@Getter
	private UsageUnitSetting usageUnitSet;
	/** 会社別通常勤務労働時間 */
	@Getter
	private WorkingTimeSetting comRegLaborTime;
	/** 会社別変形労働労働時間 */
	@Getter
	private WorkingTimeSetting comIrgLaborTime;
	/** 雇用別通常勤務労働時間 */
	private ConcurrentMap<String, Object> empRegLaborTimeMap;
	/** 雇用別変形労働労働時間 */
	private ConcurrentMap<String, Object> empIrgLaborTimeMap;
	/** 職場別通常勤務労働時間 */
	private ConcurrentMap<String, Object> wkpRegLaborTimeMap;
	/** 職場別変形労働労働時間 */
	private ConcurrentMap<String, Object> wkpIrgLaborTimeMap;
	/** 通常勤務会社別月別実績集計設定 */
	@Getter
	private Optional<ComRegulaMonthActCalSet> comRegSetOpt;
	/** 変形労働会社別月別実績集計設定 */
	@Getter
	private Optional<ComDeforLaborMonthActCalSet> comIrgSetOpt;
	/** フレックス会社別月別実績集計設定 */
	@Getter
	private Optional<ComFlexMonthActCalSet> comFlexSetOpt;
	/** フレックス勤務の月別集計設定 */
	@Getter
	private MonthlyAggrSetOfFlex aggrSetOfFlex;
	/** フレックス勤務所定労働時間 */
	@Getter
	private GetFlexPredWorkTime flexPredWorkTime;
	/** フレックス不足の年休補填管理 */
	@Getter
	private Optional<InsufficientFlexHolidayMnt> insufficientFlexOpt;
	/** フレックス不足の繰越上限管理 */
	@Getter
	private Optional<FlexShortageLimit> flexShortageLimitOpt;
	/** 休暇加算設定 */
	@Getter
	private VacationAddSet vacationAddSet;
	/** 時間外超過設定 */
	@Getter
	private OutsideOTSetting outsideOverTimeSet;
	/** 時間外超過設定：内訳項目一覧（積上番号順） */
	@Getter
	private CopyOnWriteArrayList<OutsideOTBRDItem> outsideOTBDItems;
	/** 時間外超過設定：超過時間一覧（超過時間順） */
	@Getter
	private CopyOnWriteArrayList<Overtime> outsideOTOverTimes;
	/** 丸め設定 */
	@Getter
	private RoundingSetOfMonthly roundingSet;
	/** 回数集計 */
	@Getter
	private CopyOnWriteArrayList<TotalTimes> totalTimesList;
	/** 月別実績の給与項目カウント */
	@Getter
	private PayItemCountOfMonthly payItemCount;
	/** 月別実績の縦計方法 */
	@Getter
	private AggregateMethodOfMonthly verticalTotalMethod;
	/** 36協定運用設定 */
	@Getter
	private Optional<AgreementOperationSetting> agreementOperationSet;
	/** 任意項目 */
	@Getter
	private ConcurrentMap<Integer, OptionalItem> optionalItemMap;
	/** 適用する雇用条件 */
	@Getter
	private ConcurrentMap<Integer, EmpCondition> empConditionMap;
	/** 計算式 */
	@Getter
	private CopyOnWriteArrayList<Formula> formulaList;
	/** 計算式の並び順 */
	@Getter
	private CopyOnWriteArrayList<FormulaDispOrder> formulaOrderList;
	/** 年休設定 */
	@Getter
	private AnnualPaidLeaveSetting annualLeaveSet;
	/** 年休付与テーブル設定 */
	@Getter
	private ConcurrentMap<String, GrantHdTblSet> grantHdTblSetMap;
	/** 勤続年数テーブル */
	@Getter
	private ConcurrentMap<String, List<LengthServiceTbl>> lengthServiceTblListMap;
	/** 積立年休設定 */
	@Getter
	private Optional<RetentionYearlySetting> retentionYearlySet;
	/** 雇用積立年休設定 */
	@Getter
	private ConcurrentHashMap<String, EmptYearlyRetentionSetting> emptYearlyRetentionSetMap;
	/** 振休管理設定 */
	@Getter
	private Optional<ComSubstVacation> absSettingOpt;
	/** 代休管理設定 */
	@Getter
	private CompensatoryLeaveComSetting dayOffSetting;
	/** 実績ロック */
//	private ConcurrentMap<Integer, ActualLock> actualLockMap;
	/** 日別実績の運用開始設定 */
	@Getter
	private Optional<OperationStartSetDailyPerform> operationStartSet;
	
	/** エラー情報 */
	@Getter
	private ConcurrentMap<String, ErrMessageContent> errorInfos;
	
	private static Object NullObject = new Object();
	
	private MonAggrCompanySettings(String companyId){
		this.companyId = companyId;
		this.workTypeMap = new ConcurrentHashMap<>();
		this.workTimeCommonSetMap = new ConcurrentHashMap<>();
		this.predetermineTimeSetMap = new ConcurrentHashMap<>();
		this.closureMap = new ConcurrentHashMap<>();
		this.currentMonthPeriodMap = new ConcurrentHashMap<>();
		this.roleOverTimeFrameList = new CopyOnWriteArrayList<>();
		this.workDayoffFrameList = new CopyOnWriteArrayList<>();
		this.holidayAdditionMap = new ConcurrentHashMap<>();
		this.empRegLaborTimeMap = new ConcurrentHashMap<>();
		this.empIrgLaborTimeMap = new ConcurrentHashMap<>();
		this.wkpRegLaborTimeMap = new ConcurrentHashMap<>();
		this.wkpIrgLaborTimeMap = new ConcurrentHashMap<>();
		this.comRegSetOpt = Optional.empty();
		this.comIrgSetOpt = Optional.empty();
		this.comFlexSetOpt = Optional.empty();
		this.insufficientFlexOpt = Optional.empty();
		this.flexShortageLimitOpt = Optional.empty();
		this.outsideOTBDItems = new CopyOnWriteArrayList<>();
		this.outsideOTOverTimes = new CopyOnWriteArrayList<>();
		this.totalTimesList = new CopyOnWriteArrayList<>();
		this.agreementOperationSet = Optional.empty();
		this.optionalItemMap = new ConcurrentHashMap<>();
		this.empConditionMap = new ConcurrentHashMap<>();
		this.formulaList = new CopyOnWriteArrayList<>();
		this.formulaOrderList = new CopyOnWriteArrayList<>();
		this.grantHdTblSetMap = new ConcurrentHashMap<>();
		this.lengthServiceTblListMap = new ConcurrentHashMap<>();
		this.retentionYearlySet = Optional.empty();
		this.emptYearlyRetentionSetMap = new ConcurrentHashMap<>();
		this.absSettingOpt = Optional.empty();
		this.dayOffSetting = null;
		this.operationStartSet = Optional.empty();
		this.errorInfos = new ConcurrentHashMap<>();
	}
	
	/**
	 * 設定読み込み
	 * @param companyId 会社ID
	 * @return 月別集計で必要な会社別設定
	 */
	public static MonAggrCompanySettings loadSettings(RequireM6 require, String companyId){
		
		MonAggrCompanySettings domain = new MonAggrCompanySettings(companyId);
		
		// 月別実績の給与項目カウント　取得
		domain.payItemCount = new PayItemCountOfMonthly(companyId);
		val payItemCountOpt = require.monthPayItemCount(companyId);
		if (payItemCountOpt.isPresent()) domain.payItemCount = payItemCountOpt.get();
		
		// 月別実績の縦計方法　取得
		//*****（未）　設計待ち。特定日設定部分が保留中。仮に空条件で。
		domain.verticalTotalMethod = new AggregateMethodOfMonthly(companyId);
		
		// 任意項目
		val optionalItems = require.optionalItems(companyId);
		List<Integer> optionalItemNoList = new ArrayList<>();
		for (val optionalItem : optionalItems){
			domain.optionalItemMap.put(optionalItem.getOptionalItemNo().v(), optionalItem);
			optionalItemNoList.add(optionalItem.getOptionalItemNo().v());
		}
		
		// 適用する雇用条件
		val empConditions = require.employmentConditions(companyId, optionalItemNoList);
		for (val empCondifion : empConditions){
			domain.empConditionMap.put(empCondifion.getOptItemNo().v(), empCondifion);
		}
		
		// 計算式
		domain.formulaList.addAll(require.formulas(companyId));
		
		// 計算式の並び順
		domain.formulaOrderList.addAll(require.formulaDispOrder(companyId));
		
		// 年休設定
		domain.annualLeaveSet = require.annualPaidLeaveSetting(companyId);

		// 年休付与テーブル設定、勤続年数テーブル
		val yearHolidays = require.grantHdTblSets(companyId);
		for (val yearHoliday : yearHolidays){
			val yearHolidayCode = yearHoliday.getYearHolidayCode().v();
			domain.grantHdTblSetMap.put(yearHolidayCode, yearHoliday);
			domain.lengthServiceTblListMap.put(yearHolidayCode,
					require.lengthServiceTbl(companyId, yearHolidayCode));
		}
		
		// 積立年休設定
		domain.retentionYearlySet = require.retentionYearlySetting(companyId);
		
		// 雇用積立年休設定
		val emptYearlyRetentionSets = require.emptYearlyRetentionSet(companyId);
		for (val emptYearlyRetentionSet : emptYearlyRetentionSets){
			val employmentCode = emptYearlyRetentionSet.getEmploymentCode();
			domain.emptYearlyRetentionSetMap.put(employmentCode, emptYearlyRetentionSet);
		}
		
		// 振休管理設定
		domain.absSettingOpt = require.comSubstVacation(companyId);
		
		// 代休管理設定
		domain.dayOffSetting = require.compensatoryLeaveComSetting(companyId);
		
		// 日別実績の運用開始設定
		domain.operationStartSet = require.dailyOperationStartSet(new CompanyId(companyId));
		
		// 設定読み込み処理　（36協定時間用）
		domain.loadSettingsForAgreementProc(require, companyId);
		
		return domain;
	}
	
	/**
	 * 設定読み込み　（36協定時間用）
	 * @param companyId 会社ID
	 * @return 月別集計で必要な会社別設定
	 */
	public static MonAggrCompanySettings loadSettingsForAgreement(RequireM5 require, String companyId){
		
		MonAggrCompanySettings domain = new MonAggrCompanySettings(companyId);

		// 設定読み込み処理　（36協定時間用）
		domain.loadSettingsForAgreementProc(require, companyId);
		
		return domain;
	}
	
	/**
	 * 設定読み込み処理　（36協定時間用）
	 * @param companyId 会社ID
	 * @return 月別集計で必要な会社別設定
	 */
	private void loadSettingsForAgreementProc(RequireM5 require, String companyId) {
		// 締め
		val closures = require.closure(companyId);
		for (val closure : closures){
			val closureId = closure.getClosureId().value;
			this.closureMap.putIfAbsent(closureId, closure);
			
			// 締め当月の期間
			val currentMonth = closure.getClosureMonth();
			if (currentMonth == null) continue;
			if (currentMonth.getProcessingYm() == null) continue;
			val currentPeriods = closure.getPeriodByYearMonth(currentMonth.getProcessingYm());
			val nextPeriods = closure.getPeriodByYearMonth(currentMonth.getProcessingYm().addMonths(1));
			if (currentPeriods.size() <= 0) continue;
			// ※　当月期間が2つある時は、2つめが当月。翌月が2つある時は、その1つめが当月の後期になる。
			val currentPeriod = currentPeriods.get(currentPeriods.size() - 1);
			boolean isMultiPeriod = (nextPeriods.size() > 1);
			if (isMultiPeriod){
				if (currentMonth.getClosureClassification().isPresent()){
					if (currentMonth.getClosureClassification().get() ==
							ClosureClassification.ClassificationClosingBefore){
						// 締め日変更前期間の時、当月期間そのまま
						this.currentMonthPeriodMap.put(closureId, currentPeriod);
					}
					else {
						// 締め日変更後期間の時、翌月の1つめの期間が当月
						if (nextPeriods.size() > 0){
							this.currentMonthPeriodMap.put(closureId, nextPeriods.get(0));
						}
					}
				}
				else {
					// 締め変更区分がない時、当月期間そのまま
					this.currentMonthPeriodMap.put(closureId, currentPeriod);
				}
			}
			else {
				// 締め期間が複数に分かれていない時、当月期間そのまま
				this.currentMonthPeriodMap.put(closureId, currentPeriod);
			}
		}
		
		// 法定内振替順設定
		val legalTransferOrderSetOpt = require.monthLegalTransferOrderCalcSet(companyId);
		if (!legalTransferOrderSetOpt.isPresent()){
			this.errorInfos.put("009", new ErrMessageContent(TextResource.localize("Msg_1232")));
		}
		else {
			this.legalTransferOrderSet = legalTransferOrderSetOpt.get();
		}

		// 残業枠の役割
		this.roleOverTimeFrameList.addAll(require.roleOvertimeWorks(companyId));

		// 休出枠の役割
		this.workDayoffFrameList.addAll(require.workdayoffFrames(companyId));
		
		// 休暇時間加算設定
		for (val holidayAddition : require.holidayAddtionSets(companyId).entrySet()){
			if (holidayAddition.getValue() == null) continue;
			this.holidayAdditionMap.put(holidayAddition.getKey(), holidayAddition.getValue());
		}
		
		// 労働時間と日数の設定の利用単位の設定
		this.usageUnitSet = new UsageUnitSetting(new CompanyId(companyId), false, false, false);
		val usagaUnitSetOpt = require.usageUnitSetting(companyId);
		if (usagaUnitSetOpt.isPresent()) this.usageUnitSet = usagaUnitSetOpt.get();
		
		// 会社別通常勤務労働時間
		val comRegLaborTime = require.regularLaborTimeByCompany(companyId);
		if (comRegLaborTime.isPresent()) this.comRegLaborTime = comRegLaborTime.get();
		
		// 会社別変形労働労働時間
		val comIrgLaborTime = require.deforLaborTimeByCompany(companyId);
		if (comIrgLaborTime.isPresent()) this.comIrgLaborTime = comIrgLaborTime.get();
		
		// 通常勤務会社別月別実績集計設定
		this.comRegSetOpt = require.monthRegulaCalSetByCompany(companyId);
		
		// 変形労働会社別月別実績集計設定
		this.comIrgSetOpt = require.monthDeforLaborCalSetByCompany(companyId);
		
		// フレックス会社別月別実績集計設定
		this.comFlexSetOpt = require.monthFlexCalSetByCompany(companyId);
		
		// フレックス勤務の月別集計設定
		val aggrSetOfFlexOpt = require.monthFlexAggrSet(companyId);
		if (!aggrSetOfFlexOpt.isPresent()){
			this.errorInfos.put("011", new ErrMessageContent(TextResource.localize("Msg_1238")));
		}
		else {
			this.aggrSetOfFlex = aggrSetOfFlexOpt.get();
		}

		// フレックス勤務所定労働時間
		val flexPredWorkTimeOpt = require.flexPredWorkTime(companyId);
		if (!flexPredWorkTimeOpt.isPresent()){
			this.errorInfos.put("016", new ErrMessageContent(TextResource.localize("Msg_1243")));
		}
		else {
			this.flexPredWorkTime = flexPredWorkTimeOpt.get();
		}

		// フレックス不足の年休補填管理
		this.insufficientFlexOpt = require.insufficientFlexHolidayMnt(companyId);
		
		// フレックス不足の繰越上限管理
		this.flexShortageLimitOpt = require.flexShortageLimit(companyId);
		
		// 休暇加算設定
		this.vacationAddSet = GetVacationAddSet.get(require, companyId);
		
		// 時間外超過設定
		val outsideOTSetOpt = require.outsideOTSetting(companyId);
		if (!outsideOTSetOpt.isPresent()){
			this.errorInfos.put("014", new ErrMessageContent(TextResource.localize("Msg_1236")));
		}
		else {
			this.outsideOverTimeSet = outsideOTSetOpt.get();
			
			// 時間外超過設定：内訳項目一覧（積上番号順）
			this.outsideOTBDItems.addAll(this.outsideOverTimeSet.getBreakdownItems());
			this.outsideOTBDItems.removeIf(a -> { return a.getUseClassification() != UseClassification.UseClass_Use; });
			this.outsideOTBDItems.sort((a, b) -> a.getProductNumber().value - b.getProductNumber().value);
			
			// 時間外超過設定：超過時間一覧（超過時間順）
			this.outsideOTOverTimes.addAll(this.outsideOverTimeSet.getOvertimes());
			this.outsideOTOverTimes.removeIf(a -> { return a.getUseClassification() != UseClassification.UseClass_Use; });
			this.outsideOTOverTimes.sort((a, b) -> a.getOvertime().v() - b.getOvertime().v());
		}
		
		// 丸め設定
		this.roundingSet = new RoundingSetOfMonthly(companyId);
		val roundingSetOpt = require.monthRoundingSet(companyId);
		if (roundingSetOpt.isPresent()) {
			this.roundingSet = roundingSetOpt.get();
		}
		else {
			this.errorInfos.put("013", new ErrMessageContent(TextResource.localize("Msg_1239")));
		}
		
		// 回数集計
		this.totalTimesList.addAll(require.totalTimes(companyId));
		if (this.totalTimesList.size() <= 0){
			this.errorInfos.put("020", new ErrMessageContent(TextResource.localize("Msg_1416")));
		}
		
		// 36協定運用設定を取得
		this.agreementOperationSet = require.agreementOperationSetting(companyId);
		if (!this.agreementOperationSet.isPresent()){
			this.errorInfos.put("017", new ErrMessageContent(TextResource.localize("Msg_1246")));
		}
	}
	
	
	/**
	 * 勤務種類の取得
	 * @param workTypeCode 勤務種類コード
	 * @return 勤務種類　（なければ、null）
	 */
	public WorkType getWorkTypeMap(RequireM4 require, String workTypeCode) {
		
		if (this.workTypeMap.containsKey(workTypeCode)){
			val result = this.workTypeMap.get(workTypeCode);
			if (result == NullObject) return null;
			return (WorkType)result;
		}
		
		val workTypeOpt = require.workType(this.companyId, workTypeCode);
		if (!workTypeOpt.isPresent()){
			this.workTypeMap.put(workTypeCode, NullObject);
			return null;
		}
		this.workTypeMap.put(workTypeCode, workTypeOpt.get());
		return (WorkType)this.workTypeMap.get(workTypeCode);
	}

	/**
	 * 現在の全ての勤務種類の取得
	 * @return 勤務種類マップ
	 */
	public Map<String, WorkType> getAllWorkTypeMap(){
		Map<String, WorkType> results = new HashMap<>();
		for (val entry : this.workTypeMap.entrySet()){
			WorkType value = null;
			if (entry.getValue() != NullObject) value = (WorkType)entry.getValue();
			results.put(entry.getKey(), value);
		}
		return results;
	}
	
	/**
	 * 就業時間帯：共通設定の取得
	 * @param workTimeCode 就業時間帯コード
	 * @return 就業時間帯：共通設定　（なければ、null）
	 */
	public WorkTimezoneCommonSet getWorkTimeCommonSetMap(RequireM3 require, String workTimeCode){

		if (this.workTimeCommonSetMap.containsKey(workTimeCode)){
			val result = this.workTimeCommonSetMap.get(workTimeCode);
			if (result == NullObject) return null;
			return (WorkTimezoneCommonSet)result;
		}
		
		val workTimeCommonSetOpt = GetCommonSet.workTimezoneCommonSet(require, this.companyId, workTimeCode);
		if (!workTimeCommonSetOpt.isPresent()){
			this.workTimeCommonSetMap.put(workTimeCode, NullObject);
			return null;
		}
		
		this.workTimeCommonSetMap.put(workTimeCode, workTimeCommonSetOpt.get());
		
		return (WorkTimezoneCommonSet)this.workTimeCommonSetMap.get(workTimeCode);
	}

	/**
	 * 所定時間設定の取得
	 * @param workTimeCode 就業時間帯コード
	 * @return 所定時間設定　（なければ、null）
	 */
	public PredetemineTimeSetting getPredetemineTimeSetMap(RequireM2 require, String workTimeCode){

		if (this.predetermineTimeSetMap.containsKey(workTimeCode)){
			val result = this.predetermineTimeSetMap.get(workTimeCode);
			if (result == NullObject) return null;
			return (PredetemineTimeSetting)result;
		}
		
		val predetermineTimeSetOpt = require.predetemineTimeSetByWorkTimeCode(this.companyId, workTimeCode);
		if (!predetermineTimeSetOpt.isPresent()){
			this.predetermineTimeSetMap.put(workTimeCode, NullObject);
			return null;
		}
		this.predetermineTimeSetMap.put(workTimeCode, predetermineTimeSetOpt.get());
		return (PredetemineTimeSetting)this.predetermineTimeSetMap.get(workTimeCode);
	}
	
	/**
	 * 労働時間の取得
	 * @param employmentCd 雇用コード
	 * @param workplaceIds 上位職場含む職場コードリスト
	 * @param workingSystem 労働制
	 * @param employeeSets 月別集計で必要な社員別設定
	 * @return 労働時間
	 */
	public Optional<WorkingTimeSetting> getWorkingTimeSetting(RequireM1 require, String employmentCd, List<String> workplaceIds, 
			WorkingSystem workingSystem, MonAggrEmployeeSettings employeeSets){
		
		// 通常勤務
		if (workingSystem == WorkingSystem.REGULAR_WORK){
			if (this.usageUnitSet.isEmployee()){
				if (employeeSets.getRegLaborTime().isPresent()){
					return Optional.of(employeeSets.getRegLaborTime().get());
				}
			}
			if (this.usageUnitSet.isWorkPlace()){
				for (val workplaceId : workplaceIds){
					if (this.wkpRegLaborTimeMap.containsKey(workplaceId)){
						if (this.wkpRegLaborTimeMap.get(workplaceId) != NullObject){
							return Optional.of((WorkingTimeSetting)this.wkpRegLaborTimeMap.get(workplaceId));
						}
					}
					else {
						val wkpLaborTimeOpt = require.regularLaborTimeByWorkplace(this.companyId, workplaceId);
						if (wkpLaborTimeOpt.isPresent()){
							this.wkpRegLaborTimeMap.put(workplaceId, wkpLaborTimeOpt.get());
							return Optional.of((WorkingTimeSetting)this.wkpRegLaborTimeMap.get(workplaceId));
						}
						else {
							this.wkpRegLaborTimeMap.put(workplaceId, NullObject);
						}
					}
				}
			}
			if (this.usageUnitSet.isEmployment()){
				if (this.empRegLaborTimeMap.containsKey(employmentCd)){
					if (this.empRegLaborTimeMap.get(employmentCd) != NullObject){
						return Optional.of((WorkingTimeSetting)this.empRegLaborTimeMap.get(employmentCd));
					}
				}
				else {
					val empLaborTimeOpt = require.regularLaborTimeByEmployment(this.companyId, employmentCd);
					if (empLaborTimeOpt.isPresent()){
						this.empRegLaborTimeMap.put(employmentCd, empLaborTimeOpt.get());
						return Optional.of((WorkingTimeSetting)this.empRegLaborTimeMap.get(employmentCd));
					}
					else {
						this.empRegLaborTimeMap.put(employmentCd, NullObject);
					}
				}
			}
			return Optional.ofNullable(this.comRegLaborTime);
		}
		
		// 変形労働
		if (workingSystem == WorkingSystem.VARIABLE_WORKING_TIME_WORK){
			if (this.usageUnitSet.isEmployee()){
				if (employeeSets.getIrgLaborTime().isPresent()){
					return Optional.of(employeeSets.getIrgLaborTime().get());
				}
			}
			if (this.usageUnitSet.isWorkPlace()){
				for (val workplaceId : workplaceIds){
					if (this.wkpIrgLaborTimeMap.containsKey(workplaceId)){
						if (this.wkpIrgLaborTimeMap.get(workplaceId) != NullObject){
							return Optional.of((WorkingTimeSetting)this.wkpIrgLaborTimeMap.get(workplaceId));
						}
					}
					else {
						val wkpLaborTimeOpt = require.deforLaborTimeByWorkplace(this.companyId, workplaceId);
						if (wkpLaborTimeOpt.isPresent()){
							this.wkpIrgLaborTimeMap.put(workplaceId, wkpLaborTimeOpt.get());
							return Optional.of((WorkingTimeSetting)this.wkpIrgLaborTimeMap.get(workplaceId));
						}
						else {
							this.wkpIrgLaborTimeMap.put(workplaceId, NullObject);
						}
					}
				}
			}
			if (this.usageUnitSet.isEmployment()){
				if (this.empIrgLaborTimeMap.containsKey(employmentCd)){
					if (this.empIrgLaborTimeMap.get(employmentCd) != NullObject){
						return Optional.of((WorkingTimeSetting)this.empIrgLaborTimeMap.get(employmentCd));
					}
				}
				else {
					val empLaborTimeOpt = require.deforLaborTimeByEmployment(this.companyId, employmentCd);
					if (empLaborTimeOpt.isPresent()){
						this.empIrgLaborTimeMap.put(employmentCd, empLaborTimeOpt.get());
						return Optional.of((WorkingTimeSetting)this.empIrgLaborTimeMap.get(employmentCd));
					}
					else {
						this.empIrgLaborTimeMap.put(employmentCd, NullObject);
					}
				}
			}
			return Optional.ofNullable(this.comIrgLaborTime);
		}
		
		return Optional.empty();
	}
	
	public static interface RequireM1 {
		
		Optional<RegularLaborTimeWkp> regularLaborTimeByWorkplace(String cid, String wkpId);

		Optional<RegularLaborTimeEmp> regularLaborTimeByEmployment(String cid, String employmentCode);

		Optional<DeforLaborTimeWkp> deforLaborTimeByWorkplace(String cid, String wkpId);

		Optional<DeforLaborTimeEmp> deforLaborTimeByEmployment(String cid, String employmentCode);
	}
	
	public static interface RequireM2 {
		
		Optional<PredetemineTimeSetting> predetemineTimeSetByWorkTimeCode(String companyId, String workTimeCode);
	}
	
	public static interface RequireM3 extends GetCommonSet.RequireM3 { }
	
	public static interface RequireM4 {
		
		Optional<WorkType> workType(String companyId, String workTypeCd);
	}
	
	public static interface RequireM5 extends GetVacationAddSet.RequireM1 {
		
		Optional<LegalTransferOrderSetOfAggrMonthly> monthLegalTransferOrderCalcSet(String companyId);
		
		List<RoleOvertimeWork> roleOvertimeWorks(String companyId);
		
		List<WorkdayoffFrame> workdayoffFrames(String companyId);
		
		Map<String, AggregateRoot> holidayAddtionSets(String companyId);
		
		Optional<UsageUnitSetting> usageUnitSetting(String companyId);
		
		Optional<RegularLaborTimeCom> regularLaborTimeByCompany(String companyId);
		
		Optional<DeforLaborTimeCom> deforLaborTimeByCompany(String companyId);
		
		Optional<ComRegulaMonthActCalSet> monthRegulaCalSetByCompany(String companyId);
		
		Optional<ComDeforLaborMonthActCalSet> monthDeforLaborCalSetByCompany(String companyId);
		
		Optional<ComFlexMonthActCalSet> monthFlexCalSetByCompany(String companyId);
		
		Optional<MonthlyAggrSetOfFlex> monthFlexAggrSet(String companyId);
		
		Optional<GetFlexPredWorkTime> flexPredWorkTime(String companyId);
		
		Optional<InsufficientFlexHolidayMnt> insufficientFlexHolidayMnt(String cid);
		
		Optional<FlexShortageLimit> flexShortageLimit(String companyId);
		
		Optional<OutsideOTSetting> outsideOTSetting(String companyId);
		
		Optional<RoundingSetOfMonthly> monthRoundingSet(String companyId);
		
		List<TotalTimes> totalTimes(String companyId);
		
		Optional<AgreementOperationSetting> agreementOperationSetting(String companyId);
		
		List<Closure> closure(String companyId);
	}
	
	public static interface RequireM6 extends RequireM5 {
		
		Optional<PayItemCountOfMonthly> monthPayItemCount(String companyId);
		
		List<OptionalItem> optionalItems(String companyId);
		
		List<EmpCondition> employmentConditions(String companyId, List<Integer> optionalItemNoList);
		
		List<Formula> formulas(String companyId);
		
		List<FormulaDispOrder> formulaDispOrder(String companyId);
		
		AnnualPaidLeaveSetting annualPaidLeaveSetting(String companyId);
		
		List<GrantHdTblSet> grantHdTblSets(String companyId);
		
		List<LengthServiceTbl> lengthServiceTbl(String companyId, String yearHolidayCode);
		
		Optional<RetentionYearlySetting> retentionYearlySetting(String companyId);
		
		List<EmptYearlyRetentionSetting> emptYearlyRetentionSet(String companyId);
		
		Optional<ComSubstVacation> comSubstVacation(String companyId);
		
		CompensatoryLeaveComSetting compensatoryLeaveComSetting(String companyId);
		
		Optional<OperationStartSetDailyPerform> dailyOperationStartSet(CompanyId companyId);
		
	}
}