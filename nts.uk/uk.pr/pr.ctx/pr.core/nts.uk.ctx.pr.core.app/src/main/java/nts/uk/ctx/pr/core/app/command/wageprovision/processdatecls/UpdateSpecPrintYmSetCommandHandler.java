package nts.uk.ctx.pr.core.app.command.wageprovision.processdatecls;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.pr.core.dom.wageprovision.processdatecls.SpecPrintYmSet;
import nts.uk.ctx.pr.core.dom.wageprovision.processdatecls.SpecPrintYmSetRepository;

@Stateless
@Transactional
public class UpdateSpecPrintYmSetCommandHandler extends CommandHandler<SpecPrintYmSetCommand>
{
    
    @Inject
    private SpecPrintYmSetRepository repository;
    
    @Override
    protected void handle(CommandHandlerContext<SpecPrintYmSetCommand> context) {
        SpecPrintYmSetCommand updateCommand = context.getCommand();
        repository.update(new SpecPrintYmSet(updateCommand.getCid(), updateCommand.getProcessCateNo(), updateCommand.getProcessDate(), updateCommand.getPrintDate()));
    
    }
}
