package nts.uk.ctx.exio.app.command.exo.dataformat.dataformatsetting;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.exio.dom.exo.dataformat.dataformatsetting.AwDataFormatSetting;
import nts.uk.ctx.exio.dom.exo.outputitem.StandardOutputItemRepository;

@Stateless
@Transactional
public class AddAtWorkClsDfsCommandHandler extends CommandHandler<AtWorkClsDfsCommand> {

	@Inject
	private StandardOutputItemRepository repository;

	@Override
	protected void handle(CommandHandlerContext<AtWorkClsDfsCommand> context) {
		AtWorkClsDfsCommand addCommand = context.getCommand();
		repository.register(new AwDataFormatSetting(addCommand.getCid(), addCommand.getClosedOutput(),
				addCommand.getAbsenceOutput(), addCommand.getFixedValue(), addCommand.getValueOfFixedValue(),
				addCommand.getAtWorkOutput(), addCommand.getRetirementOutput(), addCommand.getCondSetCd(),
				addCommand.getOutItemCd()));

	}
}
