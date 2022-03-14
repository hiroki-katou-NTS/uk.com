package nts.uk.screen.at.app.command.kdl.kdl016;

import lombok.AllArgsConstructor;
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
import nts.uk.ctx.at.shared.dom.common.EmployeeId;
import nts.uk.ctx.at.shared.dom.common.time.TimeSpanForCalc;
import nts.uk.ctx.at.shared.dom.employeeworkway.businesstype.employee.BusinessTypeOfEmployee;
import nts.uk.ctx.at.shared.dom.employeeworkway.businesstype.employee.repository.BusinessTypeEmpService;
import nts.uk.ctx.at.shared.dom.employeeworkway.medicalcare.medicalworkstyle.EmpMedicalWorkStyleHistoryItem;
import nts.uk.ctx.at.shared.dom.employeeworkway.medicalcare.medicalworkstyle.EmpMedicalWorkStyleHistoryRepository;
import nts.uk.ctx.at.shared.dom.employeeworkway.medicalcare.medicalworkstyle.NurseClassification;
import nts.uk.ctx.at.shared.dom.employeeworkway.medicalcare.medicalworkstyle.NurseClassificationRepository;
import nts.uk.ctx.at.shared.dom.schedule.basicschedule.BasicScheduleService;
import nts.uk.ctx.at.shared.dom.schedule.basicschedule.SetupType;
import nts.uk.ctx.at.shared.dom.supportmanagement.SupportType;
import nts.uk.ctx.at.shared.dom.supportmanagement.supportableemployee.SupportableEmployee;
import nts.uk.ctx.at.shared.dom.supportmanagement.supportableemployee.SupportableEmployeeCheckService;
import nts.uk.ctx.at.shared.dom.supportmanagement.supportableemployee.SupportableEmployeeRepository;
import nts.uk.ctx.at.shared.dom.supportmanagement.supportoperationsetting.SupportOperationSetting;
import nts.uk.ctx.at.shared.dom.supportmanagement.supportoperationsetting.SupportOperationSettingRepository;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItem;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionRepository;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.employeeinfor.employee.EmployeeAdapter;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.employeeinfor.employee.importeddto.EmployeeCodeAndDisplayNameImport;
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
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.i18n.TextResource;
import nts.uk.shr.com.time.TimeWithDayAttr;
import org.apache.logging.log4j.util.Strings;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import java.util.*;
import java.util.stream.Collectors;

