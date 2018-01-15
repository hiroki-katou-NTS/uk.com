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
import nts.uk.ctx.at.shared.infra.entity.worktime.KshmtFlexOdRestSet;
import nts.uk.ctx.at.shared.infra.entity.worktime.KshmtFlexOdRestSetPK;
import nts.uk.ctx.at.shared.infra.entity.worktime.KshmtFlexOdRtSet;

/**
 * The Class JpaFlexODFlowRestTzSetMemento.
 */
public class JpaFlexODFlowRestTzSetMemento implements FlowRestTimezoneSetMemento{
	
	/** The entity. */
	private KshmtFlexOdRtSet entity;

	/** The period no. */
	private int periodNo = 0;
	
	/**
	 * Instantiates a new jpa flex OD flow rest tz set memento.
	 *
	 * @param entity the entity
	 */
	public JpaFlexODFlowRestTzSetMemento(KshmtFlexOdRtSet entity) {
		super();
		this.entity = entity;
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.worktime.common.FlowRestTimezoneSetMemento#setFlowRestSet(java.util.List)
	 */
	@Override
	public void setFlowRestSet(List<FlowRestSetting> set) {
		if (CollectionUtil.isEmpty(set)) {
			this.entity.setKshmtFlexOdRestSets(new ArrayList<>());
		} else {
			periodNo = 0;
			this.entity.setKshmtFlexOdRestSets(set.stream().map(domain -> {
				periodNo++;
				KshmtFlexOdRestSet entity = new KshmtFlexOdRestSet(
						new KshmtFlexOdRestSetPK(this.entity.getKshmtFlexOdRtSetPK().getCid(),
								this.entity.getKshmtFlexOdRtSetPK().getWorktimeCd(), periodNo));
				domain.saveToMemento(new JpaFlexODFlowRestSetMemento(entity));
				return entity;
			}).collect(Collectors.toList()));
		}
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.worktime.common.FlowRestTimezoneSetMemento#setUseHereAfterRestSet(boolean)
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
		if (set != null) {
			set.saveToMemento(new JpaFlexODFlowRestSettingSetMemento(this.entity));
		}

	}
	

}
