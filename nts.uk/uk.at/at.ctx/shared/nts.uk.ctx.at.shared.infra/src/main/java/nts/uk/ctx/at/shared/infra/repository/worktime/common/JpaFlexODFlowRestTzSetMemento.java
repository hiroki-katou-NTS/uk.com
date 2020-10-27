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
import nts.uk.ctx.at.shared.infra.entity.worktime.flexset.KshmtWtFleBrFlHolTs;
import nts.uk.ctx.at.shared.infra.entity.worktime.flexset.KshmtWtFleBrFlHolTsPK;
import nts.uk.ctx.at.shared.infra.entity.worktime.flexset.KshmtWtFleBrFlHol;

/**
 * The Class JpaFlexODFlowRestTzSetMemento.
 */
public class JpaFlexODFlowRestTzSetMemento implements FlowRestTimezoneSetMemento{
	
	/** The entity. */
	private KshmtWtFleBrFlHol entity;
	
	/**
	 * Instantiates a new jpa flex OD flow rest tz set memento.
	 *
	 * @param entity the entity
	 */
	public JpaFlexODFlowRestTzSetMemento(KshmtWtFleBrFlHol entity) {
		super();
		this.entity = entity;
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.worktime.common.FlowRestTimezoneSetMemento#setFlowRestSet(java.util.List)
	 */
	@Override
	public void setFlowRestSet(List<FlowRestSetting> set) {
		if (CollectionUtil.isEmpty(set)) {
			this.entity.setKshmtWtFleBrFlHolTss(new ArrayList<>());
		} else {
			if (this.entity.getKshmtWtFleBrFlHolTss() == null) {
				this.entity.setKshmtWtFleBrFlHolTss(new ArrayList<>());
			}
			Map<KshmtWtFleBrFlHolTsPK, KshmtWtFleBrFlHolTs> mapEntity = this.entity.getKshmtWtFleBrFlHolTss().stream()
					.collect(Collectors.toMap(KshmtWtFleBrFlHolTs::getKshmtWtFleBrFlHolTsPK, Function.identity()));
			List<KshmtWtFleBrFlHolTs> lstNew = new ArrayList<>();
			for (int i = 0; i < set.size(); i++) {
				KshmtWtFleBrFlHolTsPK newPK = new KshmtWtFleBrFlHolTsPK(this.entity.getKshmtWtFleBrFlHolPK().getCid(),
						this.entity.getKshmtWtFleBrFlHolPK().getWorktimeCd(), i + 1);
				KshmtWtFleBrFlHolTs newEntity = new KshmtWtFleBrFlHolTs(newPK);

				KshmtWtFleBrFlHolTs oldEntity = mapEntity.get(newPK);
				if (oldEntity != null) {
					// update
					newEntity = oldEntity;
				}
				// insert
				set.get(i).saveToMemento(new JpaFlexODFlowRestSetMemento(newEntity));
				lstNew.add(newEntity);
			}
			this.entity.setKshmtWtFleBrFlHolTss(lstNew);
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
