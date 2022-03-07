package nts.uk.ctx.at.record.dom.dailyprocess.calc;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.val;
import nts.arc.layer.app.cache.CacheCarrier;
import nts.arc.layer.app.cache.DateHistoryCache;
import nts.arc.layer.app.cache.KeyDateHistoryCache;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.adapter.workplace.affiliate.AffWorkplaceAdapter;
import nts.uk.ctx.at.shared.dom.attendance.MasterShareBus;
import nts.uk.ctx.at.shared.dom.attendance.MasterShareBus.MasterShareContainer;
import nts.uk.ctx.at.shared.dom.common.TimeOfDay;
import nts.uk.ctx.at.shared.dom.remainingnumber.paymana.SEmpHistoryImport;
import nts.uk.ctx.at.shared.dom.remainingnumber.paymana.SysEmploymentHisAdapter;
import nts.uk.ctx.at.shared.dom.scherec.addsettingofworktime.AddSetting;
import nts.uk.ctx.at.shared.dom.scherec.addsettingofworktime.HolidayAddtionRepository;
import nts.uk.ctx.at.shared.dom.scherec.addsettingofworktime.AddSettingOfWorkingTime;
import nts.uk.ctx.at.shared.dom.scherec.addsettingofworktime.HourlyPaymentAdditionSet;
import nts.uk.ctx.at.shared.dom.scherec.addsettingofworktime.WorkDeformedLaborAdditionSet;
import nts.uk.ctx.at.shared.dom.scherec.addsettingofworktime.WorkFlexAdditionSet;
import nts.uk.ctx.at.shared.dom.scherec.addsettingofworktime.WorkRegularAdditionSet;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.bonuspay.primitives.BonusPaySettingCode;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.bonuspay.repository.BPSettingRepository;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.bonuspay.repository.BPTimesheetRepository;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.bonuspay.repository.SpecBPTimesheetRepository;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.bonuspay.setting.BonusPaySetting;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.bonuspay.setting.BonusPayTimesheet;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.bonuspay.setting.SpecBonusPayTimesheet;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.dailyattendancework.IntegrationOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.ManagePerCompanySet;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.ManagePerPersonDailySet;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.PredetermineTimeSetForCalc;
import nts.uk.ctx.at.shared.dom.scherec.dailyprocess.calc.FactoryManagePerPersonDailySet;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.flex.FlexMonthWorkTimeAggrSet;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.flex.com.ComFlexMonthActCalSetRepo;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.flex.emp.EmpFlexMonthActCalSet;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.flex.emp.EmpFlexMonthActCalSetRepo;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.flex.sha.ShaFlexMonthActCalSetRepo;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.flex.wkp.WkpFlexMonthActCalSet;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.flex.wkp.WkpFlexMonthActCalSetRepo;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.export.GetFlexAggrSet;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.timezone.outsideworktime.OverTimeSheet;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.personcostcalc.employeeunitpricehistory.EmployeeUnitPriceHistoryItem;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.personcostcalc.employeeunitpricehistory.EmployeeUnitPriceHistoryRepositoly;
import nts.uk.ctx.at.shared.dom.scherec.dailyprocess.calc.FactoryManagePerPersonDailySet;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.UsageUnitSetting;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.UsageUnitSettingRepository;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.algorithm.DailyStatutoryLaborTime;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.week.DailyUnit;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.week.defor.DeforLaborTimeCom;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.week.defor.DeforLaborTimeComRepo;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.week.defor.DeforLaborTimeEmp;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.week.defor.DeforLaborTimeEmpRepo;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.week.defor.DeforLaborTimeSha;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.week.defor.DeforLaborTimeShaRepo;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.week.defor.DeforLaborTimeWkp;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.week.defor.DeforLaborTimeWkpRepo;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.week.regular.RegularLaborTimeCom;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.week.regular.RegularLaborTimeComRepo;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.week.regular.RegularLaborTimeEmp;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.week.regular.RegularLaborTimeEmpRepo;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.week.regular.RegularLaborTimeSha;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.week.regular.RegularLaborTimeShaRepo;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.week.regular.RegularLaborTimeWkp;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.week.regular.RegularLaborTimeWkpRepo;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.CompensLeaveComSetRepository;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.CompensLeaveEmSetRepository;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.CompensatoryLeaveComSetting;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.CompensatoryLeaveEmSetting;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItem;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItemRepository;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;
import nts.uk.ctx.at.shared.dom.worktime.fixedset.FixedWorkSetting;
import nts.uk.ctx.at.shared.dom.worktime.fixedset.FixedWorkSettingRepository;
import nts.uk.ctx.at.shared.dom.worktime.flexset.FlexWorkSetting;
import nts.uk.ctx.at.shared.dom.worktime.flexset.FlexWorkSettingRepository;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlowWorkSetting;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlowWorkSettingRepository;
import nts.uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSetting;
import nts.uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSettingRepository;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSetting;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSettingRepository;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeCode;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.license.option.OptionLicense;

