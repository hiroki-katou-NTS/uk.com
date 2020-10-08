package nts.uk.ctx.at.record.app.command.monthly.agreement.monthlyresult.specialprovision;

import lombok.AllArgsConstructor;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.task.tran.AtomTask;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.record.dom.monthly.agreement.approver.AppApproval;
import nts.uk.ctx.at.record.dom.monthly.agreement.monthlyresult.specialprovision.AgreementApprovalComments;
import nts.uk.ctx.at.record.dom.monthly.agreement.monthlyresult.specialprovision.ApprovalStatus;
import nts.uk.ctx.at.record.dom.monthly.agreement.monthlyresult.specialprovision.SpecialProvisionsOfAgreement;
import nts.uk.ctx.at.shared.dom.common.Year;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.exceptsetting.AgreementMonthSetting;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.exceptsetting.AgreementYearSetting;

import java.util.List;
import java.util.Optional;

/**
 * 36協定特別条項の適用申請の承認/否認を行う（36承認者）
 *
 * @author Le Huu Dat
 */
public class ApproveAppSpecialProvisionApproverCommandHandler extends CommandHandler<List<ApproveAppSpecialProvisionApproverCommand>> {
    @Override
    protected void handle(CommandHandlerContext<List<ApproveAppSpecialProvisionApproverCommand>> context) {
        RequireImpl require = new RequireImpl();
        List<ApproveAppSpecialProvisionApproverCommand> commands = context.getCommand();
        for (ApproveAppSpecialProvisionApproverCommand command : commands) {
            Optional<AgreementApprovalComments> approvalComment = Optional.empty();
            if (command.getApprovalComment() != null) {
                approvalComment = Optional.of(new AgreementApprovalComments(command.getApprovalComment()));
            }
            AtomTask persist = AppApproval.change(require, command.getApplicantId(), command.getApproverId(),
                    EnumAdaptor.valueOf(command.getApprovalStatus(), ApprovalStatus.class), approvalComment);
            transaction.execute(persist);
        }

    }

    @AllArgsConstructor
    private class RequireImpl implements AppApproval.Require {

        @Override
        public Optional<SpecialProvisionsOfAgreement> getApp(String applicantId) {
            return Optional.empty();
        }

        @Override
        public Optional<AgreementMonthSetting> getYearMonthSetting(String empId, YearMonth yearMonth) {
            return Optional.empty();
        }

        @Override
        public Optional<AgreementYearSetting> getYearSetting(String empId, Year year) {
            return Optional.empty();
        }

        @Override
        public void updateApp(SpecialProvisionsOfAgreement app) {

        }

        @Override
        public void addYearMonthSetting(AgreementMonthSetting setting) {

        }

        @Override
        public void updateYearMonthSetting(AgreementMonthSetting setting) {

        }

        @Override
        public void addYearSetting(AgreementYearSetting setting) {

        }

        @Override
        public void updateYearSetting(AgreementYearSetting setting) {

        }
    }
}
