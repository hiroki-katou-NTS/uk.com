package nts.uk.ctx.exio.app.command.exo.datafomat;

import javax.ejb.Stateless;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;


@Stateless
@Transactional
public class AddNumberDataFmSetCommandHandler extends CommandHandler<NumberDataFmSetCommand>
{
    
    @Override
    protected void handle(CommandHandlerContext<NumberDataFmSetCommand> context) {
      
    
    }
}
