package nts.uk.ctx.pr.core.app.command.wageprovision.statementitem;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.pr.core.dom.wageprovision.statementitem.breakdownitemset.BreakdownItemSetRepository;

@Stateless
@Transactional
public class RemoveBreakdownItemSetCommandHandler extends CommandHandler<BreakdownItemSetCommand> {

	@Inject
	private BreakdownItemSetRepository breakdownItemSetRepository;

	@Override
	protected void handle(CommandHandlerContext<BreakdownItemSetCommand> context) {
		BreakdownItemSetCommand command = context.getCommand();
		breakdownItemSetRepository.remove(command.getSalaryItemId(), command.getBreakdownItemCode());
	}

}
