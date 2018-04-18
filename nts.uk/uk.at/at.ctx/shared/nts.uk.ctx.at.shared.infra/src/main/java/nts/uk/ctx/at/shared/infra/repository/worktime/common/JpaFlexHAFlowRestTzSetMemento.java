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
import nts.uk.ctx.at.shared.infra.entity.worktime.flexset.KshmtFlexHaRestSet;
import nts.uk.ctx.at.shared.infra.entity.worktime.flexset.KshmtFlexHaRestSetPK;
import nts.uk.ctx.at.shared.infra.entity.worktime.flexset.KshmtFlexHaRtSet;

public class JpaFlexHAFlowRestTzSetMemento implements FlowRestTimezoneSetMemento{
	
	/** The entity. */
	private KshmtFlexHaRtSet entity;
	
	/** The period no. */
	private int periodNo = 1;
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
	public void setFlowRestSet(List<FlowRestSetting> lstFlowRestSet) {
		
		// check list entity get empty
		if (CollectionUtil.isEmpty(this.entity.getKshmtFlexHaRestSets())) {
			this.entity.setKshmtFlexHaRestSets(new ArrayList<>());
		}
		
		// check input empty
		if (CollectionUtil.isEmpty(lstFlowRestSet)) {
			this.entity.setKshmtFlexHaRestSets(new ArrayList<>());
			return;
		}
		
		// convert map entity
		Map<KshmtFlexHaRestSetPK, KshmtFlexHaRestSet> mapEntity = this.entity.getKshmtFlexHaRestSets().stream()
				.collect(Collectors.toMap(entity -> ((KshmtFlexHaRestSet) entity).getKshmtFlexHaRestSetPK(),
						Function.identity()));
		
		String companyId = this.entity.getKshmtFlexHaRtSetPK().getCid();
		String workTimeCd = this.entity.getKshmtFlexHaRtSetPK().getWorktimeCd();
		Integer amPmAtr = this.entity.getKshmtFlexHaRtSetPK().getAmPmAtr();
		
		// set list entity
		this.entity.setKshmtFlexHaRestSets(lstFlowRestSet.stream().map(domain -> {
			
			// newPk
			KshmtFlexHaRestSetPK pk = new KshmtFlexHaRestSetPK();
			pk.setCid(companyId);
			pk.setWorktimeCd(workTimeCd);
			pk.setAmPmAtr(amPmAtr);
			pk.setPeriodNo(periodNo);
			
			// find entity existed if not have, new entity
			KshmtFlexHaRestSet entity = mapEntity.get(pk);
			if (entity == null) {
				entity = new KshmtFlexHaRestSet(pk);
			}
			
			// save to memento
			domain.saveToMemento(new JpaFlexHAFlowRestSetMemento(entity));
			
			// increase period no
			periodNo++;
			
			return entity;
		}).collect(Collectors.toList()));
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
