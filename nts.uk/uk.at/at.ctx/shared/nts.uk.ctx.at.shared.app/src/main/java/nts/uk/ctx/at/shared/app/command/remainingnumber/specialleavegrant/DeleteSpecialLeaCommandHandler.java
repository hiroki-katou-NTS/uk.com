package nts.uk.ctx.at.shared.app.command.remainingnumber.specialleavegrant;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.shared.dom.remainingnumber.specialleave.empinfo.grantremainingdata.SpecialLeaveGrantRepository;

@Stateless
public class DeleteSpecialLeaCommandHandler extends CommandHandler<DeleteSpecialLeaCommand>{
	
	@Inject
	private SpecialLeaveGrantRepository repo;

	@Override
	protected void handle(CommandHandlerContext<DeleteSpecialLeaCommand> context) {
		DeleteSpecialLeaCommand command = context.getCommand();
		
		repo.delete(command.getSpecialid());
		
	}

}
