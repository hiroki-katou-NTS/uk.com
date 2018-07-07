package nts.uk.ctx.exio.app.command.exo.charegister;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.exio.dom.exo.dataformat.ChacDataFmSet;
import nts.uk.ctx.exio.dom.exo.dataformat.ChacDataFmSetRepository;
import nts.uk.ctx.exio.dom.exo.dataformat.ItemType;

@Stateless
@Transactional
public class SettingDataCharUpdateService extends CommandHandler<ChacDataFmSetCommand>{


	@Inject
	ChacDataFmSetRepository chacDataFmSetRepository;
	
	@Override
	protected void handle(CommandHandlerContext<ChacDataFmSetCommand> context) {
		ChacDataFmSetCommand command = context.getCommand();
		ChacDataFmSet chacDataFmSet = new ChacDataFmSet(ItemType.CHARACTER.value, command.getCid(), command.getNullValueReplace(), command.getValueOfNullValueReplace(), command.getCdEditting(), command.getFixedValue(), command.getCdEdittingMethod(), command.getCdEditDigit(), command.getCdConvertCd(), command.getSpaceEditting(), command.getEffectDigitLength(), command.getStartDigit(), command.getEndDigit(), command.getValueOfFixedValue());
		chacDataFmSetRepository.update(chacDataFmSet);
	}

}
