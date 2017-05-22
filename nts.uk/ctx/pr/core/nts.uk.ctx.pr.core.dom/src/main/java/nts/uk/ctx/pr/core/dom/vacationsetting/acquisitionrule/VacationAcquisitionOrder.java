/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.dom.vacationsetting.acquisitionrule;

import lombok.Getter;
import nts.arc.error.BusinessException;
import nts.arc.time.YearMonth;
import nts.uk.ctx.pr.core.dom.insurance.MonthRange;

/**
 * The Class VacationAcquisitionOrder.
 */
@Getter
public class VacationAcquisitionOrder {
	
	/** The vacation type. */
	private VacationType vacationType;
	
	/** The priority. */
	private int priority;
	
	/**
	 * Instantiates a new vacation acquisition order.
	 *
	 * @param memento the memento
	 */
	public VacationAcquisitionOrder(VaAcOrderGetMemento memento){
		super();
		this.vacationType = memento.getVacationType();
		this.priority = memento.getPriority();
	}
	
	public void saveToMemento (VaAcOrderSetMemento memento){
		memento.setVacationType(this.vacationType);
		memento.setPriority(this.priority);
	}
}
