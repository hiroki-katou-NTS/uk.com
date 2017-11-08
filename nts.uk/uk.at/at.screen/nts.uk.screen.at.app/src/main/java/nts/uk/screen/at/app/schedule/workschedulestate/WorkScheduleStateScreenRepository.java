package nts.uk.screen.at.app.schedule.workschedulestate;

import java.util.List;

import nts.arc.time.GeneralDate;
/**
 * 
 * @author sonnh1
 *
 */
public interface WorkScheduleStateScreenRepository {
	/**
	 * 
	 * @param sId
	 * @param startDate
	 * @param endDate
	 * @param scheduleId = 1->4
	 * @return List WorkScheduleStateScreenDto
	 */
	List<WorkScheduleStateScreenDto> getByListSidAndDateAndScheId(List<String> sId, GeneralDate startDate, GeneralDate endDate);
}
