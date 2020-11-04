package nts.uk.screen.at.app.query.ksu.ksu002.a;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.AllArgsConstructor;
import nts.arc.layer.app.cache.DateHistoryCache;
import nts.arc.layer.app.cache.KeyDateHistoryCache;
import nts.arc.layer.app.cache.NestedMapCache;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.schedule.dom.schedule.workschedule.ConfirmedATR;
import nts.uk.ctx.at.schedule.dom.schedule.workschedule.ScheManaStatuTempo;
import nts.uk.ctx.at.schedule.dom.schedule.workschedule.WorkSchedule;
import nts.uk.ctx.at.schedule.dom.schedule.workschedule.WorkScheduleRepository;
import nts.uk.ctx.at.schedule.dom.workschedule.domainservice.WorkScheManaStatusService;
import nts.uk.ctx.at.shared.dom.WorkInformation;
import nts.uk.ctx.at.shared.dom.adapter.employment.employwork.leaveinfo.EmpLeaveHistoryAdapter;
import nts.uk.ctx.at.shared.dom.adapter.employment.employwork.leaveinfo.EmpLeaveWorkHistoryAdapter;
import nts.uk.ctx.at.shared.dom.adapter.employment.employwork.leaveinfo.EmpLeaveWorkPeriodImport;
import nts.uk.ctx.at.shared.dom.adapter.employment.employwork.leaveinfo.EmployeeLeaveJobPeriodImport;
import nts.uk.ctx.at.shared.dom.schedule.basicschedule.BasicScheduleService;
import nts.uk.ctx.at.shared.dom.schedule.basicschedule.SetupType;
import nts.uk.ctx.at.shared.dom.schedule.basicschedule.WorkStyle;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.attendancetime.TimeLeavingWork;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.editstate.EditStateOfDailyAttd;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.workinfomation.GetListWtypeWtimeUseDailyAttendRecordService;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.workinfomation.WorkInfoOfDailyAttendance;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.workinfomation.WorkTypeWorkTimeUseDailyAttendanceRecord;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItem;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItemWithPeriod;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionRepository;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.employeeinfor.employmenthistory.imported.EmpComHisAdapter;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.employeeinfor.employmenthistory.imported.EmpEnrollPeriodImport;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.employeeinfor.employmenthistory.imported.EmploymentHisScheduleAdapter;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.employeeinfor.employmenthistory.imported.EmploymentPeriodImported;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSetting;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSettingRepository;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSettingService;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.internal.PredetermineTimeSetForCalc;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeCode;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeInfor;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeRepository;
import nts.uk.screen.at.app.query.ksu.ksu002.a.dto.EditStateOfDailyAttdDto;
import nts.uk.screen.at.app.query.ksu.ksu002.a.dto.WorkScheduleWorkInforDto;
import nts.uk.screen.at.app.query.ksu.ksu002.a.input.DisplayInWorkInfoInput;
import nts.uk.screen.at.app.query.ksu.ksu002.a.input.GetDateInfoDuringThePeriodInput;
import nts.uk.shr.com.context.AppContexts;

/**
 * 
 * @author chungnt ScreenQuery : 勤務予定を取得する
 */

@Stateless
public class GetScheduleOfWorkInfo002 {
	@Inject
	private WorkScheduleRepository workScheduleRepo;

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
	private WorkTypeRepository workTypeRepo;
	@Inject
	private WorkTimeSettingRepository workTimeSettingRepo;
	@Inject
	private WorkTimeSettingService workTimeSettingService;
	@Inject
	private BasicScheduleService basicScheduleService;
	@Inject
	private GetDateInfoDuringThePeriod getDateInfoDuringThePeriod;

