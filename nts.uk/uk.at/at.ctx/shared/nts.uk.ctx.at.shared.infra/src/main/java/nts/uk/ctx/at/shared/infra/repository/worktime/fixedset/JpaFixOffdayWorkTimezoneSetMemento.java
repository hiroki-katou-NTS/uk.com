/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.worktime.fixedset;

import java.util.List;

import nts.uk.ctx.at.shared.dom.worktime.common.HDWorkTimeSheetSetting;
import nts.uk.ctx.at.shared.dom.worktime.fixedset.FixOffdayWorkTimezoneSetMemento;
import nts.uk.ctx.at.shared.dom.worktime.fixedset.FixRestTimezoneSet;
import nts.uk.ctx.at.shared.infra.entity.worktime.KshmtFlexWorkSet;
import nts.uk.ctx.at.shared.infra.entity.worktime.KshmtFlexWorkSetPK;

/**
 * The Class JpaCoreTimeSettingGetMemento.
 */
public class JpaFixOffdayWorkTimezoneSetMemento implements FixOffdayWorkTimezoneSetMemento {
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
	public void setLstWorkTimezone(List<HDWorkTimeSheetSetting> lstWorkTimezone) {
		// TODO Auto-generated method stub

	}

}
