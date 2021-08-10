package nts.uk.screen.at.app.ksu001.aggreratepersonaltotal;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.AllArgsConstructor;
import nts.arc.layer.app.cache.DateHistoryCache;
import nts.arc.layer.app.cache.KeyDateHistoryCache;
import nts.arc.layer.app.cache.NestedMapCache;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.DateInMonth;
import nts.arc.time.calendar.period.DatePeriod;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.aggregation.dom.schedulecounter.aggregationprocess.personcounter.EstimatedSalary;
import nts.uk.ctx.at.aggregation.dom.schedulecounter.aggregationprocess.personcounter.EstimatedSalaryAggregationService;
import nts.uk.ctx.at.aggregation.dom.schedulecounter.aggregationprocess.personcounter.WorkdayHolidayCounterService;
import nts.uk.ctx.at.aggregation.dom.schedulecounter.aggregationprocess.personcounter.WorkingTimeCounterService;
import nts.uk.ctx.at.aggregation.dom.schedulecounter.criterion.CriterionAmountForCompany;
import nts.uk.ctx.at.aggregation.dom.schedulecounter.criterion.CriterionAmountForCompanyRepository;
import nts.uk.ctx.at.aggregation.dom.schedulecounter.criterion.CriterionAmountForEmployment;
import nts.uk.ctx.at.aggregation.dom.schedulecounter.criterion.CriterionAmountForEmploymentRepository;
import nts.uk.ctx.at.aggregation.dom.schedulecounter.criterion.CriterionAmountUsageSetting;
import nts.uk.ctx.at.aggregation.dom.schedulecounter.criterion.CriterionAmountUsageSettingRepository;
import nts.uk.ctx.at.aggregation.dom.schedulecounter.criterion.HandlingOfCriterionAmount;
import nts.uk.ctx.at.aggregation.dom.schedulecounter.criterion.HandlingOfCriterionAmountRepository;
import nts.uk.ctx.at.aggregation.dom.schedulecounter.tally.PersonalCounterCategory;
import nts.uk.ctx.at.record.app.find.dailyperform.DailyRecordDto;
import nts.uk.ctx.at.record.app.find.dailyperform.DailyRecordWorkFinder;
import nts.uk.ctx.at.schedule.dom.schedule.workschedule.ScheManaStatuTempo;
import nts.uk.ctx.at.schedule.dom.schedule.workschedule.WorkSchedule;
import nts.uk.ctx.at.schedule.dom.schedule.workschedule.WorkScheduleRepository;
import nts.uk.ctx.at.schedule.dom.workschedule.domainservice.DailyResultAccordScheduleStatusService;
import nts.uk.ctx.at.shared.dom.adapter.employment.employwork.leaveinfo.EmpLeaveHistoryAdapter;
import nts.uk.ctx.at.shared.dom.adapter.employment.employwork.leaveinfo.EmpLeaveWorkHistoryAdapter;
import nts.uk.ctx.at.shared.dom.adapter.employment.employwork.leaveinfo.EmpLeaveWorkPeriodImport;
import nts.uk.ctx.at.shared.dom.adapter.employment.employwork.leaveinfo.EmployeeLeaveJobPeriodImport;
import nts.uk.ctx.at.shared.dom.common.EmployeeId;
import nts.uk.ctx.at.shared.dom.scherec.aggregation.perdaily.AttendanceTimesForAggregation;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.dailyattendancework.IntegrationOfDaily;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.EmploymentCode;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItem;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItemWithPeriod;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionRepository;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.employeeinfor.employmenthistory.imported.EmpComHisAdapter;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.employeeinfor.employmenthistory.imported.EmpEnrollPeriodImport;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.employeeinfor.employmenthistory.imported.EmploymentHisScheduleAdapter;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.employeeinfor.employmenthistory.imported.EmploymentPeriodImported;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeCode;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * 個人計を集計する
 * @author hoangnd
 *
 */

@Stateless
public class ScreenQueryAggregatePersonal {

	@Inject 
	private ScreenQueryAggregateNumberTimePs screenQueryAggrerateNumberTime;
	
	@Inject
	private EmpComHisAdapter empComHisAdapter;
	
	@Inject
	private WorkingConditionRepository workCondRepo;
	
	@Inject
	private EmpLeaveHistoryAdapter empLeaveHisAdapter;
	
