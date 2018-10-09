package nts.uk.ctx.pr.core.app.command.wageprovision.individualwagecontract;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;

@Stateless
@Transactional
public class UpdateSalIndAmountHisCommandHandler extends CommandHandler<SalIndAmountHisCommand>
{
    
    @Inject
    private SalIndAmountHisRepository repository;
    
    @Override
    protected void handle(CommandHandlerContext<SalIndAmountHisCommand> context) {
        SalIndAmountHisCommand command = context.getCommand();
        repository.update(new SalIndAmountHis(command.get(), command.getPerValCode(), command.getEmpId(), command.getCateIndicator(), command.getPeriod(), command.getPeriod(), command.getSalBonusCate()));
    
    }
}