/**
 * @author kazuki_watanabe
 */
@Stateless
public class FactoryManagePerPersonDailySetImpl implements FactoryManagePerPersonDailySet {

	/** 労働時間と日数の設定の利用単位の設定 */
	@Inject
	private UsageUnitSettingRepository usageUnitSettingRepo;
	/** 所属職場履歴 */
	@Inject
	private AffWorkplaceAdapter affWorkplaceAdapter;
	/** 会社別通常勤務法定労働時間 */
	@Inject
	private RegularLaborTimeComRepo regularLaborTimeComRepo;
	/** 会社別変形労働法定労働時間 */
	@Inject
	private DeforLaborTimeComRepo deforLaborTimeComRepo;
	/** 職場別別通常勤務法定労働時間 */
	@Inject
	private RegularLaborTimeWkpRepo regularLaborTimeWkpRepo;

	/* 社員単価履歴 */
	@Inject
	private EmployeeUnitPriceHistoryRepositoly employeeUnitPriceHistoryRepositoly;

	@Inject
	private DeforLaborTimeWkpRepo deforLaborTimeWkpRepo;
	/** 雇用別通常勤務法定労働時間 */
	@Inject
	private RegularLaborTimeEmpRepo regularLaborTimeEmpRepo;
	/** 雇用別変形労働法定労働時間 */
	@Inject
	private DeforLaborTimeEmpRepo deforLaborTimeEmpRepo;
	/** 社員別通常勤務法定労働時間 */
	@Inject
	private RegularLaborTimeShaRepo regularLaborTimeShaRepo;
	/** 社員別変形労働法定労働時間 */
	@Inject
	private DeforLaborTimeShaRepo deforLaborTimeShaRepo;
	/** 労働条件項目 */
	@Inject
	private WorkingConditionItemRepository workingConditionItemRepo;
	/** 所定時間設定 */
	@Inject
	private PredetemineTimeSettingRepository predetemineTimeSetRepository;
	/** 代休管理設定（会社別） */
	@Inject
	private CompensLeaveComSetRepository compensLeaveComSetRepo;
	/** 雇用の代休管理設定 */
	@Inject
	private CompensLeaveEmSetRepository compensLeaveEmSetRepo;
	/** 就業時間帯の設定 */
	@Inject
	private WorkTimeSettingRepository workTimeSettingRepo;
	/** 固定勤務設定 */
	@Inject
	private FixedWorkSettingRepository fixedWorkSetRepo;
	/** 流動勤務設定 */
	@Inject
	private FlowWorkSettingRepository flowWorkSetRepo;
	/** フレックス勤務設定 */
	@Inject
	private FlexWorkSettingRepository flexWorkSetRepo;
	/** 勤務種類 */
	@Inject
	private WorkTypeRepository workTypeRepository;
	/** 会社別フレックス勤務集計方法 */
	@Inject
	private ComFlexMonthActCalSetRepo comFlexMonthActCalSetRepo;
	/** 職場別フレックス勤務集計方法 */
	@Inject
	private WkpFlexMonthActCalSetRepo wkpFlexMonthActCalSetRepo;
	/** 雇用別フレックス勤務集計方法 */
	@Inject
	private EmpFlexMonthActCalSetRepo empFlexMonthActCalSetRepo;
	/** 社員別フレックス勤務集計方法 */
	@Inject
	private ShaFlexMonthActCalSetRepo shaFlexMonthActCalSetRepo;
	/** 加給設定 */
	@Inject
	private BPSettingRepository bPSettingRepository;
	/** 加給時間帯設定 */
	@Inject
	private BPTimesheetRepository bPTimesheetRepository;
	/** 特定日加給時間帯設定 */
	@Inject
	private SpecBPTimesheetRepository specBPTimesheetRepository;
	/** 休暇加算設定 */
	@Inject
	private HolidayAddtionRepository hollidayAdditonRepository;
	/** 社員雇用履歴 */
	@Inject
	private SysEmploymentHisAdapter sysEmploymentHisAdapter;
	
	@Override
	public Optional<ManagePerPersonDailySet> create(String companyId, ManagePerCompanySet companySetting, IntegrationOfDaily daily, WorkingConditionItem nowWorkingItem ) {
		return internalCreate(companyId, daily, nowWorkingItem, companySetting.getUsageSetting(), companySetting.getShareContainer());
	}

