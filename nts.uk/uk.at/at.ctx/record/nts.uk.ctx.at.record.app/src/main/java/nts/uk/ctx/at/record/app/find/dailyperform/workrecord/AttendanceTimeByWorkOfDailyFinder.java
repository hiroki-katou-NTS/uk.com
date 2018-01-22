package nts.uk.ctx.at.record.app.find.dailyperform.workrecord;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.app.find.dailyperform.workrecord.dto.AttendanceTimeByWorkOfDailyDto;
import nts.uk.ctx.at.record.dom.actualworkinghours.daily.workrecord.repo.AttendanceTimeByWorkOfDailyRepository;
import nts.uk.ctx.at.shared.app.util.attendanceitem.FinderFacade;

@Stateless
public class AttendanceTimeByWorkOfDailyFinder extends FinderFacade {

	@Inject
	private AttendanceTimeByWorkOfDailyRepository repo;

	@SuppressWarnings("unchecked")
	@Override
	public AttendanceTimeByWorkOfDailyDto find(String employeeId, GeneralDate baseDate) {
		return AttendanceTimeByWorkOfDailyDto.getDto(this.repo.find(employeeId, baseDate).orElse(null));
	}

}
