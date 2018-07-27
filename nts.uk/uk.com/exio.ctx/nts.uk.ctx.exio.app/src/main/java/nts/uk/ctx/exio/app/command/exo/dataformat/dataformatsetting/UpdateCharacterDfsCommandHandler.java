package nts.uk.ctx.exio.app.command.exo.dataformat.dataformatsetting;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.exio.dom.exo.dataformat.dataformatsetting.CharacterDataFmSetting;
import nts.uk.ctx.exio.dom.exo.outputitem.StandardOutputItemRepository;

@Stateless
@Transactional
public class UpdateCharacterDfsCommandHandler extends CommandHandler<CharacterDfsCommand> {

	@Inject
	private StandardOutputItemRepository repository;

	@Override
	protected void handle(CommandHandlerContext<CharacterDfsCommand> context) {
		CharacterDfsCommand updateCommand = context.getCommand();
		repository.register(new CharacterDataFmSetting(updateCommand.getCid(), updateCommand.getNullValueReplace(),
				updateCommand.getValueOfNullValueReplace(), updateCommand.getCdEditting(),
				updateCommand.getFixedValue(), updateCommand.getCdEdittingMethod(), updateCommand.getCdEditDigit(),
				updateCommand.getCdConvertCd(), updateCommand.getSpaceEditting(), updateCommand.getEffectDigitLength(),
				updateCommand.getStartDigit(), updateCommand.getEndDigit(), updateCommand.getValueOfFixedValue(),
				updateCommand.getCondSetCd(), updateCommand.getOutItemCd()));
	}
}
