package nts.uk.ctx.exio.app.command.exo.cdconvert;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.exio.dom.exo.cdconvert.OutputCodeConvertRepository;

@Stateless
@Transactional
public class RemoveOutputCodeConvertCommandHandler extends CommandHandler<OutputCodeConvertCommand>
{
    
    @Inject
    private OutputCodeConvertRepository repository;
    
    @Override
    protected void handle(CommandHandlerContext<OutputCodeConvertCommand> context) {
        repository.remove();
    }
}
