package nts.uk.ctx.at.shared.infra.repository.workingcondition;

import java.util.Arrays;
import java.util.List;

import lombok.Setter;
import nts.uk.ctx.at.shared.dom.workingcondition.DateHistoryItem;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionGetMemento;
import nts.uk.ctx.at.shared.infra.entity.workingcondition.KshmtWorkingCond;


/**
 * The Class JpaWorkingConditionGetMemento.
 */
public class JpaWorkingConditionGetMemento implements WorkingConditionGetMemento {
	
	/** The kshmt working cond. */
	@Setter
	private KshmtWorkingCond kshmtWorkingCond;
	
	/**
	 * Instantiates a new jpa working condition get memento.
	 *
	 * @param kshmtWorkingCond the kshmt working cond
	 */
	public JpaWorkingConditionGetMemento(KshmtWorkingCond kshmtWorkingCond) {
		this.kshmtWorkingCond = kshmtWorkingCond;
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionGetMemento#getCompanyId()
	 */
	@Override
	public String getCompanyId() {
		return this.kshmtWorkingCond.getCid();
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionGetMemento#getEmployeeId()
	 */
	@Override
	public String getEmployeeId() {
		return this.kshmtWorkingCond.getKshmtWorkingCondPK().getSid();
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionGetMemento#getDateHistoryItem()
	 */
	@Override
	public List<DateHistoryItem> getDateHistoryItem() {
		return Arrays.asList(new DateHistoryItem(new JpaDateHistoryItemGetMemento(this.kshmtWorkingCond)));
	}
	
	
	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionGetMemento#getHistoryId()
	 */
	@Override
	public String getHistoryId() {
		return this.kshmtWorkingCond.getKshmtWorkingCondPK().getHistoryId();
	}

}
