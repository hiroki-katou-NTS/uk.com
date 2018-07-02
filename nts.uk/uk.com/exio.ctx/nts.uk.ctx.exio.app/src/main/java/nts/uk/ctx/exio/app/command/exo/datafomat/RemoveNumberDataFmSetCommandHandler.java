package nts.uk.ctx.exio.app.command.exo.datafomat;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.exio.dom.exo.datafomat.NumberDataFmSetRepository;

@Stateless
@Transactional
public class RemoveNumberDataFmSetCommandHandler extends CommandHandler<NumberDataFmSetCommand>
{
    
    @Inject
    private NumberDataFmSetRepository repository;
    
    @Override
    protected void handle(CommandHandlerContext<NumberDataFmSetCommand> context) {
        repository.remove();
    }
}
