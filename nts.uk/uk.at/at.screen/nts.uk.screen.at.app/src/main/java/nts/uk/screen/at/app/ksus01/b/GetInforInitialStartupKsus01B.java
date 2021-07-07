package nts.uk.screen.at.app.ksus01.b;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.AllArgsConstructor;
import lombok.val;
import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.arc.time.calendar.DateInMonth;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.aggregation.dom.common.DailyAttendanceGettingService;
import nts.uk.ctx.at.aggregation.dom.schedulecounter.aggregationprocess.personcounter.EstimatedSalary;
import nts.uk.ctx.at.aggregation.dom.schedulecounter.aggregationprocess.personcounter.EstimatedSalaryAggregationService;
import nts.uk.ctx.at.aggregation.dom.schedulecounter.aggregationprocess.personcounter.WorkingTimeCounterService;
import nts.uk.ctx.at.aggregation.dom.schedulecounter.criterion.CriterionAmountForCompany;
import nts.uk.ctx.at.aggregation.dom.schedulecounter.criterion.CriterionAmountForCompanyRepository;
import nts.uk.ctx.at.aggregation.dom.schedulecounter.criterion.CriterionAmountForEmployment;
import nts.uk.ctx.at.aggregation.dom.schedulecounter.criterion.CriterionAmountForEmploymentRepository;
import nts.uk.ctx.at.aggregation.dom.schedulecounter.criterion.CriterionAmountUsageSetting;
import nts.uk.ctx.at.aggregation.dom.schedulecounter.criterion.CriterionAmountUsageSettingRepository;
import nts.uk.ctx.at.aggregation.dom.schedulecounter.criterion.HandlingOfCriterionAmount;
import nts.uk.ctx.at.aggregation.dom.schedulecounter.criterion.HandlingOfCriterionAmountRepository;
import nts.uk.ctx.at.record.app.find.dailyperform.DailyRecordDto;
import nts.uk.ctx.at.record.app.find.dailyperform.DailyRecordWorkFinder;
import nts.uk.ctx.at.schedule.dom.adapter.classification.SClsHistImported;
import nts.uk.ctx.at.schedule.dom.adapter.classification.SyClassificationAdapter;
import nts.uk.ctx.at.schedule.dom.schedule.workschedule.WorkSchedule;
import nts.uk.ctx.at.schedule.dom.schedule.workschedule.WorkScheduleRepository;
import nts.uk.ctx.at.shared.dom.adapter.employee.SClsHistImport;
import nts.uk.ctx.at.shared.dom.adapter.employment.SharedSyEmploymentImport;
import nts.uk.ctx.at.shared.dom.adapter.jobtitle.SharedAffJobTitleHisImport;
import nts.uk.ctx.at.shared.dom.adapter.jobtitle.SharedAffJobtitleHisAdapter;
import nts.uk.ctx.at.shared.dom.adapter.workplace.SharedAffWorkPlaceHisAdapter;
import nts.uk.ctx.at.shared.dom.adapter.workplace.SharedAffWorkPlaceHisImport;
import nts.uk.ctx.at.shared.dom.common.EmployeeId;
import nts.uk.ctx.at.shared.dom.dailyattdcal.converter.DailyRecordShareFinder;
import nts.uk.ctx.at.shared.dom.employeeworkway.businesstype.employee.BusinessTypeOfEmployee;
import nts.uk.ctx.at.shared.dom.employeeworkway.businesstype.employee.repository.BusinessTypeEmpService;
import nts.uk.ctx.at.shared.dom.schedule.basicschedule.BasicScheduleService;
import nts.uk.ctx.at.shared.dom.schedule.basicschedule.SetupType;
import nts.uk.ctx.at.shared.dom.scherec.aggregation.perdaily.AttendanceTimesForAggregation;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.dailyattendancework.IntegrationOfDaily;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.EmploymentCode;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItem;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionRepository;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.employeeinfor.employmenthistory.imported.EmploymentHisScheduleAdapter;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.employeeinfor.employmenthistory.imported.EmploymentPeriodImported;
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
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * UKDesign.UniversalK.就業.KSU_スケジュール.KSUS01_スケジュール参照（スマホ）.スケジュール参照.B:補足情報.メニュー別OCD.初期起動時
 * @author huylq
 *
 */
@Stateless
public class GetInforInitialStartupKsus01B {
	
