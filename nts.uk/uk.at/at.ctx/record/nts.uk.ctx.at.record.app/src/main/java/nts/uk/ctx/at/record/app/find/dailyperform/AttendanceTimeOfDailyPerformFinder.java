package nts.uk.ctx.at.record.app.find.dailyperform;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.app.find.dailyperform.dto.AttendanceTimeDailyPerformDto;
import nts.uk.ctx.at.record.dom.actualworkinghours.repository.AttendanceTimeRepository;
import nts.uk.ctx.at.shared.app.util.attendanceitem.FinderFacade;

/** 日別実績の勤怠時間 finder */
@Stateless
public class AttendanceTimeOfDailyPerformFinder extends FinderFacade {

	@Inject
	private AttendanceTimeRepository attendanceTimeRepo;

	@Override
	@SuppressWarnings("unchecked")
	public AttendanceTimeDailyPerformDto find(String employeeId, GeneralDate baseDate) {
		return AttendanceTimeDailyPerformDto.getDto(attendanceTimeRepo.find(employeeId, baseDate).orElse(null));
	}

}
