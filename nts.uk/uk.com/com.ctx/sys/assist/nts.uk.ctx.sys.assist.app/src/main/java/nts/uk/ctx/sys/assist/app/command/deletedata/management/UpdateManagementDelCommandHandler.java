package nts.uk.ctx.sys.assist.app.command.deletedata.management;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.sys.assist.dom.deletedata.ManagementDeletionRepository;
import nts.uk.ctx.sys.assist.dom.deletedata.OperatingCondition;

@Stateless
@Transactional
public class UpdateManagementDelCommandHandler extends CommandHandler<ManagementDelCommand>
{
    
    @Inject
    private ManagementDeletionRepository repository;
    
    @Override
    protected void handle(CommandHandlerContext<ManagementDelCommand> context) {
    	ManagementDelCommand updateCommand = context.getCommand();
        repository.setInterruptDeleting(updateCommand.getDelId(),updateCommand.getIsInterruptedFlg(),
        		OperatingCondition.valueOf(updateCommand.getOperatingCondition()));
    }
}
