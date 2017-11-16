/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.bs.employee.dom.workplace.affiliate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.GeneralDate;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

/**
 * The Class AffiliationWorkplaceHistory.
 */
// 所属職場履歴
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AffWorkplaceHistory extends AggregateRoot {

	/** The period. */
	// 期間
	private DatePeriod period;

	/** The employee id. */
	// 社員ID
	private String employeeId;

	/** The work place id. */
	// 職場ID
	private WorkplaceId workplaceId;

	/**
	 * Instantiates a new employee work place history.
	 *
	 * @param memento
	 *            the memento
	 */
	public AffWorkplaceHistory(AffWorkplaceHistoryGetMemento memento) {
		this.period = memento.getPeriod();
		this.employeeId = memento.getEmployeeId();
		this.workplaceId = memento.getWorkplaceId();
	}

	/**
	 * Save to memen to.
	 *
	 * @param memento
	 *            the memen to
	 */
	public void saveToMemento(AffWorkplaceHistorySetMemento memento) {
		memento.setPeriod(this.period);
		memento.setEmployeeId(this.employeeId);
		memento.setWorkplaceId(this.workplaceId);
	}
	
	public static AffWorkplaceHistory createFromJavaType(String workplaceId, GeneralDate startDate, GeneralDate endDate, String employeeId){
		return new AffWorkplaceHistory(new DatePeriod(startDate, endDate), employeeId, new WorkplaceId(workplaceId));
	}
}