	public List<WorkScheduleWorkInforDto> getDataScheduleOfWorkInfo(DisplayInWorkInfoInput param) {

		String companyId = AppContexts.user().companyId();
		// step 1 start
		// call 予定管理状態に応じて勤務予定を取得する
		DatePeriod period = new DatePeriod(param.startDate, param.endDate);
		RequireImpl RequireImpl = new RequireImpl(param.listSid, period, workScheduleRepo, empComHisAdapter,
				workCondRepo, empLeaveHisAdapter, empLeaveWorkHisAdapter, employmentHisScheduleAdapter);

		// 管理状態と勤務予定Map
		Map<ScheManaStatuTempo, Optional<WorkSchedule>> mngStatusAndWScheMap = WorkScheManaStatusService
				.getScheduleManagement(RequireImpl, param.listSid, period);

		List<WorkInfoOfDailyAttendance> listWorkInfo = new ArrayList<WorkInfoOfDailyAttendance>();
		mngStatusAndWScheMap.forEach((k, v) -> {
			if (v.isPresent()) {
				WorkInfoOfDailyAttendance workInfo = v.get().getWorkInfo();
				listWorkInfo.add(workInfo);
			}
		});
		
		// call 日別勤怠の実績で利用する勤務種類と就業時間帯のリストを取得する
		WorkTypeWorkTimeUseDailyAttendanceRecord wTypeWTimeUseDailyAttendRecord = GetListWtypeWtimeUseDailyAttendRecordService
				.getdata(listWorkInfo);

		List<WorkTypeCode> workTypeCodes = wTypeWTimeUseDailyAttendRecord.getLstWorkTypeCode().stream()
				.filter(wt -> wt != null).collect(Collectors.toList());
		List<String> lstWorkTypeCode = workTypeCodes.stream().map(i -> i.toString()).collect(Collectors.toList());
		// <<Public>> 指定した勤務種類をすべて取得する
		List<WorkTypeInfor> lstWorkTypeInfor = this.workTypeRepo.getPossibleWorkTypeAndOrder(companyId,
				lstWorkTypeCode);

		List<WorkTimeCode> workTimeCodes = wTypeWTimeUseDailyAttendRecord.getLstWorkTimeCode().stream()
				.filter(wt -> wt != null).collect(Collectors.toList());
		List<String> lstWorkTimeCode = workTimeCodes.stream().map(i -> i.toString()).collect(Collectors.toList());
		List<WorkTimeSetting> lstWorkTimeSetting = workTimeSettingRepo.getListWorkTimeSetByListCode(companyId,
				lstWorkTimeCode);

		List<WorkScheduleWorkInforDto> listWorkScheduleWorkInfor = new ArrayList<>();
		mngStatusAndWScheMap.forEach((k, v) -> {
			ScheManaStatuTempo key = k;
			Optional<WorkSchedule> value = v;

			boolean needToWork = key.getScheManaStatus().needCreateWorkSchedule();
			if (!value.isPresent()) {
				
				// KSU002
				List<String> sids = new ArrayList<>();
				sids.add(AppContexts.user().employeeId());
				GetDateInfoDuringThePeriodInput param1 = new GetDateInfoDuringThePeriodInput();
				param1.setGeneralDate(key.getDate());
				param1.setSids(sids);
				
				WorkScheduleWorkInforDto dto = WorkScheduleWorkInforDto.builder().employeeId(key.getEmployeeID())
						.date(key.getDate()).haveData(false).achievements(false).confirmed(false).needToWork(needToWork)
						.supportCategory(SupportCategory.NOT_CHEERING.value).workTypeCode(null).workTypeName(null)
						.workTypeEditStatus(null).workTimeCode(null).workTimeName(null).workTimeEditStatus(null)
						.startTime(null).startTimeEditState(null).endTime(null).endTimeEditState(null)
						.workHolidayCls(null)
						.dateInfoDuringThePeriod(this.getDateInfoDuringThePeriod.get(param1))
						.build();

				listWorkScheduleWorkInfor.add(dto);
			} else {
				// step 5.2.1
				WorkSchedule workSchedule = value.get();
				WorkInformation workInformation = workSchedule.getWorkInfo().getRecordInfo();

				WorkInformation.Require require2 = new RequireWorkInforImpl(workTypeRepo, workTimeSettingRepo,
						workTimeSettingService, basicScheduleService);
				Optional<WorkStyle> workStyle = Optional.empty();
				if (workInformation.getWorkTypeCode() != null) {
					workStyle = workInformation.getWorkStyle(require2); // workHolidayCls
				}

				String workTypeCode = Optional
						.ofNullable(workInformation.getWorkTypeCode())
						.map(m -> m.v())
						.orElse(null);				

				String workTypeName = lstWorkTypeInfor
						.stream()
						.filter(i -> i.getWorkTypeCode().equals(workTypeCode))
						.findFirst()
						.map(m -> m.getAbbreviationName())
						.orElse(workTypeCode == null ? null : workTypeCode + "{#KSU002_31}");
				
				String workTimeCode = workInformation
						.getWorkTimeCodeNotNull()
						.map(m -> m.v())
						.orElse(null);
				
				String workTimeName = lstWorkTimeSetting
						.stream()
						.filter(i -> i.getWorktimeCode().v().equals(workTimeCode))
						.findFirst()
						.map(m -> m.getWorkTimeDisplayName())
						.map(m -> m.getWorkTimeAbName().v())
						.orElse(workTimeCode == null ? null : workTimeCode + "{#KSU002_31}");

				Integer startTime = null;
				Integer endtTime = null;
				
				boolean confirmed = workSchedule.getConfirmedATR().value == ConfirmedATR.CONFIRMED.value; 
				
				Optional<TimeLeavingWork> tlwork =  workSchedule.getOptTimeLeaving()
				.map(m -> m.getTimeLeavingWorks())
				.map(m -> m.stream().filter(f -> f.getWorkNo().v() == 1).findFirst())
				.orElse(Optional.empty());
				

				if (workTimeCode != null) {
					
					if (tlwork.isPresent()){
						startTime = tlwork.get().getAttendanceStamp()
								.map(m -> m.getStamp())
								.orElse(null)
								.map(m -> m.getAfterRoundingTime())
								.map(m -> m.v())
								.orElse(null);
						
						endtTime = tlwork.get().getLeaveStamp()
								.map(m -> m.getStamp())
								.orElse(null)
								.map(m -> m.getAfterRoundingTime())
								.map(m -> m.v())
								.orElse(null);
					}
				}

				Optional<EditStateOfDailyAttd> workTypeEditStatus = workSchedule.getLstEditState().stream()
						.filter(i -> i.getAttendanceItemId() == 1).findFirst();
				Optional<EditStateOfDailyAttd> workTimeEditStatus = workSchedule.getLstEditState().stream()
						.filter(i -> i.getAttendanceItemId() == 2).findFirst();
				Optional<EditStateOfDailyAttd> startTimeEditStatus = workSchedule.getLstEditState().stream()
						.filter(i -> i.getAttendanceItemId() == 3).findFirst();
				Optional<EditStateOfDailyAttd> endTimeEditStatus = workSchedule.getLstEditState().stream()
						.filter(i -> i.getAttendanceItemId() == 4).findFirst();

				// KSU002
				List<String> sids = new ArrayList<>();
				sids.add(AppContexts.user().employeeId());
				GetDateInfoDuringThePeriodInput param1 = new GetDateInfoDuringThePeriodInput();
				param1.setGeneralDate(key.getDate());
				param1.setSids(sids);
				
				WorkScheduleWorkInforDto dto = WorkScheduleWorkInforDto.builder().employeeId(key.getEmployeeID())
						.date(key.getDate()).haveData(true).achievements(false)
						.confirmed(confirmed)
						.needToWork(needToWork).supportCategory(SupportCategory.NOT_CHEERING.value)
						.workTypeCode(workTypeCode).workTypeName(workTypeName)
						.workTypeEditStatus(
								workTypeEditStatus.isPresent()
										? new EditStateOfDailyAttdDto(workTypeEditStatus.get().getAttendanceItemId(),
												workTypeEditStatus.get().getEditStateSetting().value)
										: null)
						.workTimeCode(workTimeCode).workTimeName(workTimeName)
						.workTimeEditStatus(
								workTimeEditStatus.isPresent()
										? new EditStateOfDailyAttdDto(workTimeEditStatus.get().getAttendanceItemId(),
												workTimeEditStatus.get().getEditStateSetting().value)
										: null)
						.startTime(startTime)
						.startTimeEditState(
								startTimeEditStatus.isPresent()
										? new EditStateOfDailyAttdDto(startTimeEditStatus.get().getAttendanceItemId(),
												startTimeEditStatus.get().getEditStateSetting().value)
										: null)
						.endTime(endtTime)
						.endTimeEditState(
								endTimeEditStatus.isPresent()
										? new EditStateOfDailyAttdDto(endTimeEditStatus.get().getAttendanceItemId(),
												endTimeEditStatus.get().getEditStateSetting().value)
										: null)
						.workHolidayCls(workStyle.map(m -> m.value).orElse(null))
						.dateInfoDuringThePeriod(this.getDateInfoDuringThePeriod.get(param1))
						.build();

				listWorkScheduleWorkInfor.add(dto);
			}
		});

		return listWorkScheduleWorkInfor;
	}

