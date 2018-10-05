package nts.uk.ctx.pr.core.app.command.wageprovision.statementitem;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.pr.core.dom.wageprovision.statementitem.breakdownitemset.BreakdownItemSet;
import nts.uk.ctx.pr.core.dom.wageprovision.statementitem.breakdownitemset.BreakdownItemSetRepository;

@Stateless
@Transactional
public class UpdateBreakdownItemSetCommandHandler extends CommandHandler<BreakdownItemSetCommand> {

	@Inject
	private BreakdownItemSetRepository breakdownItemSetRepository;

	@Override
	protected void handle(CommandHandlerContext<BreakdownItemSetCommand> context) {
		BreakdownItemSetCommand command = context.getCommand();
		BreakdownItemSet breakdownItemSet = new BreakdownItemSet(command.getSalaryItemId(),
				command.getBreakdownItemCode(), command.getBreakdownItemName());
		breakdownItemSetRepository.update(breakdownItemSet);
	}
}
