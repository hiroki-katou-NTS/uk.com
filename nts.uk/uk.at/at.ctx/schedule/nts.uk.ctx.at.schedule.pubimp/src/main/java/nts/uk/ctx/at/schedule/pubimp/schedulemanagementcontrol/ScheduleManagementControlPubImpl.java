/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.pubimp.schedulemanagementcontrol;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.schedule.pub.schedulemanagementcontrol.ScheduleManagementControlPub;
import nts.uk.ctx.at.shared.dom.workingcondition.ManageAtr;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItem;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItemRepository;

/**
 * The Class ScheduleManagementControlPubImpl.
 */
@Stateless
public class ScheduleManagementControlPubImpl implements ScheduleManagementControlPub {

	/** The repository. */
	@Inject
	// private ScheduleManagementControlRepository repository;
	private WorkingConditionItemRepository repository;

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.schedule.pub.schedulemanagementcontrol.
	 * ScheduleManagementControlPub#isScheduleManagementAtr(java.lang.String)
	 */
	@Override
	public boolean isScheduleManagementAtr(String employeeId, GeneralDate baseDate) {
		Optional<WorkingConditionItem> optionalScheduleManagementControl = this.repository
				.getBySidAndStandardDate(employeeId, baseDate);
		
		if (!optionalScheduleManagementControl.isPresent()) {
			return false;
		}
		
		return ManageAtr.USE
				.equals(optionalScheduleManagementControl.get().getScheduleManagementAtr());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.schedule.pub.schedulemanagementcontrol.
	 * ScheduleManagementControlPub#isScheduleManagementAtr(java.lang.String)
	 */
	@Override
	public boolean isScheduleManagementAtr(String employeeId) {
		Optional<WorkingConditionItem> optionalScheduleManagementControl = this.repository
				.getBySidAndStandardDate(employeeId, GeneralDate.today());

		if (!optionalScheduleManagementControl.isPresent()) {
			return false;
		}

		return ManageAtr.USE
				.equals(optionalScheduleManagementControl.get().getScheduleManagementAtr());
	}

}
