package nts.uk.ctx.at.record.app.find.dailyperform.attendanceleavinggate;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.app.find.dailyperform.attendanceleavinggate.dto.AttendanceLeavingGateOfDailyDto;
import nts.uk.ctx.at.record.dom.daily.attendanceleavinggate.repo.AttendanceLeavingGateOfDailyRepo;
import nts.uk.ctx.at.shared.app.util.attendanceitem.FinderFacade;

@Stateless
public class AttendanceLeavingGateOfDailyFinder extends FinderFacade {

	@Inject
	private AttendanceLeavingGateOfDailyRepo repo;

	@SuppressWarnings("unchecked")
	@Override
	public AttendanceLeavingGateOfDailyDto find(String employeeId, GeneralDate baseDate) {
		return AttendanceLeavingGateOfDailyDto.getDto(this.repo.find(employeeId, baseDate).orElse(null));
	}

}
