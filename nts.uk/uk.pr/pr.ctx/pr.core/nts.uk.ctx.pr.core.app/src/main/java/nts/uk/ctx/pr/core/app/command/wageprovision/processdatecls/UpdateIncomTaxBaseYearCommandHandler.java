package nts.uk.ctx.pr.core.app.command.wageprovision.processdatecls;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.pr.core.dom.wageprovision.processdatecls.IncomTaxBaseYear;
import nts.uk.ctx.pr.core.dom.wageprovision.processdatecls.IncomTaxBaseYearRepository;

@Stateless
@Transactional
public class UpdateIncomTaxBaseYearCommandHandler extends CommandHandler<IncomTaxBaseYearCommand> {

	@Inject
	private IncomTaxBaseYearRepository repository;

	@Override
	protected void handle(CommandHandlerContext<IncomTaxBaseYearCommand> context) {
		IncomTaxBaseYearCommand updateCommand = context.getCommand();
		repository.update(new IncomTaxBaseYear(updateCommand.getRefeDate(), updateCommand.getBaseYear(),
				updateCommand.getBaseMonth()));

	}
}