	@Inject
	private EmpLeaveWorkHistoryAdapter empLeaveWorkHisAdapter;
	
	@Inject
	private EmploymentHisScheduleAdapter employmentHisScheduleAdapter;
	
	@Inject
	private DailyRecordWorkFinder dailyRecordWorkFinder;
	
	@Inject
	private WorkScheduleRepository workScheduleRepository;
	
	@Inject
	private WorkTypeRepository workTypeRepository;
	
	@Inject
	private CriterionAmountUsageSettingRepository criterionAmountUsageSettingRepository;
	
	@Inject
	private CriterionAmountForCompanyRepository criterionAmountForCompanyRepository;
	
	@Inject
	private CriterionAmountForEmploymentRepository criterionAmountForEmploymentRepository;
	
	@Inject
	private HandlingOfCriterionAmountRepository handlingOfCriterionAmountRepository;
	
	
	public AggregatePersonalDto aggreratePersonal(
			PersonalCounterCategory personalCounter, // 個人計カテゴリ
			List<IntegrationOfDaily> aggrerateintegrationOfDaily, // 日別勤怠リスト
			DatePeriod datePeriod, // 期間
			DateInMonth closeDate // 締め日
			) {
		
		Require require = new Require(
				workScheduleRepository,
				dailyRecordWorkFinder,
				empComHisAdapter,
				workCondRepo,
				empLeaveHisAdapter,
				empLeaveWorkHisAdapter,
				employmentHisScheduleAdapter,
				criterionAmountUsageSettingRepository,
				criterionAmountForCompanyRepository,
				criterionAmountForEmploymentRepository,
				handlingOfCriterionAmountRepository,
				AppContexts.user().companyId()
				);
		
		Require2 require2 = new Require2(workTypeRepository);
		
		AggregatePersonalDto output = new AggregatePersonalDto();
		if (personalCounter == PersonalCounterCategory.WORKING_HOURS) {
			// 1: 集計する(List<日別勤怠(Work)>)
			Map<EmployeeId, Map<AttendanceTimesForAggregation, BigDecimal>> dailyWorks = 
					WorkingTimeCounterService.get(aggrerateintegrationOfDaily);
			
			Map<String, Map<Integer, BigDecimal>> workHours = dailyWorks.entrySet()
					  .stream()
					  .collect(Collectors.toMap(
							  e -> e.getKey().v(),
							  e -> e.getValue()
							  	.entrySet()
							  	.stream()
							  	.collect(Collectors.toMap(
							  			x -> x.getKey().getValue(),
							  			x -> x.getValue())))
							  );
			output.setWorkHours(workHours);
		}
	
		else if (personalCounter == PersonalCounterCategory.MONTHLY_EXPECTED_SALARY) {
			
			// 2: 月間想定給与額を集計する(Require, 年月, 日付, List<日別勤怠(Work)>)
			Map<EmployeeId, EstimatedSalary> etimatedMonthSalarys = EstimatedSalaryAggregationService.aggregateByMonthly(
					require,
					datePeriod.end().yearMonth(),
					closeDate,
					aggrerateintegrationOfDaily
					);
			output.estimatedSalary = etimatedMonthSalarys.entrySet()
										.stream()
										.collect(Collectors.toMap(
												e -> e.getKey().v(),
												e -> EstimatedSalaryDto.fromDomain(e.getValue())));
			
		}
		else if (personalCounter == PersonalCounterCategory.CUMULATIVE_ESTIMATED_SALARY) { // 個人計カテゴリ == 年間想定給与額
			
			// 3: 年間想定給与額を集計する(Require, 年月日, List<社員ID>)
			Map<EmployeeId, EstimatedSalary> etimatedYearSalarys = EstimatedSalaryAggregationService.aggregateByYearly(
					require, // require
					datePeriod.end(), // 基準日 = Input.期間.終了日
					aggrerateintegrationOfDaily.stream() // 社員IDリスト = Input.日別勤怠リスト : map $.社員ID distinct
					.map(x -> x.getEmployeeId())
					.distinct()
					.map(x -> new EmployeeId(x))
					.collect(Collectors.toList())
					); 
			
			output.estimatedSalary = etimatedYearSalarys.entrySet()
					.stream()
					.collect(Collectors.toMap(
							e -> e.getKey().v(),
							e -> EstimatedSalaryDto.fromDomain(e.getValue())));
		}
		
		// 4: 個人計カテゴリ == 回数集計１ or 回数集計２ or 回数集計３
		else if (personalCounter == PersonalCounterCategory.TIMES_COUNTING_1
				|| personalCounter == PersonalCounterCategory.TIMES_COUNTING_2
				|| personalCounter == PersonalCounterCategory.TIMES_COUNTING_3) {
			
			// 回数集計を集計する
			Map<String, Map<TotalTimesDto, BigDecimal>> timeCount = 
					screenQueryAggrerateNumberTime.aggregate(
							personalCounter,
							aggrerateintegrationOfDaily)
					.entrySet()
					.stream()
					.collect(
							Collectors.toMap(
							e -> e.getKey().v(),
							e -> e.getValue().entrySet()
											 .stream()
											 .collect(
												 Collectors.toMap(
														 f -> TotalTimesDto.fromDomain(f.getKey()),
														 f -> f.getValue())
											 )
							 )
					);
			output.setTimeCount(timeCount);
		}
		
		// 5: 個人計カテゴリ == 出勤・休日日数
		else if (personalCounter == PersonalCounterCategory.ATTENDANCE_HOLIDAY_DAYS) {
			
			// 集計する(Require, List<日別勤怠(Work)>)
			Map<String, Map<Integer, BigDecimal>> attendenceHoliday = 
					WorkdayHolidayCounterService.count(
							require2,
							aggrerateintegrationOfDaily)
					.entrySet()
					.stream()
					.collect(
							Collectors.toMap(
							e -> e.getKey().v(),
							e -> e.getValue().entrySet()
											 .stream()
											 .collect(
												 Collectors.toMap(
														 f -> f.getKey().value,
														 f -> f.getValue())
											 )
							 )
					);
			output.setWorkHours(attendenceHoliday);
		}
		
		return output;
		
	}
	
