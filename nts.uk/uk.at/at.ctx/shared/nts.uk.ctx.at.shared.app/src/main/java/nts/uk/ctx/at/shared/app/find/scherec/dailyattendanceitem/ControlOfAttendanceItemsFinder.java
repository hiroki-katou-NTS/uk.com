package nts.uk.ctx.at.shared.app.find.scherec.dailyattendanceitem;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.ControlOfAttendanceItems;
import nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.repository.ControlOfAttendanceItemsRepository;

@Stateless
public class ControlOfAttendanceItemsFinder {

	@Inject
	private ControlOfAttendanceItemsRepository repo;
	
	public ControlOfAttendanceItemsDto getControlOfAttendanceItem(String companyID,int itemDailyID) {
		Optional<ControlOfAttendanceItems> data = repo.getControlOfAttendanceItem(companyID, itemDailyID);
		if(data.isPresent())
			return ControlOfAttendanceItemsDto.fromDomain(data.get());
		return null;
	}
	
}
