package nts.uk.ctx.pr.core.app.command.itemmaster.itemsalaryperiod;

import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.pr.core.dom.itemmaster.itemsalaryperiod.ItemSalaryPeriodRepository;

public class DeleteItemSalaryPeriodCommandHandler extends CommandHandler<DeleteItemSalaryPeriodCommand> {
	
	@Inject
	ItemSalaryPeriodRepository itemSalaryPeriodRepository;
	
	@Override
	protected void handle(CommandHandlerContext<DeleteItemSalaryPeriodCommand> context) {
		this.itemSalaryPeriodRepository.delete(context.getCommand().getItemCd());
		
	}

}
