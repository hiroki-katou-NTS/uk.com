package nts.uk.screen.at.app.ksus01.a;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.AllArgsConstructor;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.function.dom.adapter.RegulationInfoEmployeeAdapter;
import nts.uk.ctx.at.schedule.dom.schedule.workschedule.GetWorkTogetherEmpOnDayBySpecEmpService;
import nts.uk.ctx.at.schedule.dom.schedule.workschedule.WorkSchedule;
import nts.uk.ctx.at.schedule.dom.schedule.workschedule.WorkScheduleRepository;
import nts.uk.ctx.at.schedule.dom.shift.management.workavailability.DesiredSubmissionStatus;
import nts.uk.ctx.at.schedule.dom.shift.management.workavailability.GetStatusSubmissionWishes;
import nts.uk.ctx.at.schedule.dom.shift.management.workavailability.WorkAvailabilityOfOneDay;
import nts.uk.ctx.at.schedule.dom.shift.management.workavailability.WorkAvailabilityOfOneDayRepository;
import nts.uk.ctx.at.shared.dom.WorkInformation;
import nts.uk.ctx.at.shared.dom.schedule.basicschedule.BasicScheduleService;
import nts.uk.ctx.at.shared.dom.schedule.basicschedule.SetupType;
import nts.uk.ctx.at.shared.dom.schedule.basicschedule.WorkStyle;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.attendancetime.TimeLeavingOfDailyAttd;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.RegulationInfoEmpQuery;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.adapter.EmpAffiliationInforAdapter;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.adapter.EmpOrganizationImport;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.adapter.WorkplaceGroupAdapter;
import nts.uk.ctx.at.shared.dom.workrule.shiftmaster.GetCombinationrAndWorkHolidayAtrService;
import nts.uk.ctx.at.shared.dom.workrule.shiftmaster.ShiftMaster;
import nts.uk.ctx.at.shared.dom.workrule.shiftmaster.ShiftMasterCode;
import nts.uk.ctx.at.shared.dom.workrule.shiftmaster.ShiftMasterRepository;
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
import nts.uk.ctx.sys.auth.dom.algorithm.AcquireUserIDFromEmpIDService;
import nts.uk.query.pub.employee.EmployeeSearchQueryDto;
import nts.uk.query.pub.employee.RegulationInfoEmployeeExport;
import nts.uk.query.pub.employee.RegulationInfoEmployeePub;
import nts.uk.shr.com.context.AppContexts;

/**
 * 
 * UKDesign.UniversalK.就業.KSU_スケジュール.KSUS01_スケジュール参照（スマホ）.スケジュール参照.A：スケジュール参照.メニュー別OCD.対象期間の情報を取得する
 * @author dungbn
 *
 */
@Stateless
public class GetInforOnTargetPeriod {
	
	@Inject
	private WorkAvailabilityOfOneDayRepository workAvailabilityOfOneDayRepository;
	
	@Inject
	private WorkScheduleRepository workScheduleRepository;
	
	@Inject
	private ShiftMasterRepository shiftMasterRepo;
	
	@Inject
	private WorkTypeRepository workTypeRepo;
	
	final static String DATE_TIME_FORMAT = "YYYY/MM/DD";
	
