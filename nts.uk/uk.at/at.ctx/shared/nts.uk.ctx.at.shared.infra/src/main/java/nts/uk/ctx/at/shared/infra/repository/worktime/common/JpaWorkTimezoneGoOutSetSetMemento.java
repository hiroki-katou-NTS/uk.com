/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.worktime.common;

import nts.uk.ctx.at.shared.dom.worktime.common.GoOutTimeRoundingMethod;
import nts.uk.ctx.at.shared.dom.worktime.common.GoOutTimezoneRoundingSet;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimezoneGoOutSetSetMemento;
import nts.uk.ctx.at.shared.infra.entity.worktime.common.KshmtWtCom;
import nts.uk.ctx.at.shared.infra.entity.worktime.common.KshmtWtComGoout;
import nts.uk.ctx.at.shared.infra.entity.worktime.common.KshmtWorktimeGoOutSetPK;

/**
 * The Class JpaWorkTimezoneGoOutSetSetMemento.
 */
public class JpaWorkTimezoneGoOutSetSetMemento implements WorkTimezoneGoOutSetSetMemento {

	/** The entity. */
	private KshmtWtCom entity;

	/**
	 * Instantiates a new jpa work timezone go out set set memento.
	 *
	 * @param entity the entity
	 */
	public JpaWorkTimezoneGoOutSetSetMemento(KshmtWtCom entity) {
		super();
		this.entity = entity;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.common.WorkTimezoneGoOutSetSetMemento#
	 * setTotalRoundingSet(nts.uk.ctx.at.shared.dom.worktime.common.
	 * GoOutTimeRoundingMethod)
	 */
	@Override
	public void setRoundingMethod(GoOutTimeRoundingMethod set) {
		if (this.entity.getKshmtWtComGoout() == null) {			
			KshmtWorktimeGoOutSetPK pk = new KshmtWorktimeGoOutSetPK(this.entity.getKshmtWorktimeCommonSetPK().getCid(),
					this.entity.getKshmtWorktimeCommonSetPK().getWorktimeCd(),
					this.entity.getKshmtWorktimeCommonSetPK().getWorkFormAtr(),
					this.entity.getKshmtWorktimeCommonSetPK().getWorktimeSetMethod());
			KshmtWtComGoout entity = new KshmtWtComGoout();
			entity.setKshmtWorktimeGoOutSetPK(pk);
			this.entity.setKshmtWtComGoout(entity);
		}
		this.entity.getKshmtWtComGoout().setRoundingMethod(set.value);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.common.WorkTimezoneGoOutSetSetMemento#
	 * setDiffTimezoneSetting(nts.uk.ctx.at.shared.dom.worktime.common.
	 * GoOutTimezoneRoundingSet)
	 */
	@Override
	public void setDiffTimezoneSetting(GoOutTimezoneRoundingSet set) {
		set.saveToMememto(new JpaGoOutTimezoneRoundingSetSetMemento(this.entity));
	}

}
