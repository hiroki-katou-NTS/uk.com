package nts.uk.ctx.exio.app.command.exo.datafomat;

import javax.ejb.Stateless;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;


@Stateless
@Transactional
public class UpdateChacDataFmSetCommandHandler extends CommandHandler<ChacDataFmSetCommand>
{
    

    
    @Override
    protected void handle(CommandHandlerContext<ChacDataFmSetCommand> context) {
       
    
    }
}
