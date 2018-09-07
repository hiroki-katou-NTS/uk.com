package nts.uk.ctx.pr.core.app.command.salary;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.pr.core.dom.salary.SpecPrintYmSet;
import nts.uk.ctx.pr.core.dom.salary.SpecPrintYmSetRepository;

@Stateless
@Transactional
public class AddSpecPrintYmSetCommandHandler extends CommandHandler<SpecPrintYmSetCommand>
{
    
    @Inject
    private SpecPrintYmSetRepository repository;
    
    @Override
    protected void handle(CommandHandlerContext<SpecPrintYmSetCommand> context) {
        SpecPrintYmSetCommand addCommand = context.getCommand();
        repository.add(new SpecPrintYmSet(addCommand.getCid(), addCommand.getProcessCateNo(), addCommand.getProcessDate(), addCommand.getPrintDate()));
    
    }
}
