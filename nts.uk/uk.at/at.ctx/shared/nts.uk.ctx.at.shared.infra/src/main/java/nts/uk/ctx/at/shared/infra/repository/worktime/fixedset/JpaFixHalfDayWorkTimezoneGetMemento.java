/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.worktime.fixedset;

import nts.uk.ctx.at.shared.dom.worktime.common.FixedWorkTimezoneSet;
import nts.uk.ctx.at.shared.dom.worktime.fixedset.FixHalfDayWorkTimezoneGetMemento;
import nts.uk.ctx.at.shared.dom.worktime.fixedset.FixRestTimezoneSet;
import nts.uk.ctx.at.shared.dom.worktime_old.AmPmClassification;
import nts.uk.ctx.at.shared.infra.entity.worktime.KshmtFlexWorkSet;
import nts.uk.ctx.at.shared.infra.entity.worktime.KshmtFlexWorkSetPK;

/**
 * The Class JpaCoreTimeSettingGetMemento.
 */
public class JpaFixHalfDayWorkTimezoneGetMemento implements FixHalfDayWorkTimezoneGetMemento {
//	/** The entity. */
//	private KshmtFlexWorkSet entity;
//	
//	/**
//	 * Instantiates a new jpa core time setting get memento.
//	 *
//	 * @param entity the entity
//	 */
//	public JpaCoreTimeSettingGetMemento(KshmtFlexWorkSet entity) {
//		super();
//		if(entity.getKshmtFlexWorkSetPK() == null){
//			entity.setKshmtFlexWorkSetPK(new KshmtFlexWorkSetPK());
//		}
//		this.entity = entity;
//	}

	@Override
	public FixRestTimezoneSet getRestTimezone() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public FixedWorkTimezoneSet getWorkTimezone() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AmPmClassification getDayAtr() {
		// TODO Auto-generated method stub
		return null;
	}

}
