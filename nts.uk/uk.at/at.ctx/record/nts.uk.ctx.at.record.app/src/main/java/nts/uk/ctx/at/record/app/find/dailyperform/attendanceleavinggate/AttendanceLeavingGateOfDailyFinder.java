package nts.uk.ctx.at.record.app.find.dailyperform.attendanceleavinggate;

import javax.ejb.Stateless;

import nts.uk.ctx.at.record.app.find.dailyperform.entrance.dto.AttendanceLeavingGateOfDailyDto;
import nts.uk.ctx.at.shared.app.util.attendanceitem.FinderFacade;

@Stateless
public class AttendanceLeavingGateOfDailyFinder extends FinderFacade {

	@SuppressWarnings("unchecked")
	@Override
	public AttendanceLeavingGateOfDailyDto find() {
		// TODO Auto-generated method stub
		return new AttendanceLeavingGateOfDailyDto();
	}

}
