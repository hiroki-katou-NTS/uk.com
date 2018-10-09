package nts.uk.ctx.exio.app.command.exo.categoryitemdata;

import javax.ejb.Stateless;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;



@Stateless
@Transactional
public class UpdateCtgItemDataCommandHandler extends CommandHandler<CtgItemDataCommand>
{
    

    
    @Override
    protected void handle(CommandHandlerContext<CtgItemDataCommand> context) {
        
    }
}
