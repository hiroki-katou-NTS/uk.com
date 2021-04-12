package nts.uk.ctx.at.record.dom.workrecord.erroralarm.schedule.monthly.algorithm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.util.Strings;

import lombok.val;
import nts.arc.layer.app.cache.CacheCarrier;
import nts.arc.task.parallel.ManagedParallelWithContext;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.arc.time.YearMonth;
import nts.arc.time.calendar.period.DatePeriod;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.record.dom.adapter.employment.SyEmploymentAdapter;
import nts.uk.ctx.at.record.dom.adapter.employment.SyEmploymentImport;
import nts.uk.ctx.at.record.dom.adapter.workrule.closure.ClosureAdapter;
import nts.uk.ctx.at.record.dom.adapter.workrule.closure.ClosureDateOfEmploymentImport;
import nts.uk.ctx.at.record.dom.adapter.workrule.closure.PresentClosingPeriodImport;
import nts.uk.ctx.at.record.dom.adapter.workrule.closure.WorkDayAdapter;
import nts.uk.ctx.at.record.dom.adapter.workschedule.WorkScheduleWorkInforAdapter;
import nts.uk.ctx.at.record.dom.adapter.workschedule.WorkScheduleWorkInforImport;
import nts.uk.ctx.at.record.dom.remainingnumber.annualleave.export.GetAnnAndRsvRemNumWithinPeriod;
import nts.uk.ctx.at.record.dom.remainingnumber.annualleave.export.param.AggrResultOfAnnAndRsvLeave;
import nts.uk.ctx.at.record.dom.remainingnumber.annualleave.export.param.AggrResultOfAnnualLeave;
import nts.uk.ctx.at.record.dom.require.RecordDomRequireService;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.condition.attendanceitem.CheckedCondition;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.condition.attendanceitem.CompareRange;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.condition.attendanceitem.CompareSingleValue;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.enums.CompareOperatorText;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.enums.ConvertCompareTypeToText;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.mastercheck.algorithm.StatusOfEmployeeAdapterAl;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.mastercheck.algorithm.WorkPlaceHistImportAl;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.mastercheck.algorithm.WorkPlaceIdAndPeriodImportAl;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.schedule.daily.algorithm.OutputCheckResult;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.schedule.monthly.DayCheckCond;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.schedule.monthly.ExtractionCondScheduleMonth;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.schedule.monthly.ExtractionCondScheduleMonthRepository;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.schedule.monthly.FixedExtractionSMonCon;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.schedule.monthly.FixedExtractionSMonConRepository;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.schedule.monthly.FixedExtractionSMonItems;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.schedule.monthly.FixedExtractionSMonItemsRepository;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.schedule.monthly.PublicHolidayCheckCond;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.schedule.monthly.ScheMonCheckService;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.schedule.monthly.TypeOfDays;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.schedule.monthly.TypeOfTime;
import nts.uk.ctx.at.record.dom.workrecord.errorsetting.algorithm.CompareValueRangeChecking;
import nts.uk.ctx.at.shared.dom.adapter.attendanceitemname.AttendanceItemNameAdapter;
import nts.uk.ctx.at.shared.dom.adapter.employment.ShareEmploymentAdapter;
import nts.uk.ctx.at.shared.dom.adapter.employment.SharedSidPeriodDateEmploymentImport;
import nts.uk.ctx.at.shared.dom.alarmList.extractionResult.AlarmListCheckInfor;
import nts.uk.ctx.at.shared.dom.alarmList.extractionResult.AlarmListCheckType;
import nts.uk.ctx.at.shared.dom.alarmList.extractionResult.ExtractionAlarmPeriodDate;
import nts.uk.ctx.at.shared.dom.alarmList.extractionResult.ExtractionResultDetail;
import nts.uk.ctx.at.shared.dom.alarmList.extractionResult.ResultOfEachCondition;
import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.dailyattdcal.converter.DailyRecordShareFinder;
import nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.common.Year;
import nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.company.CompanyMonthDaySetting;
import nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.company.CompanyMonthDaySettingRepository;
import nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.configuration.PublicHolidayManagementUsageUnit;
import nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.configuration.PublicHolidayManagementUsageUnitRepository;
import nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.configuration.PublicHolidaySetting;
import nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.configuration.PublicHolidaySettingRepository;
import nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.employee.EmployeeMonthDaySetting;
import nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.employee.EmployeeMonthDaySettingRepository;
import nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.employment.EmploymentMonthDaySetting;
import nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.employment.EmploymentMonthDaySettingRepository;
import nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.workplace.WorkplaceMonthDaySetting;
import nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.workplace.WorkplaceMonthDaySettingRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.export.InterimRemainMngMode;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.dailyattendancework.IntegrationOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.flex.CarryforwardSetInShortageFlex;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.flex.com.ComFlexMonthActCalSet;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.flex.com.ComFlexMonthActCalSetRepo;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.flex.emp.EmpFlexMonthActCalSet;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.flex.emp.EmpFlexMonthActCalSetRepo;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.flex.sha.ShaFlexMonthActCalSet;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.flex.sha.ShaFlexMonthActCalSetRepo;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.flex.wkp.WkpFlexMonthActCalSet;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.flex.wkp.WkpFlexMonthActCalSetRepo;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.AttendanceTimeOfMonthly;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.AttendanceTimeOfMonthlyRepository;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.vacation.annualleave.AnnualLeaveRemainingNumber;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.UsageUnitSetting;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.UsageUnitSettingRepository;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.monunit.MonthlyWorkTimeSetCom;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.monunit.MonthlyWorkTimeSetEmp;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.monunit.MonthlyWorkTimeSetRepo;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.monunit.MonthlyWorkTimeSetSha;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.monunit.MonthlyWorkTimeSetWkp;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingCondition;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItem;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItemRepository;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionRepository;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingSystem;
import nts.uk.ctx.at.shared.dom.workrule.closure.Closure;
import nts.uk.ctx.at.shared.dom.workrule.closure.service.ClosureIdPresentClosingPeriod;
import nts.uk.ctx.at.shared.dom.workrule.closure.service.ClosureService;
import nts.uk.ctx.at.shared.dom.workrule.closure.service.GetClosureIdPresentClosingPeriods;
import nts.uk.ctx.at.shared.dom.worktype.DailyWork;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeClassification;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeRepository;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeUnit;
import nts.uk.ctx.at.shared.dom.worktype.algorithm.JudgmentWorkTypeService;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.i18n.TextResource;

@Stateless
public class ScheMonCheckServiceImpl implements ScheMonCheckService {
	@Inject
	private ManagedParallelWithContext parallelManager;
	@Inject
	private AttendanceItemNameAdapter attendanceItemNameAdapter;
	@Inject
	private DailyRecordShareFinder dailyRecordShareFinder;
	@Inject
	private WorkScheduleWorkInforAdapter workScheduleWorkInforAdapter;
	@Inject
    private AttendanceTimeOfMonthlyRepository attendanceTimeOfMonthlyRepo;
	@Inject
	private SyEmploymentAdapter syEmploymentAdapter;
	@Inject
	private FixedExtractionSMonItemsRepository fixedExtractionSMonItemsRepository;
	@Inject
	private FixedExtractionSMonConRepository fixedExtractSMonthlyConRepository;
	@Inject
	private ExtractionCondScheduleMonthRepository extraCondScheMonRepository;
	@Inject
    private ShareEmploymentAdapter shareEmploymentAdapter;
	@Inject
	private WorkDayAdapter workDayAdapter;
	@Inject
	private GetClosureIdPresentClosingPeriods getClosureIdPresentClosingPeriods;
	@Inject
	private WorkTypeRepository workTypeRep;
	@Inject
    private PublicHolidaySettingRepository publicHolidaySettingRepo;
	@Inject
	private EmploymentMonthDaySettingRepository employmentMonthDaySettingRepo;
	@Inject
	private EmployeeMonthDaySettingRepository employeeMonthDaySettingRepo;
	@Inject
	private WorkplaceMonthDaySettingRepository workplaceMonthDaySettingRepo;
	@Inject
	private CompanyMonthDaySettingRepository companyMonthDaySettingRepo;
	@Inject
	private UsageUnitSettingRepository usageUnitSettingRepository;
	@Inject
    private MonthlyWorkTimeSetRepo monthlyWorkTimeSetRepo;
	@Inject
	private EmpFlexMonthActCalSetRepo empFlexMonthActCalSetRepo;
	@Inject
	private WkpFlexMonthActCalSetRepo wkpFlexMonthActCalSetRepo;
	@Inject
	private ShaFlexMonthActCalSetRepo shaFlexMonthActCalSetRepo;
	@Inject
	private ComFlexMonthActCalSetRepo comFlexMonthActCalSetRepo;
	@Inject
	private WorkingConditionItemRepository workingConditionItemRepository;
	@Inject
	private WorkingConditionRepository workingConditionRepository;
	@Inject
	private CompareValueRangeChecking compareValueRangeChecking;
	@Inject
	private ConvertCompareTypeToText convertComparaToText;
	@Inject
	private GetNumberOfDayHolidayService numberOfDayHolidayService;
	@Inject
	private CalNumOfPubHolidayService calNumOfPubHolidayService;
	@Inject 
	private PublicHolidayManagementUsageUnitRepository publicHolidayManagementUsageUnitRepository;
	@Inject
	private CheckScheTimeAndTotalWorkingService scheTimeAndTotalWorkingService;
	@Inject
	private RecordDomRequireService requireService;
	@Inject
	private ClosureAdapter closureAdapter;
	@Inject
	private CalculateVacationDayService calculateVacationDayService;
	@Inject
	private CalMonWorkingTimeService calMonWorkingTimeService;
	@Inject
	private JudgmentWorkTypeService judgmentWorkTypeService;
	