	@Inject
	private WorkTypeRepository workTypeRepo;
	@Inject
	private WorkTimeSettingRepository workTimeSettingRepository;
	@Inject
	private BasicScheduleService basicScheduleService;
	@Inject
	private FixedWorkSettingRepository fixedWorkSettingRepository;
	@Inject
	private FlowWorkSettingRepository flowWorkSettingRepository;
	@Inject
	private FlexWorkSettingRepository flexWorkSettingRepository;
	@Inject
	private PredetemineTimeSettingRepository predetemineTimeSettingRepository;
	@Inject
	private EmploymentHisScheduleAdapter employmentHisScheduleAdapter;
	@Inject
	private SharedAffJobtitleHisAdapter sharedAffJobtitleHisAdapter;
	@Inject
	private SharedAffWorkPlaceHisAdapter sharedAffWorkPlaceHisAdapter;
	@Inject
	private WorkingConditionRepository workingConditionRepo;
	@Inject
	private BusinessTypeEmpService businessTypeEmpService;
	@Inject
	private SyClassificationAdapter syClassificationAdapter;
	@Inject
	private WorkScheduleRepository workScheduleRepository;
	@Inject
	private DailyRecordShareFinder dailyRecordShareFinder;
	@Inject
	private CriterionAmountUsageSettingRepository criterionAmountUsageSettingRepository;
	@Inject
	private CriterionAmountForCompanyRepository criterionAmountForCompanyRepository;
	@Inject
	private CriterionAmountForEmploymentRepository criterionAmountForEmploymentRepository;
	@Inject
	private HandlingOfCriterionAmountRepository handlingOfCriterionAmountRepository;
	
	final static String DATE_TIME_FORMAT = "yyyy/MM/dd";

	public InforInitialDto handle(InforInitialInput input) {
		
		String employeeId = AppContexts.user().employeeId();
		String companyId = AppContexts.user().companyId();
		YearMonth baseYM = new YearMonth(input.getBaseYM());
		DateInMonth closingDay = DateInMonth.of(input.getClosingDay());
		
		WorkScheduleRequireImpl workScheduleRequire = new WorkScheduleRequireImpl(companyId, workTypeRepo, workTimeSettingRepository, basicScheduleService, fixedWorkSettingRepository, flowWorkSettingRepository, flexWorkSettingRepository, predetemineTimeSettingRepository, employmentHisScheduleAdapter, sharedAffJobtitleHisAdapter, sharedAffWorkPlaceHisAdapter, workingConditionRepo, businessTypeEmpService, syClassificationAdapter);
		EstimatedSalaryAggregationServiceRequireImpl estimatedSalaryAggregationServiceRequire = new EstimatedSalaryAggregationServiceRequireImpl(companyId, workScheduleRepository, dailyRecordShareFinder, criterionAmountUsageSettingRepository, criterionAmountForCompanyRepository, criterionAmountForEmploymentRepository, employmentHisScheduleAdapter, handlingOfCriterionAmountRepository);
		
		
		//	1: *<call>: List<日別勤怠(Work)>
		List<IntegrationOfDaily> integrationOfDailyList = input.getListWorkSchedule().stream().map(el -> {
					//	1.1: 作る(require, 社員ID, 年月日, 勤務情報): 勤務予定
					WorkSchedule workSchedule = WorkSchedule.create(workScheduleRequire, 
							employeeId, 
							GeneralDate.fromString(el.getYmd(), DATE_TIME_FORMAT), 
							el.getWorkInfo().toDomain());
					//	1.2: 日別勤怠Workに変換する(): 日別勤怠(Work)
					return workSchedule.convertToIntegrationOfDaily();
				}).collect(Collectors.toList());
		
		//	2: 月間想定給与額を集計する(基準年月, 締め日, List<日別勤怠（Work）>): Map<社員ID, 想定給与額>
		Map<EmployeeId, EstimatedSalary> estimatedSalaryMonthlyMap = EstimatedSalaryAggregationService.aggregateByMonthly(
				estimatedSalaryAggregationServiceRequire, 
				baseYM, 
				closingDay, 
				integrationOfDailyList);
		
		//	3: 累計想定給与額を集計する(require, 基準年月, 締め日, 社員IDリスト): Map<社員ID, 想定給与額>
		List<EmployeeId> empIds = new ArrayList<EmployeeId>();
		EmployeeId empId = new EmployeeId(employeeId);
		empIds.add(empId);
		Map<EmployeeId, EstimatedSalary> estimatedSalaryCumulativeMap = EstimatedSalaryAggregationService.aggregateByCumulatively(
				estimatedSalaryAggregationServiceRequire, 
				baseYM, 
				closingDay, 
				empIds);
		
		//	4: 集計する(社員別勤怠時間リスト): Map<社員ID, Map<集計対象の勤怠時間, BigDecimal>>
		Map<EmployeeId, Map<AttendanceTimesForAggregation, BigDecimal>> totalWorkingTimeMap = WorkingTimeCounterService.get(integrationOfDailyList);

		AttendanceTimesForAggregation enum0 = null;
		for(AttendanceTimesForAggregation enumConstant: AttendanceTimesForAggregation.class.getEnumConstants()) {
			if(enumConstant.getValue() == 0) {
				enum0 = enumConstant;
			}
		}
		return new InforInitialDto(
				totalWorkingTimeMap.get(empId).get(enum0),
				estimatedSalaryMonthlyMap.get(empId).getSalary(), 
				estimatedSalaryCumulativeMap.get(empId).getSalary());
	}
	
