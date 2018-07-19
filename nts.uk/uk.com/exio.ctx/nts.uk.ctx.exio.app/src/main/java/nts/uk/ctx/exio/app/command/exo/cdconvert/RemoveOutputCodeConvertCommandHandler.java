package nts.uk.ctx.exio.app.command.exo.cdconvert;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.exio.dom.exo.cdconvert.OutputCodeConvertRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
@Transactional
public class RemoveOutputCodeConvertCommandHandler extends CommandHandler<OutputCodeConvertCommand>
{
    
    @Inject
    private OutputCodeConvertRepository repository;
    
    @Override
    protected void handle(CommandHandlerContext<OutputCodeConvertCommand> context) {
    	String cid = AppContexts.user().companyId();
		String convertCd = context.getCommand().getConvertCode();
        repository.remove(cid, convertCd);
    }
}
