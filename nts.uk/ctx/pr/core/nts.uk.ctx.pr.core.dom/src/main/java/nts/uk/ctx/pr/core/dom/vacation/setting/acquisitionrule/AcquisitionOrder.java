/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.dom.vacation.setting.acquisitionrule;

import lombok.Getter;
import nts.arc.error.BusinessException;
import nts.arc.time.YearMonth;
import nts.uk.ctx.pr.core.dom.insurance.MonthRange;

/**
 * The Class VacationAcquisitionOrder.
 */
@Getter
public class AcquisitionOrder {
	
	/** The vacation type. */
	private AcquisitionType vacationType;
	
	/** The priority. */
	private Priority priority;
	
	/**
	 * Instantiates a new vacation acquisition order.
	 *
	 * @param memento the memento
	 */
	public AcquisitionOrder(AcquisitionOrderGetMemento memento){
		super();
		this.vacationType = memento.getVacationType();
		this.priority = memento.getPriority();
	}
	
	public void saveToMemento (AcquisitionOrderSetMemento memento){
		memento.setVacationType(this.vacationType);
		memento.setPriority(this.priority);
	}
}
