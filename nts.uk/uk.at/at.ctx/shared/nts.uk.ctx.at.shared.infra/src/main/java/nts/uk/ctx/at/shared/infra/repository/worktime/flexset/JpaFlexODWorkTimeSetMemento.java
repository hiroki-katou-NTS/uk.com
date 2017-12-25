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
import nts.uk.ctx.at.shared.infra.entity.worktime.flexset.KshmtFlexArrayGroup;
import nts.uk.ctx.at.shared.infra.entity.worktime.flexset.KshmtFlexSetGroup;
import nts.uk.ctx.at.shared.infra.repository.worktime.common.JpaFlexODFlWRestTzSetMemento;
import nts.uk.ctx.at.shared.infra.repository.worktime.common.JpaFlexOffdayHDWTSheetSetMemento;

/**
 * The Class JpaFlexODWorkTimeGetMemento.
 */
public class JpaFlexODWorkTimeSetMemento implements FlexOffdayWorkTimeSetMemento{
	
	/** The entity array group. */
	private KshmtFlexArrayGroup entityArrayGroup;
	
	/** The entity set group. */
	private KshmtFlexSetGroup entitySetGroup;
	
	/**
	 * Instantiates a new jpa flex OD work time get memento.
	 *
	 * @param entityArrayGroup the entity array group
	 * @param entitySetGroup the entity set group
	 */
	public JpaFlexODWorkTimeSetMemento(KshmtFlexArrayGroup entityArrayGroup, KshmtFlexSetGroup entitySetGroup) {
		super();
		this.entityArrayGroup = entityArrayGroup;
		this.entitySetGroup = entitySetGroup;
	}



	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.worktime.flexset.FlexOffdayWorkTimeSetMemento#setLstWorkTimezone(java.util.List)
	 */
	@Override
	public void setLstWorkTimezone(List<HDWorkTimeSheetSetting> lstWorkTimezone) {
		if(CollectionUtil.isEmpty(lstWorkTimezone)){
			this.entityArrayGroup.setEntityWorktimezones(new ArrayList<>());
		}
		else {
			this.entityArrayGroup.setEntityWorktimezones(lstWorkTimezone.stream().map(domain -> {
				KshmtFlexHolSet entity = new KshmtFlexHolSet();
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
			restTimezone.saveToMemento(new JpaFlexODFlWRestTzSetMemento(this.entityArrayGroup, this.entitySetGroup));
		}
	}

}