	@Override
	public void extractScheMonCheck(String cid, List<String> lstSid, DatePeriod dPeriod, String errorCheckId,
			String listFixedItemId, String listOptionalItemId, List<WorkPlaceHistImportAl> wplByListSidAndPeriod,
			List<StatusOfEmployeeAdapterAl> lstStatusEmp, List<ResultOfEachCondition> lstResultCondition,
			List<AlarmListCheckInfor> lstCheckType, Consumer<Integer> counter, Supplier<Boolean> shouldStop) {
		String contractCode = AppContexts.user().contractCode();
		// チェックする前データを準備
		ScheMonPrepareData prepareData = prepareDataBeforeChecking(contractCode, cid, lstSid, dPeriod, errorCheckId, listFixedItemId, listOptionalItemId);
		
		parallelManager.forEach(CollectionUtil.partitionBySize(lstSid, 100), emps -> {
			synchronized (this) {
				if (shouldStop.get()) {
					return;
				}
			}
			
			// Input．List＜社員ID＞をループ
			for (String sid: lstSid) {
				// Input．期間の開始月から終了月まで１ヶ月ごとにループ
				List<YearMonth> months = dPeriod.yearMonthsBetween();
				for(int mon = 0; mon < months.size(); mon++) {
					YearMonth exMon = months.get(mon);
					
					// 日別実績を探す
					// 条件： 
					// ・社員ID　＝　ループ中の社員ID
					// ・ループ中の年月の開始日＜＝年月日＜＝ループ中の年月の終了日
					// Output: List＜日別実績＞
					List<IntegrationOfDaily> integrationOfDailys = prepareData.getListIntegrationDai().stream()
							.filter(x -> x.getEmployeeId().equals(sid) 
									&& x.getYmd().afterOrEquals(exMon.firstGeneralDate()) && x.getYmd().beforeOrEquals(exMon.lastGeneralDate()))
							.collect(Collectors.toList());
					// 勤務予定を探す
					// 条件：
					// ・社員ID　＝　ループ中の社員ID
					// ・ループ中の年月の開始日＜＝年月日＜＝ループ中の年月の終了日
					// Output: List＜勤務予定＞
					List<WorkScheduleWorkInforImport> workSchedules = prepareData.getWorkScheduleWorkInfos().stream()
							.filter(x -> x.getEmployeeId().equals(sid) 
									&& x.getYmd().afterOrEquals(exMon.firstGeneralDate()) && x.getYmd().beforeOrEquals(exMon.lastGeneralDate()))
							.collect(Collectors.toList());
					
					// 月別実績を探す
					// 条件：
					// ・社員ID　＝　ループ中の社員ID
					// ・年月　＝　ループ中の年月
					// Output: 月別実績
					Optional<AttendanceTimeOfMonthly> attendanceTimeOfMonOpt = prepareData.getAttendanceTimeOfMonthlies().stream()
							.filter(x -> x.getEmployeeId().equals(sid) && x.getYearMonth().equals(exMon))
							.findFirst();
					AttendanceTimeOfMonthly attendanceTimeOfMon = null;
					if(!attendanceTimeOfMonOpt.isPresent()) {
						attendanceTimeOfMon = attendanceTimeOfMonOpt.get();
					}

					String wplId = "";
					Optional<WorkPlaceHistImportAl> optWorkPlaceHistImportAl = wplByListSidAndPeriod.stream().filter(x -> x.getEmployeeId().equals(sid)).findFirst();
					if(optWorkPlaceHistImportAl.isPresent()) {
						Optional<WorkPlaceIdAndPeriodImportAl> optWorkPlaceIdAndPeriodImportAl = optWorkPlaceHistImportAl.get().getLstWkpIdAndPeriod().stream()
								.filter(x -> x.getDatePeriod().start().beforeOrEquals(exMon.firstGeneralDate()) 
										&& x.getDatePeriod().end().afterOrEquals(exMon.lastGeneralDate())).findFirst();
						if(optWorkPlaceIdAndPeriodImportAl.isPresent()) {
							wplId = optWorkPlaceIdAndPeriodImportAl.get().getWorkplaceId();
						}
					}
					
					// チェック条件の種類ごとにチェックを行う
					
					// 任意の場合
					OutputCheckResult tab2 = extractConditionTab2(cid, sid, wplId, exMon, prepareData, wplByListSidAndPeriod, integrationOfDailys, workSchedules, attendanceTimeOfMon);
					if (!tab2.getLstResultCondition().isEmpty()) {
						lstResultCondition.addAll(tab2.getLstResultCondition());
					}
					
					if (!tab2.getLstCheckType().isEmpty()) {
						lstCheckType.addAll(tab2.getLstCheckType());
					}
					
					// 固定の場合
					// 優先使用のアラーム値を作成する
					OutputCheckResult tab3 = extractConditionTab3(cid, sid, wplId, exMon, prepareData, wplByListSidAndPeriod, integrationOfDailys, workSchedules, attendanceTimeOfMon);
					if (!tab3.getLstResultCondition().isEmpty()) {
						lstResultCondition.addAll(tab3.getLstResultCondition());
					}
					
					if (!tab3.getLstCheckType().isEmpty()) {
						lstCheckType.addAll(tab3.getLstCheckType());
					}
				}
			}
			
			synchronized (this) {
				counter.accept(emps.size());
			}
		});
	}
	
