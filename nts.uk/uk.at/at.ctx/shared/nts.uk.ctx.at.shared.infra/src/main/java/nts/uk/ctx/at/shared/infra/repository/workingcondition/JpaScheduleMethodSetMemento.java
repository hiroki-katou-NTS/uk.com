package nts.uk.ctx.at.shared.infra.repository.workingcondition;

import nts.uk.ctx.at.shared.dom.workingcondition.MonthlyPatternWorkScheduleCre;
import nts.uk.ctx.at.shared.dom.workingcondition.ScheduleMethodSetMemento;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkScheduleBasicCreMethod;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkScheduleBusCal;
import nts.uk.ctx.at.shared.infra.entity.workingcondition.KshmtScheduleMethod;


/**
 * The Class JpaScheduleMethodSetMemento.
 */
public class JpaScheduleMethodSetMemento implements ScheduleMethodSetMemento {

	/** The kshmt schedule method. */
	private KshmtScheduleMethod kshmtScheduleMethod;
	
	/**
	 * Instantiates a new jpa schedule method set memento.
	 *
	 * @param entity the entity
	 */
	public JpaScheduleMethodSetMemento(KshmtScheduleMethod entity){
		this.kshmtScheduleMethod = entity;
	}
	
	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.workingcondition.ScheduleMethodSetMemento#setBasicCreateMethod(nts.uk.ctx.at.shared.dom.workingcondition.WorkScheduleBasicCreMethod)
	 */
	@Override
	public void setBasicCreateMethod(WorkScheduleBasicCreMethod basicCreateMethod) {
		this.kshmtScheduleMethod.setBasicCreateMethod(basicCreateMethod.value);
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.workingcondition.ScheduleMethodSetMemento#setWorkScheduleBusCal(nts.uk.ctx.at.shared.dom.workingcondition.WorkScheduleBusCal)
	 */
	@Override
	public void setWorkScheduleBusCal(WorkScheduleBusCal workScheduleBusCal) {
		workScheduleBusCal.saveToMemento(new JpaWorkScheduleBusCalSetMemento(this.kshmtScheduleMethod));
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.workingcondition.ScheduleMethodSetMemento#setMonthlyPatternWorkScheduleCre(nts.uk.ctx.at.shared.dom.workingcondition.MonthlyPatternWorkScheduleCre)
	 */
	@Override
	public void setMonthlyPatternWorkScheduleCre(MonthlyPatternWorkScheduleCre monthlyPatternWorkScheduleCre) {
		monthlyPatternWorkScheduleCre.saveToMemento(new JpaMonthlyPatternWorkScheduleCreSetMemento(this.kshmtScheduleMethod));
	}

}
