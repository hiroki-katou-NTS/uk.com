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
public class AddTimeDataFmSetCommandHandler extends CommandHandler<TimeDataFmSetCommand>
{
    
    @Inject
    private TimeDataFmSetRepository repository;
    
    @Override
    protected void handle(CommandHandlerContext<TimeDataFmSetCommand> context) {
        TimeDataFmSetCommand addCommand = context.getCommand();
        repository.add(new TimeDataFmSet(addCommand.getCid(), addCommand.getNullValueSubs(), addCommand.getOutputMinusAsZero(), addCommand.getFixedValue(), addCommand.getValueOfFixedValue(), addCommand.getFixedLengthOutput(), addCommand.getFixedLongIntegerDigit(), addCommand.getFixedLengthEditingMothod(), addCommand.getDelimiterSetting(), addCommand.getSelectHourMinute(), addCommand.getMinuteFractionDigit(), addCommand.getDecimalSelection(), addCommand.getFixedValueOperationSymbol(), addCommand.getFixedValueOperation(), addCommand.getFixedCalculationValue(), addCommand.getValueOfNullValueSubs(), addCommand.getMinuteFractionDigitProcessCla()));
    
    }
}