	@AllArgsConstructor
	private static class Require2 implements WorkdayHolidayCounterService.Require {
		
		@Inject
		private WorkTypeRepository workTypeRepository;
		
		@Override
		public Optional<WorkType> getWorkType(WorkTypeCode workTypeCd) {
			return workTypeRepository.findByPK(AppContexts.user().companyId(), workTypeCd.v());
		}
		
	}
	
	@AllArgsConstructor
	private static class Require implements EstimatedSalaryAggregationService.Require {

		private WorkScheduleRepository workScheduleRepository;
		
		private DailyRecordWorkFinder dailyRecordWorkFinder;

		private EmpComHisAdapter empComHisAdapter;
		
		private WorkingConditionRepository workCondRepo;
		
		private EmpLeaveHistoryAdapter empLeaveHisAdapter;
		
		private EmpLeaveWorkHistoryAdapter empLeaveWorkHisAdapter;
		
		private EmploymentHisScheduleAdapter employmentHisScheduleAdapter;
		
		private CriterionAmountUsageSettingRepository criterionAmountUsageSettingRepository;
		
		private CriterionAmountForCompanyRepository criterionAmountForCompanyRepository;
		
		private CriterionAmountForEmploymentRepository criterionAmountForEmploymentRepository;
		
		private HandlingOfCriterionAmountRepository handlingOfCriterionAmountRepository;
		
		
		
		private String cid;

		
//		public Require(
//				WorkScheduleRepository workScheduleRepository,
//				DailyRecordWorkFinder dailyRecordWorkFinder,
//				EmpComHisAdapter empComHisAdapter,
//				WorkingConditionRepository workCondRepo,
//				EmpLeaveHistoryAdapter empLeaveHisAdapter,
//				EmpLeaveWorkHistoryAdapter empLeaveWorkHisAdapter,
//				EmploymentHisScheduleAdapter employmentHisScheduleAdapter,
//				CriterionAmountUsageSettingRepository criterionAmountUsageSettingRepository,
//				CriterionAmountForCompanyRepository criterionAmountForCompanyRepository,
//				CriterionAmountForEmploymentRepository criterionAmountForEmploymentRepository,
//				HandlingOfCriterionAmountRepository handlingOfCriterionAmountRepository
//				) {
//			this.workScheduleRepository = workScheduleRepository;
//
//			}
		
		
		@Override
		public List<IntegrationOfDaily> getSchduleList(List<EmployeeId> empIds, DatePeriod period) {
			List<WorkSchedule> workSchedules = 
					workScheduleRepository.getList(
						empIds.stream()
							.map(x -> x.v())
							.collect(Collectors.toList()),
						period);
			return workSchedules.stream()
								 .map(WorkSchedule::convertToIntegrationOfDaily)
								 .collect(Collectors.toList());
		}

