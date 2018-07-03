package nts.uk.ctx.exio.app.command.exo.datafomat;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.exio.dom.exo.datafomat.TimeDataFmSet;
import nts.uk.ctx.exio.dom.exo.datafomat.TimeDataFmSetRepository;

@Stateless
@Transactional
public class UpdateTimeDataFmSetCommandHandler extends CommandHandler<TimeDataFmSetCommand>
{
    
    @Inject
    private TimeDataFmSetRepository repository;
    
    @Override
    protected void handle(CommandHandlerContext<TimeDataFmSetCommand> context) {
        TimeDataFmSetCommand updateCommand = context.getCommand();
        repository.update(new TimeDataFmSet(updateCommand.getCid(), updateCommand.getNullValueSubs(), updateCommand.getOutputMinusAsZero(), updateCommand.getFixedValue(), updateCommand.getValueOfFixedValue(), updateCommand.getFixedLengthOutput(), updateCommand.getFixedLongIntegerDigit(), updateCommand.getFixedLengthEditingMothod(), updateCommand.getDelimiterSetting(), updateCommand.getSelectHourMinute(), updateCommand.getMinuteFractionDigit(), updateCommand.getDecimalSelection(), updateCommand.getFixedValueOperationSymbol(), updateCommand.getFixedValueOperation(), updateCommand.getFixedCalculationValue(), updateCommand.getValueOfNullValueSubs(), updateCommand.getMinuteFractionDigitProcessCla()));
    
    }
}
