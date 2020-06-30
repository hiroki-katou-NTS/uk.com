package nts.uk.screen.at.app.schedule.personal.correction.shiftdisplay;

import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.Data;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.shared.dom.workrule.shiftmaster.ShiftMaster;
import nts.uk.screen.at.app.schedule.personal.correction.WorkShiftScheduleDto;
import nts.uk.screen.at.app.schedule.personal.correction.shiftdisplay.GetWorkScheduleQuery.WorkScheduleDto;

/**
 * @author anhdt
 * 	予定・実績をシフトで取得する
 */
@Stateless
public class GetSchedulesAndAchievementsByShiftingQuery {
	
	@Inject
	private GetWorkScheduleQuery workScheduleQuery;
	
	@Inject
	private GetWorkRecordQuery getWorkRecordQuery;
	
	public SchedulesAndAchievementDto getScheduleandAchievementsByShift(List<ShiftMaster> shifts, List<String> empIds, DatePeriod period, boolean acquireAchievements ) {
		// 1.1 call() 勤務予定（シフト）を取得する
		WorkScheduleDto workSche1 = workScheduleQuery.getWorkSchedule(shifts, empIds, period);
		
		if(acquireAchievements) {
			// 2.2
			WorkScheduleDto workSche2 = getWorkRecordQuery.get(shifts, empIds, period);
		}
		
		return null;
	}
	
	@Data
	class SchedulesAndAchievementDto {
		private List<WorkShiftScheduleDto> schedules;
		private List<Map<Integer, Integer>> shiftWorkHolCat;
	}
}
