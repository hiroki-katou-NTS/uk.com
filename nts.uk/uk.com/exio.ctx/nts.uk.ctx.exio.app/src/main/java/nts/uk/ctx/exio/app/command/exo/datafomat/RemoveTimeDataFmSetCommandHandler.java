package nts.uk.ctx.exio.app.command.exo.datafomat;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.exio.dom.exo.datafomat.TimeDataFmSetRepository;

@Stateless
@Transactional
public class RemoveTimeDataFmSetCommandHandler extends CommandHandler<TimeDataFmSetCommand>
{
    
    @Inject
    private TimeDataFmSetRepository repository;
    
    @Override
    protected void handle(CommandHandlerContext<TimeDataFmSetCommand> context) {
        repository.remove();
    }
}