	/**
	 * Get data before checking
	 */
	private ScheMonPrepareData prepareDataBeforeChecking(String contractCode, String cid, List<String> lstSid,
			DatePeriod dPeriod, String errorCheckId, String listFixedItem, String listOptionalItem) {
		YearMonth ym = dPeriod.yearMonthsBetween().get(0);
		Year year = new Year(ym.year());
		
		// スケジュールの勤怠項目を取得する
		Map<Integer, String> attendanceItems = attendanceItemNameAdapter.getAttendanceItemNameAsMapName(cid, 0);
		
		// 社員ID(List)、期間を設定して勤務予定を取得する
		List<WorkScheduleWorkInforImport> workScheduleWorkInfos = workScheduleWorkInforAdapter.getBy(lstSid, dPeriod);
		
		// 社員と期間を条件に日別実績を取得する
		List<IntegrationOfDaily> empDailyPerformance = dailyRecordShareFinder.findByListEmployeeId(lstSid, dPeriod);
		
		// 社員ID（List）、期間を設定して月別実績を取得する
		List<AttendanceTimeOfMonthly> attendanceTimeOfMonthlies = attendanceTimeOfMonthlyRepo.findBySidsAndYearMonths(lstSid, dPeriod.yearMonthsBetween());
				
		// アルゴリズム「社員IDと基準日から社員の雇用コードを取得」を実行する。
		Map<String, List<SyEmploymentImport>> empHistory = this.syEmploymentAdapter.finds(lstSid, dPeriod);
        
		Map<String, Map<DatePeriod, WorkingConditionItem>> empWorkingCondItem = new HashMap<>();
		List<FixedExtractionSMonCon> fixedScheConds = new ArrayList<>();
		List<FixedExtractionSMonItems> fixedScheCondItems = new ArrayList<>();
		List<ExtractionCondScheduleMonth> scheCondMonths = new ArrayList<>();
		
		if (!StringUtils.isEmpty(listFixedItem)) {
			// スケジュール月次の固有抽出条件を取得する
			fixedScheConds = this.fixedExtractSMonthlyConRepository.getScheFixCond(contractCode, cid, listFixedItem);
			
			// スケジュール月次の固有抽出項目を取得
			fixedScheCondItems = this.fixedExtractionSMonItemsRepository.getAll();
		}
		
		if (!StringUtils.isEmpty(listOptionalItem)) {
			// スケジュール月次の任意抽出条件を取得する
			scheCondMonths = this.extraCondScheMonRepository.getScheAnyCond(contractCode, cid, listOptionalItem);
		}
		
		Optional<PublicHolidaySetting> publicHdSettingOpt = Optional.empty();
		Optional<PublicHolidayManagementUsageUnit> publicHolidayManagementUsageUnit = Optional.empty();
		List<EmploymentMonthDaySetting> optEmploymentMonthDaySetting = new ArrayList<>();
		List<EmployeeMonthDaySetting> optEmployeeMonthDaySetting = new ArrayList<>();
		List<WorkplaceMonthDaySetting> optWorkplaceMonthDaySetting = new ArrayList<>();
		Optional<CompanyMonthDaySetting> optCompanyMonthDaySetting = Optional.empty();
		Optional<UsageUnitSetting> usageUnitSetting = Optional.empty();
		List<MonthlyWorkTimeSetCom> monthlyWorkTimeSetComs = new ArrayList<>();
		List<MonthlyWorkTimeSetEmp> monthlyWorkTimeSetEmps = new ArrayList<>();
		List<MonthlyWorkTimeSetWkp> monthlyWorkTimeSetWkps = new ArrayList<>();
		List<MonthlyWorkTimeSetSha> monthlyWorkTimeSetShas = new ArrayList<>();
		List<EmpFlexMonthActCalSet> empFlexMonthActCalSets = new ArrayList<>();
		List<WkpFlexMonthActCalSet> wkpFlexMonthActCalSets = new ArrayList<>();
		List<ShaFlexMonthActCalSet> shaFlexMonthActCalSets = new ArrayList<>();
		Optional<ComFlexMonthActCalSet> comFlexMonthActCalSetOpt = Optional.empty();
		
		// 任意抽出条件のデータを取得
		for (ExtractionCondScheduleMonth scheCondMonth: scheCondMonths) {
			// チェック項目の種類　＝　対比　AND　チェックする対比　＝　所定公休日数比
			if (scheCondMonth.isPublicHolidayWorkingDay()) {
				// ドメインモデル「公休設定」を取得する。
		        publicHdSettingOpt = publicHolidaySettingRepo.get(cid);
		        
		        // ドメインモデル「公休利用単位設定」を取得
		        // 会社ID　＝　Input．会社ID
		        // Output: 公休利用単位設定
		        publicHolidayManagementUsageUnit = publicHolidayManagementUsageUnitRepository.get(cid);
		        
		        // TODO ドメインモデル「雇用月間日数設定」を取得する
		        // 条件：
		        // ・会社ID　＝　Input．社員ID
		        // Output: List＜雇用月間日数設定＞
		        optEmploymentMonthDaySetting = this.employmentMonthDaySettingRepo.findByCompany(new CompanyId(cid));
		        
		        // TODO  ドメインモデル「社員月間日数設定」を取得する
		        // 条件：
		        // ・会社ID　＝　Input．会社ID
		        // ・社員ID　＝　Input．List＜社員ID＞
		        // Output: List＜社員月間日数設定＞
		        optEmployeeMonthDaySetting = this.employeeMonthDaySettingRepo.findByYear(new CompanyId(cid), lstSid, year);
		        
		        // TODO ドメインモデル「職場月間日数設定」を取得する
		        // 条件：
		        // ・会社ID　＝　Input．会社ID
		        // Output: List＜職場月間日数設定＞
		        optWorkplaceMonthDaySetting = this.workplaceMonthDaySettingRepo.findByYear(new CompanyId(cid), year);
		        
		        // TODO ドメインモデル「会社月間日数設定」を取得する
		        // 条件：
		        // ・会社ID　＝　Input．会社ID
		        // ・公休管理年　＝　List＜Input．期間．年＞
		        // Output: 会社月間日数設定
				optCompanyMonthDaySetting = this.companyMonthDaySettingRepo.findByYear(new CompanyId(cid), year);
			}
			
			// チェック項目の種類　＝　対比　AND　チェックする対比　！＝　所定公休日数比
			boolean isNotPublicHolidayWorkingDay = scheCondMonth.isContrast() && !scheCondMonth.isPublicHolidayWorkingDay();
			if (isNotPublicHolidayWorkingDay) {
				// ドメインモデル「労働時間と日数の設定の利用単位の設定」を取得
				usageUnitSetting = usageUnitSettingRepository.findByCompany(cid);
				
				// TODO ドメインモデル「会社別月単位労働時間」を取得
				// 条件：
				// ・会社ID　＝　Input．会社ID
				// Output: 会社別月単位労働時間
				monthlyWorkTimeSetComs = monthlyWorkTimeSetRepo.findCompany(cid, ym.year());
				
				// TODO ドメインモデル「雇用別月単位労働時間」を取得
				// 条件：
				// ・会社ID　＝　Input．会社ID
				// Output: List＜雇用別月単位労働時間＞
				monthlyWorkTimeSetEmps = monthlyWorkTimeSetRepo.findEmployment(cid);
				
				// TODO ドメインモデル「職場別月単位労働時間」を取得する。
				// 条件：
				// ・会社ID　＝　Input．会社ID
				// Output: List＜職場別月単位労働時間＞
				monthlyWorkTimeSetWkps = monthlyWorkTimeSetRepo.findWorkplace(cid);
		        
		        // TODO ドメインモデル「社員別月単位労働時間」を取得
		        // 条件：
		        // ・会社ID　＝　Input．会社ID
		        // ・社員ID　＝　Input．List＜社員ID＞
		        // List＜社員別月単位労働時間＞
				monthlyWorkTimeSetShas = monthlyWorkTimeSetRepo.findMonWorkTimeShaEmployee(cid, lstSid);
		        
		        // ドメインモデル「雇用別フレックス勤務集計方法」を取得
		        // 条件：
		        // ・会社ID　＝　Input．会社ID
		        // Output: ・List<雇用別フレックス勤務集計方法＞
				empFlexMonthActCalSets = empFlexMonthActCalSetRepo.findEmpFlexMonthByCid(cid);
		        
		        // ドメインモデル「職場別フレックス勤務集計方法」を取得
		        // 条件：
		        // ・会社ID　＝　Input．会社ID
		        // Output: List<職場別フレックス勤務集計方法＞
				wkpFlexMonthActCalSets = wkpFlexMonthActCalSetRepo.findByCid(cid);
		        
		        // ドメインモデル「社員別フレックス勤務集計方法」を取得
		        // 条件：
		        // ・会社ID　＝　Input．会社ID
		        // ・社員ID　＝　Input．List＜社員ID＞
		        // Output: List<社員別フレックス勤務集計方法＞
				shaFlexMonthActCalSets = shaFlexMonthActCalSetRepo.findAllShaByCid(cid);
				
				comFlexMonthActCalSetOpt = comFlexMonthActCalSetRepo.find(cid);
			}
			
			// (チェック項目の種類　＝　対比　AND　チェックする対比　!＝　所定公休日数比)　OR　チェック項目の種類　＝　日数
			if (isNotPublicHolidayWorkingDay || scheCondMonth.isNumberDay()) {
				List<WorkingCondition> listWorkingCondition = workingConditionRepository
						.getBySidsAndDatePeriod(lstSid, dPeriod);
				
				// 社員ID（List）と期間から労働条件を取得する
				List<WorkingConditionItem> workingConditionItems = workingConditionItemRepository.getBySidsAndDatePeriod(lstSid, dPeriod);
				
				// ドメインモデル「労働条件項目」を取得
				Map<String, WorkingConditionItem> mapWorkingCondtionItem = workingConditionItems.stream()
						.collect(Collectors.toMap(WorkingConditionItem::getHistoryId, x -> x));
				
				// 9, 労働条件を作成
				listWorkingCondition.forEach(x -> {
					Map<DatePeriod, WorkingConditionItem> mapWorkingCondItems = new HashMap<>();
					x.getDateHistoryItem().forEach(y -> {
						WorkingConditionItem workingConditionItem = mapWorkingCondtionItem.get(y.identifier());
						mapWorkingCondItems.put(y.span(), workingConditionItem);
					});
					empWorkingCondItem.put(x.getEmployeeId(), mapWorkingCondItems);
				});
			}
		}		
		
		// 社員ID（List）と指定期間から社員の雇用履歴を取得
		List<SharedSidPeriodDateEmploymentImport> lstEmploymentHis = this.shareEmploymentAdapter.getEmpHistBySidAndPeriodRequire(new CacheCarrier(), lstSid, dPeriod);
		
		// [NO.642]指定した会社の雇用毎の締め日を取得する
		List<ClosureDateOfEmploymentImport> closureDateOfEmps = workDayAdapter.getClosureDate(cid);
		
		// 全ての締めの処理年月と締め期間を取得する
		List<ClosureIdPresentClosingPeriod> closingPeriods = getClosureIdPresentClosingPeriods.get(cid);
		
		// <<Public>> 勤務種類をすべて取得する
		List<WorkType> listWorkType = workTypeRep.findByCompanyId(cid);
		
		return ScheMonPrepareData.builder()
				.attendanceItems(attendanceItems)
				.workScheduleWorkInfos(workScheduleWorkInfos)
				.listIntegrationDai(empDailyPerformance)
				.attendanceTimeOfMonthlies(attendanceTimeOfMonthlies)
				.fixedScheCondItems(fixedScheCondItems)
				.fixedScheConds(fixedScheConds)
				.scheCondMonths(scheCondMonths)
				.lstEmploymentHis(lstEmploymentHis)
				.listWorkType(listWorkType)
				.empWorkingCondItem(empWorkingCondItem)
				.closingPeriods(closingPeriods)
				.closureDateOfEmps(closureDateOfEmps)
				.publicHdSettingOpt(publicHdSettingOpt)
				.publicHolidayManagementUsageUnit(publicHolidayManagementUsageUnit)
				.employmentMonthDaySetting(optEmploymentMonthDaySetting)
				.employeeMonthDaySetting(optEmployeeMonthDaySetting)
				.workplaceMonthDaySetting(optWorkplaceMonthDaySetting)
				.companyMonthDaySetting(optCompanyMonthDaySetting)
				.usageUnitSetting(usageUnitSetting)
				.monthlyWorkTimeSetComs(monthlyWorkTimeSetComs)
				.monthlyWorkTimeSetEmps(monthlyWorkTimeSetEmps)
				.monthlyWorkTimeSetWkps(monthlyWorkTimeSetWkps)
				.monthlyWorkTimeSetShas(monthlyWorkTimeSetShas)
				.empFlexMonthActCalSets(empFlexMonthActCalSets)
				.wkpFlexMonthActCalSets(wkpFlexMonthActCalSets)
				.shaFlexMonthActCalSets(shaFlexMonthActCalSets)
				.comFlexMonthActCalSetOpt(comFlexMonthActCalSetOpt)
				.build();
	}
	
