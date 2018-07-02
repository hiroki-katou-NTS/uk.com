package nts.uk.ctx.exio.app.command.exo.datafomat;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.exio.dom.exo.datafomat.AwDataFormatSetRepository;

@Stateless
@Transactional
public class RemoveAwDataFormatSetCommandHandler extends CommandHandler<AwDataFormatSetCommand>
{
    
    @Inject
    private AwDataFormatSetRepository repository;
    
    @Override
    protected void handle(CommandHandlerContext<AwDataFormatSetCommand> context) {
        repository.remove();
    }
}
