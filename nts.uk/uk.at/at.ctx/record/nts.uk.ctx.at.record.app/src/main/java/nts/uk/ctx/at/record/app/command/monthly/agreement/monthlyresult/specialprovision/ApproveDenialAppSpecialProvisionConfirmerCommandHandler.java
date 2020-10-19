package nts.uk.ctx.at.record.app.command.monthly.agreement.monthlyresult.specialprovision;

import lombok.AllArgsConstructor;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.task.tran.AtomTask;
import nts.uk.ctx.at.record.dom.monthly.agreement.approver.AppConfirmation;
import nts.uk.ctx.at.record.dom.monthly.agreement.monthlyresult.specialprovision.ConfirmationStatus;
import nts.uk.ctx.at.record.dom.monthly.agreement.monthlyresult.specialprovision.SpecialProvisionsOfAgreement;
import nts.uk.ctx.at.record.dom.monthly.agreement.monthlyresult.specialprovision.SpecialProvisionsOfAgreementRepo;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

/**
 * 36協定特別条項の適用申請の承認/否認を行う（従業員代表）
 *
 * @author Le Huu Dat
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class ApproveDenialAppSpecialProvisionConfirmerCommandHandler extends CommandHandler<List<ApproveDenialAppSpecialProvisionConfirmerCommand>> {

    @Inject
    private SpecialProvisionsOfAgreementRepo specialProvisionsOfAgreementRepo;

    @Override
    protected void handle(CommandHandlerContext<List<ApproveDenialAppSpecialProvisionConfirmerCommand>> context) {
        RequireImpl require = new RequireImpl(specialProvisionsOfAgreementRepo);
        List<ApproveDenialAppSpecialProvisionConfirmerCommand> commands = context.getCommand();
        for (ApproveDenialAppSpecialProvisionConfirmerCommand command : commands) {
            AtomTask persist = AppConfirmation.change(require, command.getApplicantId(), command.getConfirmerId(),
                    EnumAdaptor.valueOf(command.getConfirmStatus(), ConfirmationStatus.class));
            transaction.execute(persist);
        }
    }

    @AllArgsConstructor
    private class RequireImpl implements AppConfirmation.Require {

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
