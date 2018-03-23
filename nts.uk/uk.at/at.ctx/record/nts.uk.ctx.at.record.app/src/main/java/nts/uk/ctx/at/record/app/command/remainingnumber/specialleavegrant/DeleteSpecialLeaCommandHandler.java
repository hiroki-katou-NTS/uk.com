package nts.uk.ctx.at.record.app.command.remainingnumber.specialleavegrant;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.AsyncCommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.record.dom.remainingnumber.specialleave.empinfo.grantremainingdata.SpecialLeaveGrantRepository;

@Stateless
public class DeleteSpecialLeaCommandHandler extends AsyncCommandHandler<DeleteSpecialLeaCommand>{
	
	@Inject
	private SpecialLeaveGrantRepository repo;

	@Override
	protected void handle(CommandHandlerContext<DeleteSpecialLeaCommand> context) {
		DeleteSpecialLeaCommand command = context.getCommand();
		
		repo.delete(command.getEmployeeId(),command.getSpecialCode() ,command.getGrantDate());
		
	}

}
