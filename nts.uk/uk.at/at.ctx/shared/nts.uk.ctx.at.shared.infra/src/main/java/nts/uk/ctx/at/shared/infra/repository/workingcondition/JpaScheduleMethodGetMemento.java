package nts.uk.ctx.at.shared.infra.repository.workingcondition;

import nts.uk.ctx.at.shared.dom.workingcondition.MonthlyPatternWorkScheduleCre;
import nts.uk.ctx.at.shared.dom.workingcondition.ScheduleMethodGetMemento;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkScheduleBasicCreMethod;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkScheduleBusCal;
import nts.uk.ctx.at.shared.infra.entity.workingcondition.KshmtScheduleMethod;


/**
 * The Class JpaScheduleMethodGetMemento.
 */
public class JpaScheduleMethodGetMemento implements ScheduleMethodGetMemento {

	/** The kshmt schedule method. */
	private KshmtScheduleMethod kshmtScheduleMethod;
	
	/**
	 * Instantiates a new jpa schedule method get memento.
	 *
	 * @param entity the entity
	 */
	public JpaScheduleMethodGetMemento(KshmtScheduleMethod entity){
		this.kshmtScheduleMethod = entity;
	}
	
	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.workingcondition.ScheduleMethodGetMemento#getBasicCreateMethod()
	 */
	@Override
	public WorkScheduleBasicCreMethod getBasicCreateMethod() {
		return WorkScheduleBasicCreMethod.valueOf(this.kshmtScheduleMethod.getBasicCreateMethod());
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.workingcondition.ScheduleMethodGetMemento#getWorkScheduleBusCal()
	 */
	@Override
	public WorkScheduleBusCal getWorkScheduleBusCal() {
		return new WorkScheduleBusCal(new JpaWorkScheduleBusCalGetMemento(this.kshmtScheduleMethod));
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.workingcondition.ScheduleMethodGetMemento#getMonthlyPatternWorkScheduleCre()
	 */
	@Override
	public MonthlyPatternWorkScheduleCre getMonthlyPatternWorkScheduleCre() {
		return new MonthlyPatternWorkScheduleCre(new JpaMonthlyPatternWorkScheduleCreGetMemento(this.kshmtScheduleMethod));
	}

}
