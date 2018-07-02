package nts.uk.ctx.exio.app.command.exo.datafomat;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.exio.dom.exo.datafomat.DateFormatSetRepository;

@Stateless
@Transactional
public class RemoveDateFormatSetCommandHandler extends CommandHandler<DateFormatSetCommand>
{
    
    @Inject
    private DateFormatSetRepository repository;
    
    @Override
    protected void handle(CommandHandlerContext<DateFormatSetCommand> context) {
        repository.remove();
    }
}
