/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.worktime.flexset;

import nts.uk.ctx.at.shared.dom.worktime.common.AmPmAtr;
import nts.uk.ctx.at.shared.dom.worktime.common.FixedWorkTimezoneSet;
import nts.uk.ctx.at.shared.dom.worktime.common.FlowWorkRestTimezone;
import nts.uk.ctx.at.shared.dom.worktime.flexset.FlexHalfDayWorkTimeSetMemento;
import nts.uk.ctx.at.shared.infra.entity.worktime.flexset.KshmtFlexSettingGroup;

/**
 * The Class JpaFlexHAWorkTimeSetMemento.
 */
public class JpaFlexHAWorkTimeSetMemento implements FlexHalfDayWorkTimeSetMemento{

	/** The entity setting. */
	private KshmtFlexSettingGroup entitySetting;


	/**
	 * Instantiates a new jpa flex HA work time set memento.
	 *
	 * @param entitySetting the entity setting
	 */
	public JpaFlexHAWorkTimeSetMemento(KshmtFlexSettingGroup entitySetting) {
		super();
		this.entitySetting = entitySetting;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.flexset.FlexHalfDayWorkTimeSetMemento#
	 * setRestTimezone(nts.uk.ctx.at.shared.dom.worktime.common.
	 * FlowWorkRestTimezone)
	 */
	@Override
	public void setRestTimezone(FlowWorkRestTimezone lstRestTimezone) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.flexset.FlexHalfDayWorkTimeSetMemento#
	 * setWorkTimezone(nts.uk.ctx.at.shared.dom.worktime.common.
	 * FixedWorkTimezoneSet)
	 */
	@Override
	public void setWorkTimezone(FixedWorkTimezoneSet workTimezone) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.flexset.FlexHalfDayWorkTimeSetMemento#
	 * setAmpmAtr(nts.uk.ctx.at.shared.dom.worktime.common.AmPmAtr)
	 */
	@Override
	public void setAmpmAtr(AmPmAtr ampmAtr) {
		// TODO Auto-generated method stub

	}

}
