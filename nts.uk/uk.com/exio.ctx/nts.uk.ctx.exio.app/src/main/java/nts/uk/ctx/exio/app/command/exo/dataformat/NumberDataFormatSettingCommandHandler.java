package nts.uk.ctx.exio.app.command.exo.dataformat;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.exio.dom.exo.base.ItemType;
import nts.uk.ctx.exio.dom.exo.dataformat.init.DataFormatSettingRepository;
import nts.uk.ctx.exio.dom.exo.dataformat.init.NumberDataFmSet;
import nts.uk.shr.com.context.AppContexts;

@Stateless
@Transactional
public class NumberDataFormatSettingCommandHandler extends CommandHandler<NumberDataFormatSettingCommand> {

	@Inject
	private DataFormatSettingRepository dataFormatSettingRepository;

	@Override
	protected void handle(CommandHandlerContext<NumberDataFormatSettingCommand> context) {
		NumberDataFormatSettingCommand command = context.getCommand();
		String cid = AppContexts.user().companyId();
		NumberDataFmSet numberDataFmSet = new NumberDataFmSet(ItemType.NUMERIC.value, cid, command.getNullValueReplace(),
				command.getValueOfNullValueReplace(), command.getOutputMinusAsZero(), command.getFixedValue(),
				command.getValueOfFixedValue(), command.getFixedValueOperation(), command.getFixedCalculationValue(),
				command.getFixedValueOperationSymbol(), command.getFixedLengthOutput(),
				command.getFixedLengthIntegerDigit(), command.getFixedLengthEditingMethod(), command.getDecimalDigit(),
				command.getDecimalPointClassification(), command.getDecimalFraction(), command.getFormatSelection());

		if (dataFormatSettingRepository.getNumberDataFmSetByCid(cid).isPresent()) {
			dataFormatSettingRepository.update(numberDataFmSet);
		} else {
			dataFormatSettingRepository.add(numberDataFmSet);
		}
	}
}
