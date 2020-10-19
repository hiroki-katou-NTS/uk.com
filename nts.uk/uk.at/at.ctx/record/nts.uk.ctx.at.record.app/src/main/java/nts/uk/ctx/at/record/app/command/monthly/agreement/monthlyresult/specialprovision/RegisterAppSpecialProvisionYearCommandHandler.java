package nts.uk.ctx.at.record.app.command.monthly.agreement.monthlyresult.specialprovision;

import lombok.AllArgsConstructor;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerWithResult;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.adapter.classification.affiliate.AffClassificationAdapter;
import nts.uk.ctx.at.record.dom.adapter.classification.affiliate.AffClassificationSidImport;
import nts.uk.ctx.at.record.dom.adapter.employment.SyEmploymentAdapter;
import nts.uk.ctx.at.record.dom.adapter.employment.SyEmploymentImport;
import nts.uk.ctx.at.record.dom.adapter.personempbasic.EmployeeInfor;
import nts.uk.ctx.at.record.dom.adapter.personempbasic.PersonEmpBasicInfoAdapter;
import nts.uk.ctx.at.record.dom.adapter.workplace.SWkpHistRcImported;
import nts.uk.ctx.at.record.dom.adapter.workplace.SyWorkplaceAdapter;
import nts.uk.ctx.at.record.dom.adapter.workplace.affiliate.AffWorkplaceAdapter;
import nts.uk.ctx.at.record.dom.monthly.agreement.approver.*;
import nts.uk.ctx.at.record.dom.monthly.agreement.monthlyresult.approveregister.UnitOfApprover;
import nts.uk.ctx.at.record.dom.monthly.agreement.monthlyresult.approveregister.UnitOfApproverRepo;
import nts.uk.ctx.at.record.dom.monthly.agreement.monthlyresult.specialprovision.SpecialProvisionsOfAgreement;
import nts.uk.ctx.at.record.dom.monthly.agreement.monthlyresult.specialprovision.SpecialProvisionsOfAgreementRepo;
import nts.uk.ctx.at.record.dom.require.RecordDomRequireService;
import nts.uk.ctx.at.record.dom.standardtime.repository.*;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.AgreementTimeOfClassification;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.AgreementTimeOfCompany;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.AgreementTimeOfEmployment;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.AgreementTimeOfWorkPlace;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.enums.LaborSystemtAtr;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.setting.AgreementUnitSetting;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItem;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 36協定特別条項の適用申請の登録を行う（年間）
 *
 * @author Le Huu Dat
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class RegisterAppSpecialProvisionYearCommandHandler
        extends CommandHandlerWithResult<List<RegisterAppSpecialProvisionYearCommand>, List<ErrorResultDto>> {

    @Inject
    private RecordDomRequireService requireService;
    @Inject
    private SpecialProvisionsOfAgreementRepo specialProvisionsOfAgreementRepo;
    @Inject
    private Approver36AgrByCompanyRepo approver36AgrByCompanyRepo;
    @Inject
    private UnitOfApproverRepo unitOfApproverRepo;
    @Inject
    private SyWorkplaceAdapter syWorkplaceAdapter;
    @Inject
    private Approver36AgrByWorkplaceRepo approver36AgrByWorkplaceRepo;
    @Inject
    private AgreementUnitSettingRepository agreementUnitSettingRepository;
    @Inject
    private AffClassificationAdapter affClassificationAdapter;
    @Inject
    private AgreementTimeOfClassificationRepository agreementTimeOfClassificationRepo;
    @Inject
    private AffWorkplaceAdapter affWorkplaceAdapter;
    @Inject
    private AgreementTimeOfWorkPlaceRepository agreementTimeWorkPlaceRepo;
    @Inject
    private SyEmploymentAdapter syEmploymentAdapter;
    @Inject
    private AgreementTimeOfEmploymentRepostitory agreementTimeOfEmploymentRepo;
    @Inject
    private AgreementTimeCompanyRepository agreementTimeCompanyRepo;
    @Inject
    private PersonEmpBasicInfoAdapter personEmpBasicInfoAdapter;

    @Override
    protected List<ErrorResultDto> handle(CommandHandlerContext<List<RegisterAppSpecialProvisionYearCommand>> context) {
        String cid = AppContexts.user().companyId();
        RequireImpl require = new RequireImpl(cid, requireService.createRequire(), specialProvisionsOfAgreementRepo,
                approver36AgrByCompanyRepo,
                unitOfApproverRepo, syWorkplaceAdapter, approver36AgrByWorkplaceRepo,
                agreementUnitSettingRepository,
                affClassificationAdapter, agreementTimeOfClassificationRepo,
                affWorkplaceAdapter, agreementTimeWorkPlaceRepo, syEmploymentAdapter,
                agreementTimeOfEmploymentRepo, agreementTimeCompanyRepo);
        List<RegisterAppSpecialProvisionYearCommand> commands = context.getCommand();
        List<ErrorResultDto> errorResults = new ArrayList<>();
        for (RegisterAppSpecialProvisionYearCommand command : commands) {
            // 年間申請を登録する
            AppCreationResult result = AnnualAppCreate.create(require, cid, command.getContent().getEmployeeId(),
                    command.getContent().toAnnualAppContent(), command.getScreenInfo().toScreenDisplayInfo());
            if (result.getAtomTask().isPresent()) {
                transaction.execute(result.getAtomTask().get());
            }
            // get errors
            List<ExcessErrorContentDto> errors = result.getErrorInfo().stream().map(ExcessErrorContentDto::new).collect(Collectors.toList());
            errorResults.add(new ErrorResultDto(command.getContent().getEmployeeId(),
                    null, null, errors));
        }

        // 社員IDから個人社員基本情報を取得
        List<String> employeeIds = errorResults.stream().map(ErrorResultDto::getEmployeeId)
                .distinct().collect(Collectors.toList());
        Map<String, EmployeeInfor> empInfo = personEmpBasicInfoAdapter.getPerEmpBasicInfo(employeeIds)
                .stream().collect(Collectors.toMap(EmployeeInfor::getEmployeeId, c -> c));
        errorResults.forEach(x -> x.mappingEmpInfo(empInfo));

        return errorResults;
    }


    @AllArgsConstructor
    private class RequireImpl implements AnnualAppCreate.Require {

        private String companyId;
        private RecordDomRequireService.Require require;
        private SpecialProvisionsOfAgreementRepo specialProvisionsOfAgreementRepo;
        private Approver36AgrByCompanyRepo approver36AgrByCompanyRepo;
        private UnitOfApproverRepo unitOfApproverRepo;
        private SyWorkplaceAdapter syWorkplaceAdapter;
        private Approver36AgrByWorkplaceRepo approver36AgrByWorkplaceRepo;
        private AgreementUnitSettingRepository agreementUnitSettingRepository;
        private AffClassificationAdapter affClassificationAdapter;
        private AgreementTimeOfClassificationRepository agreementTimeOfClassificationRepo;
        private AffWorkplaceAdapter affWorkplaceAdapter;
        private AgreementTimeOfWorkPlaceRepository agreementTimeWorkPlaceRepo;
        private SyEmploymentAdapter syEmploymentAdapter;
        private AgreementTimeOfEmploymentRepostitory agreementTimeOfEmploymentRepo;
        private AgreementTimeCompanyRepository agreementTimeCompanyRepo;

        @Override
        public void addApp(SpecialProvisionsOfAgreement app) {
            specialProvisionsOfAgreementRepo.insert(app);
        }

        @Override
        public Optional<Approver36AgrByCompany> getApproverHistoryItem(GeneralDate baseDate) {
            return approver36AgrByCompanyRepo.getByCompanyIdAndDate(companyId, baseDate);
        }

        @Override
        public UnitOfApprover getUsageSetting() {
            return unitOfApproverRepo.getByCompanyId(companyId);
        }

        @Override
        public Optional<SWkpHistRcImported> getYourWorkplace(String employeeId, GeneralDate baseDate) {
            return syWorkplaceAdapter.findBySid(employeeId, baseDate);
        }

        @Override
        public Optional<Approver36AgrByWorkplace> getApproveHistoryItem(String workplaceId, GeneralDate baseDate) {
            return approver36AgrByWorkplaceRepo.getByWorkplaceIdAndDate(workplaceId, baseDate);
        }

        @Override
        public List<String> getUpperWorkplace(String workplaceID, GeneralDate date) {
            return affWorkplaceAdapter.getUpperWorkplace(companyId, workplaceID, date);
        }

        @Override
        public Optional<AgreementUnitSetting> agreementUnitSetting(String companyId) {
            return agreementUnitSettingRepository.find(companyId);
        }

        @Override
        public Optional<AffClassificationSidImport> affEmployeeClassification(String companyId, String employeeId, GeneralDate baseDate) {
            return affClassificationAdapter.findByEmployeeId(companyId, employeeId, baseDate);
        }

        @Override
        public Optional<AgreementTimeOfClassification> agreementTimeOfClassification(String companyId, LaborSystemtAtr laborSystemAtr, String classificationCode) {
            return agreementTimeOfClassificationRepo.find(companyId, laborSystemAtr, classificationCode);
        }

        @Override
        public List<String> getCanUseWorkplaceForEmp(String companyId, String employeeId, GeneralDate baseDate) {
            return affWorkplaceAdapter.findAffiliatedWorkPlaceIdsToRoot(companyId, employeeId, baseDate);
        }

        @Override
        public Optional<AgreementTimeOfWorkPlace> agreementTimeOfWorkPlace(String workplaceId, LaborSystemtAtr laborSystemAtr) {
            return agreementTimeWorkPlaceRepo.findAgreementTimeOfWorkPlace(workplaceId, laborSystemAtr);
        }

        @Override
        public Optional<SyEmploymentImport> employment(String companyId, String employeeId, GeneralDate baseDate) {
            return syEmploymentAdapter.findByEmployeeId(companyId, employeeId, baseDate);
        }

        @Override
        public Optional<AgreementTimeOfEmployment> agreementTimeOfEmployment(String companyId, String employmentCategoryCode, LaborSystemtAtr laborSystemAtr) {
            return agreementTimeOfEmploymentRepo.find(companyId, employmentCategoryCode, laborSystemAtr);
        }

        @Override
        public Optional<AgreementTimeOfCompany> agreementTimeOfCompany(String companyId, LaborSystemtAtr laborSystemAtr) {
            return agreementTimeCompanyRepo.find(companyId, laborSystemAtr);
        }

        @Override
        public Optional<WorkingConditionItem> workingConditionItem(String employeeId, GeneralDate baseDate) {
            return this.require.workingConditionItem(employeeId, baseDate);
        }
    }
}
