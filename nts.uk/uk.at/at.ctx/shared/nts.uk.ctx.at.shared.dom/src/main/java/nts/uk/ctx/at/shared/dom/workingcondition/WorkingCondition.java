/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.workingcondition;

import java.util.List;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.GeneralDate;
import nts.uk.shr.com.history.DateHistoryItem;
import nts.uk.shr.com.history.strategic.PersistentResidentHistory;
import nts.uk.shr.com.time.calendar.period.DatePeriod;


/**
 * The Class WorkingCondition.
 */
@Getter
// 労働条件
public class WorkingCondition extends AggregateRoot implements PersistentResidentHistory<DateHistoryItem, DatePeriod, GeneralDate> {
	
	/** The company id. */
	// 会社ID
	private String companyId;
	
	/** The employee id. */
	// 社員ID
	private String employeeId;
	
	/** The date history item. */
	// 履歴項目
	public List<DateHistoryItem> dateHistoryItem;
	
	/**
	 * Instantiates a new working condition.
	 *
	 * @param memento the memento
	 */
	public WorkingCondition(WorkingConditionGetMemento memento){
		this.companyId = memento.getCompanyId();
		this.employeeId = memento.getEmployeeId();
		this.dateHistoryItem = memento.getDateHistoryItem();
	}
	
	/**
	 * Save to memento.
	 *
	 * @param memento
	 *            the memento
	 */
	public void saveToMemento(WorkingConditionSetMemento memento) {
		memento.setCompanyId(this.companyId);
		memento.setEmployeeId(this.employeeId);
		memento.setDateHistoryItem(this.dateHistoryItem);
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((employeeId == null) ? 0 : employeeId.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof WorkingCondition))
			return false;
		WorkingCondition other = (WorkingCondition) obj;
		if (employeeId == null) {
			if (other.employeeId != null)
				return false;
		} else if (!employeeId.equals(other.employeeId))
			return false;
		return true;
	}

	/* (non-Javadoc)
	 * @see nts.uk.shr.com.history.History#items()
	 */
	@Override
	public List<DateHistoryItem> items() {
		return this.dateHistoryItem;
	}

	public WorkingCondition(String companyId, String employeeId, List<DateHistoryItem> dateHistoryItem) {
		super();
		this.companyId = companyId;
		this.employeeId = employeeId;
		this.dateHistoryItem = dateHistoryItem;
	}
	
	
}
