/**
 * 
 */
package nts.uk.screen.at.app.ksu001.getworkscheduleshift;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.AllArgsConstructor;
import nts.arc.layer.app.cache.NestedMapCache;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.schedule.dom.schedule.workschedule.ConfirmedATR;
import nts.uk.ctx.at.schedule.dom.schedule.workschedule.ScheManaStatuTempo;
import nts.uk.ctx.at.schedule.dom.schedule.workschedule.WorkSchedule;
import nts.uk.ctx.at.schedule.dom.schedule.workschedule.WorkScheduleRepository;
import nts.uk.ctx.at.schedule.dom.workschedule.domainservice.DeterEditStatusShiftService;
import nts.uk.ctx.at.schedule.dom.workschedule.domainservice.ShiftEditState;
import nts.uk.ctx.at.schedule.dom.workschedule.domainservice.WorkScheManaStatusService;
import nts.uk.ctx.at.shared.dom.WorkInformation;
import nts.uk.ctx.at.shared.dom.adapter.employment.employwork.leaveinfo.EmpLeaveHistoryAdapter;
import nts.uk.ctx.at.shared.dom.adapter.employment.employwork.leaveinfo.EmpLeaveWorkHistoryAdapter;
import nts.uk.ctx.at.shared.dom.adapter.employment.employwork.leaveinfo.EmpLeaveWorkPeriodImport;
import nts.uk.ctx.at.shared.dom.adapter.employment.employwork.leaveinfo.EmployeeLeaveJobPeriodImport;
import nts.uk.ctx.at.shared.dom.dailyattdcal.dailyattendance.workinfomation.GetWorkInforUsedDailyAttenRecordService;
import nts.uk.ctx.at.shared.dom.dailyattdcal.dailyattendance.workinfomation.WorkInfoOfDailyAttendance;
import nts.uk.ctx.at.shared.dom.schedule.basicschedule.BasicScheduleService;
import nts.uk.ctx.at.shared.dom.schedule.basicschedule.SetupType;
import nts.uk.ctx.at.shared.dom.schedule.basicschedule.WorkStyle;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItem;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionRepository;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.employeeinfor.employmenthistory.imported.EmpComHisAdapter;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.employeeinfor.employmenthistory.imported.EmpEnrollPeriodImport;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.employeeinfor.employmenthistory.imported.EmploymentHisScheduleAdapter;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.employeeinfor.employmenthistory.imported.EmploymentPeriodImported;
import nts.uk.ctx.at.shared.dom.workrule.shiftmaster.GetCombinationrAndWorkHolidayAtrService;
import nts.uk.ctx.at.shared.dom.workrule.shiftmaster.ShiftMaster;
import nts.uk.ctx.at.shared.dom.workrule.shiftmaster.ShiftMasterRepository;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSetting;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSettingRepository;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSettingService;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.internal.PredetermineTimeSetForCalc;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeRepository;
import nts.uk.screen.at.app.ksu001.displayinshift.ShiftMasterMapWithWorkStyle;
import nts.uk.screen.at.app.ksu001.start.SupportCategory;
import nts.uk.shr.com.context.AppContexts;

/**
 * @author laitv
 * ScreenQuery 勤務予定（シフト）を取得する
 *
 */
