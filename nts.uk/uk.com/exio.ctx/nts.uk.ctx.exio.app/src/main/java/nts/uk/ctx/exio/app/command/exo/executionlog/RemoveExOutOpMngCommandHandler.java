package nts.uk.ctx.exio.app.command.exo.executionlog;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.exio.dom.exo.executionlog.ExOutOpMngRepository;

@Stateless
@Transactional
public class RemoveExOutOpMngCommandHandler extends CommandHandler<ExOutOpMngCommandDelete>
{
    
    @Inject
    private ExOutOpMngRepository repository;
    
    @Override
    protected void handle(CommandHandlerContext<ExOutOpMngCommandDelete> context) {
        String exOutProId = context.getCommand().getExOutProId();
        repository.remove(exOutProId);
    }
}
