package nts.uk.ctx.pr.core.app.command.salary;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.pr.core.dom.salary.CurrProcessDate;
import nts.uk.ctx.pr.core.dom.salary.CurrProcessDateRepository;

@Stateless
@Transactional
public class AddCurrProcessDateCommandHandler extends CommandHandler<CurrProcessDateCommand>
{
    
    @Inject
    private CurrProcessDateRepository repository;
    
    @Override
    protected void handle(CommandHandlerContext<CurrProcessDateCommand> context) {
        CurrProcessDateCommand addCommand = context.getCommand();
        repository.add(new CurrProcessDate(addCommand.getCid(), addCommand.getProcessCateNo(), addCommand.getGiveCurrTreatYear()));
    
    }
}
