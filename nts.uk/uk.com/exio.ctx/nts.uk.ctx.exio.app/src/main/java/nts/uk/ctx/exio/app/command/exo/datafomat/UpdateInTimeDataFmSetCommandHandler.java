package nts.uk.ctx.exio.app.command.exo.datafomat;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.exio.dom.exo.datafomat.InTimeDataFmSet;
import nts.uk.ctx.exio.dom.exo.datafomat.InTimeDataFmSetRepository;

@Stateless
@Transactional
public class UpdateInTimeDataFmSetCommandHandler extends CommandHandler<InTimeDataFmSetCommand>
{
    
    @Inject
    private InTimeDataFmSetRepository repository;
    
    @Override
    protected void handle(CommandHandlerContext<InTimeDataFmSetCommand> context) {
        InTimeDataFmSetCommand updateCommand = context.getCommand();
        repository.update(new InTimeDataFmSet(updateCommand.getCid(), updateCommand.getNullValueSubs(), updateCommand.getValueOfNullValueSubs(), updateCommand.getOutputMinusAsZero(), updateCommand.getFixedValue(), updateCommand.getValueOfFixedValue(), updateCommand.getTimeSeletion(), updateCommand.getFixedLengthOutput(), updateCommand.getFixedLongIntegerDigit(), updateCommand.getFixedLengthEditingMothod(), updateCommand.getDelimiterSetting(), updateCommand.getPreviousDayOutputMethod(), updateCommand.getNextDayOutputMethod(), updateCommand.getMinuteFractionDigit(), updateCommand.getDecimalSelection(), updateCommand.getMinuteFractionDigitProcessCla()));
    
    }
}
