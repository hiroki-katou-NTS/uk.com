package nts.uk.ctx.at.shared.dom.workingcondition;

import java.util.Optional;

/**
 * The Interface ScheduleMethodGetMemento.
 */
public interface ScheduleMethodGetMemento {
	
	/**
	 * Gets the basic create method.
	 *
	 * @return the basic create method
	 */
	WorkScheduleBasicCreMethod getBasicCreateMethod();
	
	/**
	 * Gets the work schedule bus cal.
	 *
	 * @return the work schedule bus cal
	 */
	Optional<WorkScheduleBusCal> getWorkScheduleBusCal();
	
	/**
	 * Gets the monthly pattern work schedule cre.
	 *
	 * @return the monthly pattern work schedule cre
	 */
	Optional<MonthlyPatternWorkScheduleCre> getMonthlyPatternWorkScheduleCre();
}
