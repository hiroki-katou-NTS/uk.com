package nts.uk.ctx.at.shared.infra.repository.workingcondition;

import java.util.List;

import lombok.Setter;
import nts.uk.ctx.at.shared.dom.workingcondition.DateHistoryItem;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionSetMemento;
import nts.uk.ctx.at.shared.infra.entity.workingcondition.KshmtWorkingCond;
import nts.uk.ctx.at.shared.infra.entity.workingcondition.KshmtWorkingCondPK;


/**
 * The Class JpaWorkingConditionSetMemento.
 */
public class JpaWorkingConditionSetMemento implements WorkingConditionSetMemento {
	
	/** The kshmt working cond. */
	@Setter
	private KshmtWorkingCond kshmtWorkingCond;
	
	/**
	 * Instantiates a new jpa working condition set memento.
	 *
	 * @param entity the entity
	 */
	public JpaWorkingConditionSetMemento(KshmtWorkingCond entity) {
		if(entity.getKshmtWorkingCondPK() == null){
			entity.setKshmtWorkingCondPK(new KshmtWorkingCondPK());
		}
		this.kshmtWorkingCond = entity;
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionSetMemento#setCompanyId(java.lang.String)
	 */
	@Override
	public void setCompanyId(String companyId) {
		this.kshmtWorkingCond.setCid(companyId);
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionSetMemento#setEmployeeId(java.lang.String)
	 */
	@Override
	public void setEmployeeId(String employeeId) {
		this.kshmtWorkingCond.getKshmtWorkingCondPK().setSid(employeeId);
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionSetMemento#setDateHistoryItem(java.util.List)
	 */
	@Override
	public void setDateHistoryItem(List<DateHistoryItem> dateHistoryItem) {
		dateHistoryItem.stream().forEach(item -> {
			this.kshmtWorkingCond.setStrD(item.start());
			this.kshmtWorkingCond.setEndD(item.end());
		});
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionSetMemento#setHistoryId(java.lang.String)
	 */
	@Override
	public void setHistoryId(String historyId) {
		this.kshmtWorkingCond.getKshmtWorkingCondPK().setHistoryId(historyId);
	}

}
