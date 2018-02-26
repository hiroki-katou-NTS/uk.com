package nts.uk.ctx.exio.app.command.exi.codeconvert;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import javax.transaction.Transactional;
import nts.uk.ctx.exio.dom.exi.codeconvert.CdConvertDetailsRepository;
import nts.uk.ctx.exio.dom.exi.codeconvert.CdConvertDetails;

@Stateless
@Transactional
public class RemoveCdConvertDetailsCommandHandler extends CommandHandler<CdConvertDetailsCommand>
{
    
    @Inject
    private CdConvertDetailsRepository repository;
    
    @Override
    protected void handle(CommandHandlerContext<CdConvertDetailsCommand> context) {
        String cid = context.getCommand().getCid();
        String convertCd = context.getCommand().getConvertCd();
        int lineNumber = context.getCommand().getLineNumber();
        repository.remove(cid, convertCd, lineNumber);
    }
}