	private Optional<ManagePerPersonDailySet> internalCreate(
			String companyId, 
			IntegrationOfDaily daily,
			WorkingConditionItem nowWorkingItem,
			Optional<UsageUnitSetting> usageUnitSet,
			MasterShareContainer<String> shareContainer) {
		
		try {
			RequireImpl require = new RequireImpl();
			
			// 法定労働時間
			DailyUnit dailyUnit = DailyStatutoryLaborTime.getDailyUnit(
					require,
					new CacheCarrier(),
					companyId,
					daily.getAffiliationInfor().getEmploymentCode().toString(),
					daily.getEmployeeId(),
					daily.getYmd(),
					nowWorkingItem.getLaborSystem(),
					usageUnitSet);

			if(dailyUnit == null || dailyUnit.getDailyTime() == null)
				dailyUnit = new DailyUnit(new TimeOfDay(0));
			
			// 加算設定
			AddSetting addSetting = this.getAddSetting(
					companyId,
					hollidayAdditonRepository.findByCompanyId(companyId),
					nowWorkingItem);
	
			// 加給
			Optional<BonusPaySettingCode> bpCode = daily.getAffiliationInfor().getBonusPaySettingCode();
			Optional<BonusPaySetting> bonusPaySetting = Optional.empty();
			if(bpCode.isPresent() && bpCode.get() != null ) {
				bonusPaySetting = this.bPSettingRepository.getBonusPaySetting(companyId, bpCode.get());
				List<BonusPayTimesheet> bonusPay = bPTimesheetRepository.getListTimesheet(companyId, bpCode.get());
				List<SpecBonusPayTimesheet> specBonusPay = specBPTimesheetRepository.getListTimesheet(companyId, bpCode.get());
				bonusPaySetting = bonusPaySetting.map(
						b -> BonusPaySetting.createFromJavaType(
								companyId,
								b.getCode().toString(),
								b.getName().toString(),
								bonusPay,
								specBonusPay));
			}
			
			/**　勤務種類 */
			val workType = require.workType(companyId, nowWorkingItem.getWorkCategory().getWorkType().getWeekdayTimeWTypeCode());
			if(!workType.isPresent()) {
				return Optional.empty();
			}
		
			/*平日時*/
			PredetermineTimeSetForCalc predetermineTimeSetByPersonWeekDay = this.getPredByPersonInfo(
					nowWorkingItem.getWorkCategory().getWorkTime().getWeekdayTime().getWorkTimeCode().get(), shareContainer, workType.get());
			
			// フレックス勤務基本設定
			Optional<FlexMonthWorkTimeAggrSet> flexBasicSet = Optional.empty();
			if (usageUnitSet.isPresent()){
				flexBasicSet = GetFlexAggrSet.flexWorkTimeAggrSet(
						require,
						new CacheCarrier(),
						companyId,
						daily.getAffiliationInfor().getEmploymentCode().v(),
						daily.getEmployeeId(),
						daily.getYmd(),
						usageUnitSet.get(),
						this.shaFlexMonthActCalSetRepo.find(companyId, daily.getEmployeeId()),
						this.comFlexMonthActCalSetRepo.find(companyId));
			}

			/*社員単価履歴*/
			Optional<EmployeeUnitPriceHistoryItem> unitPrice = this.employeeUnitPriceHistoryRepositoly.get(daily.getEmployeeId(), daily.getYmd());
			
			return Optional.of(new ManagePerPersonDailySet(daily.getYmd(), nowWorkingItem, dailyUnit,
								addSetting, bonusPaySetting, predetermineTimeSetByPersonWeekDay,
								flexBasicSet, require, unitPrice));
		}
		catch(RuntimeException e) {
			return Optional.empty();
		}
	}

