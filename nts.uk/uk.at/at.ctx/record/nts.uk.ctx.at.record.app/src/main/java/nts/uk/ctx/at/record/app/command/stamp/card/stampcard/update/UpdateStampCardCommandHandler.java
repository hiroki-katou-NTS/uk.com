package nts.uk.ctx.at.record.app.command.stamp.card.stampcard.update;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.StampCard;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.StampCardRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.pereg.app.command.PeregUpdateCommandHandler;

@Stateless
public class UpdateStampCardCommandHandler extends CommandHandler<UpdateStampCardCommand>
		implements PeregUpdateCommandHandler<UpdateStampCardCommand> {
	
	@Inject
	private StampCardRepository stampCardRepo;

	@Override
	public String targetCategoryCd() {
		return "CS00069";
	}

	@Override
	public Class<?> commandClass() {
		return UpdateStampCardCommand.class;
	}

	@Override
	protected void handle(CommandHandlerContext<UpdateStampCardCommand> context) {
		UpdateStampCardCommand command = context.getCommand();
		
		// update domain
		StampCard stampCard = StampCard.createFromJavaType(command.getStampNumberId(), command.getEmployeeId(),
				command.getStampNumber(), GeneralDate.today(), AppContexts.user().contractCode());
		
		stampCardRepo.update(stampCard);
		
	}

}
