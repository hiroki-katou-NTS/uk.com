package nts.uk.ctx.exio.app.command.exo.dataformat.dataformatsetting;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.exio.dom.exo.dataformat.dataformatsetting.InstantTimeDataFmSetting;
import nts.uk.ctx.exio.dom.exo.outputitem.StandardOutputItemRepository;

@Stateless
@Transactional
public class AddInstantTimeDfsCommandHandler extends CommandHandler<InstantTimeDfsCommand> {

	@Inject
	private StandardOutputItemRepository repository;

	@Override
	protected void handle(CommandHandlerContext<InstantTimeDfsCommand> context) {
		InstantTimeDfsCommand addCommand = context.getCommand();
		repository.register(new InstantTimeDataFmSetting(addCommand.getCid(), addCommand.getNullValueSubs(),
				addCommand.getValueOfNullValueSubs(), addCommand.getOutputMinusAsZero(), addCommand.getFixedValue(),
				addCommand.getValueOfFixedValue(), addCommand.getTimeSeletion(), addCommand.getFixedLengthOutput(),
				addCommand.getFixedLongIntegerDigit(), addCommand.getFixedLengthEditingMethod(),
				addCommand.getDelimiterSetting(), addCommand.getPreviousDayOutputMethod(),
				addCommand.getNextDayOutputMethod(), addCommand.getMinuteFractionDigit(),
				addCommand.getDecimalSelection(), addCommand.getMinuteFractionDigitProcessCls(),
				addCommand.getCondSetCd(), addCommand.getOutItemCd()));

	}
}
