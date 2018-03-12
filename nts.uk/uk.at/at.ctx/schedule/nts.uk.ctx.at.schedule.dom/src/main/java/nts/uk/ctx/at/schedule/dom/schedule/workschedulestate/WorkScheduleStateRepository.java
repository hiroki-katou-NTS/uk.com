package nts.uk.ctx.at.schedule.dom.schedule.workschedulestate;

import java.util.List;

/**
 * 
 * @author sonnh1
 *
 */
public interface WorkScheduleStateRepository {
	/**
	 * find all data of WorkScheduleState
	 * 
	 * @return List WorkScheduleState
	 */
	public List<WorkScheduleState> findAll();

	/**
	 * update ScheduleEditState
	 * 
	 * @param domain
	 * @return
	 */
	public void updateScheduleEditState(WorkScheduleState domain);
}
