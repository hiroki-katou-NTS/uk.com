package nts.uk.ctx.at.shared.dom.workingcondition;

import lombok.Getter;
import nts.arc.layer.dom.DomainObject;


/**
 * The Class WorkScheduleBusCal.
 */
@Getter
public class WorkScheduleBusCal extends DomainObject {
	
	/** The reference business day calendar. */
	private WorkScheduleMasterReferenceAtr referenceBusinessDayCalendar;
	
	/** The reference basic work. */
	private WorkScheduleMasterReferenceAtr referenceBasicWork;
	
	/** The reference working hours. */
	private TimeZoneScheduledMasterAtr referenceWorkingHours;
	
	
	/**
	 * Instantiates a new work schedule bus cal.
	 *
	 * @param memento the memento
	 */
	public WorkScheduleBusCal(WorkScheduleBusCalGetMemento memento){
		this.referenceBusinessDayCalendar = memento.getReferenceBusinessDayCalendar();
		this.referenceBasicWork = memento.getReferenceBasicWork();
		this.referenceWorkingHours = memento.getReferenceWorkingHours();
	}
	
	/**
	 * Save to memento.
	 *
	 * @param memento
	 *            the memento
	 */
	public void saveToMemento(WorkScheduleBusCalSetMemento memento) {
		memento.setReferenceBusinessDayCalendar(this.referenceBusinessDayCalendar);
		memento.setReferenceBasicWork(this.referenceBasicWork);
		memento.setReferenceWorkingHours(this.referenceWorkingHours);
	}
}
