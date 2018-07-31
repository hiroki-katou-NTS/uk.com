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
public class UpdateInstantTimeDfsCommandHandler extends CommandHandler<InstantTimeDfsCommand> {

	@Inject
	private StandardOutputItemRepository repository;

	@Override
	protected void handle(CommandHandlerContext<InstantTimeDfsCommand> context) {
		InstantTimeDfsCommand updateCommand = context.getCommand();
		repository.register(new InstantTimeDataFmSetting(updateCommand.getCid(), updateCommand.getNullValueSubs(),
				updateCommand.getValueOfNullValueSubs(), updateCommand.getOutputMinusAsZero(), updateCommand.getFixedValue(),
				updateCommand.getValueOfFixedValue(), updateCommand.getTimeSeletion(), updateCommand.getFixedLengthOutput(),
				updateCommand.getFixedLongIntegerDigit(), updateCommand.getFixedLengthEditingMethod(),
				updateCommand.getDelimiterSetting(), updateCommand.getPreviousDayOutputMethod(),
				updateCommand.getNextDayOutputMethod(), updateCommand.getMinuteFractionDigit(),
				updateCommand.getDecimalSelection(), updateCommand.getMinuteFractionDigitProcessCls(),
				updateCommand.getCondSetCd(), updateCommand.getOutItemCd()));
	}
}
