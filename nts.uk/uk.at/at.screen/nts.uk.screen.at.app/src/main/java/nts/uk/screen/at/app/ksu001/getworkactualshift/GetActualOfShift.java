/**
 *
 */
package nts.uk.screen.at.app.ksu001.getworkactualshift;

import javax.ejb.Stateless;

/**
 * @author laitv
 * 勤務実績（シフト）を取得する
 */
@Stateless
public class GetActualOfShift {

//	@Inject
//	private EmpComHisAdapter empComHisAdapter;
//	@Inject
//	private WorkingConditionRepository workCondRepo;
//	@Inject
//	private EmpLeaveHistoryAdapter empLeaveHisAdapter;
//	@Inject
//	private EmpLeaveWorkHistoryAdapter empLeaveWorkHisAdapter;
//	@Inject
//	private EmploymentHisScheduleAdapter employmentHisScheduleAdapter;
//	@Inject
//	private DailyRecordWorkFinder dailyRecordWorkFinder;
//	@Inject
//	private CreateWorkScheduleShiftBase createWorkScheduleShiftBase;
//	@Inject
//	private EmpAffiliationInforAdapter empAffiliationInforAdapter;
//
//	public ScheduleOfShiftResult getActualOfShift(ScheduleOfShiftParam param) {

//		// step 1
//		// call 予定管理状態に応じて日別実績を取得する
//		DatePeriod period = new DatePeriod(param.startDate, param.endDate);
//		RequireDailyImpl requireDailyImpl = new RequireDailyImpl(param.listSid, period);
//		Map<EmployeeWorkingStatus , Optional<IntegrationOfDaily>> mapDataDaily = GetDailyRecordByScheduleManagementService.get(requireDailyImpl, param.listSid, period);
//
//		// step 2
//		// call 勤務実績で勤務予定（シフト）dtoを作成する
//		WorkScheduleShiftBaseResult rs = createWorkScheduleShiftBase.getWorkScheduleShiftBase(mapDataDaily, param.listShiftMasterNotNeedGetNew);
//		Map<ShiftMaster,Optional<WorkStyle>> mapShiftMasterWithWorkStyle = rs.mapShiftMasterWithWorkStyle;
//		List<ShiftMasterMapWithWorkStyle> listShiftMaster = new ArrayList<>();
//		mapShiftMasterWithWorkStyle.forEach((k,v)-> {
//			ShiftMasterMapWithWorkStyle shift = new ShiftMasterMapWithWorkStyle(k, v.isPresent() ? v.get().value + "" : null);
//			listShiftMaster.add(shift);
//
//		});
//		return new ScheduleOfShiftResult(rs.listWorkScheduleShift, listShiftMaster);
//	}
//
//	private class RequireDailyImpl implements GetDailyRecordByScheduleManagementService.Require {
//
//		private NestedMapCache<String, GeneralDate, DailyRecordDto> workScheduleCache;
//		private KeyDateHistoryCache<String, EmpEnrollPeriodImport> affCompanyHistByEmployeeCache;
//		private KeyDateHistoryCache<String, EmploymentPeriodImported> employmentPeriodCache;
//		private KeyDateHistoryCache<String, EmployeeLeaveJobPeriodImport> empLeaveJobPeriodCache;
//		private KeyDateHistoryCache<String, EmpLeaveWorkPeriodImport> empLeaveWorkPeriodCache;
//		private KeyDateHistoryCache<String, WorkingConditionItemWithPeriod> workCondItemWithPeriodCache;
//
//		public RequireDailyImpl(List<String> empIdList, DatePeriod period) {
//
//			List<DailyRecordDto> sDailyRecordDtos = dailyRecordWorkFinder.find(empIdList, period);
//			workScheduleCache = NestedMapCache.preloadedAll(sDailyRecordDtos.stream(),
//					workSchedule -> workSchedule.getEmployeeId(),
//					workSchedule -> workSchedule.getDate());
//
//			List<EmpEnrollPeriodImport> affCompanyHists =  empComHisAdapter.getEnrollmentPeriod(empIdList, period);
//			Map<String, List<EmpEnrollPeriodImport>> data2 = affCompanyHists.stream().collect(Collectors.groupingBy(item ->item.getEmpID()));
//			affCompanyHistByEmployeeCache = KeyDateHistoryCache.loaded(createEntries1(data2));
//
//			List<EmploymentPeriodImported> listEmploymentPeriodImported = employmentHisScheduleAdapter.getEmploymentPeriod(empIdList, period);
//			Map<String, List<EmploymentPeriodImported>> data3 = listEmploymentPeriodImported.stream().collect(Collectors.groupingBy(item ->item.getEmpID()));
//			employmentPeriodCache = KeyDateHistoryCache.loaded(createEntries2(data3));
//
//			List<EmployeeLeaveJobPeriodImport> empLeaveJobPeriods = empLeaveHisAdapter.getLeaveBySpecifyingPeriod(empIdList, period);
//			Map<String, List<EmployeeLeaveJobPeriodImport>> data4 = empLeaveJobPeriods.stream().collect(Collectors.groupingBy(item ->item.getEmpID()));
//			empLeaveJobPeriodCache = KeyDateHistoryCache.loaded(createEntries3(data4));
//
//			List<EmpLeaveWorkPeriodImport> empLeaveWorkPeriods =  empLeaveWorkHisAdapter.getHolidayPeriod(empIdList, period);
//			Map<String, List<EmpLeaveWorkPeriodImport>> data5 = empLeaveWorkPeriods.stream().collect(Collectors.groupingBy(item ->item.getEmpID()));
//			empLeaveWorkPeriodCache = KeyDateHistoryCache.loaded(createEntries4(data5));
//
//			List<WorkingConditionItemWithPeriod> listData = workCondRepo.getWorkingConditionItemWithPeriod(AppContexts.user().companyId(),empIdList, period);
//			Map<String, List<WorkingConditionItemWithPeriod>> data6 = listData.stream().collect(Collectors.groupingBy(item ->item.getWorkingConditionItem().getEmployeeId()));
//			workCondItemWithPeriodCache = KeyDateHistoryCache.loaded(createEntries5(data6));
//		}
//
//		private Map<String, List<DateHistoryCache.Entry<EmpEnrollPeriodImport>>>  createEntries1(Map<String, List<EmpEnrollPeriodImport>> data) {
//			Map<String, List<DateHistoryCache.Entry<EmpEnrollPeriodImport>>> rs = new HashMap<>();
//			data.forEach( (k,v) -> {
//				List<DateHistoryCache.Entry<EmpEnrollPeriodImport>> s = v.stream().map(i->new DateHistoryCache.Entry<EmpEnrollPeriodImport>(i.getDatePeriod(),i)).collect(Collectors.toList()) ;
//				rs.put(k, s);
//			});
//			return rs;
//		}
//
//		private Map<String, List<DateHistoryCache.Entry<EmploymentPeriodImported>>>  createEntries2(Map<String, List<EmploymentPeriodImported>> data) {
//			Map<String, List<DateHistoryCache.Entry<EmploymentPeriodImported>>> rs = new HashMap<>();
//			data.forEach( (k,v) -> {
//				List<DateHistoryCache.Entry<EmploymentPeriodImported>> s = v.stream().map(i->new DateHistoryCache.Entry<EmploymentPeriodImported>(i.getDatePeriod(),i)).collect(Collectors.toList()) ;
//				rs.put(k, s);
//			});
//			return rs;
//		}
//
//		private Map<String, List<DateHistoryCache.Entry<EmployeeLeaveJobPeriodImport>>>  createEntries3(Map<String, List<EmployeeLeaveJobPeriodImport>> data) {
//			Map<String, List<DateHistoryCache.Entry<EmployeeLeaveJobPeriodImport>>> rs = new HashMap<>();
//			data.forEach( (k,v) -> {
//				List<DateHistoryCache.Entry<EmployeeLeaveJobPeriodImport>> s = v.stream().map(i->new DateHistoryCache.Entry<EmployeeLeaveJobPeriodImport>(i.getDatePeriod(),i)).collect(Collectors.toList()) ;
//				rs.put(k, s);
//			});
//			return rs;
//		}
//
//		private Map<String, List<DateHistoryCache.Entry<EmpLeaveWorkPeriodImport>>>  createEntries4(Map<String, List<EmpLeaveWorkPeriodImport>> data) {
//			Map<String, List<DateHistoryCache.Entry<EmpLeaveWorkPeriodImport>>> rs = new HashMap<>();
//			data.forEach( (k,v) -> {
//				List<DateHistoryCache.Entry<EmpLeaveWorkPeriodImport>> s = v.stream().map(i->new DateHistoryCache.Entry<EmpLeaveWorkPeriodImport>(i.getDatePeriod(),i)).collect(Collectors.toList()) ;
//				rs.put(k, s);
//			});
//			return rs;
//		}
//
//		private Map<String, List<DateHistoryCache.Entry<WorkingConditionItemWithPeriod>>>  createEntries5(Map<String, List<WorkingConditionItemWithPeriod>> data) {
//			Map<String, List<DateHistoryCache.Entry<WorkingConditionItemWithPeriod>>> rs = new HashMap<>();
//			data.forEach( (k,v) -> {
//				List<DateHistoryCache.Entry<WorkingConditionItemWithPeriod>> s = v.stream().map(i->new DateHistoryCache.Entry<WorkingConditionItemWithPeriod>(i.getDatePeriod(),i)).collect(Collectors.toList()) ;
//				rs.put(k, s);
//			});
//			return rs;
//		}
//
//		@Override
//		public Optional<IntegrationOfDaily> getDailyResults(String empId, GeneralDate date) {
//			Optional<DailyRecordDto> dailyRecordDto = workScheduleCache.get(empId, date);
//			if (dailyRecordDto.isPresent()) {
//				IntegrationOfDaily data = dailyRecordDto.get().toDomain(empId, date);
//				return Optional.of(data);
//			}
//			return Optional.empty();
//		}
//
//		@Override
//		public Optional<EmpEnrollPeriodImport> getAffCompanyHistByEmployee(String sid, GeneralDate startDate) {
//			return affCompanyHistByEmployeeCache.get(sid, startDate);
//		}
//
//		@Override
//		public Optional<WorkingConditionItem> getBySidAndStandardDate(String employeeId, GeneralDate baseDate) {
//			Optional<WorkingConditionItemWithPeriod> data = workCondItemWithPeriodCache.get(employeeId, baseDate);
//			return data.isPresent() ? Optional.of(data.get().getWorkingConditionItem()) : Optional.empty();
//		}
//
//		@Override
//		public Optional<EmployeeLeaveJobPeriodImport> getByDatePeriod(String sid, GeneralDate startDate) {
//			return  empLeaveJobPeriodCache.get(sid, startDate);
//		}
//
//		@Override
//		public Optional<EmpLeaveWorkPeriodImport> specAndGetHolidayPeriod(String sid, GeneralDate startDate) {
//			return empLeaveWorkPeriodCache.get(sid, startDate);
//		}
//
//		@Override
//		public Optional<EmploymentPeriodImported> getEmploymentHistory(String sid, GeneralDate startDate) {
//			return employmentPeriodCache.get(sid, startDate);
//		}
//
//		@Override
//		public List<EmpOrganizationImport> getEmpOrganization(GeneralDate baseDate, List<String> lstEmpId) {
//			return empAffiliationInforAdapter.getEmpOrganization(baseDate, lstEmpId);
//		}
//	}
}
