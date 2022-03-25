package nts.uk.screen.at.app.command.kdl.kdl016;

import lombok.AllArgsConstructor;
import lombok.val;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerWithResult;
import nts.arc.task.tran.AtomTask;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.schedule.dom.adapter.classification.SClsHistImported;
import nts.uk.ctx.at.schedule.dom.adapter.classification.SyClassificationAdapter;
import nts.uk.ctx.at.schedule.dom.schedule.support.supportschedule.RegisterResultFromSupportableEmployee;
import nts.uk.ctx.at.schedule.dom.schedule.support.supportschedule.UpdateSupportScheduleFromSupportableEmployee;
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
import nts.uk.ctx.at.shared.dom.supportmanagement.supportableemployee.SupportableEmployee;
import nts.uk.ctx.at.shared.dom.supportmanagement.supportableemployee.SupportableEmployeeRepository;
import nts.uk.ctx.at.shared.dom.supportmanagement.supportoperationsetting.SupportOperationSetting;
import nts.uk.ctx.at.shared.dom.supportmanagement.supportoperationsetting.SupportOperationSettingRepository;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItem;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionRepository;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.employeeinfor.employee.EmployeeAdapter;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.employeeinfor.employee.importeddto.EmployeeCodeAndDisplayNameImport;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.employeeinfor.employee.importeddto.EmployeeInfoImport;
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

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import java.util.*;
import java.util.stream.Collectors;

