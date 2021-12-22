package nts.uk.ctx.at.schedule.app.command.shift.specificdayset.workplace;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.schedule.dom.shift.specificdaysetting.WorkplaceSpecificDateRepository;

@Stateless
public class DeleteWorkplaceSpecificDateCommandHandler extends CommandHandler<DeleteWorkplaceSpecificDateCommand> {

	@Inject
	private WorkplaceSpecificDateRepository repo;
	
	private static final String DATE_FORMAT = "yyyy/MM/dd";

	@Override
	protected void handle(CommandHandlerContext<DeleteWorkplaceSpecificDateCommand> context) {
		DeleteWorkplaceSpecificDateCommand workplaceSpecificDateCommand = context.getCommand();
		GeneralDate startDate = GeneralDate.fromString(workplaceSpecificDateCommand.getStartDate(), DATE_FORMAT);
		GeneralDate endDate = GeneralDate.fromString(workplaceSpecificDateCommand.getEndDate(), DATE_FORMAT);
		DatePeriod period = new DatePeriod(startDate, endDate);
		this.repo.delete(workplaceSpecificDateCommand.getWorkPlaceId(), period);
	}

}
