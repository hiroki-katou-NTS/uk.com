package nts.uk.ctx.at.shared.infra.repository.workingcondition;

import lombok.Setter;
import nts.uk.ctx.at.shared.dom.workingcondition.DateHistoryItemSetMemento;
import nts.uk.ctx.at.shared.infra.entity.workingcondition.KshmtWorkingCond;
import nts.uk.ctx.at.shared.infra.entity.workingcondition.KshmtWorkingCondPK;
import nts.uk.shr.com.time.calendar.period.DatePeriod;


/**
 * The Class JpaDateHistoryItemSetMemento.
 */
public class JpaDateHistoryItemSetMemento implements DateHistoryItemSetMemento {
	
	/**
	 * Sets the kshmt working cond.
	 *
	 * @param kshmtWorkingCond the new kshmt working cond
	 */
	@Setter
	private KshmtWorkingCond kshmtWorkingCond;
	
	/**
	 * Instantiates a new jpa date history item set memento.
	 *
	 * @param entity the entity
	 */
	public JpaDateHistoryItemSetMemento(KshmtWorkingCond entity) {
		if(entity.getKshmtWorkingCondPK() == null){
			entity.setKshmtWorkingCondPK(new KshmtWorkingCondPK());
		}
		this.kshmtWorkingCond = entity;
	}
	
	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.workingcondition.DateHistoryItemSetMemento#setHistoryId(java.lang.String)
	 */
	@Override
	public void setHistoryId(String historyId) {
		this.kshmtWorkingCond.getKshmtWorkingCondPK().setHistoryId(historyId);
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.workingcondition.DateHistoryItemSetMemento#setPeriod(nts.uk.shr.com.time.calendar.period.DatePeriod)
	 */
	@Override
	public void setPeriod(DatePeriod period) {
		this.kshmtWorkingCond.setStrD(period.start());
		this.kshmtWorkingCond.setEndD(period.start());
	}

}
