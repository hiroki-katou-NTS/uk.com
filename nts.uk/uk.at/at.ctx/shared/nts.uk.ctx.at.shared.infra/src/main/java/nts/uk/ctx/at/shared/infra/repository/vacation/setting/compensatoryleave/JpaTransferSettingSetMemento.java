/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.vacation.setting.compensatoryleave;

import nts.uk.ctx.at.shared.dom.worktime.common.DesignatedTime;
import nts.uk.ctx.at.shared.dom.worktime.common.OneDayTime;
import nts.uk.ctx.at.shared.dom.worktime.common.SubHolTransferSetAtr;
import nts.uk.ctx.at.shared.dom.worktime.common.SubHolTransferSetSetMemento;
import nts.uk.ctx.at.shared.infra.entity.vacation.setting.compensatoryleave.KocmtOccurrenceSet;

/**
 * The Class JpaTransferSettingSetMemento.
 */
public class JpaTransferSettingSetMemento implements SubHolTransferSetSetMemento {

	/** The entity. */
	private KocmtOccurrenceSet entity;

	/** The value true. */
	private static int VALUE_TRUE = 1;

	/** The value false. */
	private static int VALUE_FALSE = 0;

	/**
	 * Instantiates a new jpa transfer setting set memento.
	 *
	 * @param entity
	 *            the entity
	 */
	public JpaTransferSettingSetMemento(KocmtOccurrenceSet entity) {
		this.entity = entity;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.
	 * TransferSettingSetMemento#setCertainTime(nts.uk.ctx.at.shared.dom.
	 * vacation.setting.compensatoryleave.OneDayTime)
	 */
	@Override
	public void setCertainTime(OneDayTime certainTime) {
		if (certainTime != null) {
			this.entity.setCertainTime(certainTime.v().longValue());
		}
	}

	/**
	 * Sets the use division.
	 *
	 * @param useDivision
	 *            the new use division
	 */
	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.
	 * TransferSettingSetMemento#setUseDivision(boolean)
	 */
	@Override
	public void setUseDivision(boolean useDivision) {
		this.entity.setUseType(useDivision == true ? VALUE_TRUE : VALUE_FALSE);
	}

	@Override
	public void setDesignatedTime(DesignatedTime time) {
		if (time.getOneDayTime() != null) {
			this.entity.setOneDayTime(time.getOneDayTime().v().longValue());
		}
		if (time.getHalfDayTime() != null) {
			this.entity.setHalfDayTime(time.getHalfDayTime().v().longValue());
		}
	}

	@Override
	public void setSubHolTransferSetAtr(SubHolTransferSetAtr atr) {
		this.entity.setTransfType(atr.value);
	}

}
