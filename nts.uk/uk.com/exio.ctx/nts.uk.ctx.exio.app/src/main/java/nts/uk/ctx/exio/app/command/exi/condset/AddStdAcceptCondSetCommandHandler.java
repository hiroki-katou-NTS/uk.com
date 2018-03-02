package nts.uk.ctx.exio.app.command.exi.condset;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.exio.dom.exi.condset.StdAcceptCondSetRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.ctx.exio.dom.exi.condset.StdAcceptCondSet;

@Stateless
@Transactional
public class AddStdAcceptCondSetCommandHandler extends CommandHandler<StdAcceptCondSetCommand>
{
    
    @Inject
    private StdAcceptCondSetRepository repository;
    
    @Override
    protected void handle(CommandHandlerContext<StdAcceptCondSetCommand> context) {
        StdAcceptCondSetCommand addCommand = context.getCommand();
        String companyId = AppContexts.user().companyId();
//        repository.add(new StdAcceptCondSet(companyId, addCommand.getConditionSetCd(), addCommand.getCategoryId(), addCommand.getCsvDataLineNumber(), addCommand.getSystemType(), addCommand.getDeleteExistData(), addCommand.getCsvDataStartLine(), addCommand.getAcceptMode(), addCommand.getConditionSetName(), addCommand.getCheckCompleted(), addCommand.getDeleteExtDataMethod()));
    
    }
}
