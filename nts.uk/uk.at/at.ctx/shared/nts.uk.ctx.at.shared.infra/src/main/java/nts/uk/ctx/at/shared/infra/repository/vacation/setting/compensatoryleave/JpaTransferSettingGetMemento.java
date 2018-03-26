/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.vacation.setting.compensatoryleave;

import nts.uk.ctx.at.shared.dom.worktime.common.DesignatedTime;
import nts.uk.ctx.at.shared.dom.worktime.common.OneDayTime;
import nts.uk.ctx.at.shared.dom.worktime.common.SubHolTransferSetAtr;
import nts.uk.ctx.at.shared.dom.worktime.common.SubHolTransferSetGetMemento;
import nts.uk.ctx.at.shared.infra.entity.vacation.setting.compensatoryleave.KocmtOccurrenceSet;

/**
 * The Class JpaTransferSettingGetMemento.
 */
public class JpaTransferSettingGetMemento implements SubHolTransferSetGetMemento {

	/** The true. */
	private static int TRUE = 1;

	/** The entity. */
	private KocmtOccurrenceSet entity;

	/**
	 * Instantiates a new jpa transfer setting get memento.
	 *
	 * @param entity
	 *            the entity
	 */
	public JpaTransferSettingGetMemento(KocmtOccurrenceSet entity) {
		this.entity = entity;
	}

	/**
	 * Gets the certain time.
	 *
	 * @return the certain time
	 */
	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.
	 * TransferSettingGetMemento#getCertainTime()
	 */
	@Override
	public OneDayTime getCertainTime() {
		return new OneDayTime(this.entity.getCertainTime().intValue());
	}

	@Override
	public boolean getUseDivision() {
		return this.entity.getUseType() == TRUE ? true : false;
	}

	@Override
	public DesignatedTime getDesignatedTime() {
		return new DesignatedTime(new OneDayTime(this.entity.getOneDayTime().intValue()),
				new OneDayTime(this.entity.getHalfDayTime().intValue()));
	}

	@Override
	public SubHolTransferSetAtr getSubHolTransferSetAtr() {
		return SubHolTransferSetAtr.valueOf(this.entity.getTransfType());
	}

}
