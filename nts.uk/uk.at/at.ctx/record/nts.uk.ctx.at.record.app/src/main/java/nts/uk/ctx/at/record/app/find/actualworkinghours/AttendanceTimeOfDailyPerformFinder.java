package nts.uk.ctx.at.record.app.find.actualworkinghours;

import javax.ejb.Stateless;

import nts.uk.ctx.at.record.app.find.actualworkinghours.dto.AttendanceTimeDailyPerformDto;
import nts.uk.ctx.at.shared.app.util.attendanceitem.FinderFacade;

/** 日別実績の勤怠時間 finder */
@Stateless
public class AttendanceTimeOfDailyPerformFinder extends FinderFacade {

	@Override
	@SuppressWarnings("unchecked")
	public AttendanceTimeDailyPerformDto find(){
		//TODO: get 日別実績の勤怠時間 and convert to dto
		return new AttendanceTimeDailyPerformDto();
	}
}