	public InforOnTargetPeriodDto handle(InforOnTargetPeriodInput input) {
		
		String empId = AppContexts.user().employeeId();
		String companyId = AppContexts.user().companyId();
		GetStatusSubmissionWishesRequireImpl getStatusSubmissionWishesRequire = new GetStatusSubmissionWishesRequireImpl(workAvailabilityOfOneDayRepository);
		GetCombinationrAndWorkHolidayAtrRequireImpl getComAndWorkHolidayAtrRequire = new GetCombinationrAndWorkHolidayAtrRequireImpl(companyId, shiftMasterRepo, workTypeRepo);
		
		if (!(input.getDesiredPeriodWork().getStart().length() > 0) || !(input.getDesiredPeriodWork().getEnd().length() > 0)) {
			return null;
		}
		
		List<DesiredSubmissionStatusByDate> listDesiredSubmissionStatusByDate = new ArrayList<DesiredSubmissionStatusByDate>();
		DatePeriod desiredPeriodWork = new DatePeriod(GeneralDate.fromString(input.getDesiredPeriodWork().getStart(), DATE_TIME_FORMAT), GeneralDate.fromString(input.getDesiredPeriodWork().getEnd(), DATE_TIME_FORMAT));
		
		// 1: [Optional<勤務希望期間>．isPresent]: <call>()
		desiredPeriodWork.forEach(e -> {
			
			// 1.1: 取得する(require, 社員ID, 年月日): 希望提出状況
			DesiredSubmissionStatus status = GetStatusSubmissionWishes.get(getStatusSubmissionWishesRequire, empId, e);    
			DesiredSubmissionStatusByDate item = new DesiredSubmissionStatusByDate(e.toString(), status.value);
			listDesiredSubmissionStatusByDate.add(item);
		});
		
		if (!(input.getScheduledWorkingPeriod().getStart().length() > 0) || !(input.getScheduledWorkingPeriod().getEnd().length() > 0)) {
			return null;
		}
		
		DatePeriod scheduledWorkingPeriod = new DatePeriod(GeneralDate.fromString(input.getScheduledWorkingPeriod().getStart(), DATE_TIME_FORMAT), GeneralDate.fromString(input.getScheduledWorkingPeriod().getEnd(), DATE_TIME_FORMAT));
		
		List<String> listEmpId = new ArrayList<String>();
		listEmpId.add(empId);
		
		// 2: [Optional<勤務予定期間>．isPresent]: <call>()
		// 2.1:  勤務予定リストを取得する(社員ID、年月日):  勤務予定
		List<WorkSchedule> listWorkSchedule = workScheduleRepository.getList(listEmpId, scheduledWorkingPeriod);
		
		List<WorkInformation> lstWorkInformation = listWorkSchedule.stream().map(e -> e.getWorkInfo().getRecordInfo()).collect(Collectors.toList());
		
		// 3: 勤務情報で取得する(require, 会社ID, 勤務情報リスト): Map<シフトマスタ, Optional<出勤休日区分>>
		Map<ShiftMaster,Optional<WorkStyle>> mapByWorkInfo = GetCombinationrAndWorkHolidayAtrService.getbyWorkInfo(getComAndWorkHolidayAtrRequire, companyId, lstWorkInformation);
		
		List<WorkScheduleDto> listWorkScheduleDto = new ArrayList<WorkScheduleDto>();
		
		for (WorkSchedule workSchedule : listWorkSchedule) {
			mapByWorkInfo.forEach((k, v) -> {
				
				boolean check = false;
				List<AttendanceDto> listAttendaceDto = new ArrayList<AttendanceDto>();
				
				
    			Optional<TimeLeavingOfDailyAttd> optTimeLeaving = workSchedule.getOptTimeLeaving();
    			
    			if (optTimeLeaving.isPresent()) {
    				listAttendaceDto = optTimeLeaving.get().getTimeLeavingWorks().stream().map(e -> new AttendanceDto(e.getAttendanceStamp().get().getStamp().get().getTimeDay().getTimeWithDay().get().getInDayTimeWithFormat(), 
    																												e.getLeaveStamp().get().getStamp().get().getTimeDay().getTimeWithDay().get().getInDayTimeWithFormat())).collect(Collectors.toList());
    			}
	    		
				
				// map by workTimeCode and workTypeCode
				if (k.getWorkTypeCode().v() == workSchedule.getWorkInfo().getRecordInfo().getWorkTypeCode().v()) {
					
					if (k.getWorkTimeCode() != null) {
						if (k.getWorkTimeCode().v().equals(workSchedule.getWorkInfo().getRecordInfo().getWorkTimeCode().v())) {
							check = true;
						}
					} else {
						if (workSchedule.getWorkInfo().getRecordInfo().getWorkTimeCode() == null) {
							check = true;
						}
					}
				}
				
				if (check == true) {
					
					WorkScheduleDto workScheduleDto = new WorkScheduleDto(workSchedule.getYmd().toString(), new ShiftMasterDto(k.getDisplayInfor().getName().v(), k.getDisplayInfor().getColor().v()),
																			v.isPresent() ? v.get().value : null, listAttendaceDto);
					listWorkScheduleDto.add(workScheduleDto);
				} else {
					WorkScheduleDto workScheduleDto = new WorkScheduleDto(workSchedule.getYmd().toString(), new ShiftMasterDto("", ""),
																			v.isPresent() ? v.get().value : null, listAttendaceDto);
					listWorkScheduleDto.add(workScheduleDto);
				}
			});
		}
		
		return new InforOnTargetPeriodDto(listWorkScheduleDto, listDesiredSubmissionStatusByDate);
	}
	
	
	@AllArgsConstructor
	private static class GetCombinationrAndWorkHolidayAtrRequireImpl implements GetCombinationrAndWorkHolidayAtrService.Require {
		
