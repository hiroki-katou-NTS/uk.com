package nts.uk.ctx.at.record.app.command.monthly.agreement.monthlyresult.specialprovision;

import lombok.AllArgsConstructor;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerWithResult;
import nts.arc.time.GeneralDate;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.record.dom.adapter.classification.affiliate.AffClassificationSidImport;
import nts.uk.ctx.at.record.dom.adapter.employment.SyEmploymentImport;
import nts.uk.ctx.at.record.dom.adapter.personempbasic.EmployeeInfor;
import nts.uk.ctx.at.record.dom.adapter.personempbasic.PersonEmpBasicInfoAdapter;
import nts.uk.ctx.at.record.dom.monthly.agreement.approver.AnnualAppUpdate;
import nts.uk.ctx.at.record.dom.monthly.agreement.approver.AppCreationResult;
import nts.uk.ctx.at.record.dom.monthly.agreement.monthlyresult.specialprovision.ReasonsForAgreement;
import nts.uk.ctx.at.record.dom.monthly.agreement.monthlyresult.specialprovision.SpecialProvisionsOfAgreement;
import nts.uk.ctx.at.record.dom.monthly.agreement.monthlyresult.specialprovision.SpecialProvisionsOfAgreementRepo;
import nts.uk.ctx.at.record.dom.require.RecordDomRequireService;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.AgreementTimeOfClassification;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.AgreementTimeOfCompany;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.AgreementTimeOfEmployment;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.AgreementTimeOfWorkPlace;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.enums.LaborSystemtAtr;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.oneyear.AgreementOneYearTime;
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
 * 36協定特別条項の適用申請を更新登録する（年間）
 *
 * @author Le Huu Dat
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class ApplyAppSpecialProvisionYearCommandHandler
        extends CommandHandlerWithResult<List<ApplyAppSpecialProvisionYearCommand>, List<ErrorResultDto>> {

    @Inject
    private RecordDomRequireService requireService;
    @Inject
    private SpecialProvisionsOfAgreementRepo specialProvisionsOfAgreementRepo;
    @Inject
    private PersonEmpBasicInfoAdapter personEmpBasicInfoAdapter;

    @Override
    protected List<ErrorResultDto> handle(CommandHandlerContext<List<ApplyAppSpecialProvisionYearCommand>> context) {
        String cid = AppContexts.user().companyId();
        RequireImpl require = new RequireImpl(requireService.createRequire(), specialProvisionsOfAgreementRepo);
        List<ApplyAppSpecialProvisionYearCommand> commands = context.getCommand();
        List<ErrorResultDto> errorResults = new ArrayList<>();
        for (ApplyAppSpecialProvisionYearCommand command : commands) {
            AppCreationResult result = AnnualAppUpdate.update(require, cid, command.getApplicantId(),
                    new AgreementOneYearTime(command.getOneYearTime()),
                    new ReasonsForAgreement(command.getReason()));
            if (result.getAtomTask().isPresent()){
                transaction.execute(result.getAtomTask().get());
            }
            // get errors
            List<ExcessErrorContentDto> errors = result.getErrorInfo().stream().map(ExcessErrorContentDto::new).collect(Collectors.toList());
            if (!CollectionUtil.isEmpty(errors)) {
                errorResults.add(new ErrorResultDto(result.getEmpId(),
                        null, null, errors));
            }
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
    private class RequireImpl implements AnnualAppUpdate.Require {

        private RecordDomRequireService.Require require;
        private SpecialProvisionsOfAgreementRepo specialProvisionsOfAgreementRepo;

        @Override
        public Optional<SpecialProvisionsOfAgreement> getApp(String applicantId) {
            return specialProvisionsOfAgreementRepo.getByAppId(applicantId);
        }

        @Override
        public void updateApp(SpecialProvisionsOfAgreement app) {
            specialProvisionsOfAgreementRepo.update(app);
        }

        @Override
        public Optional<WorkingConditionItem> workingConditionItem(String employeeId, GeneralDate baseDate) {
            return this.require.workingConditionItem(employeeId, baseDate);
        }

        @Override
        public Optional<AgreementUnitSetting> agreementUnitSetting(String companyId) {
            return this.require.agreementUnitSetting(companyId);
        }

        @Override
        public Optional<AffClassificationSidImport> affEmployeeClassification(String companyId, String employeeId, GeneralDate baseDate) {
            return this.require.affEmployeeClassification(companyId, employeeId, baseDate);
        }

        @Override
        public Optional<AgreementTimeOfClassification> agreementTimeOfClassification(String companyId, LaborSystemtAtr laborSystemAtr, String classificationCode) {
            return this.require.agreementTimeOfClassification(companyId, laborSystemAtr, classificationCode);
        }

        @Override
        public List<String> getCanUseWorkplaceForEmp(String companyId, String employeeId, GeneralDate baseDate) {
            return this.require.getCanUseWorkplaceForEmp(companyId, employeeId, baseDate);
        }

        @Override
        public Optional<AgreementTimeOfWorkPlace> agreementTimeOfWorkPlace(String workplaceId, LaborSystemtAtr laborSystemAtr) {
            return this.require.agreementTimeOfWorkPlace(workplaceId, laborSystemAtr);
        }

        @Override
        public Optional<SyEmploymentImport> employment(String companyId, String employeeId, GeneralDate baseDate) {
            return this.require.employment(companyId, employeeId, baseDate);
        }

        @Override
        public Optional<AgreementTimeOfEmployment> agreementTimeOfEmployment(String companyId, String employmentCategoryCode, LaborSystemtAtr laborSystemAtr) {
            return this.require.agreementTimeOfEmployment(companyId, employmentCategoryCode, laborSystemAtr);
        }

        @Override
        public Optional<AgreementTimeOfCompany> agreementTimeOfCompany(String companyId, LaborSystemtAtr laborSystemAtr) {
            return this.require.agreementTimeOfCompany(companyId, laborSystemAtr);
        }
    }
}
