package nts.uk.ctx.at.schedule.app.command.shift.specificdayset.company;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.schedule.dom.shift.specificdaysetting.CompanySpecificDateItem;
import nts.uk.ctx.at.schedule.dom.shift.specificdaysetting.CompanySpecificDateRepository;
import nts.uk.ctx.at.schedule.dom.shift.specificdaysetting.OneDaySpecificItem;
import nts.uk.ctx.at.schedule.dom.shift.specificdaysetting.SpecificDateItemNo;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class InsertCompanySpecificDateCommandHandler extends CommandHandler<List<CompanySpecificDateCommand>> {

	@Inject
	private CompanySpecificDateRepository repo;
	
	private static final String DATE_FORMAT = "yyyy/MM/dd";

	@Override
	protected void handle(CommandHandlerContext<List<CompanySpecificDateCommand>> context) {
		String companyId = AppContexts.user().companyId();
		
		for (CompanySpecificDateCommand command : context.getCommand()) {
			GeneralDate date = GeneralDate.fromString(command.getSpecificDate(), DATE_FORMAT);
			Optional<CompanySpecificDateItem> existData = this.repo.get(companyId, date);
			List<SpecificDateItemNo> specificDayItems = command.getSpecificDateNo().stream()
					.map(item -> new SpecificDateItemNo(item))
					.collect(Collectors.toList());
			if (existData.isPresent()) {
				this.repo.delete(companyId, date);
			}
			if (!specificDayItems.isEmpty()) {
				this.repo.insert(new CompanySpecificDateItem(companyId, date, OneDaySpecificItem.create(specificDayItems)));
			}
		}
	}

}