	/**
	 * Extract condition tab2 (Dynamic item)
	 * @return
	 */
	private OutputCheckResult extractConditionTab2(
			String cid, String sid, String wkpId, YearMonth ym, 
			ScheMonPrepareData prepareData, 
			List<WorkPlaceHistImportAl> wplByListSidAndPeriod,
			List<IntegrationOfDaily> integrationOfDailys,
			List<WorkScheduleWorkInforImport> workSchedules,
			AttendanceTimeOfMonthly attendanceTimeOfMon) {
		OutputCheckResult result = new OutputCheckResult(new ArrayList<>(), new ArrayList<>());
		
		// 社員に対応する処理締めを取得する
		val require = requireService.createRequire();
        val cacheCarrier = new CacheCarrier();
        GeneralDate criteriaDate = GeneralDate.today();
        Closure cloure = ClosureService.getClosureDataByEmployee(require, cacheCarrier, sid, criteriaDate);
	
		// 処理年月と締め期間を取得する
        PresentClosingPeriodImport presentClosingPeriod = null;
        Optional<PresentClosingPeriodImport> presentClosingPeriodOpt = closureAdapter.findByClosureId(cid, cloure.getClosureId().value);
		if (presentClosingPeriodOpt.isPresent()) {
			presentClosingPeriod = presentClosingPeriodOpt.get();
		}
		
		// Input．スケジュール月次の任意抽出条件をループする
		for (ExtractionCondScheduleMonth scheCondMon: prepareData.getScheCondMonths()) {
			// チェック項目をチェック
			switch (scheCondMon.getCheckItemType()) {
			case CONTRAST:
				// 対比の場合
				// 対比のチェック条件をチェック
				// ・社員ID　＝　ループ中の社員ID
				// ・期間．開始日　＜＝　ループ中の年月．終了日
				// ・期間．終了日　＞＝　ループ中の年月．開始日
				List<SharedSidPeriodDateEmploymentImport> lstEmploymentHis = prepareData.getLstEmploymentHis().stream()
					.filter(x -> x.getEmployeeId().equals(sid) &&
							x.getAffPeriodEmpCodeExports().stream()
							.anyMatch(a -> a.getPeriod().start().beforeOrEquals(ym.lastGeneralDate()) && a.getPeriod().end().afterOrEquals(ym.firstGeneralDate())))
					.collect(Collectors.toList());
				if (lstEmploymentHis.isEmpty()) {
					continue;
				}
				
				if (lstEmploymentHis.get(0).getAffPeriodEmpCodeExports().isEmpty()) {
					continue;
				}
				
				// 複数雇用コードを探したら最後の雇用コードを使う
				String employeeCode = lstEmploymentHis.get(0).getAffPeriodEmpCodeExports().get(0).getEmploymentCode();
				
				// 取得したList＜雇用毎の締め日＞から締め日を探す
				// ・雇用コード　＝　①の探した雇用コード
				Optional<ClosureDateOfEmploymentImport> closureDateOfEmploymentOpt = prepareData.getClosureDateOfEmps().stream()
						.filter(x -> x.getEmploymentCd().equals(employeeCode)).findFirst();
				if (!closureDateOfEmploymentOpt.isPresent()) {
					continue;
				}
				
				ClosureDateOfEmploymentImport closureDateOfEmployment = closureDateOfEmploymentOpt.get();
				
				// 取得したList＜現在の締め期間＞から現在の締め期間を探す
				// ・締め日　＝　②の探した締め日
				Optional<ClosureIdPresentClosingPeriod> closureIdPresentClosingPeriodOpt = prepareData.getClosingPeriods().stream()
						.filter(x -> x.getClosureId().intValue() == closureDateOfEmployment.getClosureDate()).findFirst();
				if (!closureIdPresentClosingPeriodOpt.isPresent()) {
					continue;
				}
				
				ExtractionResultDetail extractDetailItemContrast = conditionTab2ItemContrast(
						cid, sid, wkpId, employeeCode, ym,
						closureIdPresentClosingPeriodOpt.get(), prepareData, attendanceTimeOfMon, scheCondMon);
				if (extractDetailItemContrast == null) {
					continue;
				}
				
				result.getLstResultCondition().add(new ResultOfEachCondition(
						AlarmListCheckType.FreeCheck, 
						String.valueOf(scheCondMon.getSortOrder()), 
						Arrays.asList(extractDetailItemContrast)));
				break;
			case NUMBER_DAYS:
				// 日数の場合
				ExtractionResultDetail extractDetailItemDay = conditionTab2ItemDay(
						cid, sid, wkpId, ym, 
						attendanceTimeOfMon, 
						workSchedules, 
						prepareData.getListIntegrationDai(), 
						scheCondMon,
						presentClosingPeriod, 
						prepareData.getListWorkType(), 
						prepareData.getEmpWorkingCondItem().get(sid));
				if (extractDetailItemDay == null) {
					continue;
				}
				
				result.getLstResultCondition().add(new ResultOfEachCondition(
						AlarmListCheckType.FreeCheck, 
						String.valueOf(scheCondMon.getSortOrder()), 
						Arrays.asList(extractDetailItemDay)));
				break;
			case REMAIN_NUMBER:
				// TODO EA not description
				break;
			case TIME:
				// 時間の場合
				ExtractionResultDetail extractDetailItemTime =conditionTab2ItemTime(
						cid, sid, wkpId, ym, 
						attendanceTimeOfMon, workSchedules, integrationOfDailys, 
						scheCondMon, presentClosingPeriod);
				if (extractDetailItemTime == null) {
					continue;
				}
				
				result.getLstResultCondition().add(new ResultOfEachCondition(
						AlarmListCheckType.FreeCheck, 
						String.valueOf(scheCondMon.getSortOrder()), 
						Arrays.asList(extractDetailItemTime)));
				break;
				
			default:
				break;
			}
		}
		
		return result;
	}
	
	/**
	 * Extract condition tab 3 (Fix Item)
	 * @return
	 */
	private OutputCheckResult extractConditionTab3(
			String cid, String sid, String wkpId, YearMonth ym, 
			ScheMonPrepareData prepareData, 
			List<WorkPlaceHistImportAl> wplByListSidAndPeriod,
			List<IntegrationOfDaily> integrationOfDailys,
			List<WorkScheduleWorkInforImport> workSchedules,
			AttendanceTimeOfMonthly attendanceTimeOfMon) {
		OutputCheckResult result = new OutputCheckResult(new ArrayList<>(), new ArrayList<>());
		
		// 対比の場合
		// 対比のチェック条件をチェック
		// ・社員ID　＝　ループ中の社員ID
		// ・期間．開始日　＜＝　ループ中の年月．終了日
		// ・期間．終了日　＞＝　ループ中の年月．開始日
		List<SharedSidPeriodDateEmploymentImport> lstEmploymentHis = prepareData.getLstEmploymentHis().stream()
			.filter(x -> x.getEmployeeId().equals(sid) &&
					x.getAffPeriodEmpCodeExports().stream()
					.anyMatch(a -> a.getPeriod().start().beforeOrEquals(ym.lastGeneralDate()) && a.getPeriod().end().afterOrEquals(ym.firstGeneralDate())))
			.collect(Collectors.toList());
		if (lstEmploymentHis.isEmpty()) {
			return result;
		}
		
		if (lstEmploymentHis.get(0).getAffPeriodEmpCodeExports().isEmpty()) {
			return result;
		}
		
		// 複数雇用コードを探したら最後の雇用コードを使う
		String employeeCode = lstEmploymentHis.get(0).getAffPeriodEmpCodeExports().get(0).getEmploymentCode();
		
		// 取得したList＜雇用毎の締め日＞から締め日を探す
		// ・雇用コード　＝　①の探した雇用コード
		Optional<ClosureDateOfEmploymentImport> closureDateOfEmploymentOpt = prepareData.getClosureDateOfEmps().stream()
				.filter(x -> x.getEmploymentCd().equals(employeeCode)).findFirst();
		if (!closureDateOfEmploymentOpt.isPresent()) {
			return result;
		}
		
		ClosureDateOfEmploymentImport closureDateOfEmployment = closureDateOfEmploymentOpt.get();
		
		// 取得したList＜現在の締め期間＞から現在の締め期間を探す
		// ・締め日　＝　②の探した締め日
		Optional<ClosureIdPresentClosingPeriod> closureIdPresentClosingPeriodOpt = prepareData.getClosingPeriods().stream()
				.filter(x -> x.getClosureId().intValue() == closureDateOfEmployment.getClosureDate()).findFirst();
		if (!closureIdPresentClosingPeriodOpt.isPresent()) {
			return result;
		}
		ClosureIdPresentClosingPeriod closureIdPresentClosingPeriod = closureIdPresentClosingPeriodOpt.get();
		
		// 当月かチェック
		// 年月　＝＝　Input．現在の締め期間．処理年月
		if (!ym.equals(closureIdPresentClosingPeriod.getCurrentClosingPeriod().getProcessingYm())) {
			return result;
		}
		
		// Input．List＜スケジュール月次の固有抽出条件＞をループ
		for (FixedExtractionSMonCon fixScheMon: prepareData.getFixedScheConds()) {
			AnnualLeaveOutput annualLeaveOutput = new AnnualLeaveOutput();
			switch (fixScheMon.getFixedCheckSMonItems()) {
			case ANNUAL_LEAVE:
				annualLeaveOutput = conditionTab3ItemAnnualLeave(
						cid, sid, ym, workSchedules, integrationOfDailys, prepareData.getListWorkType(), 
						prepareData.getFixedScheConds());
				break;
			case HOLIDAY:
			case PRIORITY_ANNUAL_LEAVE:
			case SUB_HOLIDAY:
				// EA not implement
				break;
			default:
				break;
			}
			
			// 取得したList＜勤務分類、年月日＞をチェック
			if (annualLeaveOutput.workTypeMap.isEmpty()) {
				continue;
			}
			
			// 抽出結果詳細を作成
			String param0 = TextResource.localize("KAL010_1114");
			String param1 = String.valueOf(annualLeaveOutput.totalRemainingDays) + "日";
			if (annualLeaveOutput.totalRemainingTimes > 0) {
				// TODO need format time 8:00
				param1 = param1 + "　" + String.valueOf(annualLeaveOutput.totalRemainingTimes) + "時間";
			}
			String param2 = ""; //TODO need format date 2020/09/09
			for (Map.Entry<GeneralDate, WorkTypeClassification> entry : annualLeaveOutput.workTypeMap.entrySet()) {
				param2 +=  entry.getKey() + "　" + TextResource.localize(entry.getValue().nameId);
			}
			// アラーム内容
			String alarmContent = TextResource.localize("KAL010_1115", param0, param1, param2);
			// チェック対象値
			String checkValue = param2;
			
			String comment = fixScheMon.getMessageDisp() != null && fixScheMon.getMessageDisp().isPresent()
					? fixScheMon.getMessageDisp().get().v()
					: Strings.EMPTY;
			ExtractionResultDetail extractDetailItemTime = new ExtractionResultDetail();
			extractDetailItemTime.setSID(sid);
			extractDetailItemTime.setWpID(Optional.ofNullable(wkpId));
			extractDetailItemTime.setComment(Optional.ofNullable(comment));
			extractDetailItemTime.setPeriodDate(new ExtractionAlarmPeriodDate(Optional.of(ym.firstGeneralDate()), Optional.of(ym.lastGeneralDate())));
			extractDetailItemTime.setRunTime(GeneralDateTime.now());
			extractDetailItemTime.setAlarmContent(alarmContent);
			extractDetailItemTime.setCheckValue(Optional.ofNullable(checkValue));
			result.getLstResultCondition().add(new ResultOfEachCondition(
					AlarmListCheckType.FreeCheck, 
					String.valueOf(fixScheMon.getFixedCheckSMonItems().value), 
					Arrays.asList(extractDetailItemTime)));
			
		}
		
		return result;
	}
	
