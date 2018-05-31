package nts.uk.ctx.at.shared.app.find.scherec.dailyattendanceitem;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.ControlOfAttendanceItems;
import nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.repository.ControlOfAttendanceItemsRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class ControlOfAttendanceItemsFinder {

	@Inject
	private ControlOfAttendanceItemsRepository repo;

	public ControlOfAttendanceItemsDto getControlOfAttendanceItem(int itemDailyID) {
		String companyId = AppContexts.user().companyId();
		Optional<ControlOfAttendanceItems> data = repo.getControlOfAttendanceItem(companyId, itemDailyID);
		if (data.isPresent())
			return ControlOfAttendanceItemsDto.fromDomain(data.get());
		return null;
	}

}
