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
        repository.add(AcScreenCondSet.createFromJavaType(0L, addCommand.getCid(), addCommand.getConditionSetCd(), addCommand.getAcceptItemNum(), addCommand.getSelCompareCond(), addCommand.getTimeCondVal2(), addCommand.getTimeCondVal1(), addCommand.getTimeMoCondVal2(), addCommand.getTimeMoCondVal1(), addCommand.getDateCondVal2(), addCommand.getDateCondVal1(), addCommand.getCharCondVal2(), addCommand.getCharCondVal1(), addCommand.getNumCondVal2(), addCommand.getNumCondVal1()));
    
    }
}
