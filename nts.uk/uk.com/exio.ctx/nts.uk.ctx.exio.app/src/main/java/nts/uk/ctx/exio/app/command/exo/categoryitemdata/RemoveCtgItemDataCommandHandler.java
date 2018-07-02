package nts.uk.ctx.exio.app.command.exo.categoryitemdata;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.exio.dom.exo.categoryitemdata.CtgItemDataRepository;

@Stateless
@Transactional
public class RemoveCtgItemDataCommandHandler extends CommandHandler<CtgItemDataCommand>
{
    
    @Inject
    private CtgItemDataRepository repository;
    
    @Override
    protected void handle(CommandHandlerContext<CtgItemDataCommand> context) {
        repository.remove();
    }
}
