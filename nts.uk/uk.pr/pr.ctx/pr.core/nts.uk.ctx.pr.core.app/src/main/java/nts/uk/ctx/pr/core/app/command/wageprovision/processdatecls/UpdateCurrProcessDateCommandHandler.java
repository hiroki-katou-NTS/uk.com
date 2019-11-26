package nts.uk.ctx.pr.core.app.command.wageprovision.processdatecls;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.pr.core.dom.wageprovision.processdatecls.CurrProcessDate;
import nts.uk.ctx.pr.core.dom.wageprovision.processdatecls.CurrProcessDateRepository;

@Stateless
@Transactional
public class UpdateCurrProcessDateCommandHandler extends CommandHandler<CurrProcessDateCommand>
{
    
    @Inject
    private CurrProcessDateRepository repository;
    
    @Override
    protected void handle(CommandHandlerContext<CurrProcessDateCommand> context) {
        CurrProcessDateCommand updateCommand = context.getCommand();
        repository.update(new CurrProcessDate(updateCommand.getCid(), updateCommand.getProcessCateNo(), updateCommand.getGiveCurrTreatYear()));
    
    }
}
