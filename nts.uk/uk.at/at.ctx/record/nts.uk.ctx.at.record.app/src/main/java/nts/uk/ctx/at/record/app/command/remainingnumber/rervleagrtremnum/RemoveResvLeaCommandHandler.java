package nts.uk.ctx.at.record.app.command.remainingnumber.rervleagrtremnum;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.AsyncCommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.record.dom.remainingnumber.reserveleave.empinfo.grantremainingdata.RervLeaGrantRemDataRepository;

@Stateless
public class RemoveResvLeaCommandHandler extends AsyncCommandHandler<RemoveResvLeaRemainCommand>{
	
	@Inject
	private RervLeaGrantRemDataRepository resvLeaRepo;

	@Override
	protected void handle(CommandHandlerContext<RemoveResvLeaRemainCommand> context) {
		resvLeaRepo.delete(context.getCommand().getRvsLeaId());
	}
}
