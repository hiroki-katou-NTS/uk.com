/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.worktime.fixedset;

import java.util.List;

import nts.uk.ctx.at.shared.dom.worktime.common.DeductionTime;
import nts.uk.ctx.at.shared.dom.worktime.fixedset.FixRestTimezoneSetSetMemento;
import nts.uk.ctx.at.shared.infra.entity.worktime.KshmtFlexWorkSet;
import nts.uk.ctx.at.shared.infra.entity.worktime.KshmtFlexWorkSetPK;

/**
 * The Class JpaCoreTimeSettingGetMemento.
 */
public class JpaFixRestTimezoneSetSetMemento implements FixRestTimezoneSetSetMemento {
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
	public void setLstTimezone(List<DeductionTime> lstTimezone) {
		// TODO Auto-generated method stub

	}
}
