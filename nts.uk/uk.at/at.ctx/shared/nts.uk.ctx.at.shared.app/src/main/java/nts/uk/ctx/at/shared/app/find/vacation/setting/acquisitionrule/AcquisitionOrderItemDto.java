/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.find.vacation.setting.acquisitionrule;

import lombok.Builder;
import nts.uk.ctx.at.shared.dom.vacation.setting.acquisitionrule.AcquisitionOrderSetMemento;
import nts.uk.ctx.at.shared.dom.vacation.setting.acquisitionrule.AcquisitionType;
import nts.uk.ctx.at.shared.dom.vacation.setting.acquisitionrule.Priority;

/**
 * The Class VacationAcquisitionOrderItemDto.
 */
@Builder
public class AcquisitionOrderItemDto implements AcquisitionOrderSetMemento {

	/** The vacation type. */
	public int vacationType;

	/** The priority. */
	public int priority;

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.vacation.setting.acquisitionrule.
	 * AcquisitionOrderSetMemento#setVacationType(nts.uk.ctx.at.shared.dom.
	 * vacation.setting.acquisitionrule.AcquisitionType)
	 */
	@Override
	public void setVacationType(AcquisitionType vacationType) {
		this.vacationType = vacationType.value;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.vacation.setting.acquisitionrule.
	 * AcquisitionOrderSetMemento#setPriority(nts.uk.ctx.at.shared.dom.vacation.
	 * setting.acquisitionrule.Priority)
	 */
	@Override
	public void setPriority(Priority priority) {
		this.priority = priority.v();

	}

}
