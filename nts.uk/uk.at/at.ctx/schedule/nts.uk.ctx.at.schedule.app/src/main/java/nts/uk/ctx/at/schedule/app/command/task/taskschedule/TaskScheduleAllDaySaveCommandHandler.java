package nts.uk.ctx.at.schedule.app.command.task.taskschedule;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.AllArgsConstructor;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.schedule.dom.adapter.classification.SClsHistImported;
import nts.uk.ctx.at.schedule.dom.adapter.classification.SyClassificationAdapter;
import nts.uk.ctx.at.schedule.dom.schedule.workschedule.WorkSchedule;
import nts.uk.ctx.at.schedule.dom.schedule.workschedule.WorkScheduleRepository;
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
import nts.uk.ctx.at.shared.dom.schedule.basicschedule.BasicScheduleService;
import nts.uk.ctx.at.shared.dom.schedule.basicschedule.SetupType;
import nts.uk.ctx.at.shared.dom.scherec.taskmanagement.taskmaster.TaskCode;
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
 * 作業予定を終日指定して更新する
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.勤務予定.勤務予定.勤務予定.App.作業予定を終日指定して更新する.作業予定を終日指定して更新する
 * @author quytb
 *
 */

@Stateless
public class TaskScheduleAllDaySaveCommandHandler extends CommandHandler<TaskScheduleCommand> {

	@Inject
	private WorkScheduleRepository repository;

	@Inject
	private  WorkTypeRepository workTypeRepo;

	@Inject
	private  WorkTimeSettingRepository workTimeSettingRepository;

	@Inject
	private  BasicScheduleService basicScheduleService;

	@Inject
	private  FixedWorkSettingRepository fixedWorkSettingRepository;

	@Inject
	private  FlowWorkSettingRepository flowWorkSettingRepository;

	@Inject
	private  FlexWorkSettingRepository flexWorkSettingRepository;

	@Inject
	private  PredetemineTimeSettingRepository predetemineTimeSettingRepository;

	@Inject
	private EmploymentHisScheduleAdapter employmentHisScheduleAdapter;

	@Inject
	private SharedAffJobtitleHisAdapter sharedAffJobtitleHisAdapter;

	@Inject
	private SharedAffWorkPlaceHisAdapter sharedAffWorkPlaceHisAdapter;

	@Inject
	private SyClassificationAdapter syClassificationAdapter;

	@Inject
	private WorkingConditionRepository workingConditionRepo;

	@Inject
	private BusinessTypeEmpService businessTypeEmpService;
	
	@Inject
	private EmpAffiliationInforAdapter empAffiliationInforAdapter;
	@Inject
	private EmpMedicalWorkStyleHistoryRepository empMedicalWorkStyleHistoryRepo;
	@Inject
	private NurseClassificationRepository nurseClassificationRepo;

	@Override
	protected void handle(CommandHandlerContext<TaskScheduleCommand> context) {
		TaskScheduleCommand command = context.getCommand();
		String companyId = AppContexts.user().companyId();
		GeneralDate ymd = command.toDate();

		RequireImpl require = new RequireImpl(companyId, workTypeRepo, workTimeSettingRepository,
				basicScheduleService, fixedWorkSettingRepository, flowWorkSettingRepository, flexWorkSettingRepository,
				predetemineTimeSettingRepository, employmentHisScheduleAdapter, sharedAffJobtitleHisAdapter,
				sharedAffWorkPlaceHisAdapter, syClassificationAdapter, workingConditionRepo, businessTypeEmpService,
				empAffiliationInforAdapter, empMedicalWorkStyleHistoryRepo, nurseClassificationRepo);
		/**loop:社員ID in 社員IDリスト */
		command.getEmployeeIds().stream().forEach(empId -> {
			/** 1.1: get(社員ID、年月日):Optional<勤務予定>*/
			Optional<WorkSchedule> optional = repository.get(empId, ymd);
			/** 1.2: [not 勤務予定．isEmpty]:一日中に作業予定を作成する(Require, 作業コード)*/
			if(optional.isPresent()) {
				WorkSchedule workSchedule = optional.get();
				workSchedule.createTaskScheduleForWholeDay(require, companyId, new TaskCode(command.getTaskCode()));
				
				/** 2: persist*/
				repository.update(workSchedule);
			} else {
				return;
			}
		});
	}

	@AllArgsConstructor
	private static class RequireImpl implements WorkSchedule.Require {

		private String companyId;

		private  WorkTypeRepository workTypeRepo;

		private  WorkTimeSettingRepository workTimeSettingRepository;

		private  BasicScheduleService basicScheduleService;

		private  FixedWorkSettingRepository fixedWorkSettingRepository;

		private  FlowWorkSettingRepository flowWorkSettingRepository;

		private  FlexWorkSettingRepository flexWorkSettingRepository;

		private  PredetemineTimeSettingRepository predetemineTimeSettingRepository;

		private EmploymentHisScheduleAdapter employmentHisScheduleAdapter;

		private SharedAffJobtitleHisAdapter sharedAffJobtitleHisAdapter;

		private SharedAffWorkPlaceHisAdapter sharedAffWorkPlaceHisAdapter;

