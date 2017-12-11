package nts.uk.ctx.at.shared.dom.workingcondition;

/**
 * The Interface WorkScheduleBusCalGetMemento.
 */
public interface WorkScheduleBusCalGetMemento {
	
	/**
	 * Gets the reference business day calendar.
	 *
	 * @return the reference business day calendar
	 */
	public WorkScheduleMasterReferenceAtr getReferenceBusinessDayCalendar();
	
	/**
	 * Gets the reference basic work.
	 *
	 * @return the reference basic work
	 */
	public WorkScheduleMasterReferenceAtr getReferenceBasicWork();
	
	/**
	 * Gets the reference working hours.
	 *
	 * @return the reference working hours
	 */
	public TimeZoneScheduledMasterAtr getReferenceWorkingHours();
}
