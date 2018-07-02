package nts.uk.ctx.exio.app.command.exo.datafomat;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.exio.dom.exo.datafomat.ChacDataFmSetRepository;

@Stateless
@Transactional
public class RemoveChacDataFmSetCommandHandler extends CommandHandler<ChacDataFmSetCommand>
{
    
    @Inject
    private ChacDataFmSetRepository repository;
    
    @Override
    protected void handle(CommandHandlerContext<ChacDataFmSetCommand> context) {
        repository.remove();
    }
}
