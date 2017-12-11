package nts.uk.ctx.at.shared.dom.workingcondition;


/**
 * The Interface WorkScheduleBusCalSetMemento.
 */
public interface WorkScheduleBusCalSetMemento {
	
	/**
	 * Sets the reference business day calendar.
	 *
	 * @param referenceBusinessDayCalendar the new reference business day calendar
	 */
	public void setReferenceBusinessDayCalendar(WorkScheduleMasterReferenceAtr referenceBusinessDayCalendar);
	
	/**
	 * Sets the reference basic work.
	 *
	 * @param referenceBasicWork the new reference basic work
	 */
	public void setReferenceBasicWork(WorkScheduleMasterReferenceAtr referenceBasicWork);
	
	/**
	 * Sets the reference working hours.
	 *
	 * @param referenceWorkingHours the new reference working hours
	 */
	public void setReferenceWorkingHours(TimeZoneScheduledMasterAtr referenceWorkingHours);
}
