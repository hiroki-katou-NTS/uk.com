package nts.uk.ctx.at.record.app.command.stamp.card.stampcard.delete;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.StampCardRepository;
import nts.uk.shr.pereg.app.command.PeregDeleteCommandHandler;

@Stateless
public class DeleteStampCardCommandHandler extends CommandHandler<DeleteStampCardCommand>
	implements PeregDeleteCommandHandler<DeleteStampCardCommand>{

	
	@Inject
	private StampCardRepository stampCardRepo;

	@Override
	public String targetCategoryCd() {
		return "CS00069";
	}

	@Override
	public Class<?> commandClass() {
		return DeleteStampCardCommand.class;
	}

	@Override
	protected void handle(CommandHandlerContext<DeleteStampCardCommand> context) {

		DeleteStampCardCommand command = context.getCommand();
		
		stampCardRepo.delete(command.getStampCardId());
		
	}
	

}
