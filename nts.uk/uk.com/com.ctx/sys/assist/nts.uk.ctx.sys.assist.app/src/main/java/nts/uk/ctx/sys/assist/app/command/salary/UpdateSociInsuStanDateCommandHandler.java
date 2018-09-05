package nts.uk.ctx.sys.assist.app.command.salary;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.sys.assist.dom.salary.SociInsuStanDateRepository;
import nts.uk.ctx.sys.assist.dom.salary.SociInsuStanDate;

@Stateless
@Transactional
public class UpdateSociInsuStanDateCommandHandler extends CommandHandler<SociInsuStanDateCommand> {

	@Inject
	private SociInsuStanDateRepository repository;

	@Override
	protected void handle(CommandHandlerContext<SociInsuStanDateCommand> context) {
		SociInsuStanDateCommand updateCommand = context.getCommand();
		repository.update(new SociInsuStanDate(updateCommand.getCid(), updateCommand.getProcessCateNo(),
				updateCommand.getBaseMonth(), updateCommand.getBaseYear(), updateCommand.getRefeDate()));

	}
}