	@AllArgsConstructor
	private static class WorkScheduleRequireImpl implements WorkSchedule.Require {
		
		private String companyId;
		private WorkTypeRepository workTypeRepo;
		private WorkTimeSettingRepository workTimeSettingRepository;
		private BasicScheduleService basicScheduleService;
		private FixedWorkSettingRepository fixedWorkSettingRepository;
		private FlowWorkSettingRepository flowWorkSettingRepository;
		private FlexWorkSettingRepository flexWorkSettingRepository;
		private PredetemineTimeSettingRepository predetemineTimeSettingRepository;
		private EmploymentHisScheduleAdapter employmentHisScheduleAdapter;
		private SharedAffJobtitleHisAdapter sharedAffJobtitleHisAdapter;
		private SharedAffWorkPlaceHisAdapter sharedAffWorkPlaceHisAdapter;
		private WorkingConditionRepository workingConditionRepo;
		private BusinessTypeEmpService businessTypeEmpService;
		private SyClassificationAdapter syClassificationAdapter;
		
		@Override
		public Optional<WorkType> getWorkType(String workTypeCd) {
			return workTypeRepo.findByPK(companyId, workTypeCd);
		}

		@Override
		public Optional<WorkTimeSetting> getWorkTime(String workTimeCode) {
			return workTimeSettingRepository.findByCode(companyId, workTimeCode);
		}

		@Override
		public SetupType checkNeededOfWorkTimeSetting(String workTypeCode) {
			return basicScheduleService.checkNeededOfWorkTimeSetting(workTypeCode);
		}

		@Override
		public FixedWorkSetting getWorkSettingForFixedWork(WorkTimeCode code) {
			return fixedWorkSettingRepository.findByKey(companyId, code.v()).orElse(new FixedWorkSetting());
		}

		@Override
		public FlowWorkSetting getWorkSettingForFlowWork(WorkTimeCode code) {
			return flowWorkSettingRepository.find(companyId, code.v()).orElse(new FlowWorkSetting());
		}

		@Override
		public FlexWorkSetting getWorkSettingForFlexWork(WorkTimeCode code) {
			return flexWorkSettingRepository.find(companyId, code.v()).orElse(new FlexWorkSetting());
		}

		@Override
		public PredetemineTimeSetting getPredetermineTimeSetting(WorkTimeCode wktmCd) {
			return predetemineTimeSettingRepository.findByWorkTimeCode(companyId, wktmCd.v()).orElse(new PredetemineTimeSetting());
		}

		@Override
		public SharedSyEmploymentImport getAffEmploymentHistory(String employeeId, GeneralDate standardDate) {
			List<EmploymentPeriodImported> listEmpHist = employmentHisScheduleAdapter
					.getEmploymentPeriod(Arrays.asList(employeeId), new DatePeriod(standardDate, standardDate));
			if(listEmpHist.isEmpty())
				return null;
			return new SharedSyEmploymentImport(listEmpHist.get(0).getEmpID(), listEmpHist.get(0).getEmploymentCd(), "",
					listEmpHist.get(0).getDatePeriod());
		}

