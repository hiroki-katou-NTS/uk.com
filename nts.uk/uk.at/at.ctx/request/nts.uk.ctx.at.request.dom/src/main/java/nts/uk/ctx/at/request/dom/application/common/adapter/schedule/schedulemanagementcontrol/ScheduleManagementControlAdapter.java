package nts.uk.ctx.at.request.dom.application.common.adapter.schedule.schedulemanagementcontrol;

import nts.arc.time.GeneralDate;

/**
 * 
 * @author Doan Duy Hung
 *
 */
public interface ScheduleManagementControlAdapter {
	
	public Boolean isScheduleManagementAtr(String employeeID);
	//RequestList536
	public boolean isScheduleManagementAtr(String employeeId, GeneralDate baseDate);
}
