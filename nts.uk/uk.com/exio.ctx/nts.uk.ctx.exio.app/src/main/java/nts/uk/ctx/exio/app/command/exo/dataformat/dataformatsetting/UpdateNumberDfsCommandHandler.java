package nts.uk.ctx.exio.app.command.exo.dataformat.dataformatsetting;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.exio.dom.exo.dataformat.dataformatsetting.NumberDataFmSetting;
import nts.uk.ctx.exio.dom.exo.outputitem.StandardOutputItemRepository;

@Stateless
@Transactional
public class UpdateNumberDfsCommandHandler extends CommandHandler<NumberDfsCommand> {

	@Inject
	private StandardOutputItemRepository repository;

	@Override
	protected void handle(CommandHandlerContext<NumberDfsCommand> context) {
		NumberDfsCommand updateCommand = context.getCommand();
		repository.register(new NumberDataFmSetting(updateCommand.getCid(), updateCommand.getNullValueReplace(),
				updateCommand.getValueOfNullValueReplace(), updateCommand.getOutputMinusAsZero(),
				updateCommand.getFixedValue(), updateCommand.getValueOfFixedValue(),
				updateCommand.getFixedValueOperation(), updateCommand.getFixedCalculationValue(),
				updateCommand.getFixedValueOperationSymbol(), updateCommand.getFixedLengthOutput(),
				updateCommand.getFixedLengthIntegerDigit(), updateCommand.getFixedLengthEditingMethod(),
				updateCommand.getDecimalDigit(), updateCommand.getDecimalPointClassification(),
				updateCommand.getDecimalFraction(), updateCommand.getFormatSelection(), updateCommand.getCondSetCd(),
				updateCommand.getOutItemCd()));

	}
}