@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class DeleteSupportInfoCommandHandler extends CommandHandlerWithResult<DeleteSupportInfoCommand, DeleteSupportInfoResult> {

    @Inject
    private EmployeeAdapter employeeAdapter;

    @Inject
    private SupportableEmployeeRepository supportableEmployeeRepo;

    @Inject
    private WorkScheduleRepository workScheduleRepo;

    @Inject
    private SupportOperationSettingRepository supportOperationSettingRepo;

    @Inject
    private WorkTypeRepository workTypeRepo;

    @Inject
    private WorkTimeSettingRepository workTimeSettingRepository;

    @Inject
    private BasicScheduleService basicScheduleService;

    @Inject
    private FixedWorkSettingRepository fixedWorkSettingRepository;

    @Inject
    private FlowWorkSettingRepository flowWorkSettingRepository;

    @Inject
    private FlexWorkSettingRepository flexWorkSettingRepository;

    @Inject
    private PredetemineTimeSettingRepository predetemineTimeSettingRepository;

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
    protected DeleteSupportInfoResult handle(CommandHandlerContext<DeleteSupportInfoCommand> commandHandlerContext) {
        DeleteSupportInfoCommand command = commandHandlerContext.getCommand();
        String companyId = AppContexts.user().companyId();

        // 1. List<応援可能な社員>
        val supportableEmployees = supportableEmployeeRepo.get(command.getEmployeeIds());

        RequireImpl require = new RequireImpl(companyId, supportableEmployeeRepo, workScheduleRepo, supportOperationSettingRepo,
                workTypeRepo, workTimeSettingRepository, basicScheduleService, fixedWorkSettingRepository, flowWorkSettingRepository,
                flexWorkSettingRepository, predetemineTimeSettingRepository, employmentHisScheduleAdapter, sharedAffJobtitleHisAdapter,
                sharedAffWorkPlaceHisAdapter, syClassificationAdapter, workingConditionRepo, businessTypeEmpService,
                empAffiliationInforAdapter, empMedicalWorkStyleHistoryRepo, nurseClassificationRepo);

        List<RegisterResultFromSupportableEmployee.ErrorInformation> lstCannotDelete = new ArrayList<>();

        for (SupportableEmployee supportableEmp : supportableEmployees) {
            // 2. 応援可能な社員から応援予定を変更する.削除する(@Require, 応援可能な社員) : 1件以上存在する場合
            RegisterResultFromSupportableEmployee result = UpdateSupportScheduleFromSupportableEmployee.remove(require, supportableEmp);
            if (result.isError() && result.getErrorInfo().isPresent()) {
                lstCannotDelete.add(result.getErrorInfo().get());
                continue;
            }

            // 3.List<応援可能な社員からの登録結果> :filter $.エラーがあるか == false
            // 3.1 delete(List<応援可能な社員ID>): List<応援可能な社員ID>：2の結果にエラーがある分を除く１の結果のID　
            supportableEmployeeRepo.delete(supportableEmp.getId());

            // 3.2. AtomTask run
            if (!result.getAtomTaskList().isEmpty()) {
                transaction.execute(() -> result.getAtomTaskList().forEach(AtomTask::run));
            }

        }

        if (lstCannotDelete.isEmpty()) {
            return new DeleteSupportInfoResult(Collections.emptyList());
        }

        // 4.社員IDリスト = List<応援可能な社員からの登録結果> ：
        //　filter $.エラーがあるか == true
        //　map $.エラー情報.応援可能な社員.社員ID
        val empErrors = lstCannotDelete.stream().map(x -> x.getSupportableEmployee().getEmployeeId().v()).collect(Collectors.toList());
        List<EmployeeCodeAndDisplayNameImport> employeeErrorInfors = employeeAdapter.getEmployeeCodeAndDisplayNameImportByEmployeeIds(empErrors);

        List<EmployeeErrorResult> employeeErrorResults = new ArrayList<>();
        for (RegisterResultFromSupportableEmployee.ErrorInformation empErr : lstCannotDelete) {
            val empInfoOpt = employeeErrorInfors.stream().filter(e -> e.getEmployeeId().equals(empErr.getSupportableEmployee().getEmployeeId().v())).findFirst();
            employeeErrorResults.add(new EmployeeErrorResult(
                    empInfoOpt.map(EmployeeCodeAndDisplayNameImport::getEmployeeCode).orElse(""),
                    empInfoOpt.map(EmployeeCodeAndDisplayNameImport::getBusinessName).orElse(""),
                    empErr.getSupportableEmployee().getPeriod().start().toString("yyyy/MM/dd"),
                    empErr.getSupportableEmployee().getPeriod().end().toString("yyyy/MM/dd"),
                    empErr.getErrorMessage()
            ));
        }

        return new DeleteSupportInfoResult(employeeErrorResults.stream().sorted(Comparator.comparing(EmployeeErrorResult::getStartDate)
                .thenComparing(Comparator.comparing(EmployeeErrorResult::getEmployeeCode))).collect(Collectors.toList()));
    }

    @AllArgsConstructor
    private class RequireImpl implements UpdateSupportScheduleFromSupportableEmployee.Require {

        private String companyId;

        private SupportableEmployeeRepository supportableEmployeeRepo;

        private WorkScheduleRepository workScheduleRepo;

        private SupportOperationSettingRepository supportOperationSettingRepo;

        private WorkTypeRepository workTypeRepo;

        private WorkTimeSettingRepository workTimeSettingRepository;

        private BasicScheduleService basicScheduleService;

        private FixedWorkSettingRepository fixedWorkSettingRepository;

        private FlowWorkSettingRepository flowWorkSettingRepository;

        private FlexWorkSettingRepository flexWorkSettingRepository;

        private PredetemineTimeSettingRepository predetemineTimeSettingRepository;

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
        public Optional<SupportableEmployee> getSupportableEmployee(String id) {
            return supportableEmployeeRepo.get(id);
        }

        @Override
        public boolean isExistWorkSchedule(String employeeId, GeneralDate date) {
            return workScheduleRepo.checkExists(employeeId, date);
        }

        @Override
        public Optional<WorkSchedule> getWorkSchedule(String employeeId, GeneralDate date) {
            return workScheduleRepo.get(employeeId, date);
        }

        @Override
        public void updateWorkSchedule(WorkSchedule workSchedule) {
            workScheduleRepo.update(workSchedule);
        }

        @Override
        public SupportOperationSetting getSupportOperationSetting() {
            return supportOperationSettingRepo.get(companyId);
        }

        @Override
        public SetupType checkNeededOfWorkTimeSetting(String workTypeCode) {
            return basicScheduleService.checkNeededOfWorkTimeSetting(workTypeCode);
        }

        @Override
        public SharedSyEmploymentImport getAffEmploymentHistory(String employeeId, GeneralDate standardDate) {
            List<EmploymentPeriodImported> listEmpHist = employmentHisScheduleAdapter
                    .getEmploymentPeriod(Arrays.asList(employeeId), new DatePeriod(standardDate, standardDate));
            if (listEmpHist.isEmpty())
                return null;
            return new SharedSyEmploymentImport(listEmpHist.get(0).getEmpID(), listEmpHist.get(0).getEmploymentCd(), "",
                    listEmpHist.get(0).getDatePeriod());
        }

        @Override
        public SharedAffJobTitleHisImport getAffJobTitleHistory(String employeeId, GeneralDate standardDate) {
            List<SharedAffJobTitleHisImport> listAffJobTitleHis = sharedAffJobtitleHisAdapter.findAffJobTitleHisByListSid(Arrays.asList(employeeId), standardDate);
            if (listAffJobTitleHis.isEmpty())
                return null;
            return listAffJobTitleHis.get(0);
        }

//        @Override
//        public SharedAffWorkPlaceHisImport getAffWorkplaceHistory(String employeeId, GeneralDate standardDate) {
//            return sharedAffWorkPlaceHisAdapter.getAffWorkPlaceHis(employeeId, standardDate).orElse(null);
//        }

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
            return workingConditionRepo.getWorkingConditionItemByEmpIDAndDate(companyId, standardDate, employeeId);
        }

        @Override
        public EmpOrganizationImport getEmpOrganization(String employeeId, GeneralDate standardDate) {
            List<EmpOrganizationImport> results = empAffiliationInforAdapter.getEmpOrganization(standardDate, Arrays.asList(employeeId));
            if(results.isEmpty())
                return null;
            return results.get(0);
        }

        @Override
        public String getLoginEmployeeId() {
            return AppContexts.user().employeeId();
        }

        @Override
        public List<EmpMedicalWorkStyleHistoryItem> getEmpMedicalWorkStyleHistoryItem(List<String> listEmp, GeneralDate referenceDate) {
            return empMedicalWorkStyleHistoryRepo.get(listEmp, referenceDate);
        }

        @Override
        public List<NurseClassification> getListCompanyNurseCategory() {
            return nurseClassificationRepo.getListCompanyNurseCategory(AppContexts.user().companyId());
        }

		@Override
		public Optional<FixedWorkSetting> fixedWorkSetting(String companyId, WorkTimeCode workTimeCode) {
			if(workTimeCode == null)
				return Optional.empty(); 
			return fixedWorkSettingRepository.findByKey(companyId, workTimeCode.v());
		}

		@Override
		public Optional<FlowWorkSetting> flowWorkSetting(String companyId, WorkTimeCode workTimeCode) {
			if(workTimeCode == null)
				return Optional.empty(); 
			return flowWorkSettingRepository.find(companyId, workTimeCode.v());
		}

		@Override
		public Optional<FlexWorkSetting> flexWorkSetting(String companyId, WorkTimeCode workTimeCode) {
			if(workTimeCode == null)
				return Optional.empty();
			return flexWorkSettingRepository.find(companyId, workTimeCode.v());
		}
		
		@Override
		public Optional<PredetemineTimeSetting> predetemineTimeSetting(String companyId, WorkTimeCode workTimeCode) {
			if(workTimeCode == null)
				return Optional.empty();
			return predetemineTimeSettingRepository.findByWorkTimeCode(companyId, workTimeCode.v());
		}

		@Override
		public Optional<WorkTimeSetting> workTimeSetting(String companyId, WorkTimeCode workTimeCode) {
			if(workTimeCode == null)
				return Optional.empty();
			return workTimeSettingRepository.findByCode(companyId, workTimeCode.v());
		}
		
		@Override
		public Optional<WorkType> workType(String companyId, WorkTypeCode workTypeCode) {
			if(workTypeCode == null)
				return Optional.empty();
			return workTypeRepo.findByPK(companyId, workTypeCode.v());
		}
    }
}
