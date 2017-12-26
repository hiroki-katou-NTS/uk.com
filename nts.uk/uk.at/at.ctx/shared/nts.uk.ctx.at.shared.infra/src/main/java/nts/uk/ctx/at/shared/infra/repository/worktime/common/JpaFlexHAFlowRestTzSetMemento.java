/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.worktime.common;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.shared.dom.worktime.common.BooleanGetAtr;
import nts.uk.ctx.at.shared.dom.worktime.common.FlowRestSetting;
import nts.uk.ctx.at.shared.dom.worktime.common.FlowRestTimezoneSetMemento;
import nts.uk.ctx.at.shared.infra.entity.worktime.KshmtFlexHaRestSet;
import nts.uk.ctx.at.shared.infra.entity.worktime.KshmtFlexHaRestSetPK;
import nts.uk.ctx.at.shared.infra.entity.worktime.KshmtFlexHaRtSet;

public class JpaFlexHAFlowRestTzSetMemento implements FlowRestTimezoneSetMemento{
	
	/** The entity. */
	private KshmtFlexHaRtSet entity;
	
	/** The period no. */
	private int periodNo = 0;
	/**
	 * Instantiates a new jpa flex HA flow rest tz set memento.
	 *
	 * @param entity the entity
	 */
	public JpaFlexHAFlowRestTzSetMemento(KshmtFlexHaRtSet entity) {
		super();
		this.entity = entity;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.common.FlowRestTimezoneSetMemento#
	 * setFlowRestSet(java.util.List)
	 */
	@Override
	public void setFlowRestSet(List<FlowRestSetting> set) {
		if (CollectionUtil.isEmpty(set)) {
			this.entity.setKshmtFlexHaRestSets(new ArrayList<>());
		} else {
			periodNo = 0;
			this.entity.setKshmtFlexHaRestSets(set.stream().map(domain -> {
				periodNo++;
				KshmtFlexHaRestSet entity = new KshmtFlexHaRestSet(
						new KshmtFlexHaRestSetPK(this.entity.getKshmtFlexHaRtSetPK().getCid(),
								this.entity.getKshmtFlexHaRtSetPK().getWorktimeCd(),
								this.entity.getKshmtFlexHaRtSetPK().getAmPmAtr(), periodNo));
				domain.saveToMemento(new JpaFlexHAFlowRestSetMemento(entity));
				return entity;
			}).collect(Collectors.toList()));
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.common.FlowRestTimezoneSetMemento#
	 * setUseHereAfterRestSet(boolean)
	 */
	@Override
	public void setUseHereAfterRestSet(boolean val) {
		this.entity.setUseRestAfterSet(BooleanGetAtr.getAtrByBoolean(val));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.common.FlowRestTimezoneSetMemento#
	 * setHereAfterRestSet(nts.uk.ctx.at.shared.dom.worktime.common.
	 * FlowRestSetting)
	 */
	@Override
	public void setHereAfterRestSet(FlowRestSetting set) {
		set.saveToMemento(new JpaFlexOffdayFlowRestSettingSetMemento(this.entity));
	}
	

}