	@AllArgsConstructor
	private static class RequireWorkInforImpl implements WorkInformation.Require {

		private final String companyId = AppContexts.user().companyId();

		@Inject
		private WorkTypeRepository workTypeRepo;

		@Inject
		private WorkTimeSettingRepository workTimeSettingRepository;

		@Inject
		private WorkTimeSettingService workTimeSettingService;

		@Inject
		private BasicScheduleService basicScheduleService;

		@Override
		public SetupType checkNeededOfWorkTimeSetting(String workTypeCode) {
			return basicScheduleService.checkNeededOfWorkTimeSetting(workTypeCode);
		}

		@Override
		public Optional<WorkType> findByPK(String workTypeCd) {
			return workTypeRepo.findByPK(companyId, workTypeCd);
		}

		@Override
		public Optional<WorkTimeSetting> findByCode(String workTimeCode) {
			return workTimeSettingRepository.findByCode(companyId, workTimeCode);
		}

		@Override
		public PredetermineTimeSetForCalc getPredeterminedTimezone(String workTimeCd, String workTypeCd,
				Integer workNo) {
			return workTimeSettingService.getPredeterminedTimezone(companyId, workTimeCd, workTypeCd, workNo);
		}

		@Override
		public WorkStyle checkWorkDay(String workTypeCode) {
			return basicScheduleService.checkWorkDay(workTypeCode);
		}
	}

