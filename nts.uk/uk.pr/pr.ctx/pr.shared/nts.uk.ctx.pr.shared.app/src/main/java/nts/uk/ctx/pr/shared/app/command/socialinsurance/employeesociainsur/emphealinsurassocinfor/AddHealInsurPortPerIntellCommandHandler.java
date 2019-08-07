package nts.uk.ctx.pr.shared.app.command.socialinsurance.employeesociainsur.emphealinsurassocinfor;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.emphealinsurassocinfor.HealInsurPortPerIntellRepository;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

@Stateless
@Transactional
public class AddHealInsurPortPerIntellCommandHandler extends CommandHandler<HealInsurPortPerIntellCommand>
{
    
    @Inject
    private HealInsurPortPerIntellRepository repository;
    
    @Override
    protected void handle(CommandHandlerContext<HealInsurPortPerIntellCommand> context) {

    }
}
