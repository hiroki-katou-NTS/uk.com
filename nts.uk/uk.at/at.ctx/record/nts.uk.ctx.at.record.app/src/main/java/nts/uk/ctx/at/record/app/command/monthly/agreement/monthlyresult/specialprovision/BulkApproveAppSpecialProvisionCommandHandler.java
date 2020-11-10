package nts.uk.ctx.at.record.app.command.monthly.agreement.monthlyresult.specialprovision;

import lombok.AllArgsConstructor;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.task.tran.AtomTask;
import nts.arc.time.YearMonth;
import nts.arc.time.calendar.Year;
import nts.uk.ctx.at.record.dom.monthly.agreement.approver.AppApproval;
import nts.uk.ctx.at.record.dom.monthly.agreement.approver.AppConfirmation;
import nts.uk.ctx.at.record.dom.monthly.agreement.monthlyresult.specialprovision.*;
import nts.uk.ctx.at.record.dom.standardtime.repository.AgreementMonthSettingRepository;
import nts.uk.ctx.at.record.dom.standardtime.repository.AgreementYearSettingRepository;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.exceptsetting.AgreementMonthSetting;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.exceptsetting.AgreementYearSetting;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * 36協定特別条項の適用申請の一括承認を行う
 *
 * @author Le Huu Dat
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class BulkApproveAppSpecialProvisionCommandHandler extends CommandHandler<BulkApproveAppSpecialProvisionCommand> {

    @Inject
    private SpecialProvisionsOfAgreementRepo specialProvisionsOfAgreementRepo;
    @Inject
    private AgreementMonthSettingRepository agreementMonthSettingRepo;
    @Inject
    private AgreementYearSettingRepository agreementYearSettingRepo;

    @Override
    protected void handle(CommandHandlerContext<BulkApproveAppSpecialProvisionCommand> context) {
        String sid = AppContexts.user().employeeId();
        BulkApproveAppSpecialProvisionCommand command = context.getCommand();

        List<AtomTask> approveTasks = new ArrayList<>();
        ApprovalRequireImpl approvalRequire = new ApprovalRequireImpl(specialProvisionsOfAgreementRepo, agreementMonthSettingRepo, agreementYearSettingRepo);
        List<BulkApproveAppSpecialProvisionApproverCommand> approvers = command.getApprovers();
        for (BulkApproveAppSpecialProvisionApproverCommand cmd : approvers) {
            Optional<AgreementApprovalComments> approvalComment = Optional.empty();
            if (cmd.getApprovalComment() != null) {
                approvalComment = Optional.of(new AgreementApprovalComments(cmd.getApprovalComment()));
            }
            // 申請を承認する
            AtomTask persist = AppApproval.change(approvalRequire, cmd.getApplicantId(), sid,
                    ApprovalStatus.APPROVED, approvalComment);
            approveTasks.add(persist);
        }
        // execute
        approveTasks.forEach(x -> transaction.execute(x));

        List<AtomTask> confirmationTasks = new ArrayList<>();
        ConfirmationRequireImpl require = new ConfirmationRequireImpl(specialProvisionsOfAgreementRepo);
        List<BulkApproveAppSpecialProvisionConfirmerCommand> confirmers = command.getConfirmers();
        for (BulkApproveAppSpecialProvisionConfirmerCommand cmd : confirmers) {
            // 申請を確認する
            AtomTask persist = AppConfirmation.change(require, cmd.getApplicantId(), sid,
                    ConfirmationStatus.CONFIRMED);
            confirmationTasks.add(persist);
        }
        // execute
        confirmationTasks.forEach(x -> transaction.execute(x));
    }

    @AllArgsConstructor
    private class ApprovalRequireImpl implements AppApproval.Require {

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
            return agreementYearSettingRepo.findBySidAndYear(empId, year.v());
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
            agreementMonthSettingRepo.update(setting);
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

    @AllArgsConstructor
    private class ConfirmationRequireImpl implements AppConfirmation.Require {

        private SpecialProvisionsOfAgreementRepo specialProvisionsOfAgreementRepo;

        @Override
        public Optional<SpecialProvisionsOfAgreement> getApp(String applicantId) {
            return specialProvisionsOfAgreementRepo.getByAppId(applicantId);
        }

        @Override
        public void updateApp(SpecialProvisionsOfAgreement app) {
            specialProvisionsOfAgreementRepo.update(app);
        }
    }
}
