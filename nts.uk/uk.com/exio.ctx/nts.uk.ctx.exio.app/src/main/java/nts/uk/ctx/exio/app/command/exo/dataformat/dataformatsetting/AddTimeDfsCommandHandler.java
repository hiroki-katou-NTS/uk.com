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
public class AddTimeDfsCommandHandler extends CommandHandler<TimeDfsCommand> {

	@Inject
	private StandardOutputItemRepository repository;

	@Override
	protected void handle(CommandHandlerContext<TimeDfsCommand> context) {
		TimeDfsCommand addCommand = context.getCommand();
		repository.register(new TimeDataFmSetting(addCommand.getCid(), addCommand.getNullValueSubs(),
				addCommand.getOutputMinusAsZero(), addCommand.getFixedValue(), addCommand.getValueOfFixedValue(),
				addCommand.getFixedLengthOutput(), addCommand.getFixedLongIntegerDigit(),
				addCommand.getFixedLengthEditingMethod(), addCommand.getDelimiterSetting(),
				addCommand.getSelectHourMinute(), addCommand.getMinuteFractionDigit(), addCommand.getDecimalSelection(),
				addCommand.getFixedValueOperationSymbol(), addCommand.getFixedValueOperation(),
				addCommand.getFixedCalculationValue(), addCommand.getValueOfNullValueSubs(),
				addCommand.getMinuteFractionDigitProcessCls(), addCommand.getCondSetCd(), addCommand.getOutItemCd()));

	}
}
