package nts.uk.ctx.at.shared.infra.repository.workingcondition;

import nts.uk.ctx.at.shared.dom.workingcondition.TimeZoneScheduledMasterAtr;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkScheduleBusCalSetMemento;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkScheduleMasterReferenceAtr;
import nts.uk.ctx.at.shared.infra.entity.workingcondition.KshmtScheduleMethod;


/**
 * The Class JpaWorkScheduleBusCalSetMemento.
 */
public class JpaWorkScheduleBusCalSetMemento implements WorkScheduleBusCalSetMemento {
	
	/** The kshmt schedule method. */
	private KshmtScheduleMethod kshmtScheduleMethod;
	
	/**
	 * Instantiates a new jpa work schedule bus cal set memento.
	 *
	 * @param entity the entity
	 */
	public JpaWorkScheduleBusCalSetMemento(KshmtScheduleMethod entity){
		this.kshmtScheduleMethod = entity;
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.workingcondition.WorkScheduleBusCalSetMemento#setReferenceBusinessDayCalendar(nts.uk.ctx.at.shared.dom.workingcondition.WorkScheduleMasterReferenceAtr)
	 */
	@Override
	public void setReferenceBusinessDayCalendar(WorkScheduleMasterReferenceAtr referenceBusinessDayCalendar) {
		this.kshmtScheduleMethod.setRefBusinessDayCalendar(referenceBusinessDayCalendar.value);
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.workingcondition.WorkScheduleBusCalSetMemento#setReferenceBasicWork(nts.uk.ctx.at.shared.dom.workingcondition.WorkScheduleMasterReferenceAtr)
	 */
	@Override
	public void setReferenceBasicWork(WorkScheduleMasterReferenceAtr referenceBasicWork) {
	this.kshmtScheduleMethod.setRefBasicWork(referenceBasicWork.value);
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.workingcondition.WorkScheduleBusCalSetMemento#setReferenceWorkingHours(nts.uk.ctx.at.shared.dom.workingcondition.TimeZoneScheduledMasterAtr)
	 */
	@Override
	public void setReferenceWorkingHours(TimeZoneScheduledMasterAtr referenceWorkingHours) {
		this.kshmtScheduleMethod.setRefWorkingHours(referenceWorkingHours.value);
	}
}
