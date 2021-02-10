/******************************************************************
 * Copyright (c) 2018 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.worktime.common;

import nts.uk.ctx.at.shared.dom.workdayoff.frame.NotUseAtr;
import nts.uk.ctx.at.shared.dom.worktime.common.HolidayCalculationSetMemento;
import nts.uk.ctx.at.shared.infra.entity.worktime.common.KshmtWtCom;

/**
 * The Class JpaHolidayCalculationSetMemento.
 */
public class JpaHolidayCalculationSetMemento implements HolidayCalculationSetMemento {

	/** The entity. */
	private KshmtWtCom entity;

	/**
	 * Instantiates a new jpa holiday calculation set memento.
	 *
	 * @param entity
	 *            the entity
	 */
	public JpaHolidayCalculationSetMemento(KshmtWtCom entity) {
		super();
		this.entity = entity;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.common.HolidayCalculationSetMemento#
	 * setIsCalculate(nts.uk.ctx.at.shared.dom.workdayoff.frame.NotUseAtr)
	 */
	@Override
	public void setIsCalculate(NotUseAtr isCalculate) {
		this.entity.setHolidayCalcIsCalculate(isCalculate.value);
	}

}
