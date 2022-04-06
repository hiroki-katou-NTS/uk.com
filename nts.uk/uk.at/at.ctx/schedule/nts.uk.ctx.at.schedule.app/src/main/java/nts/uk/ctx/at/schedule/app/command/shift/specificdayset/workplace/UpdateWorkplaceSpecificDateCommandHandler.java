package nts.uk.ctx.at.schedule.app.command.shift.specificdayset.workplace;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.schedule.dom.shift.specificdaysetting.OneDaySpecificItem;
import nts.uk.ctx.at.schedule.dom.shift.specificdaysetting.SpecificDateItemNo;
import nts.uk.ctx.at.schedule.dom.shift.specificdaysetting.WorkplaceSpecificDateItem;
import nts.uk.ctx.at.schedule.dom.shift.specificdaysetting.WorkplaceSpecificDateRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class UpdateWorkplaceSpecificDateCommandHandler extends CommandHandler<List<WorkplaceSpecificDateCommand>>{

	@Inject
	private WorkplaceSpecificDateRepository repo;
	
	private static final String DATE_FORMAT = "yyyy/MM/dd";
	
	@Override
	protected void handle(CommandHandlerContext<List<WorkplaceSpecificDateCommand>> context) {
		String companyId = AppContexts.user().companyId();
		for(WorkplaceSpecificDateCommand workplaceSpecificDateCommand :  context.getCommand()){
			GeneralDate date = GeneralDate.fromString(workplaceSpecificDateCommand.getSpecificDate(), DATE_FORMAT);
			List<SpecificDateItemNo> specificDayItems = workplaceSpecificDateCommand.getSpecificDateItemNo().stream()
					.map(itemNo -> new SpecificDateItemNo(itemNo))
					.collect(Collectors.toList());
			WorkplaceSpecificDateItem workplaceSpecificDateItem = new WorkplaceSpecificDateItem(
					workplaceSpecificDateCommand.getWorkPlaceId(),
					date,
					OneDaySpecificItem.create(specificDayItems));
			this.repo.update(companyId, workplaceSpecificDateItem);
		}
	}

}
