/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.worktime.common;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.shared.dom.worktime.common.BooleanGetAtr;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlowRestSetting;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlowRestTimezoneSetMemento;
import nts.uk.ctx.at.shared.infra.entity.worktime.flexset.KshmtFlexOdRestSet;
import nts.uk.ctx.at.shared.infra.entity.worktime.flexset.KshmtFlexOdRestSetPK;
import nts.uk.ctx.at.shared.infra.entity.worktime.flexset.KshmtFlexOdRtSet;

/**
 * The Class JpaFlexODFlowRestTzSetMemento.
 */
public class JpaFlexODFlowRestTzSetMemento implements FlowRestTimezoneSetMemento{
	
	/** The entity. */
	private KshmtFlexOdRtSet entity;
	
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
			if (this.entity.getKshmtFlexOdRestSets() == null) {
				this.entity.setKshmtFlexOdRestSets(new ArrayList<>());
			}
			Map<KshmtFlexOdRestSetPK, KshmtFlexOdRestSet> mapEntity = this.entity.getKshmtFlexOdRestSets().stream()
					.collect(Collectors.toMap(KshmtFlexOdRestSet::getKshmtFlexOdRestSetPK, Function.identity()));
			List<KshmtFlexOdRestSet> lstNew = new ArrayList<>();
			for (int i = 0; i < set.size(); i++) {
				KshmtFlexOdRestSetPK newPK = new KshmtFlexOdRestSetPK(this.entity.getKshmtFlexOdRtSetPK().getCid(),
						this.entity.getKshmtFlexOdRtSetPK().getWorktimeCd(), i + 1);
				KshmtFlexOdRestSet newEntity = new KshmtFlexOdRestSet(newPK);

				KshmtFlexOdRestSet oldEntity = mapEntity.get(newPK);
				if (oldEntity != null) {
					// update
					newEntity = oldEntity;
				}
				// insert
				set.get(i).saveToMemento(new JpaFlexODFlowRestSetMemento(newEntity));
				lstNew.add(newEntity);
			}
			this.entity.setKshmtFlexOdRestSets(lstNew);
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
