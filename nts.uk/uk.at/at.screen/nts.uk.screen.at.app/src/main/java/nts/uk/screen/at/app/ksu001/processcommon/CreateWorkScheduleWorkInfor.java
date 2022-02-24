package nts.uk.screen.at.app.ksu001.processcommon;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.AllArgsConstructor;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.schedule.dom.schedule.support.supportschedule.GetSupportInfoOfEmployee;
import nts.uk.ctx.at.schedule.dom.schedule.workschedule.WorkSchedule;
import nts.uk.ctx.at.shared.dom.WorkInformation;
import nts.uk.ctx.at.shared.dom.common.EmployeeId;
import nts.uk.ctx.at.shared.dom.employeeworkway.EmployeeWorkingStatus;
import nts.uk.ctx.at.shared.dom.schedule.basicschedule.BasicScheduleService;
import nts.uk.ctx.at.shared.dom.schedule.basicschedule.SetupType;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.dailyattendancework.IntegrationOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.workinfomation.GetListWtypeWtimeUseDailyAttendRecordService;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.workinfomation.WorkInfoOfDailyAttendance;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.workinfomation.WorkTypeWorkTimeUseDailyAttendanceRecord;
import nts.uk.ctx.at.shared.dom.supportmanagement.supportableemployee.SupportableEmployee;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.TargetOrgIdenInfor;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.adapter.EmpOrganizationImport;
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
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeInfor;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * 
 * @author laitv
 * ScreenQuery: 勤務予定で勤務予定（勤務情報）dtoを作成する
 * Path : UKDesign.UniversalK.就業.KSU_スケジュール.KSU001_個人スケジュール修正(職場別).個人別と共通の処理.勤務予定で勤務予定（勤務情報）dtoを作成する
 */
@Stateless
public class CreateWorkScheduleWorkInfor {
	
	@Inject
	private WorkTypeRepository workTypeRepo;
	@Inject
	private WorkTimeSettingRepository workTimeSettingRepo;
	@Inject
	private BasicScheduleService basicScheduleService;
	@Inject
	private FixedWorkSettingRepository fixedWorkSet; 
	@Inject
	private FlowWorkSettingRepository flowWorkSet;
	@Inject
	private FlexWorkSettingRepository flexWorkSet;
	@Inject
	private PredetemineTimeSettingRepository predetemineTimeSet;
	