	/**
	 * Process with チェック項目=対比
	 * <Tab2>
	 */
	private ExtractionResultDetail conditionTab2ItemContrast(
			String cid, String sid, String wkpId, String empCode, YearMonth ym, 
			ClosureIdPresentClosingPeriod closureIdPresentClosingPeriod, 
			ScheMonPrepareData prepareData,
			AttendanceTimeOfMonthly attendanceTimeOfMonthly,
			ExtractionCondScheduleMonth scheCondMon) {
		// ・アラーム内容
		String alarmContent = Strings.EMPTY;
		//　・チェック対象値
		String checkValue = Strings.EMPTY;	
		// データ情報区分
		MonthlyWorkTimeSetAtr monthlyWorkTimeSetAtr = null;
		
		Optional<MonthlyWorkTimeSetCom> monthlyWorkTimeSetComOpt = prepareData.getMonthlyWorkTimeSetComs().stream()
				.filter(x -> x.getYm().equals(ym))
				.findFirst();
		Optional<MonthlyWorkTimeSetEmp> monthlyWorkTimeSetEmpOpt = prepareData.getMonthlyWorkTimeSetEmps().stream()
				.filter(x -> x.getYm().equals(ym) && x.getEmployment().v().equals(empCode))
				.findFirst();
		Optional<MonthlyWorkTimeSetWkp> monthlyWorMonthlyWorkTimeSetWkpOpt = prepareData.getMonthlyWorkTimeSetWkps().stream()
				.filter(x -> x.getYm().equals(ym) && x.getWorkplaceId().equals(wkpId))
				.findFirst();
		Optional<MonthlyWorkTimeSetSha> monthlyWorkTimeSetShasOpt = prepareData.getMonthlyWorkTimeSetShas().stream()
				.filter(x -> x.getYm().equals(ym) && x.getEmpId().equals(sid))
				.findFirst();
		
		// Input．スケジュール月次の任意抽出条件．所定公休日数チェック条件．チェックする対比をチェック
		PublicHolidayCheckCond publicHoliday = (PublicHolidayCheckCond)scheCondMon.getScheCheckConditions();
		switch (publicHoliday.getTypeOfContrast()) {
		case WORKING_DAY_NUMBER:
			// 所定公休日数比の場合
			// Input．公休設定．公休を管理するかをチェック
			if (prepareData.getPublicHdSettingOpt().isPresent()) {
				// 所定公休日数比をチェック				
				
				// 公休使用数を計算する
				// Output: 公休使用日数
				Double totalNumberOfHoliday = calNumOfPubHolidayService.getNumOfPubHolidayUsed(
						cid, ym, prepareData.getWorkScheduleWorkInfos(), 
						prepareData.getListIntegrationDai(), 
						closureIdPresentClosingPeriod.getCurrentClosingPeriod(), 
						prepareData.getListWorkType());
				
				// 比較対象所定公休日数を取得
				// Output: 所定公休日数
				Optional<EmploymentMonthDaySetting> employmentMonthDaySettingOpt = prepareData.getEmploymentMonthDaySetting().stream()
						.filter(x -> x.getEmploymentCode().equals(empCode) && x.getManagementYear().v().intValue() == ym.year())
						.findFirst();
				Optional<EmployeeMonthDaySetting> employeeMonthDaySettingOpt = prepareData.getEmployeeMonthDaySetting().stream()
						.filter(x -> x.getEmployeeId().equals(sid) && x.getManagementYear().v().intValue() == ym.year())
						.findFirst();
				Optional<WorkplaceMonthDaySetting> workplaceMonthDaySettingOpt = prepareData.getWorkplaceMonthDaySetting().stream()
						.filter(x -> x.getManagementYear().v().intValue() == ym.year() && x.getWorkplaceId().equals(wkpId))
						.findFirst();
				Optional<CompanyMonthDaySetting> optCompanyMonthDaySettingOpt = prepareData.getCompanyMonthDaySetting();
				
				Double numberOfHoliday = numberOfDayHolidayService.getNumberOfHoliday(
						prepareData.getPublicHolidayManagementUsageUnitOpt().get(),
						employmentMonthDaySettingOpt,
						employeeMonthDaySettingOpt, 
						workplaceMonthDaySettingOpt, 
						optCompanyMonthDaySettingOpt);
				
				// 比率を計算
				Double ratio = 0.0;
				if (numberOfHoliday != 0) {
					ratio = totalNumberOfHoliday/numberOfHoliday;
				}
				
				// 条件をチェックする
				boolean checkNumberOfHoliday = false;
				if (scheCondMon.getCheckConditions() != null) {
					if (scheCondMon.getCheckConditions() instanceof CompareRange) {
						checkNumberOfHoliday = compareValueRangeChecking.checkCompareRange((CompareRange)scheCondMon.getCheckConditions(), ratio);
					} else {
						checkNumberOfHoliday = compareValueRangeChecking.checkCompareSingleRange((CompareSingleValue)scheCondMon.getCheckConditions(), ratio);
					}
				}
				
				// 取得したエラーするをチェック
				if (!checkNumberOfHoliday) {
					return null;
				}
				
				// 比較条件
				String param0 = getCompareOperatorText(scheCondMon.getCheckConditions(), publicHoliday.getTypeOfContrast().nameId);
				// 所定公休日数
				String param1 = String.valueOf(numberOfHoliday);
				// 合計公休使用数
				String param2 = String.valueOf(totalNumberOfHoliday);
				alarmContent = TextResource.localize("KAL010_1116", param0, param1, param2);
				checkValue = TextResource.localize("KAL010_1122", param2);
			}
			break;
		case HOLIDAY_NUMBER:
			// 基準時間比（通常）の場合
			// Input．労働条件項目．労働制をチェック			
			WorkingConditionItem workingConditionItemHoliday = getWorkingConditionItem(sid, ym, prepareData);
			if (WorkingSystem.REGULAR_WORK != workingConditionItemHoliday.getLaborSystem()) {
				return null;
			}
			
			// 基準時間比（通常）をチェック
			// 
			// 合計就業時間を計算
			Double totalWorkingTime = calMonWorkingTimeService.calTotalWorkingTime(
					cid, ym, 
					prepareData.getWorkScheduleWorkInfos(), 
					prepareData.getListIntegrationDai(), 
					closureIdPresentClosingPeriod, 
					attendanceTimeOfMonthly);
			
			// 比較対象基準時間を計算
			Double totalComparsion = 0.0;
			if (!prepareData.getUsageUnitSetting().isPresent()) {
				totalComparsion = calReferenceTimeForComparison(
						prepareData.getUsageUnitSetting().get(), 
						monthlyWorkTimeSetComOpt, 
						monthlyWorkTimeSetEmpOpt, 
						monthlyWorMonthlyWorkTimeSetWkpOpt, 
						monthlyWorkTimeSetShasOpt,
						monthlyWorkTimeSetAtr);
			}
			
			// 比率を加算
			// 比率　＝　（合計就業時間　/　比較対象基準時間）*100
			// ※取得した比較対象基準時間　＝　０　の場合
			// 比率　＝　0
			Double ratio = 0.0;
			if (totalComparsion == 0) {
				ratio = (totalWorkingTime / totalComparsion) * 100;
			}
			
			// 条件をチェックする
			boolean checkNumberOfHoliday = false;
			if (scheCondMon.getCheckConditions() != null) {
				if (scheCondMon.getCheckConditions() instanceof CompareRange) {
					checkNumberOfHoliday = compareValueRangeChecking.checkCompareRange((CompareRange)scheCondMon.getCheckConditions(), ratio);
				} else {
					checkNumberOfHoliday = compareValueRangeChecking.checkCompareSingleRange((CompareSingleValue)scheCondMon.getCheckConditions(), ratio);
				}
			}
			
			// 取得したエラーするをチェック
			if (!checkNumberOfHoliday) {
				return null;
			}
			
			// アラーム内容
			String param0 = getCompareOperatorText(scheCondMon.getCheckConditions(), publicHoliday.getTypeOfContrast().nameId);
			String param1 = String.valueOf(totalComparsion);
			String param2 = String.valueOf(totalWorkingTime);
			alarmContent = TextResource.localize("KAL010_1117", param0, param1, param2);
			
			checkValue = TextResource.localize("KAL010_1123", param2);
					
			break;
		case DAYOFF_NUMBER:
			// 基準時間比（変形労働）の場合
			// Input．労働条件項目．労働制をチェック
			WorkingConditionItem workingConditionItemDayOff = getWorkingConditionItem(sid, ym, prepareData);
			if (WorkingSystem.REGULAR_WORK != workingConditionItemDayOff.getLaborSystem()) {
				return null;
			}
			
			// 基準時間比（変形労働）をチェック
			
			// 合計就業時間を計算
			Double totalWorkingTimeDayOff = calMonWorkingTimeService.calTotalWorkingTime(
					cid, ym, 
					prepareData.getWorkScheduleWorkInfos(), 
					prepareData.getListIntegrationDai(), 
					closureIdPresentClosingPeriod, 
					attendanceTimeOfMonthly);
			
			// 比較対象基準時間を計算
			Double totalComparsionDayOff = 0.0;
			if (!prepareData.getUsageUnitSetting().isPresent()) {
				totalComparsionDayOff = calReferenceTimeForComparison(
						prepareData.getUsageUnitSetting().get(), 
						monthlyWorkTimeSetComOpt, 
						monthlyWorkTimeSetEmpOpt, 
						monthlyWorMonthlyWorkTimeSetWkpOpt, 
						monthlyWorkTimeSetShasOpt,
						monthlyWorkTimeSetAtr);
			}
			
			// 比率を加算
			// 比率　＝　（合計就業時間　/　比較対象基準時間）*100
			// ※取得した比較対象基準時間　＝　０　の場合
			// 比率　＝　0
			Double ratioDayOff = 0.0;
			if (totalComparsionDayOff == 0) {
				ratio = (totalWorkingTimeDayOff / totalComparsionDayOff) * 100;
			}
			
			// 条件をチェックする
			boolean checkValueDayOf = false;
			if (scheCondMon.getCheckConditions() != null) {
				if (scheCondMon.getCheckConditions() instanceof CompareRange) {
					checkValueDayOf = compareValueRangeChecking.checkCompareRange((CompareRange)scheCondMon.getCheckConditions(), ratioDayOff);
				} else {
					checkValueDayOf = compareValueRangeChecking.checkCompareSingleRange((CompareSingleValue)scheCondMon.getCheckConditions(), ratioDayOff);
				}
			}
			
			// 取得したエラーするをチェック
			if (!checkValueDayOf) {
				return null;
			}
			
			// アラーム内容
			String paramDayOff0 = getCompareOperatorText(scheCondMon.getCheckConditions(), publicHoliday.getTypeOfContrast().nameId);
			String paramDayOff1 = String.valueOf(totalComparsionDayOff);
			String paramDayOff2 = String.valueOf(totalWorkingTimeDayOff);
			alarmContent = TextResource.localize("KAL010_1117", paramDayOff0, paramDayOff1, paramDayOff2);
			
			checkValue = TextResource.localize("KAL010_1124", paramDayOff2);
						
			break;
		case PUBLIC_HOLIDAY_NUMBER:
			// 基準時間比（フレックス）の場合
			// Input．労働条件項目．労働制をチェック
			WorkingConditionItem workingConditionItemPubHoliday = getWorkingConditionItem(sid, ym, prepareData);
			if (WorkingSystem.REGULAR_WORK != workingConditionItemPubHoliday.getLaborSystem()) {
				return null;
			}
			
			// 比較対象基準時間を計算
			Double totalComparsionPubHoliday = 0.0;
			if (!prepareData.getUsageUnitSetting().isPresent()) {
				totalComparsionPubHoliday = calReferenceTimeForComparison(
						prepareData.getUsageUnitSetting().get(), 
						monthlyWorkTimeSetComOpt, 
						monthlyWorkTimeSetEmpOpt, 
						monthlyWorMonthlyWorkTimeSetWkpOpt, 
						monthlyWorkTimeSetShasOpt,
						monthlyWorkTimeSetAtr);
			}
			
			// 繰越区分を取得
			CarryforwardSetInShortageFlex carryforwardSet = null;
			if (MonthlyWorkTimeSetAtr.SHA == monthlyWorkTimeSetAtr) {
				Optional<ShaFlexMonthActCalSet> shaFlexMonthActCalSetOpt = prepareData.getShaFlexMonthActCalSets().stream()
						.filter(x->x.getEmpId().equals(sid)).findFirst();
				// 繰越区分　＝　Input．社員別フレックス勤務集計方法．フレックス不足設定．繰越設定
				carryforwardSet = shaFlexMonthActCalSetOpt.get().getInsufficSet().getCarryforwardSet();
			} else if (MonthlyWorkTimeSetAtr.EMP == monthlyWorkTimeSetAtr) {
				Optional<EmpFlexMonthActCalSet> empFlexMonthActCalSetOpt = prepareData.getEmpFlexMonthActCalSets().stream()
						.filter(x->x.getEmploymentCode().v().equals(empCode)).findFirst();
				// 繰越区分　＝　Input．雇用別フレックス勤務集計方法．フレックス不足設定．繰越設定
				carryforwardSet = empFlexMonthActCalSetOpt.get().getInsufficSet().getCarryforwardSet();
			} else if (MonthlyWorkTimeSetAtr.WKP == monthlyWorkTimeSetAtr) {
				Optional<WkpFlexMonthActCalSet> wkpFlexMonthActCalSetOpt = prepareData.getWkpFlexMonthActCalSets().stream()
						.filter(x->x.getWorkplaceId().equals(wkpId)).findFirst();
				// 繰越区分　＝　Input．職場別フレックス勤務集計方法．フレックス不足設定．繰越設定
				carryforwardSet = wkpFlexMonthActCalSetOpt.get().getInsufficSet().getCarryforwardSet();
			} else if (MonthlyWorkTimeSetAtr.COM == monthlyWorkTimeSetAtr) {
				// 繰越区分　＝　Input．会社別フレックス勤務集計方法．フレックス不足設定．繰越設定
				Optional<ComFlexMonthActCalSet> comFlexMonthActCalSetOpt = prepareData.getComFlexMonthActCalSetOpt();
				carryforwardSet = comFlexMonthActCalSetOpt.get().getInsufficSet().getCarryforwardSet();
			}
			
			// 合計就業時間を計算
			Double totalWorkingTimePubHoliday = calMonWorkingTimeService.calTotalWorkingTime(
					cid, ym, 
					prepareData.getWorkScheduleWorkInfos(), 
					prepareData.getListIntegrationDai(), 
					closureIdPresentClosingPeriod, 
					attendanceTimeOfMonthly);
			
			// 合計就業時間を調整
			if (ym.greaterThanOrEqualTo(closureIdPresentClosingPeriod.getCurrentClosingPeriod().getProcessingYm()) 
					&& carryforwardSet != null
					&& CarryforwardSetInShortageFlex.NEXT_MONTH_CARRYFORWARD == carryforwardSet) {
				// 合計就業時間　＝　取得した合計就業時間　－　Input．前月の月別実績．勤怠時間．月の計算．フレックス時間．フレックス不足時間
				totalWorkingTimePubHoliday = attendanceTimeOfMonthly.getMonthlyCalculation().getFlexTime().getFlexShortageTime().v().doubleValue();
			} else {
				// 合計就業時間　＝　取得した合計就業時間
			}
			
			Double ratioPubHoliday = 0.0;
			if (totalComparsionPubHoliday > 0) {
				ratioPubHoliday = (totalWorkingTimePubHoliday/totalComparsionPubHoliday) * 100;
			}
			
			// 条件をチェックする
			boolean checkValuePubHoliday = false;
			if (scheCondMon.getCheckConditions() != null) {
				if (scheCondMon.getCheckConditions() instanceof CompareRange) {
					checkValuePubHoliday = compareValueRangeChecking.checkCompareRange((CompareRange)scheCondMon.getCheckConditions(), ratioPubHoliday);
				} else {
					checkValuePubHoliday = compareValueRangeChecking.checkCompareSingleRange((CompareSingleValue)scheCondMon.getCheckConditions(), ratioPubHoliday);
				}
			}
			
			// 取得したエラーするをチェック
			if (!checkValuePubHoliday) {
				return null;
			}
			
			// アラーム内容
			String paramPub0 = getCompareOperatorText(scheCondMon.getCheckConditions(), publicHoliday.getTypeOfContrast().nameId);
			String paramPub1 = String.valueOf(totalComparsionPubHoliday);
			String paramPub2 = String.valueOf(totalWorkingTimePubHoliday);
			alarmContent = TextResource.localize("KAL010_1117", paramPub0, paramPub1, paramPub2);
			
			checkValue = TextResource.localize("KAL010_1125", paramPub2);
			
			break;

		default:
			break;
		}
		
		// 取得したアラーム内容をチェック
		// アラーム項目
		String alarmName = scheCondMon.getName().v();
		// コメント
		String comment = scheCondMon.getErrorAlarmMessage() != null && scheCondMon.getErrorAlarmMessage().isPresent()
						? scheCondMon.getErrorAlarmMessage().get().v()
						: Strings.EMPTY;		
		ExtractionAlarmPeriodDate extractionAlarmPeriodDate = new ExtractionAlarmPeriodDate(Optional.of(ym.firstGeneralDate()), Optional.empty());
		return new ExtractionResultDetail(
				sid, 
				extractionAlarmPeriodDate, 
				alarmName, 
				alarmContent, 
				GeneralDateTime.now(), 
				Optional.ofNullable(wkpId), 
				Optional.ofNullable(comment), 
				Optional.ofNullable(checkValue));
	}
	
