package nts.uk.ctx.exio.app.command.exo.datafomat;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.exio.dom.exo.datafomat.InTimeDataFmSetRepository;
import nts.uk.ctx.exio.dom.exo.datafomat.InTimeDataFmSet;

@Stateless
@Transactional
public class AddInTimeDataFmSetCommandHandler extends CommandHandler<InTimeDataFmSetCommand>
{
    
    @Inject
    private InTimeDataFmSetRepository repository;
    
    @Override
    protected void handle(CommandHandlerContext<InTimeDataFmSetCommand> context) {
        InTimeDataFmSetCommand addCommand = context.getCommand();
        repository.add(new InTimeDataFmSet(addCommand.getCid(), addCommand.getNullValueSubs(), addCommand.getValueOfNullValueSubs(), addCommand.getOutputMinusAsZero(), addCommand.getFixedValue(), addCommand.getValueOfFixedValue(), addCommand.getTimeSeletion(), addCommand.getFixedLengthOutput(), addCommand.getFixedLongIntegerDigit(), addCommand.getFixedLengthEditingMothod(), addCommand.getDelimiterSetting(), addCommand.getPreviousDayOutputMethod(), addCommand.getNextDayOutputMethod(), addCommand.getMinuteFractionDigit(), addCommand.getDecimalSelection(), addCommand.getMinuteFractionDigitProcessCla()));
    
    }
}
