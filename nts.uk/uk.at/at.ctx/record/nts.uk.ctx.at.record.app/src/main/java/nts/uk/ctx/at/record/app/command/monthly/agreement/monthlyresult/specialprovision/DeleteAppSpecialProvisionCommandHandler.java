package nts.uk.ctx.at.record.app.command.monthly.agreement.monthlyresult.specialprovision;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.record.dom.monthly.agreement.monthlyresult.specialprovision.SpecialProvisionsOfAgreement;
import nts.uk.ctx.at.record.dom.monthly.agreement.monthlyresult.specialprovision.SpecialProvisionsOfAgreementRepo;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

/**
 * 36協定特別条項の適用申請を削除する
 *
 * @author Le Huu Dat
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class DeleteAppSpecialProvisionCommandHandler extends CommandHandler<List<DeleteAppSpecialProvisionCommand>> {
    @Inject
    private SpecialProvisionsOfAgreementRepo specialProvisionsOfAgreementRepo;

    @Override
    protected void handle(CommandHandlerContext<List<DeleteAppSpecialProvisionCommand>> context) {
        List<DeleteAppSpecialProvisionCommand> commands = context.getCommand();
        for (DeleteAppSpecialProvisionCommand command : commands) {
            Optional<SpecialProvisionsOfAgreement> appOpt = specialProvisionsOfAgreementRepo.getByAppId(command.getApplicantId());
            appOpt.ifPresent(specialProvisionsOfAgreement -> specialProvisionsOfAgreementRepo.delete(specialProvisionsOfAgreement));
        }
    }
}
