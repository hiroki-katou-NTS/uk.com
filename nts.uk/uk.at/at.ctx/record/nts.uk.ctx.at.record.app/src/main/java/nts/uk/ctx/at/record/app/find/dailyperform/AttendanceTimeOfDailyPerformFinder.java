package nts.uk.ctx.at.record.app.find.dailyperform;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.app.find.dailyperform.dto.ActualWorkTimeDailyPerformDto;
import nts.uk.ctx.at.record.app.find.dailyperform.dto.AttendanceTimeDailyPerformDto;
import nts.uk.ctx.at.record.app.find.dailyperform.dto.MedicalTimeDailyPerformDto;
import nts.uk.ctx.at.record.app.find.dailyperform.dto.StayingTimeDto;
import nts.uk.ctx.at.record.app.find.dailyperform.dto.WorkScheduleTimeDailyPerformDto;
import nts.uk.ctx.at.record.dom.actualworkinghours.AttendanceTimeOfDailyPerformance;
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
		AttendanceTimeOfDailyPerformance domain = attendanceTimeRepo.find(employeeId, baseDate).orElse(null);
		if (domain != null) {
			return toDto(domain);
		}
		return new AttendanceTimeDailyPerformDto();
	}

	private AttendanceTimeDailyPerformDto toDto(AttendanceTimeOfDailyPerformance domain) {
		AttendanceTimeDailyPerformDto items = new AttendanceTimeDailyPerformDto();
		items.setEmployeeID(domain.getEmployeeId());
		items.setDate(domain.getYmd());
		items.setActualWorkTime(ActualWorkTimeDailyPerformDto.toActualWorkTime(domain.getActualWorkingTimeOfDaily()));
		items.setBudgetTimeVariance(domain.getBudgetTimeVariance().valueAsMinutes());
		items.setDate(domain.getYmd());
		items.setEmployeeID(domain.getEmployeeId());
		items.setMedicalTime(MedicalTimeDailyPerformDto.fromMedicalCareTime(domain.getMedicalCareTime()));
		items.setScheduleTime(WorkScheduleTimeDailyPerformDto.fromWorkScheduleTime(domain.getWorkScheduleTimeOfDaily()));
		items.setStayingTime(StayingTimeDto.fromStayingTime(domain.getStayingTime()));
		items.setUnemployedTime(domain.getUnEmployedTime().valueAsMinutes());
		return items;
	}
	
}
