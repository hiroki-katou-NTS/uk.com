package nts.uk.ctx.at.record.app.command.monthly.agreement.monthlyresult.specialprovision;

import lombok.AllArgsConstructor;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.task.tran.AtomTask;
import nts.uk.ctx.at.record.dom.monthly.agreement.approver.AppConfirmation;
import nts.uk.ctx.at.record.dom.monthly.agreement.monthlyresult.specialprovision.ConfirmationStatus;
import nts.uk.ctx.at.record.dom.monthly.agreement.monthlyresult.specialprovision.SpecialProvisionsOfAgreement;

import java.util.List;
import java.util.Optional;

/**
 * 36協定特別条項の適用申請の承認/否認を行う（従業員代表）
 *
 * @author Le Huu Dat
 */
public class ApproveAppSpecialProvisionConfirmerCommandHandler extends CommandHandler<List<ApproveAppSpecialProvisionConfirmerCommand>> {
    @Override
    protected void handle(CommandHandlerContext<List<ApproveAppSpecialProvisionConfirmerCommand>> context) {
        RequireImpl require = new RequireImpl();
        List<ApproveAppSpecialProvisionConfirmerCommand> commands = context.getCommand();
        for (ApproveAppSpecialProvisionConfirmerCommand command : commands) {
            AtomTask persist = AppConfirmation.change(require, command.getApplicantId(), command.getConfirmerId(),
                    EnumAdaptor.valueOf(command.getConfirmStatus(), ConfirmationStatus.class));
            transaction.execute(persist);
        }
    }

    @AllArgsConstructor
    private class RequireImpl implements AppConfirmation.Require {

        @Override
        public Optional<SpecialProvisionsOfAgreement> getApp(String applicantId) {
            return Optional.empty();
        }

        @Override
        public void updateApp(SpecialProvisionsOfAgreement app) {

        }
    }
}
