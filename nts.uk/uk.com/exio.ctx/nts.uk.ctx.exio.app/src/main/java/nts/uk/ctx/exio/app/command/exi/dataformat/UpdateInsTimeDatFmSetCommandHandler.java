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
public class UpdateInsTimeDatFmSetCommandHandler extends CommandHandler<InsTimeDatFmSetCommand>
{
    
    @Inject
    private InsTimeDatFmSetRepository repository;
    
    @Override
    protected void handle(CommandHandlerContext<InsTimeDatFmSetCommand> context) {
        InsTimeDatFmSetCommand updateCommand = context.getCommand();
        repository.update(InsTimeDatFmSet.createFromJavaType(0L, updateCommand.getCid(), updateCommand.getConditionSetCd(), updateCommand.getAcceptItemNum(), updateCommand.getDelimiterSet(), updateCommand.getFixedValue(), updateCommand.getHourMinSelect(), updateCommand.getEffectiveDigitLength(), updateCommand.getRoundProc(), updateCommand.getDecimalSelect(), updateCommand.getValueOfFixedValue(), updateCommand.getStartDigit(), updateCommand.getEndDigit(), updateCommand.getRoundProcCls()));
    
    }
}
