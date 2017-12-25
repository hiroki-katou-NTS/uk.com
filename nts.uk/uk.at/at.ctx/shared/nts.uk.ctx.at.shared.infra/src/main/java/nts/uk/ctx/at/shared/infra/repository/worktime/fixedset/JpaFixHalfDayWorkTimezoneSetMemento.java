/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.worktime.fixedset;

import nts.uk.ctx.at.shared.dom.worktime.common.FixedWorkTimezoneSet;
import nts.uk.ctx.at.shared.dom.worktime.fixedset.FixHalfDayWorkTimezoneSetMemento;
import nts.uk.ctx.at.shared.dom.worktime.fixedset.FixRestTimezoneSet;
import nts.uk.ctx.at.shared.dom.worktime_old.AmPmClassification;
import nts.uk.ctx.at.shared.infra.entity.worktime.KshmtFlexWorkSet;
import nts.uk.ctx.at.shared.infra.entity.worktime.KshmtFlexWorkSetPK;

/**
 * The Class JpaCoreTimeSettingGetMemento.
 */
public class JpaFixHalfDayWorkTimezoneSetMemento implements FixHalfDayWorkTimezoneSetMemento {
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
	public void setRestTimezone(FixRestTimezoneSet restTimezone) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setWorkTimezone(FixedWorkTimezoneSet workTimezone) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setDayAtr(AmPmClassification dayAtr) {
		// TODO Auto-generated method stub
		
	}


}
