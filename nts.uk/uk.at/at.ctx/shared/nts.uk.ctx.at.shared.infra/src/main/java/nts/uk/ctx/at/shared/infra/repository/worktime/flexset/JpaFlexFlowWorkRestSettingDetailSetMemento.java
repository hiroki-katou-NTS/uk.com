/******************************************************************
 * Copyright (c) 2018 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.worktime.flexset;

import nts.uk.ctx.at.shared.dom.common.timerounding.TimeRoundingSetting;
import nts.uk.ctx.at.shared.dom.worktime.common.BooleanGetAtr;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlowFixedRestSet;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlowRestSet;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlowWorkRestSettingDetailSetMemento;
import nts.uk.ctx.at.shared.infra.entity.worktime.flexset.KshmtWtFleBrFl;
import nts.uk.ctx.at.shared.infra.entity.worktime.flexset.KshmtWtFleBrFlPK;
import nts.uk.ctx.at.shared.infra.repository.worktime.common.JpaFlexFlowFixedRestSetSetMemento;
import nts.uk.ctx.at.shared.infra.repository.worktime.common.JpaFlexFlowRestSetSetMemento;

/**
 * The Class JpaFlexFlowWorkRestSettingDetailSetMemento.
 */
public class JpaFlexFlowWorkRestSettingDetailSetMemento implements FlowWorkRestSettingDetailSetMemento {

	/** The entity. */
	private KshmtWtFleBrFl entity;

	/**
	 * Instantiates a new jpa flex flow work rest setting detail set memento.
	 *
	 * @param entity
	 *            the entity
	 */
	public JpaFlexFlowWorkRestSettingDetailSetMemento(KshmtWtFleBrFl entity) {
		super();
		if (entity.getKshmtWtFleBrFlPK() == null) {
			entity.setKshmtWtFleBrFlPK(new KshmtWtFleBrFlPK());
		}
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
		set.saveToMemento(new JpaFlexFlowRestSetSetMemento(this.entity));
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
		set.saveToMemento(new JpaFlexFlowFixedRestSetSetMemento(this.entity));
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
