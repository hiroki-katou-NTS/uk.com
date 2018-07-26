package nts.uk.ctx.exio.app.command.exo.dataformat.dataformatsetting;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.exio.dom.exo.dataformat.dataformatsetting.DateFormatSetting;
import nts.uk.ctx.exio.dom.exo.outputitem.StandardOutputItemRepository;

@Stateless
@Transactional
public class UpdateDateDfsCommandHandler extends CommandHandler<DateDfsCommand> {

	@Inject
	private StandardOutputItemRepository repository;

	@Override
	protected void handle(CommandHandlerContext<DateDfsCommand> context) {
		DateDfsCommand updateCommand = context.getCommand();
		repository.register(new DateFormatSetting(updateCommand.getCid(), updateCommand.getNullValueSubstitution(),
				updateCommand.getFixedValue(), updateCommand.getValueOfFixedValue(), updateCommand.getValueOfNullValueSubs(),
				updateCommand.getFormatSelection(), updateCommand.getCondSetCd(), updateCommand.getOutItemCd()));

	}
}
