package nts.uk.ctx.at.request.ac.schedule.schedulemanagementcontrol;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.dom.application.common.adapter.schedule.schedulemanagementcontrol.ScheduleManagementControlAdapter;
import nts.uk.ctx.at.schedule.pub.schedulemanagementcontrol.ScheduleManagementControlPub;
/**
 * 
 * @author Doan Duy Hung
 *
 */
@Stateless
public class ScheduleManagementControlAdapterImpl implements ScheduleManagementControlAdapter {

	@Inject
	private ScheduleManagementControlPub scheduleManagementControlPub;
	
	@Override
	public Boolean isScheduleManagementAtr(String employeeID) {
		return scheduleManagementControlPub.isScheduleManagementAtr(employeeID);
	}

	@Override
	public boolean isScheduleManagementAtr(String employeeId, GeneralDate baseDate) {
		return scheduleManagementControlPub.isScheduleManagementAtr(employeeId, baseDate);
	}

}
