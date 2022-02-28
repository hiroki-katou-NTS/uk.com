/**
 * 
 */
package nts.uk.ctx.at.schedule.app.command.schedule.workschedule;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonObject;

import lombok.AllArgsConstructor;
import lombok.val;
import nts.arc.layer.app.command.AsyncCommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.task.data.TaskDataSetter;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.schedule.dom.adapter.classification.SClsHistImported;
import nts.uk.ctx.at.schedule.dom.adapter.classification.SyClassificationAdapter;
import nts.uk.ctx.at.schedule.dom.schedule.createworkschedule.createschedulecommon.correctworkschedule.CorrectWorkSchedule;
import nts.uk.ctx.at.schedule.dom.schedule.workschedule.CreateWorkSchedule;
import nts.uk.ctx.at.schedule.dom.schedule.workschedule.ErrorInfoOfWorkSchedule;
import nts.uk.ctx.at.schedule.dom.schedule.workschedule.ResultOfRegisteringWorkSchedule;
import nts.uk.ctx.at.schedule.dom.schedule.workschedule.WorkSchedule;
import nts.uk.ctx.at.schedule.dom.schedule.workschedule.WorkScheduleRepository;
import nts.uk.ctx.at.shared.dom.WorkInformation;
import nts.uk.ctx.at.shared.dom.adapter.employee.EmpEmployeeAdapter;
import nts.uk.ctx.at.shared.dom.adapter.employee.EmployeeImport;
import nts.uk.ctx.at.shared.dom.adapter.employee.SClsHistImport;
import nts.uk.ctx.at.shared.dom.adapter.employment.SharedSyEmploymentImport;
import nts.uk.ctx.at.shared.dom.adapter.jobtitle.SharedAffJobTitleHisImport;
import nts.uk.ctx.at.shared.dom.adapter.jobtitle.SharedAffJobtitleHisAdapter;
import nts.uk.ctx.at.shared.dom.adapter.workplace.SharedAffWorkPlaceHisAdapter;
import nts.uk.ctx.at.shared.dom.adapter.workplace.SharedAffWorkPlaceHisImport;
import nts.uk.ctx.at.shared.dom.common.EmployeeId;
import nts.uk.ctx.at.shared.dom.employeeworkway.businesstype.employee.BusinessTypeOfEmployee;
import nts.uk.ctx.at.shared.dom.employeeworkway.businesstype.employee.repository.BusinessTypeEmpService;
import nts.uk.ctx.at.shared.dom.remainingnumber.algorithm.InterimRemainDataMngRegisterDateChange;
import nts.uk.ctx.at.shared.dom.schedule.basicschedule.BasicScheduleService;
import nts.uk.ctx.at.shared.dom.schedule.basicschedule.SetupType;
import nts.uk.ctx.at.shared.dom.supportmanagement.supportableemployee.SupportTicket;
import nts.uk.ctx.at.shared.dom.supportmanagement.supportableemployee.SupportableEmployee;
import nts.uk.ctx.at.shared.dom.supportmanagement.supportableemployee.SupportableEmployeeRepository;
import nts.uk.ctx.at.shared.dom.supportmanagement.supportoperationsetting.SupportOperationSetting;
import nts.uk.ctx.at.shared.dom.supportmanagement.supportoperationsetting.SupportOperationSettingRepository;
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
 * @author laitv
 * Command: 勤務予定を登録する
 * Path: UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.勤務予定.勤務予定.勤務予定.App.勤務予定を登録する.勤務予定を登録する
 */

