package nts.uk.ctx.exio.app.command.exo.charegister;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.exio.dom.exo.base.ItemType;
import nts.uk.ctx.exio.dom.exo.dataformat.init.ChacDataFmSet;
import nts.uk.ctx.exio.dom.exo.dataformat.init.DataFormatSettingRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
@Transactional
public class SettingDataCharRegisterService extends CommandHandler<ChacDataFmSetCommand>{


	@Inject
	DataFormatSettingRepository chacDataFmSetRepository;
	
	@Override
	protected void handle(CommandHandlerContext<ChacDataFmSetCommand> context) {
		ChacDataFmSetCommand command = context.getCommand();
		String cid = AppContexts.user().companyId();
		ChacDataFmSet chacDataFmSet = new ChacDataFmSet(ItemType.CHARACTER.value, cid, command.getNullValueReplace(),
				command.getValueOfNullValueReplace(), command.getCdEditting(), command.getFixedValue(),
				command.getCdEdittingMethod(), command.getCdEditDigit(), command.getCdConvertCd(),
				command.getSpaceEditting(), command.getEffectDigitLength(), command.getStartDigit(),
				command.getEndDigit(), command.getValueOfFixedValue());
		Optional<ChacDataFmSet> chacDataFmSetCheck = chacDataFmSetRepository.getChacDataFmSetById(cid);
		if (!chacDataFmSetCheck.isPresent()) {
			chacDataFmSetRepository.add(chacDataFmSet);
		} else {
			chacDataFmSetRepository.update(chacDataFmSet);
		}
	}

}
