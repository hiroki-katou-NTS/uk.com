package nts.uk.ctx.pr.core.app.command.wageprovision.processdatecls;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.pr.core.dom.wageprovision.processdatecls.PerProcessClsSetRepository;

import javax.transaction.Transactional;

@Stateless
@Transactional
public class RemovePerProcessClsSetCommandHandler extends CommandHandler<PerProcessClsSetCommand>
{
    
    @Inject
    private PerProcessClsSetRepository repository;
    
    @Override
    protected void handle(CommandHandlerContext<PerProcessClsSetCommand> context) {
        String companyId = context.getCommand().getCid();
        int processCateNo = context.getCommand().getProcessCateNo();
        repository.remove(companyId, processCateNo);
    }
}
