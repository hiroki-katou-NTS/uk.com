package nts.uk.ctx.exio.app.command.exi.condset;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.exio.dom.exi.condset.AcScreenCondSetRepository;
import nts.uk.ctx.exio.dom.exi.condset.AcScreenCondSet;

@Stateless
@Transactional
public class UpdateAcScreenCondSetCommandHandler extends CommandHandler<AcScreenCondSetCommand>
{
    
    @Inject
    private AcScreenCondSetRepository repository;
    
    @Override
    protected void handle(CommandHandlerContext<AcScreenCondSetCommand> context) {
        AcScreenCondSetCommand updateCommand = context.getCommand();
        repository.update(AcScreenCondSet.createFromJavaType(updateCommand.getVersion(), updateCommand.getCid(), updateCommand.getConditionSetCd(), updateCommand.getAcceptItemNum(), updateCommand.getSelCompareCond(), updateCommand.getTimeCondVal2(), updateCommand.getTimeCondVal1(), updateCommand.getTimeMoCondVal2(), updateCommand.getTimeMoCondVal1(), updateCommand.getDateCondVal2(), updateCommand.getDateCondVal1(), updateCommand.getCharCondVal2(), updateCommand.getCharCondVal1(), updateCommand.getNumCondVal2(), updateCommand.getNumCondVal1()));
    
    }
}
