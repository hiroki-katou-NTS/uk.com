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
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlowRestSetting;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlowRestTimezoneSetMemento;
import nts.uk.ctx.at.shared.infra.entity.worktime.flexset.KshmtWtFleBrFlWek;
import nts.uk.ctx.at.shared.infra.entity.worktime.flexset.KshmtWtFleBrFlHolTs;

/**
 * The Class JpaFlexOffdayFlowRestTzSetMemento.
 */
public class JpaFlexOffdayFlowRestTzSetMemento implements FlowRestTimezoneSetMemento{
	
	/** The entity. */
	private KshmtWtFleBrFlWek entity;
	
	/** The entity flow rests. */
	 List<KshmtWtFleBrFlHolTs> entityFlowRests;
	

	/**
	 * Instantiates a new jpa flex offday flow rest tz set memento.
	 *
	 * @param entity the entity
	 * @param entityFlowRests the entity flow rests
	 */
	public JpaFlexOffdayFlowRestTzSetMemento(KshmtWtFleBrFlWek entity, List<KshmtWtFleBrFlHolTs> entityFlowRests) {
		super();
		this.entity = entity;
		this.entityFlowRests = entityFlowRests;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.common.FlowRestTimezoneSetMemento#
	 * setFlowRestSet(java.util.List)
	 */
	@Override
	public void setFlowRestSet(List<FlowRestSetting> set) {
		this.entityFlowRests = new ArrayList<>();
		if (CollectionUtil.isEmpty(set)) {
			return;
		} 
		
		this.entityFlowRests = set.stream().map(domain -> {
			KshmtWtFleBrFlHolTs entity = new KshmtWtFleBrFlHolTs();
			domain.saveToMemento(new JpaFlexOffdayFlowRestSetMemento(entity));
			return entity;
		}).collect(Collectors.toList());

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
