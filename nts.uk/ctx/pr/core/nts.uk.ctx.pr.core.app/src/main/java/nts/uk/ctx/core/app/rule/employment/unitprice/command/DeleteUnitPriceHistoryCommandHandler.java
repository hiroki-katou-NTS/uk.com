package nts.uk.ctx.core.app.rule.employment.unitprice.command;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.pr.core.dom.rule.employment.unitprice.UnitPriceHistoryRepository;

@Stateless
public class DeleteUnitPriceHistoryCommandHandler extends CommandHandler<DeleteUnitPriceHistoryCommand> {

	@Inject
	private UnitPriceHistoryRepository unitPriceHistoryRepository;

	@Override
	@Transactional
	protected void handle(CommandHandlerContext<DeleteUnitPriceHistoryCommand> context) {
		unitPriceHistoryRepository.remove(context.getCommand().getId(), context.getCommand().getVersion());
	}

}
