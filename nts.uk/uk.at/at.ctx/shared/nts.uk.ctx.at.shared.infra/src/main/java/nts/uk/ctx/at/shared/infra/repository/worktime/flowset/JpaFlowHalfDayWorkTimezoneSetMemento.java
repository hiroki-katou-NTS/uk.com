/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.worktime.flowset;

import nts.uk.ctx.at.shared.dom.worktime.flowset.FlowHalfDayWtzSetMemento;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlowWorkRestTimezone;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlowWorkTimezoneSetting;
import nts.uk.ctx.at.shared.infra.entity.worktime.flowset.KshmtWtFloBrFl;
import nts.uk.ctx.at.shared.infra.entity.worktime.flowset.KshmtWtFloBrFlPK;
import nts.uk.ctx.at.shared.infra.entity.worktime.flowset.KshmtWtFlo;

/**
 * The Class JpaFlowHalfDayWorkTimezoneSetMemento.
 */
public class JpaFlowHalfDayWorkTimezoneSetMemento implements FlowHalfDayWtzSetMemento {

	/** The entity. */
	private KshmtWtFlo entity;
	
	/**
	 * Instantiates a new jpa flow half day work timezone set memento.
	 *
	 * @param entity the entity
	 */
	public JpaFlowHalfDayWorkTimezoneSetMemento(KshmtWtFlo entity) {
		super();
		this.entity = entity;
	}
	
	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.worktime.flowset.FlHalfDayWtzSetMemento#setRestTimezone(nts.uk.ctx.at.shared.dom.worktime.common.FlowWorkRestTimezone)
	 */
	@Override
	public void setRestTimezone(FlowWorkRestTimezone tzone) {
		KshmtWtFloBrFl halfDayEntity = this.entity.getFlowHalfDayWorkRtSet();
		if (halfDayEntity == null) {
			KshmtWtFloBrFlPK pk = new KshmtWtFloBrFlPK();
			pk.setCid(this.entity.getKshmtWtFloPK().getCid());
			pk.setWorktimeCd(this.entity.getKshmtWtFloPK().getWorktimeCd());
			pk.setResttimeAtr(ResttimeAtr.HALF_DAY.value);
			
			halfDayEntity = new KshmtWtFloBrFl();
			halfDayEntity.setKshmtWtFloBrFlPK(pk);
			this.entity.getLstKshmtWtFloBrFl().add(halfDayEntity);
		}			
		tzone.saveToMemento(new JpaFlowWorkRestTimezoneSetMemento(this.entity.getFlowHalfDayWorkRtSet()));
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.worktime.flowset.FlHalfDayWtzSetMemento#setWorkTimeZone(nts.uk.ctx.at.shared.dom.worktime.flowset.FlowWorkTimezoneSetting)
	 */
	@Override
	public void setWorkTimeZone(FlowWorkTimezoneSetting tzone) {
		tzone.saveToMemento(new JpaFlowWorkTimezoneSettingSetMemento(this.entity));
	}

}