	/**
	 * 加算設定の取得
	 * @param companyID 会社ID
	 * @param map 加算設定Map
	 * @param workingItem 労働条件項目
	 * @return 加算設定
	 */
	private AddSetting getAddSetting(String companyID, Map<String, AggregateRoot> map, WorkingConditionItem workingItem) {
		
		switch(workingItem.getLaborSystem()) {
		case REGULAR_WORK:
			if(workingItem.getHourlyPaymentAtr().isHourlyPay()) {
				AggregateRoot hourlyPaymentAdditionSet = map.get("hourlyPaymentAdditionSet");
				return hourlyPaymentAdditionSet != null
						?(HourlyPaymentAdditionSet) hourlyPaymentAdditionSet
						: new HourlyPaymentAdditionSet(companyID, AddSettingOfWorkingTime.emptyHolidayCalcMethodSet());
			}
			AggregateRoot workRegularAdditionSet = map.get("regularWork");
			return workRegularAdditionSet != null
					?(WorkRegularAdditionSet) workRegularAdditionSet
					: new WorkRegularAdditionSet(companyID, AddSettingOfWorkingTime.emptyHolidayCalcMethodSet());
		
		case FLEX_TIME_WORK:
			AggregateRoot workFlexAdditionSet = map.get("flexWork");
			return workFlexAdditionSet != null
					?(WorkFlexAdditionSet) workFlexAdditionSet
					: new WorkFlexAdditionSet(companyID, AddSettingOfWorkingTime.emptyHolidayCalcMethodSet());
			
		case VARIABLE_WORKING_TIME_WORK:
			AggregateRoot workDeformedLaborAdditionSet = map.get("irregularWork");
			return workDeformedLaborAdditionSet != null
					? (WorkDeformedLaborAdditionSet) workDeformedLaborAdditionSet
					: new WorkDeformedLaborAdditionSet(companyID, AddSettingOfWorkingTime.emptyHolidayCalcMethodSet());
		
		default:
			return new WorkDeformedLaborAdditionSet(companyID, AddSettingOfWorkingTime.emptyHolidayCalcMethodSet());
		}
	}
	
	/**
	 * 就業時間帯の所定時間帯を取得する
	 * （所定時間帯を計算用就業時間帯に変換して返してくれる計算処理専用メソッド）
	 * @param workTimeCode 就業時間帯コード
	 * @param shareContainer 共有コンテナ
	 * @param workType 勤務種類
	 * @return 計算用所定時間設定
	 */
	private PredetermineTimeSetForCalc getPredByPersonInfo(WorkTimeCode workTimeCode,
			MasterShareContainer<String> shareContainer, WorkType workType) {

		val predSetting = getPredetermineTimeSetFromShareContainer(shareContainer, AppContexts.user().companyId(),
				workTimeCode.toString());
		if (!predSetting.isPresent())
			throw new RuntimeException("predetermineedSetting is null");
		return PredetermineTimeSetForCalc.convertFromAggregatePremiumTime(predSetting.get(), workType);
	}
	
	/**
	 * 共有コンテナを使った所定時間帯設定の取得
	 * @param shareContainer 共有コンテナ
	 * @param companyId 会社ID
	 * @param workTimeCode 終了時間帯コード
	 * @return 所定時間設定
	 */
	private Optional<PredetemineTimeSetting> getPredetermineTimeSetFromShareContainer(
			MasterShareContainer<String> shareContainer, String companyId, String workTimeCode) {
		Optional<PredetemineTimeSetting> predSet = shareContainer.getShared("PredetemineSet" + workTimeCode,
				() -> predetemineTimeSetRepository.findByWorkTimeCode(companyId, workTimeCode));
		if (predSet.isPresent()) {
			return Optional.of(predSet.get().clone());
		}
		return Optional.empty();
	}

	@Override
	public Optional<ManagePerPersonDailySet> create(String companyId, String sid, GeneralDate ymd,
			IntegrationOfDaily daily) {
		
		RequireImpl require = new RequireImpl();
		Optional<WorkingConditionItem> workingItem = require.workingConditionItem(sid, ymd);
		if(!workingItem.isPresent()) {
			return Optional.empty();
		}
		Optional<UsageUnitSetting> usageSetting = require.usageUnitSetting(companyId);
		MasterShareContainer<String> shareContainer = MasterShareBus.open();
		
		val personDailySet = internalCreate(companyId, daily, workingItem.get(), usageSetting, shareContainer);
		
		shareContainer.clearAll();
		
		return personDailySet;
	}

	private class RequireImpl implements Require {
		
		private final KeyDateHistoryCache<String, SEmpHistoryImport> historyCache =
				KeyDateHistoryCache.incremental((employeeId, date) ->
				sysEmploymentHisAdapter.findSEmpHistBySid(AppContexts.user().companyId(), employeeId, date)
				.map(h -> DateHistoryCache.Entry.of(h.getPeriod(), h)));
	
		public RequireImpl(){

		}
		
		@Override
		public Optional<UsageUnitSetting> usageUnitSetting(String companyId) {
			return usageUnitSettingRepo.findByCompany(companyId);
		}
		
