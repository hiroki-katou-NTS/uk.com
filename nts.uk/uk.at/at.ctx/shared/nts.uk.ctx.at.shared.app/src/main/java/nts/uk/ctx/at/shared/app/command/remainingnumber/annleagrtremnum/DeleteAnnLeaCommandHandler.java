package nts.uk.ctx.at.shared.app.command.remainingnumber.annleagrtremnum;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.grantremainingdata.AnnLeaGrantRemDataRepository;

@Stateless
public class DeleteAnnLeaCommandHandler extends CommandHandler<DeleteLeaGrantRemnNumCommand>{
	
	@Inject
	private AnnLeaGrantRemDataRepository annLeaRepo;

	@Override
	protected void handle(CommandHandlerContext<DeleteLeaGrantRemnNumCommand> context) {
		DeleteLeaGrantRemnNumCommand command = context.getCommand();
		
		annLeaRepo.delete(command.getAnnLeavID());
		
	}

}