@Stateless
public class GetScheduleOfShift {
	
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
	private BasicScheduleService basicScheduleService;
	@Inject
	private WorkTypeRepository workTypeRepo;
	@Inject
	private WorkTimeSettingRepository workTimeSettingRepository;
	@Inject
	private WorkTimeSettingService workTimeSettingService;
	@Inject
	private ShiftMasterRepository shiftMasterRepo;
	
	
	public ScheduleOfShiftResult getWorkScheduleShift(ScheduleOfShiftParam param) {

		// step 1 start
		// call 予定管理状態に応じて勤務予定を取得する
		long start = System.nanoTime();
		DatePeriod period = new DatePeriod(param.startDate, param.endDate);
		RequireWorkScheManaStatusImpl requireImpl1 = new RequireWorkScheManaStatusImpl(param.listSid, period, workScheduleRepo, empComHisAdapter, workCondRepo, empLeaveHisAdapter,
				empLeaveWorkHisAdapter, employmentHisScheduleAdapter);
		
		// 管理状態と勤務予定Map
		Map<ScheManaStatuTempo, Optional<WorkSchedule>> mngStatusAndWScheMap =  WorkScheManaStatusService.getScheduleManagement(requireImpl1, param.listSid, period);
		long end = System.nanoTime();
		long duration = (end - start) / 1000000; // ms;
		System.out.println("thoi gian get data Schedule cua "+ param.listSid.size() + " employee: " + duration + "ms");	
		
		List<WorkInfoOfDailyAttendance>  workInfoOfDailyAttendances = new ArrayList<WorkInfoOfDailyAttendance>();
		mngStatusAndWScheMap.forEach((k,v)->{
			if (v.isPresent()) {
				WorkInfoOfDailyAttendance workInfo = v.get().getWorkInfo();
				workInfoOfDailyAttendances.add(workInfo);
			}
		});	
		// step 1 end
		
		// step 2 
		// call 日別勤怠の実績で利用する勤務情報のリストを取得する
		List<WorkInformation> lstWorkInfo = GetWorkInforUsedDailyAttenRecordService.getListWorkInfo(workInfoOfDailyAttendances);
		
		// step 3
		// call シフトマスタと出勤休日区分の組み合わせを取得する
		GetCombinationrAndWorkHolidayAtrService.Require requireImpl2 = new RequireCombiAndWorkHolidayImpl(shiftMasterRepo, basicScheduleService, workTypeRepo,workTimeSettingRepository,workTimeSettingService, basicScheduleService);
		Map<ShiftMaster,Optional<WorkStyle>> mapShiftMasterWithWorkStyle = GetCombinationrAndWorkHolidayAtrService.getbyWorkInfo(requireImpl2,AppContexts.user().companyId(), lstWorkInfo);
		
		List<ShiftMasterMapWithWorkStyle> listShiftMaster = param.listShiftMasterNotNeedGetNew;
		List<String> listShiftMasterCodeNotNeedGetNew = param.listShiftMasterNotNeedGetNew.stream().map(mapper -> mapper.getShiftMasterCode()).collect(Collectors.toList()); // ko cần get mới

		for (Map.Entry<ShiftMaster, Optional<WorkStyle>> entry : mapShiftMasterWithWorkStyle.entrySet()) {
			String shiftMasterCd = entry.getKey().getShiftMasterCode().toString();
			if(listShiftMasterCodeNotNeedGetNew.contains(shiftMasterCd)){
				// remove di, roi add lai
				ShiftMasterMapWithWorkStyle obj = listShiftMaster.stream().filter(shiftLocal -> shiftLocal.shiftMasterCode.equals(shiftMasterCd)).findFirst().get();
				listShiftMaster.remove(obj);
			}
			ShiftMasterMapWithWorkStyle shift = new ShiftMasterMapWithWorkStyle(entry.getKey(), entry.getValue().isPresent() ? entry.getValue().get().value + "" : null);
			listShiftMaster.add(shift);
		}
		
		
		// step 4
		// call loop：entry(Map.Entry) in 管理状態と勤務予定Map
		List<ScheduleOfShiftDto> listWorkScheduleShift = new ArrayList<>();
		mngStatusAndWScheMap.forEach((k, v) -> {
			ScheManaStatuTempo key = k;
			Optional<WorkSchedule> value = v;

			// step 4.1
			boolean needToWork = key.getScheManaStatus().needCreateWorkSchedule();
			if (value.isPresent()) {
				WorkSchedule workSchedule = value.get();
				WorkInformation workInformation = workSchedule.getWorkInfo().getRecordInfo();
				// 4.2.1
				WorkInformation.Require require2 = new RequireWorkInforImpl(workTypeRepo,workTimeSettingRepository,workTimeSettingService, basicScheduleService);
				Optional<WorkStyle> workStyle = workInformation.getWorkStyle(require2);
				// 4.2.2
				ShiftEditState shiftEditState = DeterEditStatusShiftService.toDecide(workSchedule);
				ShiftEditStateDto shiftEditStateDto = ShiftEditStateDto.toDto(shiftEditState);
				
				 String workTypeCode = workInformation.getWorkTypeCode().v();
				 String workTimeCode = workInformation.getWorkTimeCode() == null ? null :  workInformation.getWorkTimeCode().toString();
				 
				 Optional<ShiftMasterMapWithWorkStyle> shiftMaster = listShiftMaster.stream().filter(shiftLocal -> {
					if(shiftLocal.workTypeCode.equals(workTypeCode) && shiftLocal.workTimeCode.equals(workTimeCode))
						return true;
					return false;
				 }).findFirst();
				 
				// 4.2.3
				ScheduleOfShiftDto dto = ScheduleOfShiftDto.builder()
						.employeeId(key.getEmployeeID())
						.date(key.getDate())
						.haveData(true)
						.achievements(false)
						.confirmed(workSchedule.getConfirmedATR().value == ConfirmedATR.CONFIRMED.value)
						.needToWork(needToWork)
						.supportCategory(SupportCategory.NotCheering.value)
						.shiftCode(shiftMaster.isPresent() ? shiftMaster.get().shiftMasterCode : null)
						.shiftName(shiftMaster.isPresent() ? shiftMaster.get().shiftMasterName : null)
						.shiftEditState(shiftEditStateDto)
						.workHolidayCls(workStyle.isPresent() ? workStyle.get().value : null)
						.isEdit(true)
						.isActive(true)
						.build();
				
				// ※Aa1
				boolean isEdit = true;
				if(dto.achievements == true || dto.confirmed == true || dto.needToWork == false || dto.supportCategory == SupportCategory.TimeSupport.value){
					isEdit = false;
				}
				// ※Aa2
				boolean isActive = true;
				if(dto.achievements == true || dto.needToWork == false || dto.supportCategory == SupportCategory.TimeSupport.value){
					isActive = false;
				}
				dto.setEdit(isEdit);
				dto.setActive(isActive);
				listWorkScheduleShift.add(dto);
				
			} else {
				// 4.3
				ScheduleOfShiftDto dto = ScheduleOfShiftDto.builder()
						.employeeId(key.getEmployeeID())
						.date(key.getDate())
						.haveData(false)
						.achievements(false)
						.confirmed(false)
						.needToWork(needToWork)
						.supportCategory(SupportCategory.NotCheering.value)
						.shiftCode(null)
						.shiftName(null)
						.shiftEditState(null)
						.workHolidayCls(null)
						.isEdit(true)
						.isActive(true)
						.build();
				// ※Aa1
				boolean isEdit = true;
				if(dto.achievements == true || dto.confirmed == true || dto.needToWork == false || dto.supportCategory == SupportCategory.TimeSupport.value){
					isEdit = false;
				}
				// ※Aa2
				boolean isActive = true;
				if(dto.achievements == true || dto.needToWork == false || dto.supportCategory == SupportCategory.TimeSupport.value){
					isActive = false;
				}
				dto.setEdit(isEdit);
				dto.setActive(isActive);
				listWorkScheduleShift.add(dto);
			}
		});
		
		return new ScheduleOfShiftResult(listWorkScheduleShift, listShiftMaster);
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
	private static class RequireCombiAndWorkHolidayImpl implements GetCombinationrAndWorkHolidayAtrService.Require {
		
		private final String companyId = AppContexts.user().companyId();
		
		@Inject
		private ShiftMasterRepository shiftMasterRepo;
		@Inject
		private BasicScheduleService service;
		@Inject
		private WorkTypeRepository workTypeRepo;
		@Inject
		private WorkTimeSettingRepository workTimeSettingRepository;
		@Inject
		private WorkTimeSettingService workTimeSettingService;
		@Inject
		private BasicScheduleService basicScheduleService;
		
		@Override
		public List<ShiftMaster> getByListEmp(String companyID, List<String> lstShiftMasterCd) {
			List<ShiftMaster> data = shiftMasterRepo.getByListShiftMaterCd2(companyId, lstShiftMasterCd);
			return data;
		}

		@Override
		public List<ShiftMaster> getByListWorkInfo(String companyId, List<WorkInformation> lstWorkInformation) {
			List<ShiftMaster> data = shiftMasterRepo.get(companyId, lstWorkInformation);
			return data;
		}

		@Override
		public SetupType checkNeededOfWorkTimeSetting(String workTypeCode) {
			 return service.checkNeededOfWorkTimeSetting(workTypeCode);
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
		public PredetermineTimeSetForCalc getPredeterminedTimezone(String workTimeCd, String workTypeCd, Integer workNo) {
			return workTimeSettingService .getPredeterminedTimezone(companyId, workTimeCd, workTypeCd, workNo);
		}

		@Override
		public WorkStyle checkWorkDay(String workTypeCode) {
			return basicScheduleService.checkWorkDay(workTypeCode);
		}
	}
	
	
	@AllArgsConstructor
	private static class RequireWorkScheManaStatusImpl implements WorkScheManaStatusService.Require {
		
		private WorkScheduleRepository workScheduleRepo;
		private EmpComHisAdapter empComHisAdapter;
		private WorkingConditionRepository workCondRepo;
		private EmpLeaveHistoryAdapter empLeaveHisAdapter;
		private EmpLeaveWorkHistoryAdapter empLeaveWorkHisAdapter;
		private EmploymentHisScheduleAdapter employmentHisScheduleAdapter;
		
		private NestedMapCache<String, GeneralDate, WorkSchedule> workScheduleCache;

		public RequireWorkScheManaStatusImpl(List<String> empIdList, DatePeriod period, WorkScheduleRepository workScheduleRepo,
				EmpComHisAdapter empComHisAdapter, WorkingConditionRepository workCondRepo,
				EmpLeaveHistoryAdapter empLeaveHisAdapter, EmpLeaveWorkHistoryAdapter empLeaveWorkHisAdapter,
				EmploymentHisScheduleAdapter employmentHisScheduleAdapter) {
			
			this.workScheduleRepo = workScheduleRepo;
			this.empComHisAdapter = empComHisAdapter;
			this.workCondRepo = workCondRepo;
			this.empLeaveHisAdapter = empLeaveHisAdapter;
			this.empLeaveWorkHisAdapter = empLeaveWorkHisAdapter;
			this.employmentHisScheduleAdapter = employmentHisScheduleAdapter;
			
			workScheduleCache = NestedMapCache.preloadedAll(workScheduleRepo.getList(empIdList, period).stream(),
					workSchedule -> workSchedule.getEmployeeID(), workSchedule -> workSchedule.getYmd());
		}
		
		@Override
		public Optional<WorkSchedule> get(String employeeID, GeneralDate ymd) {
			Optional<WorkSchedule> data = workScheduleCache.get(employeeID, ymd);
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
