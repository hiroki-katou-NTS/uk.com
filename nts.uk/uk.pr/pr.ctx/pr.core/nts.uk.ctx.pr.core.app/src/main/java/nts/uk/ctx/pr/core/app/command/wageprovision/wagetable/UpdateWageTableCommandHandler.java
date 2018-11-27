package nts.uk.ctx.pr.core.app.command.wageprovision.wagetable;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.pr.core.dom.wageprovision.wagetable.WageTableHistoryRepository;
import nts.uk.ctx.pr.core.dom.wageprovision.wagetable.WageTableRepository;

/**
 * 
 * @author HungTT
 *
 */

@Stateless
public class UpdateWageTableCommandHandler extends CommandHandler<UpdateWageTableCommand> {

	@Inject
	private WageTableRepository wageRepo;

	@Inject
	private WageTableHistoryRepository wageHistRepo;
	
	@Override
	protected void handle(CommandHandlerContext<UpdateWageTableCommand> context) {
		wageRepo.update(context.getCommand().toWageTableDomain());
	}

	
}
