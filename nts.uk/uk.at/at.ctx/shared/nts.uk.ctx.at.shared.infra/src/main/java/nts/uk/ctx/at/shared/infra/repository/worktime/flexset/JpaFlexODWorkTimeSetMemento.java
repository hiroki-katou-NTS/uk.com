/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.worktime.flexset;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.shared.dom.worktime.common.FlowWorkRestTimezone;
import nts.uk.ctx.at.shared.dom.worktime.common.HDWorkTimeSheetSetting;
import nts.uk.ctx.at.shared.dom.worktime.flexset.FlexOffdayWorkTimeSetMemento;
import nts.uk.ctx.at.shared.infra.entity.worktime.KshmtFlexHolSet;
import nts.uk.ctx.at.shared.infra.entity.worktime.KshmtFlexHolSetPK;
import nts.uk.ctx.at.shared.infra.entity.worktime.KshmtFlexOdRtSet;
import nts.uk.ctx.at.shared.infra.repository.worktime.common.JpaFlexODFlWRestTzSetMemento;
import nts.uk.ctx.at.shared.infra.repository.worktime.common.JpaFlexOffdayHDWTSheetSetMemento;

/**
 * The Class JpaFlexODWorkTimeGetMemento.
 */
public class JpaFlexODWorkTimeSetMemento implements FlexOffdayWorkTimeSetMemento{
	
	/** The entity. */
	private KshmtFlexOdRtSet entity;
	

	/**
	 * Instantiates a new jpa flex OD work time set memento.
	 *
	 * @param entity the entity
	 */
	public JpaFlexODWorkTimeSetMemento(KshmtFlexOdRtSet entity) {
		super();
		this.entity = entity;
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.worktime.flexset.FlexOffdayWorkTimeSetMemento#setLstWorkTimezone(java.util.List)
	 */
	@Override
	public void setLstWorkTimezone(List<HDWorkTimeSheetSetting> lstWorkTimezone) {
		if (CollectionUtil.isEmpty(lstWorkTimezone)) {
			this.entity.setKshmtFlexHolSets(new ArrayList<>());
		} else {
			this.entity.setKshmtFlexHolSets(lstWorkTimezone.stream().map(domain -> {
				KshmtFlexHolSet entity = new KshmtFlexHolSet(
						new KshmtFlexHolSetPK(this.entity.getKshmtFlexOdRtSetPK().getCid(),
								this.entity.getKshmtFlexOdRtSetPK().getWorktimeCd()));
				domain.saveToMemento(new JpaFlexOffdayHDWTSheetSetMemento(entity));
				return entity;
			}).collect(Collectors.toList()));
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.flexset.FlexOffdayWorkTimeSetMemento#
	 * setRestTimezone(nts.uk.ctx.at.shared.dom.worktime.common.
	 * FlowWorkRestTimezone)
	 */
	@Override
	public void setRestTimezone(FlowWorkRestTimezone restTimezone) {
		if (restTimezone != null) {
			restTimezone.saveToMemento(new JpaFlexODFlWRestTzSetMemento(this.entity));
		}
	}
	
}