	private WorkingConditionItem getWorkingConditionItem(String sid, YearMonth ym, ScheMonPrepareData prepareData) {
		DatePeriod dPeriod = new DatePeriod(ym.firstGeneralDate(), ym.lastGeneralDate());
		Map<DatePeriod, WorkingConditionItem> empWorkingCondItem = prepareData.getEmpWorkingCondItem().get(sid);
		if (empWorkingCondItem.isEmpty()) {
			return null;
		}
		
		WorkingConditionItem workingConditionItem = empWorkingCondItem.get(dPeriod);		
		return workingConditionItem;
	}
	
	/**
	 * 比較対象基準時間を計算
	 * @param usageUnitSetting 労働時間と日数の設定の利用単位の設定
	 * @param monthlyWorkTimeSetComOpt Optional＜会社別月単位労働時間＞
	 * @param monthlyWorkTimeSetEmpOpt Optional＜雇用別月単位労働時間＞
	 * @param monthlyWorkTimeSetWkpOpt Optional＜職場別月単位労働時間＞
	 * @param monthlyWorkTimeSetShas Optional＜社員別月単位労働時間＞
	 * 
	 * @return
	 */
	private Double calReferenceTimeForComparison(
			UsageUnitSetting usageUnitSetting,
			Optional<MonthlyWorkTimeSetCom> monthlyWorkTimeSetComOpt,
			Optional<MonthlyWorkTimeSetEmp> monthlyWorkTimeSetEmpOpt,
			Optional<MonthlyWorkTimeSetWkp> monthlyWorkTimeSetWkpOpt,
			Optional<MonthlyWorkTimeSetSha> monthlyWorkTimeSetShasOpt,
			MonthlyWorkTimeSetAtr monthlyWorkTimeSetAtr) {
		// 社員の労働時間と日数の管理をするかチェック
		// Input．社員別月単位労働時間　！＝　Empty AND Input．労働時間と日数の設定の利用単位の設定．社員の労働時間と日数の管理をする　＝＝　True
		if (monthlyWorkTimeSetShasOpt.isPresent() && usageUnitSetting.isEmployee()) {
			monthlyWorkTimeSetAtr = MonthlyWorkTimeSetAtr.SHA;
			// 比較対象基準時間　＝　合計（Input．社員別月単位労働時間．労働時間）　　※１月～１２月   // データ情報区分　＝　社員
			// TODO do not kown propery?
			return 0.0;
		}
		
		// 雇用の労働時間と日数の管理をするかチェック
		// Input．雇用別月単位労働時間　！＝　Empty AND Input．労働時間と日数の設定の利用単位の設定．雇用の労働時間と日数の管理をする　＝＝　True
		if (monthlyWorkTimeSetEmpOpt.isPresent() && usageUnitSetting.isEmployment()) {
			monthlyWorkTimeSetAtr = MonthlyWorkTimeSetAtr.EMP;
			// 比較対象基準時間　＝　合計（Input．雇用別月単位労働時間．労働時間）　　※１月～１２月   // データ情報区分　＝　雇用
			// TODO do not kown propery?
			return 0.0;
		}
		
		// 職場の労働時間と日数の管理をするかチェック
		// Input．職場別月単位労働時間　！＝　Empty
		// AND
		// Input．労働時間と日数の設定の利用単位の設定．職場の労働時間と日数の管理をする　＝＝　True
		if (monthlyWorkTimeSetWkpOpt.isPresent() && usageUnitSetting.isWorkPlace()) {
			monthlyWorkTimeSetAtr = MonthlyWorkTimeSetAtr.WKP;
			// ・比較対象基準時間　＝　合計（Input．職場別月単位労働時間．労働時間）　※１月～１２月  // データ情報区分　＝　職場
			// TODO do not kown propery?
			return 0.0;
		}

		// 比較対象基準時間　を計算
		if (monthlyWorkTimeSetComOpt.isPresent()) {
			monthlyWorkTimeSetAtr = MonthlyWorkTimeSetAtr.COM;
			// 比較対象基準時間　＝　合計（Input．会社別月単位労働時間．労働時間）　※１月～１２月  // データ情報区分　＝　会社
			// TODO do not kown propery?
			return 0.0;
		}
		
		return 0.0;
	}
	