	@AllArgsConstructor
	private static class RequireImpl implements WorkScheManaStatusService.Require {

		private NestedMapCache<String, GeneralDate, WorkSchedule> workScheduleCache;
		private KeyDateHistoryCache<String, EmpEnrollPeriodImport> affCompanyHistByEmployeeCache;
		private KeyDateHistoryCache<String, EmploymentPeriodImported> employmentPeriodCache;
		private KeyDateHistoryCache<String, EmployeeLeaveJobPeriodImport> empLeaveJobPeriodCache;
		private KeyDateHistoryCache<String, EmpLeaveWorkPeriodImport> empLeaveWorkPeriodCache;
		private KeyDateHistoryCache<String, WorkingConditionItemWithPeriod> workCondItemWithPeriodCache;

		public RequireImpl(List<String> empIdList, DatePeriod period, WorkScheduleRepository workScheduleRepo,
				EmpComHisAdapter empComHisAdapter, WorkingConditionRepository workCondRepo,
				EmpLeaveHistoryAdapter empLeaveHisAdapter, EmpLeaveWorkHistoryAdapter empLeaveWorkHisAdapter,
				EmploymentHisScheduleAdapter employmentHisScheduleAdapter) {

			List<WorkSchedule> lstWorkSchedule = workScheduleRepo.getList(empIdList, period);
			workScheduleCache = NestedMapCache.preloadedAll(lstWorkSchedule.stream(),
					workSchedule -> workSchedule.getEmployeeID(), workSchedule -> workSchedule.getYmd());

			List<EmpEnrollPeriodImport> affCompanyHists = empComHisAdapter.getEnrollmentPeriod(empIdList, period);
			affCompanyHistByEmployeeCache = KeyDateHistoryCache.loaded(affCompanyHists.stream().collect(Collectors
					.toMap(h -> h.getEmpID(), h -> Arrays.asList(DateHistoryCache.Entry.of(h.getDatePeriod(), h)))));

			List<EmploymentPeriodImported> listEmploymentPeriodImported = employmentHisScheduleAdapter
					.getEmploymentPeriod(empIdList, period);
			List<EmploymentPeriodImported> listEmploymentPeriodImported1 = listEmploymentPeriodImported.stream()
                    .filter( distinctByKey(p -> p.getEmpID()) )
                    .collect( Collectors.toList() );
			employmentPeriodCache = KeyDateHistoryCache.loaded(listEmploymentPeriodImported1.stream().collect(Collectors
					.toMap(h -> h.getEmpID(), h -> Arrays.asList(DateHistoryCache.Entry.of(h.getDatePeriod(), h)))));

			List<EmployeeLeaveJobPeriodImport> empLeaveJobPeriods = empLeaveHisAdapter
					.getLeaveBySpecifyingPeriod(empIdList, period);
			empLeaveJobPeriodCache = KeyDateHistoryCache.loaded(empLeaveJobPeriods.stream().collect(Collectors
					.toMap(h -> h.getEmpID(), h -> Arrays.asList(DateHistoryCache.Entry.of(h.getDatePeriod(), h)))));

			List<EmpLeaveWorkPeriodImport> empLeaveWorkPeriods = empLeaveWorkHisAdapter.getHolidayPeriod(empIdList,
					period);
			empLeaveWorkPeriodCache = KeyDateHistoryCache.loaded(empLeaveWorkPeriods.stream().collect(Collectors
					.toMap(h -> h.getEmpID(), h -> Arrays.asList(DateHistoryCache.Entry.of(h.getDatePeriod(), h)))));

			List<WorkingConditionItemWithPeriod> listData = workCondRepo
					.getWorkingConditionItemWithPeriod(AppContexts.user().companyId(), empIdList, period);
			workCondItemWithPeriodCache = KeyDateHistoryCache
					.loaded(listData.stream().collect(Collectors.toMap(h -> h.getWorkingConditionItem().getEmployeeId(),
							h -> Arrays.asList(DateHistoryCache.Entry.of(h.getDatePeriod(), h)))));
		}
		
		public static <T> Predicate<T> distinctByKey(Function<? super T, Object> keyExtractor) 
	    {
	        Map<Object, Boolean> map = new ConcurrentHashMap<>();
	        return t -> map.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
	    }

		@Override
		public Optional<WorkSchedule> get(String employeeId, GeneralDate date) {
			Optional<WorkSchedule> result = workScheduleCache.get(employeeId, date);
			return result;
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
			Optional<EmployeeLeaveJobPeriodImport> data = empLeaveJobPeriodCache.get(sid, startDate);
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
