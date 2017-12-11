package nts.uk.ctx.at.shared.infra.repository.workingcondition;

import lombok.Setter;
import nts.uk.ctx.at.shared.dom.workingcondition.DateHistoryItemGetMemento;
import nts.uk.ctx.at.shared.infra.entity.workingcondition.KshmtWorkingCond;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

/**
 * The Class JpaDateHistoryItemGetMemento.
 */
public class JpaDateHistoryItemGetMemento implements DateHistoryItemGetMemento {
	
	/** The kshmt working cond. */
	@Setter
	private KshmtWorkingCond kshmtWorkingCond;
	
	/**
	 * Instantiates a new jpa date history item get memento.
	 *
	 * @param entity the entity
	 */
	public JpaDateHistoryItemGetMemento(KshmtWorkingCond entity){
		this.kshmtWorkingCond = entity;
	}
	
	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.workingcondition.DateHistoryItemGetMemento#getHistoryId()
	 */
	@Override
	public String getHistoryId() {
		return this.kshmtWorkingCond.getKshmtWorkingCondPK().getHistoryId();
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.workingcondition.DateHistoryItemGetMemento#getPeriod()
	 */
	@Override
	public DatePeriod getPeriod() {
		return new DatePeriod(this.kshmtWorkingCond.getStrD(), this.kshmtWorkingCond.getEndD());
	}

}
