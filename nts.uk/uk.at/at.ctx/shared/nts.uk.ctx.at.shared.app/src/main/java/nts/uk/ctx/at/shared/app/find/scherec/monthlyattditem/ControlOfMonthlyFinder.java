package nts.uk.ctx.at.shared.app.find.scherec.monthlyattditem;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.shared.dom.scherec.monthlyattendanceitem.ControlOfMonthlyItems;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattendanceitem.ControlOfMonthlyItemsRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class ControlOfMonthlyFinder {
	@Inject
	private ControlOfMonthlyItemsRepository repo;

	public ControlOfMonthlyDto getControlOfAttendanceItem(int itemMonthlyID) {
		String companyId = AppContexts.user().companyId();
		Optional<ControlOfMonthlyItems> data = repo.getControlOfMonthlyItem(companyId, itemMonthlyID);
		if (data.isPresent())
			return ControlOfMonthlyDto.fromDomain(data.get());
		return null;
	}
	
	public List<ControlOfMonthlyDto> getListControlOfAttendanceItem(List<Integer> itemMonthlyID) {
		String companyId = AppContexts.user().companyId();
		List<ControlOfMonthlyDto> data = repo.getListControlOfMonthlyItem(companyId, itemMonthlyID).stream().map(c -> ControlOfMonthlyDto.fromDomain(c)).collect(Collectors.toList());
		return data;
	}
	
}
