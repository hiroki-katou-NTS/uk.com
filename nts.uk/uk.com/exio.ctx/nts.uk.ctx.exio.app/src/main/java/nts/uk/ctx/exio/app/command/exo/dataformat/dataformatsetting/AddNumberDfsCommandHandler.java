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
public class AddNumberDfsCommandHandler extends CommandHandler<NumberDfsCommand> {

	@Inject
	private StandardOutputItemRepository repository;

	@Override
	protected void handle(CommandHandlerContext<NumberDfsCommand> context) {
		NumberDfsCommand addCommand = context.getCommand();
		repository.register(new NumberDataFmSetting(addCommand.getCid(), addCommand.getNullValueReplace(),
				addCommand.getValueOfNullValueReplace(), addCommand.getOutputMinusAsZero(), addCommand.getFixedValue(),
				addCommand.getValueOfFixedValue(), addCommand.getFixedValueOperation(),
				addCommand.getFixedCalculationValue(), addCommand.getFixedValueOperationSymbol(),
				addCommand.getFixedLengthOutput(), addCommand.getFixedLengthIntegerDigit(),
				addCommand.getFixedLengthEditingMethod(), addCommand.getDecimalDigit(),
				addCommand.getDecimalPointClassification(), addCommand.getDecimalFraction(),
				addCommand.getFormatSelection(), addCommand.getCondSetCd(), addCommand.getOutItemCd()));

	}
}
