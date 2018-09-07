package nts.uk.ctx.pr.core.app.command.salary;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.pr.core.dom.salary.SociInsuStanDate;
import nts.uk.ctx.pr.core.dom.salary.SociInsuStanDateRepository;

@Stateless
@Transactional
public class AddSociInsuStanDateCommandHandler extends CommandHandler<SociInsuStanDateCommand> {

	@Inject
	private SociInsuStanDateRepository repository;

	@Override
	protected void handle(CommandHandlerContext<SociInsuStanDateCommand> context) {
		SociInsuStanDateCommand addCommand = context.getCommand();
		repository.add(new SociInsuStanDate(addCommand.getCid(), addCommand.getProcessCateNo(),
				addCommand.getBaseMonth(), addCommand.getBaseYear(), addCommand.getRefeDate()));

	}
}
