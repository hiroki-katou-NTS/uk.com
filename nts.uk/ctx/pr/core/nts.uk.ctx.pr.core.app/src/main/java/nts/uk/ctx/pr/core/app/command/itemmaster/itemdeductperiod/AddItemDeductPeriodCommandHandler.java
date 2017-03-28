package nts.uk.ctx.pr.core.app.command.itemmaster.itemdeductperiod;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.pr.core.dom.itemmaster.itemdeductperiod.ItemDeductPeriodRepository;

@Stateless
@Transactional
public class AddItemDeductPeriodCommandHandler extends CommandHandler<AddItemDeductPeriodCommand> {

	@Inject
	private ItemDeductPeriodRepository itemDeductPeriodRepo;

	@Override
	protected void handle(CommandHandlerContext<AddItemDeductPeriodCommand> context) {
		this.itemDeductPeriodRepo.add(context.getCommand().toDomain());
	}

}
