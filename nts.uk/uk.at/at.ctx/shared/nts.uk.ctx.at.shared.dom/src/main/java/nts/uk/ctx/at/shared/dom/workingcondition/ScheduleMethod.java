package nts.uk.ctx.at.shared.dom.workingcondition;

import lombok.Getter;
import nts.arc.layer.dom.DomainObject;

/**
 * The Class ScheduleMethod.
 */
@Getter
public class ScheduleMethod extends DomainObject {
	
	/** The basic create method. */
	private WorkScheduleBasicCreMethod basicCreateMethod;
	
	/** The work schedule bus cal. */
	private WorkScheduleBusCal workScheduleBusCal;
	
	/** The monthly pattern work schedule cre. */
	private MonthlyPatternWorkScheduleCre monthlyPatternWorkScheduleCre;
	
	
	/**
	 * Instantiates a new schedule method.
	 *
	 * @param memento the memento
	 */
	public ScheduleMethod(ScheduleMethodGetMemento memento){
		this.basicCreateMethod = memento.getBasicCreateMethod();
		this.workScheduleBusCal = memento.getWorkScheduleBusCal();
		this.monthlyPatternWorkScheduleCre = memento.getMonthlyPatternWorkScheduleCre();
	}
	
	/**
	 * Save to memento.
	 *
	 * @param memento
	 *            the memento
	 */
	public void saveToMemento(ScheduleMethodSetMemento memento) {
		memento.setBasicCreateMethod(this.basicCreateMethod);
		memento.setWorkScheduleBusCal(this.workScheduleBusCal);
		memento.setMonthlyPatternWorkScheduleCre(this.monthlyPatternWorkScheduleCre);
	}
}
