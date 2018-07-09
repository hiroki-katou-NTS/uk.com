package nts.uk.ctx.at.schedule.app.command.shift.specificdayset.workplace;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.schedule.dom.shift.specificdayset.workplace.WorkplaceSpecificDateItem;
import nts.uk.ctx.at.schedule.dom.shift.specificdayset.workplace.WorkplaceSpecificDateRepository;

@Stateless
public class InsertWorkplaceSpecificDateCommandHandler extends CommandHandler<List<WorkplaceSpecificDateCommand>> {

	@Inject
	private WorkplaceSpecificDateRepository repo;
	
	private static final String DATE_FORMAT = "yyyy/MM/dd";

	@Override
	protected void handle(CommandHandlerContext<List<WorkplaceSpecificDateCommand>> context) {
		for(WorkplaceSpecificDateCommand workplaceSpecificDateCommand :  context.getCommand()){
			GeneralDate date = GeneralDate.fromString(workplaceSpecificDateCommand.getSpecificDate(), DATE_FORMAT);
			List<WorkplaceSpecificDateItem> listInsert = new ArrayList<WorkplaceSpecificDateItem>();
			for(Integer specificDateNo : workplaceSpecificDateCommand.getSpecificDateItemNo()){
				listInsert.add(WorkplaceSpecificDateItem.createFromJavaType(
						workplaceSpecificDateCommand.getWorkPlaceId(),
						date,
						specificDateNo,
						"empty")
				);
			}
			repo.InsertWpSpecDate(listInsert);
		}
	}

}
