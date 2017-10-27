package nts.uk.ctx.at.schedule.app.command.shift.specificdayset.workplace;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.schedule.dom.shift.specificdayset.workplace.WorkplaceSpecificDateRepository;

@Stateless
public class DeleteWorkplaceSpecificDateCommandHandler extends CommandHandler<DeleteWorkplaceSpecificDateCommand> {

	@Inject
	private WorkplaceSpecificDateRepository repo;

	@Override
	protected void handle(CommandHandlerContext<DeleteWorkplaceSpecificDateCommand> context) {
		repo.DeleteWpSpecDate(context.getCommand().getWorkPlaceId(), context.getCommand().getYearMonth());
	}

}