		@Override
		public SharedAffJobTitleHisImport getAffJobTitleHistory(String employeeId, GeneralDate standardDate) {
			List<SharedAffJobTitleHisImport> listAffJobTitleHis =  sharedAffJobtitleHisAdapter.findAffJobTitleHisByListSid(Arrays.asList(employeeId), standardDate);
			if(listAffJobTitleHis.isEmpty())
				return null;
			return listAffJobTitleHis.get(0);
		}

		@Override
		public SharedAffWorkPlaceHisImport getAffWorkplaceHistory(String employeeId, GeneralDate standardDate) {
			Optional<SharedAffWorkPlaceHisImport> rs = sharedAffWorkPlaceHisAdapter.getAffWorkPlaceHis(employeeId, standardDate);
			return rs.isPresent() ? rs.get() : null;
		}

		@Override
		public SClsHistImport getClassificationHistory(String employeeId, GeneralDate standardDate) {
			Optional<SClsHistImported> imported = syClassificationAdapter.findSClsHistBySid(companyId, employeeId, standardDate);
			if (!imported.isPresent()) {
				return null;
			}
			return new SClsHistImport(imported.get().getPeriod(), imported.get().getEmployeeId(),
					imported.get().getClassificationCode(), imported.get().getClassificationName());
		}

		@Override
		public Optional<BusinessTypeOfEmployee> getBusinessType(String employeeId, GeneralDate standardDate) {
			List<BusinessTypeOfEmployee> list = businessTypeEmpService.getData(employeeId, standardDate);
			if (list.isEmpty()) 
				return Optional.empty();
			return Optional.of(list.get(0));
		}

		@Override
		public Optional<WorkingConditionItem> getWorkingConditionHistory(String employeeId, GeneralDate standardDate) {
			Optional<WorkingConditionItem> rs = workingConditionRepo.getWorkingConditionItemByEmpIDAndDate(companyId, standardDate, employeeId);
			return rs;
		}

		@Override
		public String getLoginEmployeeId() {
			return AppContexts.user().employeeId();
		}
		
	}
	
	@AllArgsConstructor
	private static class EstimatedSalaryAggregationServiceRequireImpl implements EstimatedSalaryAggregationService.Require {
		
		private String companyId;
		private WorkScheduleRepository workScheduleRepository;
		private DailyRecordShareFinder dailyRecordShareFinder;
		private CriterionAmountUsageSettingRepository criterionAmountUsageSettingRepository;
		private CriterionAmountForCompanyRepository criterionAmountForCompanyRepository;
		private CriterionAmountForEmploymentRepository criterionAmountForEmploymentRepository;
		private EmploymentHisScheduleAdapter employmentHisScheduleAdapter;
		private HandlingOfCriterionAmountRepository handlingOfCriterionAmountRepository;
		
		@Override
		public List<IntegrationOfDaily> getSchduleList(List<EmployeeId> empIds, DatePeriod period) {
			return workScheduleRepository
					.getList(empIds.stream().map(el -> el.v()).collect(Collectors.toList()), period)
					.stream().map(el -> el.convertToIntegrationOfDaily()).collect(Collectors.toList());
		}

		@Override
		public List<IntegrationOfDaily> getRecordList(List<EmployeeId> empIds, DatePeriod period) {
			return dailyRecordShareFinder.findByListEmployeeId(empIds.stream().map(el -> el.v()).collect(Collectors.toList()), period);
		}

		@Override
		public Optional<CriterionAmountUsageSetting> getUsageSetting() {
			return criterionAmountUsageSettingRepository.get(companyId);
		}

		@Override
		public Optional<CriterionAmountForCompany> getCriterionAmountForCompany() {
			return criterionAmountForCompanyRepository.get(companyId);
		}

		@Override
		public Optional<CriterionAmountForEmployment> getCriterionAmountForEmployment(EmploymentCode employmentCode) {
			return criterionAmountForEmploymentRepository.get(companyId, employmentCode);
		}

		@Override
		public Optional<EmploymentPeriodImported> getEmploymentHistory(EmployeeId empId, GeneralDate date) {
			val result = employmentHisScheduleAdapter.getEmploymentPeriod(Arrays.asList(empId.v()), new DatePeriod(date, date));
			if (result.isEmpty())
				return Optional.empty();
			return Optional.of(result.get(0));
		}

		@Override
		public Optional<HandlingOfCriterionAmount> getHandling() {
			return handlingOfCriterionAmountRepository.get(companyId);
		}
		
	}
}
