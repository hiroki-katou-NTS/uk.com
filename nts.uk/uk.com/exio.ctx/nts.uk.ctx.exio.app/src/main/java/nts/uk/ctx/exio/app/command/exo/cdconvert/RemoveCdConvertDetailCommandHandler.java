package nts.uk.ctx.exio.app.command.exo.cdconvert;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.exio.dom.exo.cdconvert.CdConvertDetailRepository;

@Stateless
@Transactional
public class RemoveCdConvertDetailCommandHandler extends CommandHandler<CdConvertDetailCommand>
{
    
    @Inject
    private CdConvertDetailRepository repository;
    
    @Override
    protected void handle(CommandHandlerContext<CdConvertDetailCommand> context) {
        repository.remove();
    }
}