	public List<WorkScheduleWorkInforDto> getDataScheduleOfWorkInfo(
			Map<EmployeeWorkingStatus, Optional<WorkSchedule>> mngStatusAndWScheMap, TargetOrgIdenInfor targetOrg) {		

		String companyId = AppContexts.user().companyId();
		List<WorkInfoOfDailyAttendance>  listWorkInfo = new ArrayList<WorkInfoOfDailyAttendance>();
		mngStatusAndWScheMap.forEach((k,v)->{
			if (v.isPresent()) {
				listWorkInfo.add(v.get().getWorkInfo());
			}
		});
		
		// step 1 call DomainService: 日別勤怠の実績で利用する勤務種類と就業時間帯のリストを取得する
		WorkTypeWorkTimeUseDailyAttendanceRecord wTypeWTimeUseDailyAttendRecord = GetListWtypeWtimeUseDailyAttendRecordService.getdata(listWorkInfo);

		// step 2
		List<WorkTypeCode> workTypeCodes = wTypeWTimeUseDailyAttendRecord.getLstWorkTypeCode().stream().filter(wt -> wt != null).collect(Collectors.toList());
		List<String> lstWorkTypeCode     = workTypeCodes.stream().map(i -> i.toString()).collect(Collectors.toList());
		//<<Public>> 指定した勤務種類をすべて取得する
		List<WorkTypeInfor> lstWorkTypeInfor = this.workTypeRepo.getPossibleWorkTypeAndOrder(companyId, lstWorkTypeCode).stream().collect(Collectors.toList());

		// step 3
		List<WorkTimeCode> workTimeCodes   = wTypeWTimeUseDailyAttendRecord.getLstWorkTimeCode().stream().filter(wt -> wt != null).collect(Collectors.toList());
		List<String> lstWorkTimeCode       = workTimeCodes.stream().map(i -> i.toString()).collect(Collectors.toList());
		List<WorkTimeSetting> lstWorkTimeSetting =  workTimeSettingRepo.getListWorkTime(companyId, lstWorkTimeCode);

		// step 4
		List<WorkScheduleWorkInforDto> listWorkScheduleWorkInfor = new ArrayList<>();
		WorkInformation.Require require = new RequireWorkInforImpl();

		mngStatusAndWScheMap.forEach((employeeWorkingStatus, workScheduleOpt) -> {
			
			Optional<WorkTypeInfor> workTypeInfor = Optional.empty();
			Optional<WorkTimeSetting> workTimeSetting = Optional.empty();
			if (workScheduleOpt.isPresent()) {
				//※勤務種類：2のList<勤務種類> filter:$勤務種類コード＝＝勤務予定.勤務情報.勤務種類コード
				WorkInformation workInformation = workScheduleOpt.get().getWorkInfo().getRecordInfo();
				String workTypeCode = workInformation.getWorkTypeCode() == null ? null : workInformation.getWorkTypeCode().toString();
				workTypeInfor = lstWorkTypeInfor.stream().filter(i -> i.getWorkTypeCode().equals(workTypeCode)).findFirst();
				
				//※就業時間帯：3のList<就業時間帯> filter:$就業時間帯コード＝＝勤務予定.勤務情報.就業時間帯コード
				String workTimeCode = workInformation.getWorkTimeCode() == null  ? null : workInformation.getWorkTimeCode().toString();
				workTimeSetting = lstWorkTimeSetting.stream().filter(i -> i.getWorktimeCode().toString().equals(workTimeCode)).findFirst();
			}
			
			WorkScheduleWorkInforDto dto = new WorkScheduleWorkInforDto(employeeWorkingStatus, workScheduleOpt, workTypeInfor, workTimeSetting, targetOrg, wTypeWTimeUseDailyAttendRecord, require);
			listWorkScheduleWorkInfor.add(dto);
		});

		return listWorkScheduleWorkInfor;
	}
	
	@AllArgsConstructor
	private class RequireWorkInforImpl implements WorkInformation.Require {

		private final String companyId = AppContexts.user().companyId();

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
			return workTimeSettingRepo.findByCode(companyId, workTimeCode);
		}

		@Override
		public FixedWorkSetting getWorkSettingForFixedWork(WorkTimeCode code) {
			Optional<FixedWorkSetting> workSetting = fixedWorkSet.findByKey(companyId, code.v());
			return workSetting.isPresent() ? workSetting.get() : null;
		}
		@Override
		public FlowWorkSetting getWorkSettingForFlowWork(WorkTimeCode code) {
			Optional<FlowWorkSetting> workSetting = flowWorkSet.find(companyId, code.v());
			return workSetting.isPresent() ? workSetting.get() : null;
		}
		@Override
		public FlexWorkSetting getWorkSettingForFlexWork(WorkTimeCode code) {
			Optional<FlexWorkSetting> workSetting = flexWorkSet.find(companyId, code.v());
			return workSetting.isPresent() ? workSetting.get() : null;
		}
		@Override
		public PredetemineTimeSetting getPredetermineTimeSetting(WorkTimeCode wktmCd) {
			Optional<PredetemineTimeSetting> workSetting = predetemineTimeSet.findByWorkTimeCode(companyId, wktmCd.v());
			return workSetting.isPresent() ? workSetting.get() : null;
		}
	}
	
	@AllArgsConstructor
	private class RequireGetSupportInfoImpl implements GetSupportInfoOfEmployee.Require {
		
		@Override
		public List<SupportableEmployee> getSupportableEmployee(EmployeeId employeeId, GeneralDate date) {
			return null;
		}

		@Override
		public List<EmpOrganizationImport> getEmpOrganization(GeneralDate baseDate, List<String> lstEmpId) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Optional<WorkSchedule> getWorkSchedule(String employeeId, GeneralDate date) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Optional<IntegrationOfDaily> getRecord(String employeeId, GeneralDate date) {
			// TODO Auto-generated method stub
			return null;
		}
		
		
	}
}
