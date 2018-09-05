package nts.uk.ctx.sys.assist.app.command.salary;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.sys.assist.dom.salary.IncomTaxBaseYearRepository;
import nts.uk.ctx.sys.assist.dom.salary.IncomTaxBaseYear;

@Stateless
@Transactional
public class UpdateIncomTaxBaseYearCommandHandler extends CommandHandler<IncomTaxBaseYearCommand> {

	@Inject
	private IncomTaxBaseYearRepository repository;

	@Override
	protected void handle(CommandHandlerContext<IncomTaxBaseYearCommand> context) {
		IncomTaxBaseYearCommand updateCommand = context.getCommand();
		repository.update(new IncomTaxBaseYear(updateCommand.getCid(), updateCommand.getProcessCateNo(),
				updateCommand.getRefeDate(), updateCommand.getBaseYear(), updateCommand.getBaseMonth()));

	}
}
