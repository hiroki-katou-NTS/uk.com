package nts.uk.ctx.exio.app.command.exo.dataformat.dataformatsetting;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.exio.dom.exo.dataformat.dataformatsetting.TimeDataFmSetting;
import nts.uk.ctx.exio.dom.exo.outputitem.StandardOutputItemRepository;

@Stateless
@Transactional
public class UpdateTimeDfsCommandHandler extends CommandHandler<TimeDfsCommand> {

	@Inject
	private StandardOutputItemRepository repository;

	@Override
	protected void handle(CommandHandlerContext<TimeDfsCommand> context) {
		TimeDfsCommand updateCommand = context.getCommand();
		repository.register(new TimeDataFmSetting(updateCommand.getCid(), updateCommand.getNullValueSubs(),
				updateCommand.getOutputMinusAsZero(), updateCommand.getFixedValue(),
				updateCommand.getValueOfFixedValue(), updateCommand.getFixedLengthOutput(),
				updateCommand.getFixedLongIntegerDigit(), updateCommand.getFixedLengthEditingMethod(),
				updateCommand.getDelimiterSetting(), updateCommand.getSelectHourMinute(),
				updateCommand.getMinuteFractionDigit(), updateCommand.getDecimalSelection(),
				updateCommand.getFixedValueOperationSymbol(), updateCommand.getFixedValueOperation(),
				updateCommand.getFixedCalculationValue(), updateCommand.getValueOfNullValueSubs(),
				updateCommand.getMinuteFractionDigitProcessCls(), updateCommand.getCondSetCd(),
				updateCommand.getOutItemCd()));

	}
}
