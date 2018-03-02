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
public class AddAcScreenCondSetCommandHandler extends CommandHandler<AcScreenCondSetCommand>
{
    
    @Inject
    private AcScreenCondSetRepository repository;
    
    @Override
    protected void handle(CommandHandlerContext<AcScreenCondSetCommand> context) {
        AcScreenCondSetCommand addCommand = context.getCommand();
        repository.add(AcScreenCondSet.createFromJavaType(0L, addCommand.getCid(), addCommand.getConditionSetCd(), addCommand.getAcceptItemNum(), addCommand.getSelectComparisonCondition(), addCommand.getTimeConditionValue2(), addCommand.getTimeConditionValue1(), addCommand.getTimeMomentConditionValue2(), addCommand.getTimeMomentConditionValue1(), addCommand.getDateConditionValue2(), addCommand.getDateConditionValue1(), addCommand.getCharacterConditionValue2(), addCommand.getCharacterConditionValue1(), addCommand.getNumberConditionValue2(), addCommand.getNumberConditionValue1()));
    
    }
}
