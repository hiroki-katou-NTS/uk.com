/**
 * 
 */
package nts.uk.screen.at.app.ksu001.scheduleactualworkinfo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.AllArgsConstructor;
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
import nts.uk.ctx.at.shared.dom.dailyattdcal.dailyattendance.attendancetime.TimeLeavingWork;
import nts.uk.ctx.at.shared.dom.dailyattdcal.dailyattendance.editstate.EditStateOfDailyAttd;
import nts.uk.ctx.at.shared.dom.dailyattdcal.dailyattendance.workinfomation.GetListWtypeWtimeUseDailyAttendRecordService;
import nts.uk.ctx.at.shared.dom.dailyattdcal.dailyattendance.workinfomation.WorkInfoOfDailyAttendance;
import nts.uk.ctx.at.shared.dom.dailyattdcal.dailyattendance.workinfomation.WorkTypeWorkTimeUseDailyAttendanceRecord;
import nts.uk.ctx.at.shared.dom.schedule.basicschedule.BasicScheduleService;
import nts.uk.ctx.at.shared.dom.schedule.basicschedule.SetupType;
import nts.uk.ctx.at.shared.dom.schedule.basicschedule.WorkStyle;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItem;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionRepository;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.employeeinfor.employmenthistory.imported.EmpComHisAdapter;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.employeeinfor.employmenthistory.imported.EmpEnrollPeriodImport;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.employeeinfor.employmenthistory.imported.EmploymentHisScheduleAdapter;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.employeeinfor.employmenthistory.imported.EmploymentPeriodImported;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSetting;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSettingRepository;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSettingService;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.internal.PredetermineTimeSetForCalc;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeInfor;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeRepository;
import nts.uk.screen.at.app.ksu001.displayinworkinformation.DisplayInWorkInfoParam;
import nts.uk.screen.at.app.ksu001.displayinworkinformation.EditStateOfDailyAttdDto;
import nts.uk.screen.at.app.ksu001.displayinworkinformation.WorkScheduleWorkInforDto;
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
		RequireImpl RequireImpl = new RequireImpl(workScheduleRepo, empComHisAdapter, workCondRepo, empLeaveHisAdapter,
				empLeaveWorkHisAdapter, employmentHisScheduleAdapter);
		DatePeriod period = new DatePeriod(param.startDate, param.endDate);
		
		// 管理状態と勤務予定Map
		long start = System.nanoTime();
		
		Map<ScheManaStatuTempo, Optional<WorkSchedule>> mngStatusAndWScheMap =  WorkScheManaStatusService.getScheduleManagement(RequireImpl, param.listSid, period);
		
		long end = System.nanoTime();
		long duration = (end - start) / 1000000; // ms;
		System.out.println("thoi gian get data Schedule cua "+ param.listSid.size() + " employee: " + duration + "ms");	
		
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
		List<String> lstWorkTypeCode = wTypeWTimeUseDailyAttendRecord.getLstWorkTypeCode().stream().map(i -> i.toString()).collect(Collectors.toList());
		//<<Public>> 指定した勤務種類をすべて取得する
		List<WorkTypeInfor> lstWorkTypeInfor = this.workTypeRepo.getPossibleWorkTypeAndOrder(companyId, lstWorkTypeCode);
		
		// step 4
		List<String> lstWorkTimeCode = wTypeWTimeUseDailyAttendRecord.getLstWorkTimeCode().stream().map(i -> i.toString()).collect(Collectors.toList());
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
				Optional<WorkStyle> workStyle = workInformation.getWorkStyle(require2); // workHolidayCls 
				
				String workTypeCode = workInformation.getWorkTypeCode() == null  ? null : workInformation.getWorkTypeCode().toString();
				String workTypeName = null;
				Optional<WorkTypeInfor> workTypeInfor = lstWorkTypeInfor.stream().filter(i -> i.getWorkTypeCode().equals(workTypeCode)).findFirst();
				if (workTypeInfor.isPresent()) {
					workTypeName = workTypeInfor.get().getName();
				}
				String workTimeCode = workInformation.getWorkTimeCode() == null  ? null : workInformation.getWorkTimeCode().toString();
				Optional<WorkTimeSetting> workTimeSetting = lstWorkTimeSetting.stream().filter(i -> i.getWorktimeCode().toString().equals(workTimeCode)).findFirst(); 
				String workTimeName = null;
				if (workTimeSetting.isPresent()) {
					if (workTimeSetting.get().getWorkTimeDisplayName() != null && workTimeSetting.get().getWorkTimeDisplayName().getWorkTimeAbName() != null ) {
						workTimeName = workTimeSetting.get().getWorkTimeDisplayName().getWorkTimeAbName().toString();
					}
				}
				
				Integer startTime = 0;
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
				
				Integer endtTime = 0;
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
				
				Optional<EditStateOfDailyAttd> workTypeEditStatus = workSchedule.getLstEditState().stream().filter(i -> i.getAttendanceItemId() == 1).findFirst();
				Optional<EditStateOfDailyAttd> workTimeEditStatus = workSchedule.getLstEditState().stream().filter(i -> i.getAttendanceItemId() == 2).findFirst();
				Optional<EditStateOfDailyAttd> startTimeEditStatus = workSchedule.getLstEditState().stream().filter(i -> i.getAttendanceItemId() == 3).findFirst();
				Optional<EditStateOfDailyAttd> endTimeEditStatus = workSchedule.getLstEditState().stream().filter(i -> i.getAttendanceItemId() == 4).findFirst();
				
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
						.isEdit(true)   //
						.isActive(true) // 
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
		public Optional<WorkType> findByPK(String workTypeCd) {
			return workTypeRepo.findByPK(companyId, workTypeCd);
		}

		@Override
		public Optional<WorkTimeSetting> findByCode(String workTimeCode) {
			return workTimeSettingRepository.findByCode(companyId, workTimeCode);
		}

		@Override
		public PredetermineTimeSetForCalc getPredeterminedTimezone(String workTimeCd,
				String workTypeCd, Integer workNo) {
			return workTimeSettingService .getPredeterminedTimezone(companyId, workTimeCd, workTypeCd, workNo);
		}

		@Override
		public WorkStyle checkWorkDay(String workTypeCode) {
			return basicScheduleService.checkWorkDay(workTypeCode);
		}
	}
	
	
	@AllArgsConstructor
	private static class RequireImpl implements WorkScheManaStatusService.Require {
		
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
		
		@Override
		public Optional<WorkSchedule> get(String employeeID, GeneralDate ymd) {
			Optional<WorkSchedule> data = workScheduleRepo.get(employeeID, ymd);
			return data;
		}
		
		@Override
		public Optional<EmpEnrollPeriodImport> getAffCompanyHistByEmployee(List<String> sids, DatePeriod datePeriod) {
			List<EmpEnrollPeriodImport> data =  empComHisAdapter.getEnrollmentPeriod(sids, datePeriod);
			if (data.isEmpty()) {
				return Optional.empty();
			}
			return Optional.of(data.get(0));
		}

		@Override
		public Optional<WorkingConditionItem> getBySidAndStandardDate(String employeeId, GeneralDate baseDate) {
			Optional<WorkingConditionItem> data = workCondRepo.getWorkingConditionItemByEmpIDAndDate(AppContexts.user().companyId(), baseDate, employeeId);
			return data;
		}

		@Override
		public Optional<EmployeeLeaveJobPeriodImport> getByDatePeriod(List<String> lstEmpID, DatePeriod datePeriod) {
			List<EmployeeLeaveJobPeriodImport> data = empLeaveHisAdapter.getLeaveBySpecifyingPeriod(lstEmpID, datePeriod);
			if (data.isEmpty()) {
				return Optional.empty();
			}
			return Optional.of(data.get(0));
		}

		@Override
		public Optional<EmpLeaveWorkPeriodImport> specAndGetHolidayPeriod(List<String> lstEmpID, DatePeriod datePeriod) {
			List<EmpLeaveWorkPeriodImport> data =  empLeaveWorkHisAdapter.getHolidayPeriod(lstEmpID, datePeriod);
			if (data.isEmpty()) {
				return Optional.empty();
			}
			return Optional.of(data.get(0));
		}

		@Override
		public Optional<EmploymentPeriodImported> getEmploymentHistory(List<String> lstEmpID, DatePeriod datePeriod) {
			List<EmploymentPeriodImported> data = employmentHisScheduleAdapter.getEmploymentPeriod(lstEmpID, datePeriod);
			if (data.isEmpty()) {
				return Optional.empty();
			}
			return Optional.of(data.get(0));
		}
	}
	

}
