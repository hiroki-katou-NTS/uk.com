package nts.uk.ctx.at.schedule.app.find.shift.specificdayset.workplace;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.schedule.dom.shift.specificdayset.workplace.WorkplaceSpecificDateRepository;

@Stateless
public class WorkplaceSpecificDateFinder {
	@Inject
	WorkplaceSpecificDateRepository workplaceSpecDateRepo;
	// WITH name
	public List<WokplaceSpecificDateDto> getWpSpecByDateWithName(String workplaceId, String wpSpecDate, int useAtr) {
		return workplaceSpecDateRepo.getWpSpecByDateWithName(workplaceId, wpSpecDate, useAtr)
				.stream()
				.map(item -> WokplaceSpecificDateDto.fromDomain(item))
				.collect(Collectors.toList());
	}

}