	/**
	 * 時間チェック条件をチェック
	 * <Tab2>
	 */
	private ExtractionResultDetail conditionTab2ItemTime(
			String cid, String sid, String wkpId, YearMonth ym,
			AttendanceTimeOfMonthly attendanceTimeOfMonthly,
			List<WorkScheduleWorkInforImport> workSchedules,
			List<IntegrationOfDaily> integrationDailys,
			ExtractionCondScheduleMonth scheCondMon,
			PresentClosingPeriodImport presentClosingPeriod) {		
		// 予定時間＋総労働時間の場合	
		int totalTime = scheTimeAndTotalWorkingService.getScheTimeAndTotalWorkingTime(
				ym, attendanceTimeOfMonthly, presentClosingPeriod, integrationDailys, workSchedules);
		
		// 条件をチェックする
		boolean checkCompareValue = false;
		if (scheCondMon.getCheckConditions() instanceof CompareRange) {
			checkCompareValue = compareValueRangeChecking.checkCompareRange((CompareRange)scheCondMon.getCheckConditions(), Double.valueOf(totalTime));
		} else {
			checkCompareValue = compareValueRangeChecking.checkCompareSingleRange((CompareSingleValue)scheCondMon.getCheckConditions(), Double.valueOf(totalTime));
		}
		
		if (!checkCompareValue) {
			return null;
		}
		
		// スケジュール月次のアラーム抽出値を作成して返す
		// アラーム項目
		String alarmName = scheCondMon.getName().v();
		// コメント
		String comment = scheCondMon.getErrorAlarmMessage() != null && scheCondMon.getErrorAlarmMessage().isPresent()
						? scheCondMon.getErrorAlarmMessage().get().v()
						: Strings.EMPTY;
		// アラーム内容
		String param = getCompareOperatorText(scheCondMon.getCheckConditions(), TypeOfTime.SCHETIME_WORKING_HOURS.nameId);
		String alarmContent = TextResource.localize("KAL010_1118", param, String.valueOf(totalTime));
		// チェック対象値
		String checkValue = TextResource.localize("KAL010_1121", String.valueOf(totalTime));
		
		ExtractionAlarmPeriodDate extractionAlarmPeriodDate = new ExtractionAlarmPeriodDate(Optional.of(ym.firstGeneralDate()), Optional.empty());
		return new ExtractionResultDetail(
				sid, 
				extractionAlarmPeriodDate, 
				alarmName, 
				alarmContent, 
				GeneralDateTime.now(), 
				Optional.ofNullable(wkpId), 
				Optional.ofNullable(comment), 
				Optional.ofNullable(checkValue));
	}
	
	/**
	 * 時間チェック条件をチェック
	 */
	private ExtractionResultDetail conditionTab2ItemDay(
			String cid, String sid, String wkpId, YearMonth ym,
			AttendanceTimeOfMonthly attendanceTimeOfMonthly,
			List<WorkScheduleWorkInforImport> workSchedules,
			List<IntegrationOfDaily> integrationDailys,
			ExtractionCondScheduleMonth scheCondMon,
			PresentClosingPeriodImport presentClosingPeriod,
			List<WorkType> workTypes,
			Map<DatePeriod, WorkingConditionItem> mapWorkingConditionItem) {
		// Input．スケジュール月次の任意抽出条件．日数チェック条件．チェックする日数をチェック
		Double totalTime = 0.0;
		
		DayCheckCond dayCheckCond = (DayCheckCond)scheCondMon.getScheCheckConditions();
		switch (dayCheckCond.getTypeOfDays()) {
		case PUBLIC_HOLIDAY_NUMBER:
			// 期間内の公休残数を集計する
			//TODO RQ718 not implement QA#113101
			break;
		case ANNUAL_LEAVE_NUMBER:
		case ACC_ANNUAL_LEAVE_NUMBER:
			// 期間中の年休積休残数を取得
			val require = requireService.createRequire();
			val cacheCarrier = new CacheCarrier();
			GeneralDate criteriaDate = GeneralDate.today();
			DatePeriod period = new DatePeriod(ym.firstGeneralDate(), ym.lastGeneralDate());
			AggrResultOfAnnAndRsvLeave aggResult = GetAnnAndRsvRemNumWithinPeriod.algorithm(require, cacheCarrier,
                    cid, sid, period, InterimRemainMngMode.OTHER, criteriaDate,
                    false, false, Optional.of(false),
                    Optional.empty(), Optional.empty(), Optional.empty(),
                    Optional.empty(), Optional.empty(), Optional.empty());
			
			// 休暇日数を計算
			if (dayCheckCond.getTypeOfDays() == TypeOfDays.ANNUAL_LEAVE_NUMBER) {
				// 休暇日数　＝　取得した年休積休の集計結果．年休．年休情報(期間終了日時点)．使用日数
				totalTime = aggResult.getAnnualLeave().get().getAsOfPeriodEnd().getUsedDays().v().doubleValue();
			} else {
				// 休暇日数　＝　取得した年休積休の集計結果．積立年休．積立年休情報(期間終了日時点)．使用日数
				totalTime = aggResult.getReserveLeave().get().getAsOfPeriodEnd().getUsedDays().v().doubleValue();
			}
			break;
		default:
			// 出勤日数を計算
			TypeOfDays typeOfDay = ((DayCheckCond)scheCondMon.getScheCheckConditions()).getTypeOfDays();
			totalTime = calculateVacationDayService.calVacationDay(
					cid, sid, ym, 
					presentClosingPeriod, 
					attendanceTimeOfMonthly, 
					integrationDailys, 
					workSchedules, 
					typeOfDay,
					workTypes,
					mapWorkingConditionItem);
			break;
		}
		
		// 条件をチェックする
		boolean checkCompareValue = false;
		if (scheCondMon.getCheckConditions() instanceof CompareRange) {
			checkCompareValue = compareValueRangeChecking.checkCompareRange((CompareRange)scheCondMon.getCheckConditions(), Double.valueOf(totalTime));
		} else {
			checkCompareValue = compareValueRangeChecking.checkCompareSingleRange((CompareSingleValue)scheCondMon.getCheckConditions(), Double.valueOf(totalTime));
		}
		
		if (!checkCompareValue) {
			return null;
		}
		
		// スケジュール月次のアラーム抽出値を作成して返す
		// アラーム項目
		String alarmName = scheCondMon.getName().v();
		// コメント
		String comment = scheCondMon.getErrorAlarmMessage() != null && scheCondMon.getErrorAlarmMessage().isPresent()
						? scheCondMon.getErrorAlarmMessage().get().v()
						: Strings.EMPTY;
		// アラーム内容
		String param = getCompareOperatorText(scheCondMon.getCheckConditions(), TypeOfTime.SCHETIME_WORKING_HOURS.nameId);
		String alarmContent = TextResource.localize("KAL010_1119", param, String.valueOf(totalTime));
		// チェック対象値
		String checkValue = TextResource.localize("KAL010_1120", String.valueOf(totalTime));
		
		ExtractionAlarmPeriodDate extractionAlarmPeriodDate = new ExtractionAlarmPeriodDate(Optional.of(ym.firstGeneralDate()), Optional.empty());
		return new ExtractionResultDetail(
				sid, 
				extractionAlarmPeriodDate, 
				alarmName, 
				alarmContent, 
				GeneralDateTime.now(), 
				Optional.ofNullable(wkpId), 
				Optional.ofNullable(comment), 
				Optional.ofNullable(checkValue));
	}
	
