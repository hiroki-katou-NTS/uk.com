/******************************************************************
 * Copyright (c) 2018 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.worktime.common;

import nts.uk.ctx.at.shared.dom.workdayoff.frame.NotUseAtr;
import nts.uk.ctx.at.shared.dom.worktime.common.HolidayCalculationGetMemento;
import nts.uk.ctx.at.shared.infra.entity.worktime.common.KshmtWtCom;

/**
 * The Class JpaHolidayCalculationGetMemento.
 */
public class JpaHolidayCalculationGetMemento implements HolidayCalculationGetMemento {

	/** The entity. */
	private KshmtWtCom entity;

	/**
	 * Instantiates a new jpa holiday calculation get memento.
	 *
	 * @param entity
	 *            the entity
	 */
	public JpaHolidayCalculationGetMemento(KshmtWtCom entity) {
		super();
		this.entity = entity;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.common.HolidayCalculationGetMemento#
	 * getIsCalculate()
	 */
	@Override
	public NotUseAtr getIsCalculate() {
		return NotUseAtr.valueOf(this.entity.getHolidayCalcIsCalculate());
	}

}