		@Override
		public List<String> getCanUseWorkplaceForEmp(CacheCarrier cacheCarrier, String companyId, String employeeId,
				GeneralDate baseDate) {
			return affWorkplaceAdapter.findAffiliatedWorkPlaceIdsToRootRequire(cacheCarrier, companyId, employeeId, baseDate);
		}
		
		@Override
		public Optional<RegularLaborTimeCom> regularLaborTimeByCompany(String companyId) {
			return regularLaborTimeComRepo.find(companyId);
		}
		
		@Override
		public Optional<DeforLaborTimeCom> deforLaborTimeByCompany(String companyId) {
			return deforLaborTimeComRepo.find(companyId);
		}
		
		@Override
		public Optional<RegularLaborTimeWkp> regularLaborTimeByWorkplace(String cid, String wkpId) {
			return regularLaborTimeWkpRepo.find(cid, wkpId);
		}
		
		@Override
		public Optional<DeforLaborTimeWkp> deforLaborTimeByWorkplace(String cid, String wkpId) {
			return deforLaborTimeWkpRepo.find(cid, wkpId);
		}
		
		@Override
		public Optional<RegularLaborTimeEmp> regularLaborTimeByEmployment(String cid, String employmentCode) {
			return regularLaborTimeEmpRepo.findById(cid, employmentCode);
		}
		
		@Override
		public Optional<DeforLaborTimeEmp> deforLaborTimeByEmployment(String cid, String employmentCode) {
			return deforLaborTimeEmpRepo.find(cid, employmentCode);
		}
		
		@Override
		public Optional<RegularLaborTimeSha> regularLaborTimeByEmployee(String Cid, String EmpId) {
			return regularLaborTimeShaRepo.find(Cid, EmpId);
		}
		
		@Override
		public Optional<DeforLaborTimeSha> deforLaborTimeByEmployee(String cid, String empId) {
			return deforLaborTimeShaRepo.find(cid, empId);
		}
		
		@Override
		public Optional<WorkingConditionItem> workingConditionItem(String sid, GeneralDate baseDate) {
			return workingConditionItemRepo.getBySidAndStandardDate(sid, baseDate);
		}
		
		@Override
		public Optional<PredetemineTimeSetting> predetemineTimeSetting(String companyId, WorkTimeCode workTimeCode) {
			return predetemineTimeSetRepository.findByWorkTimeCode(companyId, workTimeCode.v());
		}
		
		@Override
		public Optional<CompensatoryLeaveComSetting> compensatoryLeaveComSetting(String companyId) {
			return Optional.ofNullable(compensLeaveComSetRepo.find(companyId));
		}
		
		@Override
		public Optional<CompensatoryLeaveEmSetting> compensatoryLeaveEmSetting(String companyId,
				String employmentCode) {
			return Optional.ofNullable(compensLeaveEmSetRepo.find(companyId, employmentCode));
		}
		
		@Override
		public Optional<SEmpHistoryImport> getSEmpHistoryImport(String employeeId, GeneralDate baseDate) {
			return this.historyCache.get(employeeId, baseDate);
		}
		
		@Override
		public Optional<WorkTimeSetting> workTimeSetting(String companyId, WorkTimeCode workTimeCode) {
			return workTimeSettingRepo.findByCode(companyId, workTimeCode.v());
		}
		
		@Override
		public Optional<FixedWorkSetting> fixedWorkSetting(String companyId, WorkTimeCode workTimeCode) {
			return fixedWorkSetRepo.findByKey(companyId, workTimeCode.v());
		}
		
		@Override
		public Optional<FlowWorkSetting> flowWorkSetting(String companyId, WorkTimeCode workTimeCode) {
			return flowWorkSetRepo.find(companyId, workTimeCode.v());
		}
		
		@Override
		public Optional<FlexWorkSetting> flexWorkSetting(String companyId, WorkTimeCode workTimeCode) {
			return flexWorkSetRepo.find(companyId, workTimeCode.v());
		}
		
		@Override
		public Optional<WorkType> workType(String companyId, WorkTypeCode workTypeCode) {
			return workTypeRepository.findByPK(companyId, workTypeCode.v());
		}
		
		@Override
		public Optional<WkpFlexMonthActCalSet> wkpFlexMonthActCalSet(String companyId, String workplaceId) {
			return wkpFlexMonthActCalSetRepo.find(companyId, workplaceId);
		}
		
		@Override
		public Optional<EmpFlexMonthActCalSet> empFlexMonthActCalSet(String companyId, String employmentCode) {
			return empFlexMonthActCalSetRepo.find(companyId, employmentCode);
		}

		@Override
		public OptionLicense getOptionLicense() {
			return AppContexts.optionLicense();
		}

	}
}
