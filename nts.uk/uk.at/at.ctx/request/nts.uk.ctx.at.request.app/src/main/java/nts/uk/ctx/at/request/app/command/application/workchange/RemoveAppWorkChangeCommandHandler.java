package nts.uk.ctx.at.request.app.command.application.workchange;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.request.dom.application.workchange.IAppWorkChangeRepository;

@Stateless
@Transactional
public class RemoveAppWorkChangeCommandHandler extends CommandHandler<AppWorkChangeCommand>
{
    
    @Inject
    private IAppWorkChangeRepository repository;
    
    @Override
    protected void handle(CommandHandlerContext<AppWorkChangeCommand> context) {
    	AppWorkChangeCommand command = context.getCommand();
        repository.remove(command.getCid(), command.getAppId());
    }
}
