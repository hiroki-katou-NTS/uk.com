package nts.uk.ctx.at.shared.infra.repository.workingcondition;

import nts.uk.ctx.at.shared.dom.workingcondition.TimeZoneScheduledMasterAtr;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkScheduleBusCalGetMemento;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkScheduleMasterReferenceAtr;
import nts.uk.ctx.at.shared.infra.entity.workingcondition.KshmtWorkcondScheMeth;


/**
 * The Class JpaWorkScheduleBusCalGetMemento.
 */
public class JpaWorkScheduleBusCalGetMemento implements WorkScheduleBusCalGetMemento {
	
	/** The kshmt schedule method. */
	private KshmtWorkcondScheMeth kshmtWorkcondScheMeth;
	
	/**
	 * Instantiates a new jpa work schedule bus cal get memento.
	 *
	 * @param entity the entity
	 */
	public JpaWorkScheduleBusCalGetMemento(KshmtWorkcondScheMeth entity){
		this.kshmtWorkcondScheMeth = entity;
	}
	
	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.workingcondition.WorkScheduleBusCalGetMemento#getReferenceBusinessDayCalendar()
	 */
	@Override
	public WorkScheduleMasterReferenceAtr getReferenceBusinessDayCalendar() {
		try {
			return WorkScheduleMasterReferenceAtr.valueOf(this.kshmtWorkcondScheMeth.getRefBusinessDayCalendar());
		} catch (Exception e) {
			return WorkScheduleMasterReferenceAtr.WORK_PLACE;
		}
		
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.workingcondition.WorkScheduleBusCalGetMemento#getReferenceBasicWork()
	 */
	@Override
	public WorkScheduleMasterReferenceAtr getReferenceBasicWork() {
		try {
			return WorkScheduleMasterReferenceAtr.valueOf(this.kshmtWorkcondScheMeth.getRefBasicWork());
		} catch (Exception e) {
			return WorkScheduleMasterReferenceAtr.WORK_PLACE;
		}
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.workingcondition.WorkScheduleBusCalGetMemento#getReferenceWorkingHours()
	 */
	@Override
	public TimeZoneScheduledMasterAtr getReferenceWorkingHours() {
		try {
			return TimeZoneScheduledMasterAtr.valueOf(this.kshmtWorkcondScheMeth.getRefWorkingHours());
		} catch (Exception e) {
			return TimeZoneScheduledMasterAtr.FOLLOW_MASTER_REFERENCE;
		}
	}
	
}