		@Override
		public List<IntegrationOfDaily> getRecordList(List<EmployeeId> empIds, DatePeriod period) {
			List<String> sids = empIds.stream()
					  .map(x -> x.v())
					  .collect(Collectors.toList());
			RequireDailyImpl requireDailyImpl = new RequireDailyImpl(
					sids,
					period,
					dailyRecordWorkFinder,
					empComHisAdapter,
					workCondRepo,
					empLeaveHisAdapter,
					empLeaveWorkHisAdapter,
					employmentHisScheduleAdapter);
			Map<ScheManaStatuTempo , Optional<IntegrationOfDaily>> map = DailyResultAccordScheduleStatusService.get(requireDailyImpl, sids, period);
			
			return map.entrySet()
					  .stream()
					  .map(Map.Entry::getValue)
					  .filter(x -> x.isPresent())
					  .map(x -> x.get())
					  .collect(Collectors.toList());
		}

		@Override
		public Optional<CriterionAmountUsageSetting> getUsageSetting() {
			return criterionAmountUsageSettingRepository.get(cid);
		}

		@Override
		public Optional<CriterionAmountForCompany> getCriterionAmountForCompany() {
			return criterionAmountForCompanyRepository.get(cid);
		}

		@Override
		public Optional<CriterionAmountForEmployment> getCriterionAmountForEmployment(EmploymentCode employmentCode) {
			return criterionAmountForEmploymentRepository.get(cid, employmentCode);
		}

		@Override
		public Optional<EmploymentPeriodImported> getEmploymentHistory(EmployeeId empId, GeneralDate date) {
			String sid = empId.v();
			List<String> sids = new ArrayList<String>();
			sids.add(sid);
			List<EmploymentPeriodImported> employmentPeriodImporteds = employmentHisScheduleAdapter.getEmploymentPeriod(sids, new DatePeriod(date, date));
			return CollectionUtil.isEmpty(employmentPeriodImporteds) ? Optional.empty() : Optional.of(employmentPeriodImporteds.get(0));
		}

		@Override
		public Optional<HandlingOfCriterionAmount> getHandling() {
			return handlingOfCriterionAmountRepository.get(cid);
		}
		
		@AllArgsConstructor
		private static class RequireDailyImpl implements DailyResultAccordScheduleStatusService.Require {

			private NestedMapCache<String, GeneralDate, DailyRecordDto> workScheduleCache;
			private KeyDateHistoryCache<String, EmpEnrollPeriodImport> affCompanyHistByEmployeeCache;
			private KeyDateHistoryCache<String, EmploymentPeriodImported> employmentPeriodCache;
			private KeyDateHistoryCache<String, EmployeeLeaveJobPeriodImport> empLeaveJobPeriodCache;
			private KeyDateHistoryCache<String, EmpLeaveWorkPeriodImport> empLeaveWorkPeriodCache;
			private KeyDateHistoryCache<String, WorkingConditionItemWithPeriod> workCondItemWithPeriodCache;

