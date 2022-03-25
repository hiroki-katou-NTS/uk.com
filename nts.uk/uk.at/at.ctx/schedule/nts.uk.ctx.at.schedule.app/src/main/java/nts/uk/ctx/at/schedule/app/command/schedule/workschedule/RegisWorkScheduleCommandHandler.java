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
import nts.uk.ctx.at.shared.dom.employeeworkway.businesstype.employee.BusinessTypeOfEmployee;
import nts.uk.ctx.at.shared.dom.employeeworkway.businesstype.employee.repository.BusinessTypeEmpService;
import nts.uk.ctx.at.shared.dom.employeeworkway.medicalcare.medicalworkstyle.EmpMedicalWorkStyleHistoryItem;
import nts.uk.ctx.at.shared.dom.employeeworkway.medicalcare.medicalworkstyle.EmpMedicalWorkStyleHistoryRepository;
import nts.uk.ctx.at.shared.dom.employeeworkway.medicalcare.medicalworkstyle.NurseClassification;
import nts.uk.ctx.at.shared.dom.employeeworkway.medicalcare.medicalworkstyle.NurseClassificationRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.algorithm.InterimRemainDataMngRegisterDateChange;
import nts.uk.ctx.at.shared.dom.schedule.basicschedule.BasicScheduleService;
import nts.uk.ctx.at.shared.dom.schedule.basicschedule.SetupType;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItem;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionRepository;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.employeeinfor.employmenthistory.imported.EmploymentHisScheduleAdapter;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.employeeinfor.employmenthistory.imported.EmploymentPeriodImported;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.adapter.EmpAffiliationInforAdapter;
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
	private EmpMedicalWorkStyleHistoryRepository empMedicalWorkStyleHistoryRepo;
	@Inject
	private NurseClassificationRepository nurseClassificationRepo;
	@Inject
	private EmpAffiliationInforAdapter empAffInforAdapter;
	
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
				empMedicalWorkStyleHistoryRepo, nurseClassificationRepo, empAffInforAdapter);
		List<ResultOfRegisteringWorkSchedule> lstRsOfRegisWorkSchedule = new ArrayList<ResultOfRegisteringWorkSchedule>();
		
		String cid = AppContexts.user().companyId();
		
		// step 1
		// loop:社員ID in 社員IDリスト
		mapBySid.forEach((k, v) -> {
			String sid = k;
			List<WorkScheduleSaveCommand<T>> scheduleOfEmps = v;
			// loop:年月日 in 年月日リスト
			for (WorkScheduleSaveCommand<T> data : scheduleOfEmps) {
				WorkInformation workInfo = new WorkInformation(data.workInfor.workTypeCd, data.workInfor.workTimeCd);
				// step 1.1
				ResultOfRegisteringWorkSchedule rsOfRegisteringWorkSchedule = CreateWorkSchedule.create(
						requireImpl, cid, sid, data.ymd,
						workInfo,
						data.isBreakByHand, // TODO VN team update
						data.breakTimeList, data.mapAttendIdWithTime);
				
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
		private EmpMedicalWorkStyleHistoryRepository empMedicalWorkStyleHistoryRepo;
		@Inject
		private NurseClassificationRepository nurseClassificationRepo;
		@Inject
		private EmpAffiliationInforAdapter empAffInforAdapter;
		
		// implements WorkInformation.Require
		@Override
		public Optional<WorkType> workType(String companyId, WorkTypeCode workTypeCode) {
			return workTypeRepo.findByPK(companyId, workTypeCode.v());
		}
		
		// implements WorkInformation.Require
		@Override
		public Optional<WorkTimeSetting> workTimeSetting(String companyId, WorkTimeCode workTimeCode) {
			return workTimeSettingRepository.findByCode(companyId, workTimeCode.v());
		}
		
		// implements WorkInformation.Require
		@Override
		public SetupType checkNeededOfWorkTimeSetting(String workTypeCode) {
			return basicScheduleService.checkNeededOfWorkTimeSetting(workTypeCode);
		}
		
		// implements WorkInformation.Require
		@Override
		public Optional<FixedWorkSetting> fixedWorkSetting(String companyId, WorkTimeCode workTimeCode) {
			return fixedWorkSet.findByKey(companyId, workTimeCode.v());
		}
		
		// implements WorkInformation.Require
		@Override
		public Optional<FlowWorkSetting> flowWorkSetting(String companyId, WorkTimeCode workTimeCode) {
			return flowWorkSet.find(companyId, workTimeCode.v());
		}
		
		// implements WorkInformation.Require
		@Override
		public Optional<FlexWorkSetting> flexWorkSetting(String companyId, WorkTimeCode workTimeCode) {
			return flexWorkSet.find(companyId, workTimeCode.v());
		}
		
		// implements WorkInformation.Require
		@Override
		public Optional<PredetemineTimeSetting> predetemineTimeSetting(String companyId, WorkTimeCode workTimeCode) {
			return predetemineTimeSet.findByWorkTimeCode(companyId, workTimeCode.v());
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
		
		// implements AffiliationInforOfDailyAttd.Require
		@Override
		public EmpOrganizationImport getEmpOrganization(String employeeId, GeneralDate standardDate) {
			List<EmpOrganizationImport> info = empAffInforAdapter.getEmpOrganization(standardDate, Arrays.asList(employeeId));
			return info.isEmpty()? null: info.get(0);
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

		// GetEmpLicenseClassificationService
		@Override
		public List<EmpMedicalWorkStyleHistoryItem> getEmpMedicalWorkStyleHistoryItem(List<String> listEmp,
				GeneralDate referenceDate) {
			return empMedicalWorkStyleHistoryRepo.get(listEmp, referenceDate);
		}

		// GetEmpLicenseClassificationService
		@Override
		public List<NurseClassification> getListCompanyNurseCategory() {
			return nurseClassificationRepo.getListCompanyNurseCategory(AppContexts.user().companyId());
		}
	}
}