@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class UpdateSupportInforCommandHandler extends CommandHandlerWithResult<UpdateSupportInforCommand, UpdateSupportInforResult> {
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
    protected UpdateSupportInforResult handle(CommandHandlerContext<UpdateSupportInforCommand> commandHandlerContext) {
        UpdateSupportInforCommand command = commandHandlerContext.getCommand();
        String companyId = AppContexts.user().companyId();

        // 1. 応援可能な社員
        SupportableEmployee supportableEmployee = supportableEmployeeRepo.get(command.getEmployeeId()).orElse(null);
        if (supportableEmployee == null) return new UpdateSupportInforResult(Collections.emptyList());

        // 2.1. 応援形式＝＝終日
        SupportableEmployee supportableEmpChanged = supportableEmployee;
        if (command.getSupportType() == SupportType.ALLDAY.getValue()) {
            if (command.toDatePeriod() != null) {
                supportableEmpChanged = supportableEmployee.changePeriod(command.toDatePeriod());
            }
        }

        // 2.2. 応援形式＝＝時間帯
        if (command.getSupportType() == SupportType.TIMEZONE.getValue()) {
            supportableEmpChanged = supportableEmployee.changeTimespan(new TimeSpanForCalc(
                    new TimeWithDayAttr(command.getSupportTimeSpan().getStart()),
                    new TimeWithDayAttr(command.getSupportTimeSpan().getEnd())
            ));
        }

        // 3.
        RequireSupportableImpl requireSupportable = new RequireSupportableImpl(companyId, supportableEmployeeRepo, supportOperationSettingRepo);
        SupportableEmployeeCheckService.CheckResult supportableCheckResult = SupportableEmployeeCheckService.isRegistrable(requireSupportable, supportableEmpChanged);

        // 4. チェック結果<>登録可能
        List<SupportableEmpCannotRegisterDto> errorResults = new ArrayList<>();
        if (supportableCheckResult != SupportableEmployeeCheckService.CheckResult.REGISTABLE) {
            errorResults.add(SupportableEmpCannotRegisterDto.createWithError(supportableEmpChanged, this.getErrorMsg(supportableCheckResult)));
            Map<String, EmployeeCodeAndDisplayNameImport> empErrorInfoMap = employeeAdapter.getEmployeeCodeAndDisplayNameImportByEmployeeIds(errorResults.stream().map(x -> x.getSupportableEmployee().getEmployeeId().v()).collect(Collectors.toList()))
                    .stream().collect(Collectors.toMap(EmployeeCodeAndDisplayNameImport::getEmployeeId, e -> e));
            DatePeriod period = supportableEmployee.getPeriod();
            List<EmployeeErrorResult> employeeErrorResults = errorResults.stream().map(m -> new EmployeeErrorResult(
                    empErrorInfoMap.get(m.getSupportableEmployee().getEmployeeId().v()).getEmployeeCode(),
                    empErrorInfoMap.get(m.getSupportableEmployee().getEmployeeId().v()).getBusinessName(),
                    period.start() == null ? "" : period.start().toString("yyyy/MM/dd"),
                    period.end() == null ? "" : period.end().toString("yyyy/MM/dd"),
                    m.getErrorInfo()
            )).sorted(Comparator.comparing(EmployeeErrorResult::getStartDate)
                    .thenComparing(Comparator.comparing(EmployeeErrorResult::getEmployeeCode)))
                    .collect(Collectors.toList());

            return new UpdateSupportInforResult(employeeErrorResults);
        }

        RequireImpl require = new RequireImpl(companyId, supportableEmployeeRepo, workScheduleRepo, supportOperationSettingRepo,
                workTypeRepo, workTimeSettingRepository, basicScheduleService, fixedWorkSettingRepository, flowWorkSettingRepository,
                flexWorkSettingRepository, predetemineTimeSettingRepository, employmentHisScheduleAdapter, sharedAffJobtitleHisAdapter,
                sharedAffWorkPlaceHisAdapter, syClassificationAdapter, workingConditionRepo, businessTypeEmpService,
                empAffiliationInforAdapter, empMedicalWorkStyleHistoryRepo, nurseClassificationRepo);
        RegisterResultFromSupportableEmployee workScheduleCheckResult = UpdateSupportScheduleFromSupportableEmployee.modify(require, supportableEmpChanged);

        if (workScheduleCheckResult.isError()) {
            if (workScheduleCheckResult.getErrorInfo().isPresent()) {
                errorResults.add(SupportableEmpCannotRegisterDto.createWithError(
                        workScheduleCheckResult.getErrorInfo().get().getSupportableEmployee(),
                        workScheduleCheckResult.getErrorInfo().get().getErrorMessage()));
            }
        }

        // 7.
        if (!errorResults.isEmpty()) {
            Map<String, EmployeeCodeAndDisplayNameImport> empErrorInfoMap = employeeAdapter.getEmployeeCodeAndDisplayNameImportByEmployeeIds(errorResults.stream().map(x -> x.getSupportableEmployee().getEmployeeId().v()).collect(Collectors.toList()))
                    .stream().collect(Collectors.toMap(EmployeeCodeAndDisplayNameImport::getEmployeeId, e -> e));
            DatePeriod period = supportableEmployee.getPeriod();
            List<EmployeeErrorResult> employeeErrorResults = errorResults.stream().map(m -> new EmployeeErrorResult(
                    empErrorInfoMap.get(m.getSupportableEmployee().getEmployeeId().v()).getEmployeeCode(),
                    empErrorInfoMap.get(m.getSupportableEmployee().getEmployeeId().v()).getBusinessName(),
                    period.start() == null ? "" : period.start().toString("yyyy/MM/dd"),
                    period.end() == null ? "" : period.end().toString("yyyy/MM/dd"),
                    m.getErrorInfo()
            )).sorted(Comparator.comparing(EmployeeErrorResult::getStartDate)
                    .thenComparing(Comparator.comparing(EmployeeErrorResult::getEmployeeCode)))
                    .collect(Collectors.toList());

            return new UpdateSupportInforResult(employeeErrorResults);
        }

        // 6.
        supportableEmployeeRepo.update(companyId, supportableEmpChanged);

        if (!workScheduleCheckResult.getAtomTaskList().isEmpty()) {
            transaction.execute(() -> workScheduleCheckResult.getAtomTaskList().forEach(AtomTask::run));
        }

        return new UpdateSupportInforResult(Collections.emptyList());
    }

    private String getErrorMsg(SupportableEmployeeCheckService.CheckResult value) {
        switch (value) {
            case DUPLICATED_PERIOD:
                return TextResource.localize("Msg_3288");
            case DUPLICATED_TIMEZONE:
                return TextResource.localize("Msg_3289");
            case UPPER_LIMIT:
                return TextResource.localize("Msg_3290");
            default:
                return Strings.EMPTY;
        }
    }

    @AllArgsConstructor
    private class RequireSupportableImpl implements SupportableEmployeeCheckService.Require {

        private String companyId;

        private SupportableEmployeeRepository supportableEmployeeRepo;

        private SupportOperationSettingRepository supportOperationSettingRepo;

        @Override
        public List<SupportableEmployee> getByPeriod(EmployeeId employeeId, DatePeriod period) {
            return supportableEmployeeRepo.findByEmployeeIdWithPeriod(employeeId, period);
        }

        @Override
        public SupportOperationSetting getSetting() {
            return supportOperationSettingRepo.get(companyId);
        }
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
        public Optional<WorkType> getWorkType(String workTypeCd) {
            return workTypeRepo.findByPK(companyId, workTypeCd);
        }

        @Override
        public Optional<WorkTimeSetting> getWorkTime(String workTimeCode) {
            return workTimeSettingRepository.findByCode(companyId, workTimeCode);
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

        @Override
        public SharedAffWorkPlaceHisImport getAffWorkplaceHistory(String employeeId, GeneralDate standardDate) {
            return sharedAffWorkPlaceHisAdapter.getAffWorkPlaceHis(employeeId, standardDate).orElse(null);
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
        public PredetemineTimeSetting getPredetermineTimeSetting(WorkTimeCode wktmCd) {
            return predetemineTimeSettingRepository.findByWorkTimeCode(companyId, wktmCd.v()).orElse(null);
        }

        @Override
        public FixedWorkSetting getWorkSettingForFixedWork(WorkTimeCode code) {
            return fixedWorkSettingRepository.findByKey(companyId, code.v()).orElse(null);
        }

        @Override
        public FlowWorkSetting getWorkSettingForFlowWork(WorkTimeCode code) {
            return flowWorkSettingRepository.find(companyId, code.v()).orElse(null);
        }

        @Override
        public FlexWorkSetting getWorkSettingForFlexWork(WorkTimeCode code) {
            return flexWorkSettingRepository.find(companyId, code.v()).orElse(null);
        }

        @Override
        public List<EmpMedicalWorkStyleHistoryItem> getEmpMedicalWorkStyleHistoryItem(List<String> listEmp, GeneralDate referenceDate) {
            return empMedicalWorkStyleHistoryRepo.get(listEmp, referenceDate);
        }

        @Override
        public List<NurseClassification> getListCompanyNurseCategory() {
            return nurseClassificationRepo.getListCompanyNurseCategory(AppContexts.user().companyId());
        }
    }
}