			public RequireDailyImpl(
					List<String> empIdList,
					DatePeriod period,
					DailyRecordWorkFinder dailyRecordWorkFinder,
					EmpComHisAdapter empComHisAdapter,
					WorkingConditionRepository workCondRepo,
					EmpLeaveHistoryAdapter empLeaveHisAdapter,
					EmpLeaveWorkHistoryAdapter empLeaveWorkHisAdapter,
					EmploymentHisScheduleAdapter employmentHisScheduleAdapter) {

				long start1 = System.nanoTime();
				List<DailyRecordDto> sDailyRecordDtos = dailyRecordWorkFinder.find(empIdList, period);
				workScheduleCache = NestedMapCache.preloadedAll(sDailyRecordDtos.stream(),
						workSchedule -> workSchedule.getEmployeeId(),
						workSchedule -> workSchedule.getDate());
				System.out.println("thoi gian get data Daily " + ((System.nanoTime() - start1 )/1000000) + "ms");

				long start2 = System.nanoTime();
				List<EmpEnrollPeriodImport> affCompanyHists =  empComHisAdapter.getEnrollmentPeriod(empIdList, period);
				Map<String, List<EmpEnrollPeriodImport>> data2 = affCompanyHists.stream().collect(Collectors.groupingBy(item ->item.getEmpID()));
				affCompanyHistByEmployeeCache = KeyDateHistoryCache.loaded(createEntries1(data2));
				System.out.println("thoi gian get data affCompanyHistByEmp " + ((System.nanoTime() - start2 )/1000000) + "ms");

				long start3 = System.nanoTime();
				List<EmploymentPeriodImported> listEmploymentPeriodImported = employmentHisScheduleAdapter.getEmploymentPeriod(empIdList, period);
				Map<String, List<EmploymentPeriodImported>> data3 = listEmploymentPeriodImported.stream().collect(Collectors.groupingBy(item ->item.getEmpID()));
				employmentPeriodCache = KeyDateHistoryCache.loaded(createEntries2(data3));
				System.out.println("thoi gian get data EmploymentPeriod " + ((System.nanoTime() - start3 )/1000000) + "ms");

				long start4 = System.nanoTime();
				List<EmployeeLeaveJobPeriodImport> empLeaveJobPeriods = empLeaveHisAdapter.getLeaveBySpecifyingPeriod(empIdList, period);
				Map<String, List<EmployeeLeaveJobPeriodImport>> data4 = empLeaveJobPeriods.stream().collect(Collectors.groupingBy(item ->item.getEmpID()));
				empLeaveJobPeriodCache = KeyDateHistoryCache.loaded(createEntries3(data4));
				System.out.println("thoi gian get data EmployeeLeaveJob " + ((System.nanoTime() - start4 )/1000000) + "ms");

				long start5 = System.nanoTime();
				List<EmpLeaveWorkPeriodImport> empLeaveWorkPeriods =  empLeaveWorkHisAdapter.getHolidayPeriod(empIdList, period);
				Map<String, List<EmpLeaveWorkPeriodImport>> data5 = empLeaveWorkPeriods.stream().collect(Collectors.groupingBy(item ->item.getEmpID()));
				empLeaveWorkPeriodCache = KeyDateHistoryCache.loaded(createEntries4(data5));
				System.out.println("thoi gian get data EmpLeaveWork " + ((System.nanoTime() - start5 )/1000000) + "ms");

				long start6 = System.nanoTime();
				List<WorkingConditionItemWithPeriod> listData = workCondRepo.getWorkingConditionItemWithPeriod(AppContexts.user().companyId(),empIdList, period);
				Map<String, List<WorkingConditionItemWithPeriod>> data6 = listData.stream().collect(Collectors.groupingBy(item ->item.getWorkingConditionItem().getEmployeeId()));
				workCondItemWithPeriodCache = KeyDateHistoryCache.loaded(createEntries5(data6));
				System.out.println("thoi gian get data WorkingConditionItem " + ((System.nanoTime() - start6 )/1000000) + "ms");

				System.out.println("thoi gian get data để lưu vào Cache" + ((System.nanoTime() - start1 )/1000000) + "ms");
			}
			
			private static Map<String, List<DateHistoryCache.Entry<EmpEnrollPeriodImport>>>  createEntries1(Map<String, List<EmpEnrollPeriodImport>> data) {
				Map<String, List<DateHistoryCache.Entry<EmpEnrollPeriodImport>>> rs = new HashMap<>();
				data.forEach( (k,v) -> {
					List<DateHistoryCache.Entry<EmpEnrollPeriodImport>> s = v.stream().map(i->new DateHistoryCache.Entry<EmpEnrollPeriodImport>(i.getDatePeriod(),i)).collect(Collectors.toList()) ;
					rs.put(k, s);
				});
				return rs;
			}
			
			private static Map<String, List<DateHistoryCache.Entry<EmploymentPeriodImported>>>  createEntries2(Map<String, List<EmploymentPeriodImported>> data) {
				Map<String, List<DateHistoryCache.Entry<EmploymentPeriodImported>>> rs = new HashMap<>();
				data.forEach( (k,v) -> {
					List<DateHistoryCache.Entry<EmploymentPeriodImported>> s = v.stream().map(i->new DateHistoryCache.Entry<EmploymentPeriodImported>(i.getDatePeriod(),i)).collect(Collectors.toList()) ;
					rs.put(k, s);
				});
				return rs;
			}
			
