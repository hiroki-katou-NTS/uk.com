package nts.uk.ctx.at.record.app.command.monthly.agreement.monthlyresult.specialprovision;

import lombok.AllArgsConstructor;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.task.tran.AtomTask;
import nts.arc.time.YearMonth;
import nts.arc.time.calendar.Year;
import nts.uk.ctx.at.record.dom.monthly.agreement.approver.AppApproval;
import nts.uk.ctx.at.record.dom.monthly.agreement.monthlyresult.specialprovision.AgreementApprovalComments;
import nts.uk.ctx.at.record.dom.monthly.agreement.monthlyresult.specialprovision.ApprovalStatus;
import nts.uk.ctx.at.record.dom.monthly.agreement.monthlyresult.specialprovision.SpecialProvisionsOfAgreement;
import nts.uk.ctx.at.record.dom.monthly.agreement.monthlyresult.specialprovision.SpecialProvisionsOfAgreementRepo;
import nts.uk.ctx.at.record.dom.standardtime.repository.AgreementMonthSettingRepository;
import nts.uk.ctx.at.record.dom.standardtime.repository.AgreementYearSettingRepository;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.exceptsetting.AgreementMonthSetting;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.exceptsetting.AgreementYearSetting;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

/**
 * 36協定特別条項の適用申請の一括承認を行う（36承認者）
 *
 * @author Le Huu Dat
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class BulkApproveAppSpecialProvisionApproverCommandHandler extends CommandHandler<List<BulkApproveAppSpecialProvisionApproverCommand>> {

    @Inject
    private SpecialProvisionsOfAgreementRepo specialProvisionsOfAgreementRepo;
    @Inject
    private AgreementMonthSettingRepository agreementMonthSettingRepo;
    @Inject
    private AgreementYearSettingRepository agreementYearSettingRepo;

    @Override
    protected void handle(CommandHandlerContext<List<BulkApproveAppSpecialProvisionApproverCommand>> context) {
        RequireImpl require = new RequireImpl(specialProvisionsOfAgreementRepo, agreementMonthSettingRepo,
                agreementYearSettingRepo);
        List<BulkApproveAppSpecialProvisionApproverCommand> commands = context.getCommand();
        for (BulkApproveAppSpecialProvisionApproverCommand command : commands) {
            Optional<AgreementApprovalComments> approvalComment = Optional.empty();
            if (command.getApprovalComment() != null) {
                approvalComment = Optional.of(new AgreementApprovalComments(command.getApprovalComment()));
            }
            AtomTask persist = AppApproval.change(require, command.getApplicantId(), command.getApproverId(),
                    ApprovalStatus.APPROVED, approvalComment);
            transaction.execute(persist);
        }

    }

    @AllArgsConstructor
    private class RequireImpl implements AppApproval.Require {

        private SpecialProvisionsOfAgreementRepo specialProvisionsOfAgreementRepo;
        private AgreementMonthSettingRepository agreementMonthSettingRepo;
        private AgreementYearSettingRepository agreementYearSettingRepo;

        @Override
        public Optional<SpecialProvisionsOfAgreement> getApp(String applicantId) {
            return specialProvisionsOfAgreementRepo.getByAppId(applicantId);
        }

        @Override
        public Optional<AgreementMonthSetting> getYearMonthSetting(String empId, YearMonth yearMonth) {
            return agreementMonthSettingRepo.getByEmployeeIdAndYm(empId, yearMonth);
        }

        @Override
        public Optional<AgreementYearSetting> getYearSetting(String empId, Year year) {
            // return agreementYearSettingRepo.;
            return Optional.empty();
        }

        @Override
        public void updateApp(SpecialProvisionsOfAgreement app) {
            specialProvisionsOfAgreementRepo.update(app);
        }

        @Override
        public void addYearMonthSetting(AgreementMonthSetting setting) {
            agreementMonthSettingRepo.add(setting);
        }

        @Override
        public void updateYearMonthSetting(AgreementMonthSetting setting) {
            agreementMonthSettingRepo.add(setting);
        }

        @Override
        public void addYearSetting(AgreementYearSetting setting) {
            agreementYearSettingRepo.add(setting);
        }

        @Override
        public void updateYearSetting(AgreementYearSetting setting) {
            agreementYearSettingRepo.update(setting);
        }
    }
}
