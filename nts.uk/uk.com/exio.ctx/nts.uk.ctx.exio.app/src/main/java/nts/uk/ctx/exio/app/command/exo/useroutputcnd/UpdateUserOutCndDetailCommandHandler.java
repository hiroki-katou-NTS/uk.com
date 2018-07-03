package nts.uk.ctx.exio.app.command.exo.useroutputcnd;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.exio.dom.exo.useroutputcnd.UserOutCndDetail;
import nts.uk.ctx.exio.dom.exo.useroutputcnd.UserOutCndDetailRepository;

@Stateless
@Transactional
public class UpdateUserOutCndDetailCommandHandler extends CommandHandler<UserOutCndDetailCommand>
{
    
    @Inject
    private UserOutCndDetailRepository repository;
    
    @Override
    protected void handle(CommandHandlerContext<UserOutCndDetailCommand> context) {
        UserOutCndDetailCommand updateCommand = context.getCommand();
        repository.update(new UserOutCndDetail(updateCommand.getCndSql(), updateCommand.getCndSetCd(), updateCommand.getUserId()));
    
    }
}
