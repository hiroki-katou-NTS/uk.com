package nts.uk.ctx.exio.app.command.exi.codeconvert;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.exio.dom.exi.codeconvert.CdConvertDetailsRepository;
import nts.uk.ctx.exio.dom.exi.codeconvert.CdConvertDetails;

@Stateless
@Transactional
public class UpdateCdConvertDetailsCommandHandler extends CommandHandler<CdConvertDetailsCommand>
{
    
    @Inject
    private CdConvertDetailsRepository repository;
    
    @Override
    protected void handle(CommandHandlerContext<CdConvertDetailsCommand> context) {
        CdConvertDetailsCommand updateCommand = context.getCommand();
       // repository.update(CdConvertDetails.createFromJavaType(updateCommand.getVersion(), updateCommand.getCid(), updateCommand.getConvertCd(), updateCommand.getLineNumber(), updateCommand.getOutputItem(), updateCommand.getSystemCd()));
    
    }
}
