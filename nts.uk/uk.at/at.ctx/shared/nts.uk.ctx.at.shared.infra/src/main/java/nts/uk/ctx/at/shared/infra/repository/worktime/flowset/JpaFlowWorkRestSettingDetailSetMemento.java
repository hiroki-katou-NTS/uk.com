/******************************************************************
 * Copyright (c) 2018 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.worktime.flowset;

import nts.uk.ctx.at.shared.dom.common.timerounding.TimeRoundingSetting;
import nts.uk.ctx.at.shared.dom.worktime.common.BooleanGetAtr;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlowFixedRestSet;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlowRestSet;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlowWorkRestSettingDetailSetMemento;
import nts.uk.ctx.at.shared.infra.entity.worktime.flowset.KshmtWtFloBrFlAll;

/**
 * The Class JpaFlowWorkRestSettingDetailSetMemento.
 */
public class JpaFlowWorkRestSettingDetailSetMemento implements FlowWorkRestSettingDetailSetMemento {

	/** The entity. */
	KshmtWtFloBrFlAll entity;

	/**
	 * Instantiates a new jpa flow work rest setting detail set memento.
	 *
	 * @param entity
	 *            the entity
	 */
	public JpaFlowWorkRestSettingDetailSetMemento(KshmtWtFloBrFlAll entity) {
		super();
		this.entity = entity;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.common.
	 * FlowWorkRestSettingDetailSetMemento#setFlowRestSetting(nts.uk.ctx.at.
	 * shared.dom.worktime.common.FlowRestSet)
	 */
	@Override
	public void setFlowRestSetting(FlowRestSet set) {
		set.saveToMemento(new JpaFlowRestSetSetMemento(this.entity));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.common.
	 * FlowWorkRestSettingDetailSetMemento#setFlowFixedRestSetting(nts.uk.ctx.at
	 * .shared.dom.worktime.common.FlowFixedRestSet)
	 */
	@Override
	public void setFlowFixedRestSetting(FlowFixedRestSet set) {
		set.saveToMemento(new JpaFlowFixedRestSetSetMemento(this.entity));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.common.
	 * FlowWorkRestSettingDetailSetMemento#setUsePluralWorkRestTime(boolean)
	 */
	@Override
	public void setUsePluralWorkRestTime(boolean val) {
		this.entity.setUsePluralWorkRestTime(BooleanGetAtr.getAtrByBoolean(val));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.flowset.
	 * FlowWorkRestSettingDetailSetMemento#setRoundingBreakMultipleWork(nts.uk.
	 * ctx.at.shared.dom.common.timerounding.TimeRoundingSetting)
	 */
	@Override
	public void setRoundingBreakMultipleWork(TimeRoundingSetting val) {
		this.entity.setRestSetUnit(val.getRoundingTime().value);
		this.entity.setRestSetRounding(val.getRounding().value);
	}

}
