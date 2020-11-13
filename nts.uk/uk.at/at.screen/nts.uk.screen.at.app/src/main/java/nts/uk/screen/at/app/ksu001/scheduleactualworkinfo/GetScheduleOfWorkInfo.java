/**
 *
 */
package nts.uk.screen.at.app.ksu001.scheduleactualworkinfo;

import java.util.ArrayList;
import java.util.Arrays;
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
import nts.uk.screen.at.app.ksu001.displayinworkinformation.DisplayInWorkInfoParam;
import nts.uk.screen.at.app.ksu001.displayinworkinformation.EditStateOfDailyAttdDto;
import nts.uk.screen.at.app.ksu001.processcommon.WorkScheduleWorkInforDto;
import nts.uk.screen.at.app.ksu001.start.SupportCategory;
import nts.uk.shr.com.context.AppContexts;

/**
 * @author laitv
 * ScreenQuery : 勤務予定（勤務情報）を取得する
 */
@Stateless
public class GetScheduleOfWorkInfo {

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

	public List<WorkScheduleWorkInforDto> getDataScheduleOfWorkInfo(DisplayInWorkInfoParam param) {

		String companyId = AppContexts.user().companyId();
		// step 1 start
		// call 予定管理状態に応じて勤務予定を取得する
		DatePeriod period = new DatePeriod(param.startDate, param.endDate);
		RequireImpl RequireImpl = new RequireImpl(param.listSid, period, workScheduleRepo, empComHisAdapter, workCondRepo, empLeaveHisAdapter,
				empLeaveWorkHisAdapter, employmentHisScheduleAdapter);

		// 管理状態と勤務予定Map
		long start = System.nanoTime();

		Map<ScheManaStatuTempo, Optional<WorkSchedule>> mngStatusAndWScheMap =  WorkScheManaStatusService.getScheduleManagement(RequireImpl, param.listSid, period);

		System.out.println("thoi gian get data Schedule cua "+ param.listSid.size() + " employee: " + ((System.nanoTime() - start )/1000000) + "ms");

		List<WorkInfoOfDailyAttendance>  listWorkInfo = new ArrayList<WorkInfoOfDailyAttendance>();
		mngStatusAndWScheMap.forEach((k,v)->{
			if (v.isPresent()) {
				WorkInfoOfDailyAttendance workInfo = v.get().getWorkInfo();
				listWorkInfo.add(workInfo);
			}
		});
		// step 1 end

		// step 2
		// call 日別勤怠の実績で利用する勤務種類と就業時間帯のリストを取得する
		WorkTypeWorkTimeUseDailyAttendanceRecord wTypeWTimeUseDailyAttendRecord = GetListWtypeWtimeUseDailyAttendRecordService.getdata(listWorkInfo);

		// step 3
		List<WorkTypeCode> workTypeCodes = wTypeWTimeUseDailyAttendRecord.getLstWorkTypeCode().stream().filter(wt -> wt != null).collect(Collectors.toList());
		List<String> lstWorkTypeCode     = workTypeCodes.stream().map(i -> i.toString()).collect(Collectors.toList());
		//<<Public>> 指定した勤務種類をすべて取得する
		List<WorkTypeInfor> lstWorkTypeInfor = this.workTypeRepo.getPossibleWorkTypeAndOrder(companyId, lstWorkTypeCode).stream().filter(wk -> wk.getAbolishAtr() == 0).collect(Collectors.toList());

		// step 4
		List<WorkTimeCode> workTimeCodes   = wTypeWTimeUseDailyAttendRecord.getLstWorkTimeCode().stream().filter(wt -> wt != null).collect(Collectors.toList());
		List<String> lstWorkTimeCode       = workTimeCodes.stream().map(i -> i.toString()).collect(Collectors.toList());
		List<WorkTimeSetting> lstWorkTimeSetting =  workTimeSettingRepo.getListWorkTimeSetByListCode(companyId, lstWorkTimeCode);

		// step 5
		List<WorkScheduleWorkInforDto> listWorkScheduleWorkInfor = new ArrayList<>();
		mngStatusAndWScheMap.forEach((k, v) -> {
			ScheManaStatuTempo key = k;
			Optional<WorkSchedule> value = v;

			// step 5.1
			boolean needToWork = key.getScheManaStatus().needCreateWorkSchedule();
			if (!value.isPresent()) {
				// step 5.2
				WorkScheduleWorkInforDto dto = WorkScheduleWorkInforDto.builder()
						.employeeId(key.getEmployeeID())
						.date(key.getDate())
						.haveData(false)
						.achievements(false)
						.confirmed(false)
						.needToWork(needToWork)
						.supportCategory(SupportCategory.NotCheering.value)
						.workTypeCode(null)
						.workTypeName(null)
						.workTypeEditStatus(null)
						.workTimeCode(null)
						.workTimeName(null)
						.workTimeEditStatus(null)
						.startTime(null)
						.startTimeEditState(null)
						.endTime(null)
						.endTimeEditState(null)
						.workHolidayCls(null)
						.isEdit(true)   //
						.isActive(true) //
						.build();

				// ※Abc1
				boolean isEdit = true;
				if(dto.needToWork == false){
					isEdit = false;
				}

				// ※Abc2
				boolean isActive = true;
				if(dto.needToWork == false){
					isActive = false;
				}
				dto.setEdit(isEdit);
				dto.setActive(isActive);
				listWorkScheduleWorkInfor.add(dto);
			} else {
				// step 5.2.1
				WorkSchedule workSchedule = value.get();
				WorkInformation workInformation = workSchedule.getWorkInfo().getRecordInfo();

				WorkInformation.Require require2 = new RequireWorkInforImpl(workTypeRepo,workTimeSettingRepo,workTimeSettingService, basicScheduleService);
				Optional<WorkStyle> workStyle = Optional.empty();
				if (workInformation.getWorkTypeCode() != null) {
					workStyle = workInformation.getWorkStyle(require2); // workHolidayCls
				}

				String workTypeCode = workInformation.getWorkTypeCode() == null  ? null : workInformation.getWorkTypeCode().toString();
				String workTypeName = null;
				boolean workTypeIsNotExit  = false;

				Optional<WorkTypeInfor> workTypeInfor = lstWorkTypeInfor.stream().filter(i -> i.getWorkTypeCode().equals(workTypeCode)).findFirst();
				if (workTypeInfor.isPresent()) {
					workTypeName = workTypeInfor.get().getAbbreviationName();
				} else if (!workTypeInfor.isPresent() && workTypeCode != null){
					workTypeIsNotExit = true;
				}

				String workTimeCode = workInformation.getWorkTimeCode() == null  ? null : workInformation.getWorkTimeCode().toString();
				Optional<WorkTimeSetting> workTimeSetting = lstWorkTimeSetting.stream().filter(i -> i.getWorktimeCode().toString().equals(workTimeCode)).findFirst();
				String workTimeName = null;

				boolean workTimeIsNotExit  = false;
				if (workTimeSetting.isPresent()) {
					if (workTimeSetting.get().getWorkTimeDisplayName() != null && workTimeSetting.get().getWorkTimeDisplayName().getWorkTimeAbName() != null ) {
						workTimeName = workTimeSetting.get().getWorkTimeDisplayName().getWorkTimeAbName().toString();
					}
				} else  if (!workTimeSetting.isPresent() && workTimeCode != null){
					workTimeIsNotExit = true;
				}

				Integer startTime = null;
				Integer endtTime = null;

				if (workTimeCode != null) {
					if (workSchedule.getOptTimeLeaving().isPresent()) {
						Optional<TimeLeavingWork> timeLeavingWork = workSchedule.getOptTimeLeaving().get().getTimeLeavingWorks().stream().filter(i -> i.getWorkNo().v() == 1).findFirst();
						if (timeLeavingWork.isPresent()) {
							if(timeLeavingWork.get().getAttendanceStamp().isPresent()){
								if(timeLeavingWork.get().getAttendanceStamp().get().getStamp().isPresent()){
									if(timeLeavingWork.get().getAttendanceStamp().get().getStamp().get().getTimeDay() != null){
										if(timeLeavingWork.get().getAttendanceStamp().get().getStamp().get().getTimeDay().getTimeWithDay().isPresent()){
											startTime = timeLeavingWork.get().getAttendanceStamp().get().getStamp().get().getTimeDay().getTimeWithDay().get().v();
										}
									}
								}
							}
						}
					}

					if (workSchedule.getOptTimeLeaving().isPresent()) {
						Optional<TimeLeavingWork> timeLeavingWork = workSchedule.getOptTimeLeaving().get().getTimeLeavingWorks().stream().filter(i -> i.getWorkNo().v() == 1).findFirst();
						if (timeLeavingWork.isPresent()) {
							if(timeLeavingWork.get().getLeaveStamp().isPresent()){
								if(timeLeavingWork.get().getLeaveStamp().get().getStamp().isPresent()){
									if(timeLeavingWork.get().getLeaveStamp().get().getStamp().get().getTimeDay() != null){
										if(timeLeavingWork.get().getLeaveStamp().get().getStamp().get().getTimeDay().getTimeWithDay().isPresent()){
											endtTime = timeLeavingWork.get().getLeaveStamp().get().getStamp().get().getTimeDay().getTimeWithDay().get().v();
										}
									}
								}
							}
						}
					}
				}

				Optional<EditStateOfDailyAttd> workTypeEditStatus = workSchedule.getLstEditState().stream().filter(i -> i.getAttendanceItemId() == 28).findFirst();
				Optional<EditStateOfDailyAttd> workTimeEditStatus = workSchedule.getLstEditState().stream().filter(i -> i.getAttendanceItemId() == 29).findFirst();
				Optional<EditStateOfDailyAttd> startTimeEditStatus = workSchedule.getLstEditState().stream().filter(i -> i.getAttendanceItemId() == 31).findFirst();
				Optional<EditStateOfDailyAttd> endTimeEditStatus = workSchedule.getLstEditState().stream().filter(i -> i.getAttendanceItemId() == 34).findFirst();

				WorkScheduleWorkInforDto dto = WorkScheduleWorkInforDto.builder()
						.employeeId(key.getEmployeeID())
						.date(key.getDate())
						.haveData(true)
						.achievements(false)
						.confirmed(workSchedule.getConfirmedATR().value == ConfirmedATR.CONFIRMED.value)
						.needToWork(needToWork)
						.supportCategory(SupportCategory.NotCheering.value)
						.workTypeCode(workTypeCode)
						.workTypeName(workTypeName)
						.workTypeEditStatus(workTypeEditStatus.isPresent() ? new EditStateOfDailyAttdDto(workTypeEditStatus.get().getAttendanceItemId(), workTypeEditStatus.get().getEditStateSetting().value) : null)
						.workTimeCode(workTimeCode)
						.workTimeName(workTimeName)
						.workTimeEditStatus(workTimeEditStatus.isPresent() ? new EditStateOfDailyAttdDto(workTimeEditStatus.get().getAttendanceItemId(), workTimeEditStatus.get().getEditStateSetting().value) : null)
						.startTime(startTime)
						.startTimeEditState(startTimeEditStatus.isPresent() ? new EditStateOfDailyAttdDto(startTimeEditStatus.get().getAttendanceItemId(), startTimeEditStatus.get().getEditStateSetting().value) : null)
						.endTime(endtTime)
						.endTimeEditState(endTimeEditStatus.isPresent() ? new EditStateOfDailyAttdDto(endTimeEditStatus.get().getAttendanceItemId(), endTimeEditStatus.get().getEditStateSetting().value) : null)
						.workHolidayCls(workStyle.isPresent() ? workStyle.get().value : null)
						.isEdit(true)
						.isActive(true)
						.workTypeIsNotExit(workTypeIsNotExit)
						.workTimeIsNotExit(workTimeIsNotExit)
						.build();

				// ※Abc1
				boolean isEdit = true;
				if(dto.confirmed == true || dto.needToWork == false){
					isEdit = false;
				}

				// ※Abc2
				boolean isActive = true;
				if(dto.needToWork == false){
					isActive = false;
				}

				dto.setEdit(isEdit);
				dto.setActive(isActive);

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
		public Optional<WorkType> getWorkType(String workTypeCd) {
			return workTypeRepo.findByPK(companyId, workTypeCd);
		}

		@Override
		public Optional<WorkTimeSetting> getWorkTime(String workTimeCode) {
			return workTimeSettingRepository.findByCode(companyId, workTimeCode);
		}

		@Override
		public PredetermineTimeSetForCalc getPredeterminedTimezone(String workTypeCd, String workTimeCd, Integer workNo) {
			return workTimeSettingService .getPredeterminedTimezone(companyId, workTimeCd, workTypeCd, workNo);
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

			long start1 = System.nanoTime();
			List<WorkSchedule> lstWorkSchedule = workScheduleRepo.getList(empIdList, period);
			workScheduleCache = NestedMapCache.preloadedAll(lstWorkSchedule.stream(),
					workSchedule -> workSchedule.getEmployeeID(), workSchedule -> workSchedule.getYmd());
			System.out.println("thoi gian get data WorkSchedule " + ((System.nanoTime() - start1 )/1000000) + "ms");

			long start2 = System.nanoTime();
			List<EmpEnrollPeriodImport> affCompanyHists =  empComHisAdapter.getEnrollmentPeriod(empIdList, period);
			affCompanyHistByEmployeeCache = KeyDateHistoryCache.loaded(affCompanyHists.stream()
					.collect(Collectors.toMap( h -> h.getEmpID(), h -> Arrays.asList(DateHistoryCache.Entry.of(h.getDatePeriod(), h)))));
			System.out.println("thoi gian get data affCompanyHistByEmp " + ((System.nanoTime() - start2 )/1000000) + "ms");

			long start3 = System.nanoTime();
			List<EmploymentPeriodImported> listEmploymentPeriodImported = employmentHisScheduleAdapter.getEmploymentPeriod(empIdList, period);
			employmentPeriodCache = KeyDateHistoryCache.loaded(listEmploymentPeriodImported.stream()
					.collect(Collectors.toMap( h -> h.getEmpID(), h -> Arrays.asList(DateHistoryCache.Entry.of(h.getDatePeriod(), h)))));
			System.out.println("thoi gian get data EmploymentPeriod " + ((System.nanoTime() - start3 )/1000000) + "ms");

			long start4 = System.nanoTime();
			List<EmployeeLeaveJobPeriodImport> empLeaveJobPeriods = empLeaveHisAdapter.getLeaveBySpecifyingPeriod(empIdList, period);
			empLeaveJobPeriodCache = KeyDateHistoryCache.loaded(empLeaveJobPeriods.stream()
					.collect(Collectors.toMap( h -> h.getEmpID(), h -> Arrays.asList(DateHistoryCache.Entry.of(h.getDatePeriod(), h)))));
			System.out.println("thoi gian get data EmployeeLeaveJob " + ((System.nanoTime() - start4 )/1000000) + "ms");

			long start5 = System.nanoTime();
			List<EmpLeaveWorkPeriodImport> empLeaveWorkPeriods =  empLeaveWorkHisAdapter.getHolidayPeriod(empIdList, period);
			empLeaveWorkPeriodCache = KeyDateHistoryCache.loaded(empLeaveWorkPeriods.stream()
					.collect(Collectors.toMap( h -> h.getEmpID(), h -> Arrays.asList(DateHistoryCache.Entry.of(h.getDatePeriod(), h)))));
			System.out.println("thoi gian get data EmpLeaveWork " + ((System.nanoTime() - start5 )/1000000) + "ms");

			long start6 = System.nanoTime();
			List<WorkingConditionItemWithPeriod> listData = workCondRepo.getWorkingConditionItemWithPeriod(AppContexts.user().companyId(),empIdList, period);
			workCondItemWithPeriodCache = KeyDateHistoryCache.loaded(listData.stream()
					.collect(Collectors.toMap( h -> h.getWorkingConditionItem().getEmployeeId(), h -> Arrays.asList(DateHistoryCache.Entry.of(h.getDatePeriod(), h)))));
			System.out.println("thoi gian get data WorkingConditionItem " + ((System.nanoTime() - start6 )/1000000) + "ms");

			System.out.println("thoi gian get data để lưu vào Cache " + ((System.nanoTime() - start1 )/1000000) + "ms");
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
