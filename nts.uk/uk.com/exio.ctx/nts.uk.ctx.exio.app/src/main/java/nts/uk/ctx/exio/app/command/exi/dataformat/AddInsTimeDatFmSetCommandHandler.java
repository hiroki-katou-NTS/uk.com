package nts.uk.ctx.exio.app.command.exi.dataformat;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.exio.dom.exi.dataformat.InsTimeDatFmSetRepository;
import nts.uk.ctx.exio.dom.exi.dataformat.InsTimeDatFmSet;

@Stateless
@Transactional
public class AddInsTimeDatFmSetCommandHandler extends CommandHandler<InsTimeDatFmSetCommand>
{
    
    @Inject
    private InsTimeDatFmSetRepository repository;
    
    @Override
    protected void handle(CommandHandlerContext<InsTimeDatFmSetCommand> context) {
        InsTimeDatFmSetCommand addCommand = context.getCommand();
        repository.add(InsTimeDatFmSet.createFromJavaType(0L, addCommand.getCid(), addCommand.getConditionSetCd(), addCommand.getAcceptItemNum(), addCommand.getDelimiterSet(), addCommand.getFixedValue(), addCommand.getHourMinSelect(), addCommand.getEffectiveDigitLength(), addCommand.getRoundProc(), addCommand.getDecimalSelect(), addCommand.getValueOfFixedValue(), addCommand.getStartDigit(), addCommand.getEndDigit(), addCommand.getRoundProcCls()));
    
    }
}
