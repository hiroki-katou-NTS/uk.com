package nts.uk.ctx.at.request.app.command.application.workchange;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.request.dom.setting.request.application.workchange.IAppWorkChangeSetRepository;

@Stateless
@Transactional
public class RemoveAppWorkChangeSetCommandHandler extends CommandHandler<AppWorkChangeSetCommand>
{
    
    @Inject
    private IAppWorkChangeSetRepository repository;
    
    @Override
    protected void handle(CommandHandlerContext<AppWorkChangeSetCommand> context) {
        String cid = context.getCommand().getCid();
        repository.remove(cid);
    }
}