			private static Map<String, List<DateHistoryCache.Entry<EmployeeLeaveJobPeriodImport>>>  createEntries3(Map<String, List<EmployeeLeaveJobPeriodImport>> data) {
				Map<String, List<DateHistoryCache.Entry<EmployeeLeaveJobPeriodImport>>> rs = new HashMap<>();
				data.forEach( (k,v) -> {
					List<DateHistoryCache.Entry<EmployeeLeaveJobPeriodImport>> s = v.stream().map(i->new DateHistoryCache.Entry<EmployeeLeaveJobPeriodImport>(i.getDatePeriod(),i)).collect(Collectors.toList()) ;
					rs.put(k, s);
				});
				return rs;
			}
			
			private static Map<String, List<DateHistoryCache.Entry<EmpLeaveWorkPeriodImport>>>  createEntries4(Map<String, List<EmpLeaveWorkPeriodImport>> data) {
				Map<String, List<DateHistoryCache.Entry<EmpLeaveWorkPeriodImport>>> rs = new HashMap<>();
				data.forEach( (k,v) -> {
					List<DateHistoryCache.Entry<EmpLeaveWorkPeriodImport>> s = v.stream().map(i->new DateHistoryCache.Entry<EmpLeaveWorkPeriodImport>(i.getDatePeriod(),i)).collect(Collectors.toList()) ;
					rs.put(k, s);
				});
				return rs;
			}
			
			private static Map<String, List<DateHistoryCache.Entry<WorkingConditionItemWithPeriod>>>  createEntries5(Map<String, List<WorkingConditionItemWithPeriod>> data) {
				Map<String, List<DateHistoryCache.Entry<WorkingConditionItemWithPeriod>>> rs = new HashMap<>();
				data.forEach( (k,v) -> {
					List<DateHistoryCache.Entry<WorkingConditionItemWithPeriod>> s = v.stream().map(i->new DateHistoryCache.Entry<WorkingConditionItemWithPeriod>(i.getDatePeriod(),i)).collect(Collectors.toList()) ;
					rs.put(k, s);
				});
				return rs;
			}

			@Override
			public Optional<IntegrationOfDaily> getDailyResults(String empId, GeneralDate date) {
				Optional<DailyRecordDto> dailyRecordDto = workScheduleCache.get(empId, date);
				if (dailyRecordDto.isPresent()) {
					IntegrationOfDaily data = dailyRecordDto.get().toDomain(empId, date);
					return Optional.of(data);
				}
				return Optional.empty();
			}

			@Override
			public Optional<EmpEnrollPeriodImport> getAffCompanyHistByEmployee(String sid, GeneralDate startDate) {
				Optional<EmpEnrollPeriodImport> data = affCompanyHistByEmployeeCache.get(sid, startDate);
				return data;
			}

			@Override
			public Optional<WorkingConditionItem> getBySidAndStandardDate(String employeeId, GeneralDate baseDate) {
				Optional<WorkingConditionItemWithPeriod> data = workCondItemWithPeriodCache.get(employeeId, baseDate);
				return data.isPresent() ? Optional.of(data.get().getWorkingConditionItem()) : Optional.empty();
			}

			@Override
			public Optional<EmployeeLeaveJobPeriodImport> getByDatePeriod(String sid, GeneralDate startDate) {
				Optional<EmployeeLeaveJobPeriodImport> data =  empLeaveJobPeriodCache.get(sid, startDate);
				return data;
			}

			@Override
			public Optional<EmpLeaveWorkPeriodImport> specAndGetHolidayPeriod(String sid, GeneralDate startDate) {
				Optional<EmpLeaveWorkPeriodImport> data = empLeaveWorkPeriodCache.get(sid, startDate);
				return data;
			}

			@Override
			public Optional<EmploymentPeriodImported> getEmploymentHistory(String sid, GeneralDate startDate) {
				Optional<EmploymentPeriodImported> data = employmentPeriodCache.get(sid, startDate);
				return data;
			}
		}
		
	}
}
