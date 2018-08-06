package nts.uk.ctx.exio.app.command.exo.category;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.exio.dom.exo.category.ExOutLinkTableRepository;


@Stateless
@Transactional
public class RemoveExCndOutputCommandHandler extends CommandHandler<ExCndOutputCommand>
{
    
    @Inject
    private ExOutLinkTableRepository repository;
    
    @Override
    protected void handle(CommandHandlerContext<ExCndOutputCommand> context) {
        int categoryId = context.getCommand().getCategoryId();
        repository.remove(categoryId);
    }
}
