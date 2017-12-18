/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.worktime.flexset;

import nts.uk.ctx.at.shared.dom.worktime.common.AmPmAtr;
import nts.uk.ctx.at.shared.dom.worktime.common.FixedWorkTimezoneSet;
import nts.uk.ctx.at.shared.dom.worktime.common.FlowWorkRestTimezone;
import nts.uk.ctx.at.shared.dom.worktime.flexset.FlexHalfDayWorkTimeGetMemento;
import nts.uk.ctx.at.shared.infra.repository.worktime.flexset.KshmtFlexSettingGroup;

/**
 * The Class JpaFlexHAWorkTimeGetMemento.
 */
public class JpaFlexHAWorkTimeGetMemento implements FlexHalfDayWorkTimeGetMemento{

	/** The entity setting. */
	private KshmtFlexSettingGroup entitySetting;


	public JpaFlexHAWorkTimeGetMemento(KshmtFlexSettingGroup entitySetting) {
		super();
		this.entitySetting = entitySetting;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.flexset.FlexHalfDayWorkTimeGetMemento#
	 * getLstRestTimezone()
	 */
	@Override
	public FlowWorkRestTimezone getRestTimezone() {
		// TODO chua update entity & table design
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.flexset.FlexHalfDayWorkTimeGetMemento#
	 * getWorkTimezone()
	 */
	@Override
	public FixedWorkTimezoneSet getWorkTimezone() {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.flexset.FlexHalfDayWorkTimeGetMemento#
	 * getAmpmAtr()
	 */
	@Override
	public AmPmAtr getAmpmAtr() {
		// TODO Auto-generated method stub
		return null;
	}

}
