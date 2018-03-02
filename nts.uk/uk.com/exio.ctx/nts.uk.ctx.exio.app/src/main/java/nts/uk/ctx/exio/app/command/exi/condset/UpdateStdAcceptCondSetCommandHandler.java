package nts.uk.ctx.exio.app.command.exi.condset;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.exio.dom.exi.condset.StdAcceptCondSetRepository;
import nts.uk.ctx.exio.dom.exi.condset.StdAcceptCondSet;

@Stateless
@Transactional
public class UpdateStdAcceptCondSetCommandHandler extends CommandHandler<StdAcceptCondSetCommand>
{
    
    @Inject
    private StdAcceptCondSetRepository repository;
    
    @Override
    protected void handle(CommandHandlerContext<StdAcceptCondSetCommand> context) {
        StdAcceptCondSetCommand updateCommand = context.getCommand();
//        repository.update(StdAcceptCondSet.createFromJavaType(updateCommand.getVersion(), updateCommand.getCid(), updateCommand.getConditionSetCd(), updateCommand.getCategoryId(), updateCommand.getCsvDataLineNumber(), updateCommand.getSystemType(), updateCommand.getDeleteExistData(), updateCommand.getCsvDataStartLine(), updateCommand.getAcceptMode(), updateCommand.getConditionSetName(), updateCommand.getCheckCompleted(), updateCommand.getDeleteExtDataMethod()));
    
    }
}