		private SyClassificationAdapter syClassificationAdapter;

		private WorkingConditionRepository workingConditionRepo;

		private BusinessTypeEmpService businessTypeEmpService;
		
		private EmpAffiliationInforAdapter empAffiliationInforAdapter;
		
		private EmpMedicalWorkStyleHistoryRepository empMedicalWorkStyleHistoryRepo;
		
		private NurseClassificationRepository nurseClassificationRepo;

		@Override
		public Optional<WorkType> workType(String cid, WorkTypeCode workTypeCd) {
			return workTypeRepo.findByPK(cid, workTypeCd.v());
		}

		// implements WorkInformation.Require
		@Override
		public Optional<WorkTimeSetting> workTimeSetting(String cid, WorkTimeCode workTimeCode) {
			return workTimeSettingRepository.findByCode(cid, workTimeCode.v());
		}

		// implements WorkInformation.Require
		@Override
		public Optional<FixedWorkSetting> fixedWorkSetting(String companyId, WorkTimeCode workTimeCode) {
			return fixedWorkSettingRepository.findByKey(companyId, workTimeCode.v());
		}

		// implements WorkInformation.Require
		@Override
		public Optional<FlowWorkSetting> flowWorkSetting(String companyId, WorkTimeCode workTimeCode) {
			return flowWorkSettingRepository.find(companyId, workTimeCode.v());
		}

		// implements WorkInformation.Require
		@Override
		public Optional<FlexWorkSetting> flexWorkSetting(String companyId, WorkTimeCode workTimeCode) {
			return flexWorkSettingRepository.find(companyId, workTimeCode.v());
		}

		// implements WorkInformation.Require
		@Override
		public Optional<PredetemineTimeSetting> predetemineTimeSetting(String companyId, WorkTimeCode workTimeCode) {
			return predetemineTimeSettingRepository.findByWorkTimeCode(companyId, workTimeCode.v());
		}

		@Override
		public SetupType checkNeededOfWorkTimeSetting(String workTypeCode) {
			return basicScheduleService.checkNeededOfWorkTimeSetting(workTypeCode);
		}

		@Override
		public SharedSyEmploymentImport getAffEmploymentHistory(String employeeId, GeneralDate standardDate) {
			List<EmploymentPeriodImported> listEmpHist = employmentHisScheduleAdapter
					.getEmploymentPeriod(Arrays.asList(employeeId), new DatePeriod(standardDate, standardDate));
			if(listEmpHist.isEmpty())
				return null;
			return new SharedSyEmploymentImport(listEmpHist.get(0).getEmpID(), listEmpHist.get(0).getEmploymentCd(), "",
					listEmpHist.get(0).getDatePeriod());
		}

		@Override
		public SharedAffJobTitleHisImport getAffJobTitleHistory(String employeeId, GeneralDate standardDate) {
			List<SharedAffJobTitleHisImport> listAffJobTitleHis =  sharedAffJobtitleHisAdapter.findAffJobTitleHisByListSid(Arrays.asList(employeeId), standardDate);
			if(listAffJobTitleHis.isEmpty())
				return null;
			return listAffJobTitleHis.get(0);
		}

		@Override
		public SClsHistImport getClassificationHistory(String employeeId, GeneralDate standardDate) {
			Optional<SClsHistImported> imported = syClassificationAdapter.findSClsHistBySid(companyId, employeeId, standardDate);
			if (!imported.isPresent()) {
				return null;
			}
			return new SClsHistImport(imported.get().getPeriod(), imported.get().getEmployeeId(),
					imported.get().getClassificationCode(), imported.get().getClassificationName());
		}

		@Override
		public Optional<BusinessTypeOfEmployee> getBusinessType(String employeeId, GeneralDate standardDate) {
			List<BusinessTypeOfEmployee> list = businessTypeEmpService.getData(employeeId, standardDate);
			if (list.isEmpty())
				return Optional.empty();
			return Optional.of(list.get(0));
		}

		@Override
		public Optional<WorkingConditionItem> getWorkingConditionHistory(String employeeId, GeneralDate standardDate) {
			Optional<WorkingConditionItem> rs = workingConditionRepo.getWorkingConditionItemByEmpIDAndDate(companyId, standardDate, employeeId);
			return rs;
		}

		@Override
		public String getLoginEmployeeId() {
			return AppContexts.user().employeeId();
		}

		@Override
		public EmpOrganizationImport getEmpOrganization(String employeeId, GeneralDate standardDate) {
			List<EmpOrganizationImport> results = empAffiliationInforAdapter.getEmpOrganization(standardDate, Arrays.asList(employeeId));
			if(results.isEmpty())
				return null;
			return results.get(0);
		}

		@Override
		public List<EmpMedicalWorkStyleHistoryItem> getEmpMedicalWorkStyleHistoryItem(List<String> listEmp,
				GeneralDate referenceDate) {
			return empMedicalWorkStyleHistoryRepo.get(listEmp, referenceDate);
		}

		@Override
		public List<NurseClassification> getListCompanyNurseCategory() {
			String companyId = AppContexts.user().companyId();
			return nurseClassificationRepo.getListCompanyNurseCategory(companyId);
		}
	}
}
