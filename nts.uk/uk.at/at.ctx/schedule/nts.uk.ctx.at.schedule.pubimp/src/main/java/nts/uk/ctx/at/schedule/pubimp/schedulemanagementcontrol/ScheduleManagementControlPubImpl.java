/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.pubimp.schedulemanagementcontrol;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.schedule.dom.schedulemanagementcontrol.ScheduleManagementControl;
import nts.uk.ctx.at.schedule.dom.schedulemanagementcontrol.ScheduleManagementControlRepository;
import nts.uk.ctx.at.schedule.pub.schedulemanagementcontrol.ScheduleManagementControlPub;

/**
 * The Class ScheduleManagementControlPubImpl.
 */
@Stateless
public class ScheduleManagementControlPubImpl implements ScheduleManagementControlPub{

	/** The repository. */
	@Inject
	private ScheduleManagementControlRepository repository;
	
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.schedule.pub.schedulemanagementcontrol.
	 * ScheduleManagementControlPub#isScheduleManagementAtr(java.lang.String)
	 */
	@Override
	public boolean isScheduleManagementAtr(String employeeId) {
		Optional<ScheduleManagementControl> optionalScheduleManagementControl = this.repository.findById(employeeId);
		if(optionalScheduleManagementControl.isPresent()){
			return optionalScheduleManagementControl.get().isScheduleManagementAtr();
		}
		return false;
	}

}
