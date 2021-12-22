package nts.uk.ctx.at.schedule.app.find.shift.specificdayset.workplace;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.schedule.dom.shift.specificdaysetting.WorkplaceSpecificDateRepository;

@Stateless
public class WorkplaceSpecificDateFinder {
	@Inject
	WorkplaceSpecificDateRepository workplaceSpecDateRepo;
	
	private static final String DATE_FORMAT = "yyyy/MM/dd";
	
	// WITH name
	public List<WokplaceSpecificDateDto> getWpSpecByDateWithName(String workplaceId, String wpSpecDate) {
		GeneralDate startDate = GeneralDate.fromString(wpSpecDate, DATE_FORMAT);
		GeneralDate endDate = startDate.addMonths(1).addDays(-1);
		DatePeriod period = new DatePeriod(startDate, endDate);
		return this.workplaceSpecDateRepo.getList(workplaceId, period)
				.stream()
				.map(item -> {
					List<Integer> specificDateItemNo = item.getOneDaySpecificItem()
							.getSpecificDayItems()
							.stream()
							.map(itemNo -> itemNo.v())
							.collect(Collectors.toList());
					return new WokplaceSpecificDateDto(item.getWorkplaceId(), item.getSpecificDate(), specificDateItemNo);
				})
				.collect(Collectors.toList());
	}

}
