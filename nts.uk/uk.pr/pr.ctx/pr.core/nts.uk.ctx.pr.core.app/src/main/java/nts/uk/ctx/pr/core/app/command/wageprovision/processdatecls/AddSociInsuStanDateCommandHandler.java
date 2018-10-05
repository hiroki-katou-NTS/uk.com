package nts.uk.ctx.pr.core.app.command.wageprovision.processdatecls;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.pr.core.dom.wageprovision.processdatecls.SociInsuStanDate;
import nts.uk.ctx.pr.core.dom.wageprovision.processdatecls.SociInsuStanDateRepository;

@Stateless
@Transactional
public class AddSociInsuStanDateCommandHandler extends CommandHandler<SociInsuStanDateCommand> {

	@Inject
	private SociInsuStanDateRepository repository;

	@Override
	protected void handle(CommandHandlerContext<SociInsuStanDateCommand> context) {
		SociInsuStanDateCommand addCommand = context.getCommand();
		repository.add(
				new SociInsuStanDate(addCommand.getBaseMonth(), addCommand.getBaseYear(), addCommand.getRefeDate()));

	}
}