	/**
	 * 年休優先
	 */
	private AnnualLeaveOutput conditionTab3ItemAnnualLeave(
			String cid, String sid, YearMonth ym,
			List<WorkScheduleWorkInforImport> workSchedules,
			List<IntegrationOfDaily> integDailys,
			List<WorkType> workTypes,
			List<FixedExtractionSMonCon> fixScheMonConds) {
		AnnualLeaveOutput output = new AnnualLeaveOutput();
		
		// 期間中の年休積休残数を取得
		val require = requireService.createRequire();
		val cacheCarrier = new CacheCarrier();
		// 基準日　＝　Input．年月の開始日
		GeneralDate criteriaDate = ym.firstGeneralDate();
		// 集計開始日　＝　Input．年月の開始日
		// 集計終了日 = Input．年月.Add(1年）.Add（－１日） TODO need confirm firstDayInMonth - 1 day or lastDayInMonth
		DatePeriod period = new DatePeriod(ym.firstGeneralDate(), ym.firstGeneralDate().addYears(1).addDays(-1));
		// Output: 年休の集計結果
		AggrResultOfAnnAndRsvLeave aggResult = GetAnnAndRsvRemNumWithinPeriod.algorithm(require, cacheCarrier,
                cid, sid, period, InterimRemainMngMode.OTHER, criteriaDate,
                false, false, Optional.of(false),
                Optional.empty(), Optional.empty(), Optional.empty(),
                Optional.empty(), Optional.empty(), Optional.empty());
		
		// 取得した残数をチェック
		if (!aggResult.getAnnualLeave().isPresent()) {
			return output;
		}
		
		AggrResultOfAnnualLeave annualLeaveInfo = aggResult.getAnnualLeave().get();
		AnnualLeaveRemainingNumber remainingNumber = annualLeaveInfo.getAsOfPeriodEnd().getRemainingNumber().getAnnualLeaveWithMinus().getRemainingNumberInfo().getRemainingNumber();
		
		// 取得した年休の集計結果．年休情報(期間終了日時点)．残数．年休(マイナスあり)．残数．合計．合計残日数　＞　０
		// OR
		// 取得した年休の集計結果．年休情報(期間終了日時点)．残数．年休(マイナスあり)．残数．合計．合計残時間数　＞　０
		Double totalRemainingDays = remainingNumber.getTotalRemainingDays().v();
		boolean check = totalRemainingDays > 0;
		if (remainingNumber.getTotalRemainingTime().isPresent()) {
			Double totalRemainingTime = remainingNumber.getTotalRemainingTime().get().v().doubleValue();
			output.totalRemainingTimes += totalRemainingTime; //TODO need QA co phai cong don ko
			check = check || totalRemainingTime > 0;
		}
		
		if (!check) {
			return output;
		}
		
		output.totalRemainingDays += totalRemainingDays; //TODO need QA co phai cong don ko
		
		// Input．年月の開始日から終了日までループする
		DatePeriod dPeriod = new DatePeriod(ym.firstGeneralDate(), ym.lastGeneralDate());
		List<GeneralDate> listDate = dPeriod.datesBetween();
		for(int day = 0; day < listDate.size(); day++) {
			GeneralDate exDate = listDate.get(day);
			
			// Input．List＜勤務予定＞からループ中の年月日データを探す
			Optional<WorkScheduleWorkInforImport> workScheOpt = workSchedules.stream()
					.filter(x -> x.getYmd().equals(exDate)).findFirst();
			// Input．List＜日別実績＞からループ中の年月日データを探す
			Optional<IntegrationOfDaily> integrationOfDaily = integDailys.stream()
					.filter(x -> x.getYmd().equals(exDate)).findFirst();
			// 探したデータをチェック
			if (!workScheOpt.isPresent() && !integrationOfDaily.isPresent()) {
				continue;
			}
			
			// 勤務種類コードを探す
			String workTypeCode;
			if (integrationOfDaily.isPresent()) {
				workTypeCode = integrationOfDaily.get().getWorkInformation().getRecordInfo().getWorkTypeCode().v();
			} else {
				workTypeCode = workScheOpt.get().getWorkTyle();
			}
			
			// Input．List＜勤務種類＞から勤務種類コードを探す
			Optional<WorkType> workTypeOpt = workTypes.stream().filter(x->x.getWorkTypeCode().v().equals(workTypeCode)).findFirst();
			if (!workTypeOpt.isPresent()) {
				continue;
			}
			
			WorkType workType = workTypeOpt.get();
			
			// 勤務種類が休暇系か判断 TODO need QA method
			// Output: 休日区分
			boolean isHoliday = judgmentWorkTypeService.isVacationType(workType);
			if (!isHoliday) {
				continue;
			}
			
			// 勤務種類の分類が年休かチェック
			if (isAnnualOrSpecialHoliday(workType.getDailyWork())) {
				continue;
			}
			
			// List＜年月日、勤務分類＞に追加
			output.workTypeMap.put(exDate, workType.getDailyWork().getClassification());
		}
		
		return output;
	}
	
	/**
	 * 勤務種類の分類が年休かチェック
	 * @return
	 */
	private boolean isAnnualOrSpecialHoliday(DailyWork dailyWork) {
		if(dailyWork.getWorkTypeUnit() == WorkTypeUnit.OneDay) {
			return dailyWork.getOneDay().isAnnualLeave();
		}
		return dailyWork.getAfternoon().isAnnualLeave() || dailyWork.getMorning().isAnnualLeave();
	}
	
	/**
	 * Get parameter 0 for alarm content 
	 */
	public String getCompareOperatorText(CheckedCondition checkCondition, String checkCondTypeName) {
		if (checkCondition == null) {
			return checkCondTypeName;		
		}
		
		int compare = checkCondition instanceof CompareSingleValue 
				? ((CompareSingleValue) checkCondition).getCompareOpertor().value
				: ((CompareRange) checkCondition).getCompareOperator().value;
				
		CompareOperatorText compareOperatorText = convertComparaToText.convertCompareType(compare);
		
		String startValue = checkCondition instanceof CompareSingleValue 
						? ((CompareSingleValue) checkCondition).getValue().toString()
						: ((CompareRange) checkCondition).getStartValue().toString();
		String endValue = checkCondition instanceof CompareRange  
				? ((CompareRange) checkCondition).getEndValue().toString() : null;
		
		String variable0 = "";
		if(compare <= 5) {
			variable0 = startValue + compareOperatorText.getCompareLeft() + checkCondTypeName;
		} else {
			if (compare > 5 && compare <= 7) {
				variable0 = startValue + compareOperatorText.getCompareLeft() + checkCondTypeName
						+ compareOperatorText.getCompareright() + endValue;
			} else {
				variable0 = startValue + compareOperatorText.getCompareLeft()
						+ ", " + compareOperatorText.getCompareright() + endValue + checkCondTypeName;
			}
		}
		
		return variable0;
	}
	
	/**
	 * データ情報区分
	 *
	 */
	private enum MonthlyWorkTimeSetAtr {
		/**
		 * 会社
		 */
		COM(0),
		
		/**
		 * 雇用
		 */
		EMP(1),
		
		/**
		 * 職場
		 */
		WKP(2),
		
		/**
		 * 社員
		 */
		SHA(3);
		
		public int value;
		private MonthlyWorkTimeSetAtr(int value){
			this.value = value;
		}
	}
	
	private class AnnualLeaveOutput {
		/**
		 * 合計残日数
		 */
		public Double totalRemainingDays;
		
		/**
		 * 合計残時間数
		 */
		public Double totalRemainingTimes;
		
		/**
		 * List＜年月日、勤務分類＞
		 */
		public Map<GeneralDate, WorkTypeClassification> workTypeMap;
		
		public AnnualLeaveOutput() {
			workTypeMap = new HashMap<>();
		}
	}
}
