package nts.uk.screen.at.app.schedule.personal.correction.shiftdisplay;

import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;

import lombok.Data;
import nts.uk.ctx.at.shared.dom.workrule.shiftmaster.ShiftMaster;
import nts.uk.screen.at.app.schedule.personal.correction.WorkShiftScheduleDto;

/**
 * @author anhdt
 * 	予定・実績をシフトで取得する
 */
@Stateless
public class GetSchedulesAndAchievementsByShiftingQuery {
	
	public SchedulesAndAchievementDto getScheduleandAchievementsByShift(List<ShiftMaster> shifts) {
		
		return null;
	}
	
	@Data
	class SchedulesAndAchievementDto {
		private List<WorkShiftScheduleDto> schedules;
		private List<Map<Integer, Integer>> shiftWorkHolCat;
	}
}
