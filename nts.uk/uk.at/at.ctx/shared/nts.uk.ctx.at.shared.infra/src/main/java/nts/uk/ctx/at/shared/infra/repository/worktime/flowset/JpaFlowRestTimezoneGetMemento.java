/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.worktime.flowset;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.shared.dom.worktime.common.BooleanGetAtr;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlowRestSetting;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlowRestTimezoneGetMemento;
import nts.uk.ctx.at.shared.infra.entity.worktime.flowset.KshmtWtFloBrFl;

/**
 * The Class JpaFlowRestTimezoneGetMemento.
 */
public class JpaFlowRestTimezoneGetMemento implements FlowRestTimezoneGetMemento {
	
	/** The entity. */
	private KshmtWtFloBrFl entity;
	
	/**
	 * Instantiates a new jpa flow rest timezone get memento.
	 *
	 * @param entity the entity
	 */
	public JpaFlowRestTimezoneGetMemento(KshmtWtFloBrFl entity) {
		super();
		this.entity = entity;
		if (CollectionUtil.isEmpty(this.entity.getLstKshmtWtFloBrFlAllTs())) {
			this.entity.setLstKshmtWtFloBrFlAllTs(new ArrayList<>());
		}
	}
	
	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.worktime.common.FlowRestTimezoneGetMemento#getFlowRestSet()
	 */
	@Override
	public List<FlowRestSetting> getFlowRestSet() {
		return this.entity.getLstKshmtWtFloBrFlAllTs().stream()
				.map(entity -> new FlowRestSetting(new JpaFlowRestSettingGetMemento(entity)))
				.sorted((item1, item2) -> item1.getFlowRestTime().compareTo(item2.getFlowRestTime()))
				.collect(Collectors.toList());
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.worktime.common.FlowRestTimezoneGetMemento#getUseHereAfterRestSet()
	 */
	@Override
	public boolean getUseHereAfterRestSet() {
		return BooleanGetAtr.getAtrByInteger(this.entity.getUseRestAfterSet());
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.worktime.common.FlowRestTimezoneGetMemento#getHereAfterRestSet()
	 */
	@Override
	public FlowRestSetting getHereAfterRestSet() {
		return new FlowRestSetting(this.entity.getAfterRestTime(), this.entity.getAfterPassageTime());
	}

}
