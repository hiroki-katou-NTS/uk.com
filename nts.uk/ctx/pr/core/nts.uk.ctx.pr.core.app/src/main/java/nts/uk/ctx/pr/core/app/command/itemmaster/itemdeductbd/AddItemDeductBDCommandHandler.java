package nts.uk.ctx.pr.core.app.command.itemmaster.itemdeductbd;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.pr.core.dom.itemmaster.itemdeductbd.ItemDeductBDRepository;

@Stateless
@Transactional
public class AddItemDeductBDCommandHandler extends CommandHandler<AddItemDeductBDCommand> {

	@Inject
	private ItemDeductBDRepository itemDeductBDRepo;

	@Override
	protected void handle(CommandHandlerContext<AddItemDeductBDCommand> context) {
		
		this.itemDeductBDRepo.add(context.getCommand().toDomain());
	}
}
