package nts.uk.ctx.at.shared.dom.workingcondition;


/**
 * The Interface ScheduleMethodSetMemento.
 */
public interface ScheduleMethodSetMemento {
	
	/**
	 * Sets the basic create method.
	 *
	 * @param basicCreateMethod the new basic create method
	 */
	public void setBasicCreateMethod(WorkScheduleBasicCreMethod basicCreateMethod);
	
	/**
	 * Sets the work schedule bus cal.
	 *
	 * @param workScheduleBusCal the new work schedule bus cal
	 */
	public void setWorkScheduleBusCal(WorkScheduleBusCal workScheduleBusCal);
	
	/**
	 * Sets the monthly pattern work schedule cre.
	 *
	 * @param monthlyPatternWorkScheduleCre the new monthly pattern work schedule cre
	 */
	public void setMonthlyPatternWorkScheduleCre(MonthlyPatternWorkScheduleCre monthlyPatternWorkScheduleCre);
}
