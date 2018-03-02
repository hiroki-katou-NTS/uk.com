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
        repository.update(AcScreenCondSet.createFromJavaType(updateCommand.getVersion(), updateCommand.getCid(), updateCommand.getConditionSetCd(), updateCommand.getAcceptItemNum(), updateCommand.getSelectComparisonCondition(), updateCommand.getTimeConditionValue2(), updateCommand.getTimeConditionValue1(), updateCommand.getTimeMomentConditionValue2(), updateCommand.getTimeMomentConditionValue1(), updateCommand.getDateConditionValue2(), updateCommand.getDateConditionValue1(), updateCommand.getCharacterConditionValue2(), updateCommand.getCharacterConditionValue1(), updateCommand.getNumberConditionValue2(), updateCommand.getNumberConditionValue1()));
    
    }
}
