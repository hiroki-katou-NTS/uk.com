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
public class AddDateDfsCommandHandler extends CommandHandler<DateDfsCommand> {

	@Inject
	private StandardOutputItemRepository repository;

	@Override
	protected void handle(CommandHandlerContext<DateDfsCommand> context) {
		DateDfsCommand addCommand = context.getCommand();
		repository.register(new DateFormatSetting(addCommand.getCid(), addCommand.getNullValueSubstitution(),
				addCommand.getFixedValue(), addCommand.getValueOfFixedValue(), addCommand.getValueOfNullValueSubs(),
				addCommand.getFormatSelection(), addCommand.getCondSetCd(), addCommand.getOutItemCd()));

	}
}
