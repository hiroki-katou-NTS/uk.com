package nts.uk.ctx.pr.core.app.command.wageprovision.individualwagecontract;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.pr.core.dom.wageprovision.individualwagecontract.SalIndAmount;
import nts.uk.ctx.pr.core.dom.wageprovision.individualwagecontract.SalIndAmountHisRepository;

@Stateless
@Transactional
public class UpdateSalIndAmountCommandHandler extends CommandHandler<SalIndAmountCommand>
{
    
    @Inject
    private SalIndAmountHisRepository repository;
    
    @Override
    protected void handle(CommandHandlerContext<SalIndAmountCommand> context) {
        SalIndAmountCommand command = context.getCommand();
        repository.updateAmount(new SalIndAmount(command.getHistoryId(), command.getAmountOfMoney()));
    }
}
