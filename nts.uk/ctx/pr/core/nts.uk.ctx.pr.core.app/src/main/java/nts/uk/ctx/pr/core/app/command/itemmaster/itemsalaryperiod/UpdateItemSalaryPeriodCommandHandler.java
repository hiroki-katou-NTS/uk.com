package nts.uk.ctx.pr.core.app.command.itemmaster.itemsalaryperiod;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.pr.core.dom.itemmaster.itemsalaryperiod.ItemSalaryPeriodRepository;

@Stateless
@Transactional
public class UpdateItemSalaryPeriodCommandHandler extends CommandHandler<UpdateItemSalaryPeriodCommand> {

	@Inject
	private ItemSalaryPeriodRepository itemSalaryPeriodRepository;

	@Override
	protected void handle(CommandHandlerContext<UpdateItemSalaryPeriodCommand> context) {
		this.itemSalaryPeriodRepository.update(context.getCommand().toDomain());
	}

}