@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class RegisWorkScheduleCommandHandler<T> extends AsyncCommandHandler<List<WorkScheduleSaveCommand<T>>>{
	
	@Inject
	private BasicScheduleService basicScheduleService;
	@Inject
	private WorkTypeRepository workTypeRepo;
	@Inject
	private WorkTimeSettingRepository workTimeSettingRepository;
	@Inject
	private FixedWorkSettingRepository fixedWorkSet;
	@Inject
	private FlowWorkSettingRepository flowWorkSet;
	@Inject
	private FlexWorkSettingRepository flexWorkSet;
	@Inject
	private PredetemineTimeSettingRepository predetemineTimeSet;
	
	// 
	@Inject
	private WorkScheduleRepository workScheduleRepo;
	@Inject
	private CorrectWorkSchedule correctWorkSchedule;
	@Inject
	private InterimRemainDataMngRegisterDateChange interimRemainDataMngRegisterDateChange;
	
	// 
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
	private EmpEmployeeAdapter empAdapter;
	
	@Inject
	private SupportOperationSettingRepository supportOperationSettingRepo;
	@Inject
	private SupportableEmployeeRepository supportableEmployeeRepo;
	
	private final String STATUS_REGISTER = "STATUS_REGISTER";
	private final String STATUS_ERROR = "STATUS_ERROR";
	
	@Override
	protected void handle(CommandHandlerContext<List<WorkScheduleSaveCommand<T>>> context) {

		List<WorkScheduleSaveCommand<T>> commands = context.getCommand();
		
		val asyncTask = context.asAsync();
		
        TaskDataSetter setter = asyncTask.getDataSetter();

		Map<String, List<WorkScheduleSaveCommand<T>>> mapBySid = commands.stream().collect(Collectors.groupingBy(item -> item.getSid()));
		
		RequireImpl requireImpl = new RequireImpl(basicScheduleService, workTypeRepo, workTimeSettingRepository,
				fixedWorkSet, flowWorkSet, flexWorkSet, predetemineTimeSet, workScheduleRepo, correctWorkSchedule,
				interimRemainDataMngRegisterDateChange, employmentHisScheduleAdapter, sharedAffJobtitleHisAdapter,
				sharedAffWorkPlaceHisAdapter, workingConditionRepo, businessTypeEmpService, syClassificationAdapter,
				supportOperationSettingRepo);
		List<ResultOfRegisteringWorkSchedule> lstRsOfRegisWorkSchedule = new ArrayList<ResultOfRegisteringWorkSchedule>();
		
		// step 1
		// loop:社員ID in 社員IDリスト
		mapBySid.forEach((k, v) -> {
			String sid = k;
			List<WorkScheduleSaveCommand<T>> scheduleOfEmps = v;
			// loop:年月日 in 年月日リスト
			for (WorkScheduleSaveCommand<T> data : scheduleOfEmps) {
				
				// step 1.1 : 社員と期間を指定して取得する(社員ID, 期間：年月日): List<応援可能な社員>
				List<SupportableEmployee> supportableEmpList = supportableEmployeeRepo
						.findByEmployeeIdWithPeriod(new EmployeeId(sid), DatePeriod.oneDay(data.ymd));
				
				// step 1.2 :  応援チケットを作成する(年月日): Optional<応援チケット>
				List<SupportTicket> supportTicketList = new ArrayList<>();
				supportableEmpList.forEach(supportableEmp -> {
					Optional<SupportTicket> supportTicketOpt = supportableEmp.createTicket(data.ymd);
					if (supportTicketOpt.isPresent())
						supportTicketList.add(supportTicketOpt.get());
				});
				
				// step 1.3 : call DomainService 勤務予定を作る
				
				WorkInformation workInfo = new WorkInformation(data.workInfor.workTypeCd, data.workInfor.workTimeCd);
				
				ResultOfRegisteringWorkSchedule rsOfRegisteringWorkSchedule = CreateWorkSchedule.create(
						requireImpl, 
						sid, 
						data.ymd,
						workInfo,
						data.isBreakByHand,
						data.breakTimeList, 
						supportTicketList,
						data.mapAttendIdWithTime);
				
				lstRsOfRegisWorkSchedule.add(rsOfRegisteringWorkSchedule);
			}
		});
		
		// step2
		boolean isRegistered = false;
		List<ResultOfRegisteringWorkSchedule> lstRsHasNoErrors = lstRsOfRegisWorkSchedule.stream().filter(i -> i.isHasError() == false).collect(Collectors.toList());
		if (lstRsHasNoErrors.size() > 0) {
			for (ResultOfRegisteringWorkSchedule dataToRunTransaction : lstRsHasNoErrors) {
				if (dataToRunTransaction.getAtomTask().isPresent()) {
					isRegistered = true;
					transaction.execute(() -> {
						dataToRunTransaction.getAtomTask().get().run();
					});
				}
			}
		}
		
		// step3
		List<ResultOfRegisteringWorkSchedule> lstRsHasErrors = lstRsOfRegisWorkSchedule.stream().filter(i -> i.isHasError() == true).collect(Collectors.toList());
		boolean isError = false;
		List<ErrorInfoOfWorkSchedule> errorInformations = new ArrayList<>();
		
		if (lstRsHasErrors.size() > 0) {
			isError = true;
			Set<String> sids = new HashSet<>();
			for (int j = 0; j < lstRsHasErrors.size(); j++) {
				if(!lstRsHasErrors.get(j).getErrorInformation().isEmpty()){
					sids.add(lstRsHasErrors.get(j).getErrorInformation().get(0).getEmployeeId());
					errorInformations.addAll(lstRsHasErrors.get(j).getErrorInformation());
				}
			}
			List<EmployeeImport> lstEmpInfo  = empAdapter.findByEmpId(sids.stream().collect(Collectors.toList()));
			
			for (int k = 0; k < lstEmpInfo.size(); k++) {
				EmployeeImport empImport = lstEmpInfo.get(k);
				List<ErrorInfoOfWorkSchedule> errorInforOfEmp = errorInformations.stream().filter(i -> i.getEmployeeId().equals(empImport.getEmployeeId())).collect(Collectors.toList());
				for (int x = 0; x < errorInforOfEmp.size(); x++) {
					JsonObject value = Json.createObjectBuilder()
							.add("sid", empImport.getEmployeeId())
							.add("scd", empImport.getEmployeeCode())
							.add("empName", empImport.getEmployeeName())
							.add("date", errorInforOfEmp.get(x).getDate().toString())
							.add("attendanceItemId", errorInforOfEmp.get(x).getAttendanceItemId().isPresent() ? errorInforOfEmp.get(x).getAttendanceItemId().get().toString() : "" )
							.add("errorMessage", errorInforOfEmp.get(x).getErrorMessage()).build();
					setter.setData("ERROR"+k+""+x, value);
				}
			}
		}
		setter.setData(STATUS_REGISTER, isRegistered);
		setter.setData(STATUS_ERROR, isError);
	}
	
	@AllArgsConstructor
	private static class RequireImpl implements CreateWorkSchedule.Require {
		//
		private final String companyId = AppContexts.user().companyId();
		@Inject
		private BasicScheduleService basicScheduleService;
		@Inject
		private WorkTypeRepository workTypeRepo;
		@Inject
		private WorkTimeSettingRepository workTimeSettingRepository;
		@Inject
		private FixedWorkSettingRepository fixedWorkSet;
		@Inject
		private FlowWorkSettingRepository flowWorkSet;
		@Inject
		private FlexWorkSettingRepository flexWorkSet;
		@Inject
		private PredetemineTimeSettingRepository predetemineTimeSet;
		
		// 
		@Inject
		private WorkScheduleRepository workScheduleRepo;
		@Inject
		private CorrectWorkSchedule correctWorkSchedule;
		@Inject
		private InterimRemainDataMngRegisterDateChange interimRemainDataMngRegisterDateChange;
		
		// 
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
		private SupportOperationSettingRepository supportOperationSettingRepo;
		
		// implements WorkInformation.Require
		@Override
		public Optional<WorkType> getWorkType(String workTypeCd) {
			return workTypeRepo.findByPK(companyId, workTypeCd);
		}
		
		// implements WorkInformation.Require
		@Override
		public Optional<WorkTimeSetting> getWorkTime(String workTimeCode) {
			return workTimeSettingRepository.findByCode(companyId, workTimeCode);
		}
		
		// implements WorkInformation.Require
		@Override
		public SetupType checkNeededOfWorkTimeSetting(String workTypeCode) {
			return basicScheduleService.checkNeededOfWorkTimeSetting(workTypeCode);
		}
		
		// implements WorkInformation.Require
		@Override
		public FixedWorkSetting getWorkSettingForFixedWork(WorkTimeCode code) {
			Optional<FixedWorkSetting> workSetting = fixedWorkSet.findByKey(companyId, code.v());
			return workSetting.isPresent() ? workSetting.get() : null;
		}
		
		// implements WorkInformation.Require
		@Override
		public FlowWorkSetting getWorkSettingForFlowWork(WorkTimeCode code) {
			Optional<FlowWorkSetting> workSetting = flowWorkSet.find(companyId, code.v());
			return workSetting.isPresent() ? workSetting.get() : null;
		}
		
		// implements WorkInformation.Require
		@Override
		public FlexWorkSetting getWorkSettingForFlexWork(WorkTimeCode code) {
			Optional<FlexWorkSetting> workSetting = flexWorkSet.find(companyId, code.v());
			return workSetting.isPresent() ? workSetting.get() : null;
		}
		
		// implements WorkInformation.Require
		@Override
		public PredetemineTimeSetting getPredetermineTimeSetting(WorkTimeCode wktmCd) {
			Optional<PredetemineTimeSetting> workSetting = predetemineTimeSet.findByWorkTimeCode(companyId, wktmCd.v());
			return workSetting.isPresent() ? workSetting.get() : null;
		}
		
		// implements AffiliationInforOfDailyAttd.Require
		@Override
		public SharedSyEmploymentImport getAffEmploymentHistory(String employeeId, GeneralDate standardDate) {
			List<EmploymentPeriodImported> listEmpHist = employmentHisScheduleAdapter
					.getEmploymentPeriod(Arrays.asList(employeeId), new DatePeriod(standardDate, standardDate));
			if(listEmpHist.isEmpty())
				return null;
			return new SharedSyEmploymentImport(listEmpHist.get(0).getEmpID(), listEmpHist.get(0).getEmploymentCd(), "",
					listEmpHist.get(0).getDatePeriod());
		}
		
		// implements AffiliationInforOfDailyAttd.Require
		@Override
		public SharedAffJobTitleHisImport getAffJobTitleHistory(String employeeId, GeneralDate standardDate) {
			List<SharedAffJobTitleHisImport> listAffJobTitleHis =  sharedAffJobtitleHisAdapter.findAffJobTitleHisByListSid(Arrays.asList(employeeId), standardDate);
			if(listAffJobTitleHis.isEmpty())
				return null;
			return listAffJobTitleHis.get(0);
		}
		
		// implements AffiliationInforOfDailyAttd.Require
		@Override
		public SharedAffWorkPlaceHisImport getAffWorkplaceHistory(String employeeId, GeneralDate standardDate) {
			Optional<SharedAffWorkPlaceHisImport> rs = sharedAffWorkPlaceHisAdapter.getAffWorkPlaceHis(employeeId, standardDate);
			return rs.isPresent() ? rs.get() : null;
		}
		
		// implements AffiliationInforOfDailyAttd.Require
		@Override
		public SClsHistImport getClassificationHistory(String employeeId, GeneralDate standardDate) {
			Optional<SClsHistImported> imported = syClassificationAdapter.findSClsHistBySid(companyId, employeeId, standardDate);
			if (!imported.isPresent()) {
				return null;
			}
			return new SClsHistImport(imported.get().getPeriod(), imported.get().getEmployeeId(),
					imported.get().getClassificationCode(), imported.get().getClassificationName());
		}
		
		// implements AffiliationInforOfDailyAttd.Require  QA:http://192.168.50.4:3000/issues/113789
		@Override
		public Optional<BusinessTypeOfEmployee> getBusinessType(String employeeId, GeneralDate standardDate) {
			List<BusinessTypeOfEmployee> list = businessTypeEmpService.getData(employeeId, standardDate);
			if (list.isEmpty()) 
				return Optional.empty();
			return Optional.of(list.get(0));
		}
		
		// implements AffiliationInforOfDailyAttd.Require
		@Override
		public Optional<WorkingConditionItem> getWorkingConditionHistory(String employeeId, GeneralDate standardDate) {
			Optional<WorkingConditionItem> rs = workingConditionRepo.getWorkingConditionItemByEmpIDAndDate(companyId, standardDate, employeeId);
			return rs;
		}
		
		//implements EditStateOfDailyAttd.Require
		@Override
		public String getLoginEmployeeId() {
			return AppContexts.user().employeeId();
		}

		// CreateWorkSchedule.Require
		@Override
		public Optional<WorkSchedule> getWorkSchedule(String employeeId, GeneralDate date) {
			Optional<WorkSchedule> rs = workScheduleRepo.get(employeeId, date);
			return rs;
		}
		
		// CreateWorkSchedule.Require  QA: http://192.168.50.4:3000/issues/113786
		@Override
		public WorkSchedule correctWorkSchedule(WorkSchedule workSchedule) {
			WorkSchedule rs = correctWorkSchedule.correctWorkSchedule(workSchedule, workSchedule.getEmployeeID(), workSchedule.getYmd());
			return rs;
		}
		
		// CreateWorkSchedule.Require
		@Override
		public void insertWorkSchedule(WorkSchedule workSchedule) {
			workScheduleRepo.insert(workSchedule);
		}
		
		// CreateWorkSchedule.Require
		@Override
		public void updateWorkSchedule(WorkSchedule workSchedule) {
			workScheduleRepo.update(workSchedule);
		}
		
		// CreateWorkSchedule.Require
		@Override
		public void registerTemporaryData(String employeeId, GeneralDate date) {
			interimRemainDataMngRegisterDateChange.registerDateChange(companyId, employeeId, Arrays.asList(date));
		}

		@Override
		public SupportOperationSetting getSupportOperationSetting() {
			return supportOperationSettingRepo.get(companyId);
		}
	}
}
