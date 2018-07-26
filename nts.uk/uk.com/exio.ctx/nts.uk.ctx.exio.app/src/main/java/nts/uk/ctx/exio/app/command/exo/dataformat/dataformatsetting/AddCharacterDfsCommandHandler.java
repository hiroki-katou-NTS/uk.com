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
public class AddCharacterDfsCommandHandler extends CommandHandler<CharacterDfsCommand> {

	@Inject
	private StandardOutputItemRepository repository;

	@Override
	protected void handle(CommandHandlerContext<CharacterDfsCommand> context) {
		CharacterDfsCommand addCommand = context.getCommand();
		repository.register(new CharacterDataFmSetting(addCommand.getCid(), addCommand.getNullValueReplace(),
				addCommand.getValueOfNullValueReplace(), addCommand.getCdEditting(), addCommand.getFixedValue(),
				addCommand.getCdEdittingMethod(), addCommand.getCdEditDigit(), addCommand.getCdConvertCd(),
				addCommand.getSpaceEditting(), addCommand.getEffectDigitLength(),
				addCommand.getStartDigit(), addCommand.getEndDigit(), addCommand.getValueOfFixedValue(),
				addCommand.getCondSetCd(), addCommand.getOutItemCd()));

	}
}
