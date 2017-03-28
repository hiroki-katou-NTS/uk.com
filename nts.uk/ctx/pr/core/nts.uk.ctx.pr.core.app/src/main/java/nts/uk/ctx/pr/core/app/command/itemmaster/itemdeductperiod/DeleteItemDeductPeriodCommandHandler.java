package nts.uk.ctx.pr.core.app.command.itemmaster.itemdeductperiod;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.pr.core.dom.itemmaster.itemdeductperiod.ItemDeductPeriodRepository;

@Stateless
@Transactional
public class DeleteItemDeductPeriodCommandHandler extends CommandHandler<DeleteItemDeductPeriodCommand> {

	@Inject
	private ItemDeductPeriodRepository itemDeductPeriodRepository;

	@Override
	protected void handle(CommandHandlerContext<DeleteItemDeductPeriodCommand> context) {
		this.itemDeductPeriodRepository.delete(context.getCommand().getItemCd());
	}

}
