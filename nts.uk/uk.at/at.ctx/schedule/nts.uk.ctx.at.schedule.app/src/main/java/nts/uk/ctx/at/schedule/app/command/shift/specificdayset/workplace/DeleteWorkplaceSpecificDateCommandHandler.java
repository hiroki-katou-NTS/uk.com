package nts.uk.ctx.at.schedule.app.command.shift.specificdayset.workplace;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.schedule.dom.shift.specificdayset.workplace.WorkplaceSpecificDateRepository;

@Stateless
public class DeleteWorkplaceSpecificDateCommandHandler extends CommandHandler<DeleteWorkplaceSpecificDateCommand> {

	@Inject
	private WorkplaceSpecificDateRepository repo;
	
	private static final String DATE_FORMAT = "yyyy/MM/dd";

	@Override
	protected void handle(CommandHandlerContext<DeleteWorkplaceSpecificDateCommand> context) {
		GeneralDate startDate = GeneralDate.fromString(context.getCommand().getStartDate(), DATE_FORMAT);
		GeneralDate endDate = GeneralDate.fromString(context.getCommand().getEndDate(), DATE_FORMAT);
		repo.DeleteWpSpecDate(context.getCommand().getWorkPlaceId(), startDate, endDate);
	}

}