		private String companyId;
		
		private ShiftMasterRepository shiftMasterRepo;
		
		private WorkTypeRepository workTypeRepo;
		
		@Override
		public Optional<WorkType> getWorkType(String workTypeCd) {
			return workTypeRepo.findByPK(companyId, workTypeCd);
		}

		@Override
		public Optional<WorkTimeSetting> getWorkTime(String workTimeCode) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public SetupType checkNeededOfWorkTimeSetting(String workTypeCode) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public FixedWorkSetting getWorkSettingForFixedWork(WorkTimeCode code) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public FlowWorkSetting getWorkSettingForFlowWork(WorkTimeCode code) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public FlexWorkSetting getWorkSettingForFlexWork(WorkTimeCode code) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public PredetemineTimeSetting getPredetermineTimeSetting(WorkTimeCode wktmCd) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public List<ShiftMaster> getByListEmp(String companyId, List<String> lstShiftMasterCd) {
			return shiftMasterRepo.getByListShiftMaterCd2(companyId, lstShiftMasterCd);
		}

		@Override
		public List<ShiftMaster> getByListWorkInfo(String companyId, List<WorkInformation> lstWorkInformation) {
			return shiftMasterRepo.get(companyId, lstWorkInformation);
		}
		
	}
	
	
	@AllArgsConstructor
	private static class GetStatusSubmissionWishesRequireImpl implements GetStatusSubmissionWishes.Require {

		private WorkAvailabilityOfOneDayRepository workAvailabilityOfOneDayRepository;
		
		@Override
		public Optional<WorkType> getWorkType(String workTypeCd) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Optional<WorkTimeSetting> getWorkTime(String workTimeCode) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public SetupType checkNeededOfWorkTimeSetting(String workTypeCode) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public FixedWorkSetting getWorkSettingForFixedWork(WorkTimeCode code) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public FlowWorkSetting getWorkSettingForFlowWork(WorkTimeCode code) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public FlexWorkSetting getWorkSettingForFlexWork(WorkTimeCode code) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public PredetemineTimeSetting getPredetermineTimeSetting(WorkTimeCode wktmCd) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Optional<ShiftMaster> getShiftMasterByWorkInformation(WorkTypeCode workTypeCode,
				WorkTimeCode workTimeCode) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public List<ShiftMaster> getShiftMaster(List<ShiftMasterCode> shiftMasterCodeList) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public boolean shiftMasterIsExist(ShiftMasterCode shiftMasterCode) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public Optional<WorkAvailabilityOfOneDay> get(String employeeID, GeneralDate availabilityDate) {
			return workAvailabilityOfOneDayRepository.get(employeeID, availabilityDate);
		}
	}

	
}
