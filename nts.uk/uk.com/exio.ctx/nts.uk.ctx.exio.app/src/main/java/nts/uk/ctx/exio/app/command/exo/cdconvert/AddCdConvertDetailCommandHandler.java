package nts.uk.ctx.exio.app.command.exo.cdconvert;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.exio.dom.exo.cdconvert.CdConvertDetail;
import nts.uk.ctx.exio.dom.exo.cdconvert.CdConvertDetailRepository;

@Stateless
@Transactional
public class AddCdConvertDetailCommandHandler extends CommandHandler<CdConvertDetailCommand>
{
    
    @Inject
    private CdConvertDetailRepository repository;
    
    @Override
    protected void handle(CommandHandlerContext<CdConvertDetailCommand> context) {
        CdConvertDetailCommand addCommand = context.getCommand();
        repository.add(new CdConvertDetail(addCommand.getConvertCd(), addCommand.getOutputItem(), addCommand.getSystemCd(), addCommand.getLineNumber()));
    
    }
}
