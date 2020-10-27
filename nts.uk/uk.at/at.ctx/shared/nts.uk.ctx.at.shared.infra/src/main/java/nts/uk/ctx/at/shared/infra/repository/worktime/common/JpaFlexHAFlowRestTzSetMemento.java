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
import nts.uk.ctx.at.shared.infra.entity.worktime.flexset.KshmtWtFleBrFlWekTs;
import nts.uk.ctx.at.shared.infra.entity.worktime.flexset.KshmtWtFleBrFlWekTsPK;
import nts.uk.ctx.at.shared.infra.entity.worktime.flexset.KshmtWtFleBrFlWek;

public class JpaFlexHAFlowRestTzSetMemento implements FlowRestTimezoneSetMemento{
	
	/** The entity. */
	private KshmtWtFleBrFlWek entity;
	
	/** The period no. */
	private int periodNo = 1;
	/**
	 * Instantiates a new jpa flex HA flow rest tz set memento.
	 *
	 * @param entity the entity
	 */
	public JpaFlexHAFlowRestTzSetMemento(KshmtWtFleBrFlWek entity) {
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
		if (CollectionUtil.isEmpty(this.entity.getKshmtWtFleBrFlWekTss())) {
			this.entity.setKshmtWtFleBrFlWekTss(new ArrayList<>());
		}
		
		// check input empty
		if (CollectionUtil.isEmpty(lstFlowRestSet)) {
			this.entity.setKshmtWtFleBrFlWekTss(new ArrayList<>());
			return;
		}
		
		// convert map entity
		Map<KshmtWtFleBrFlWekTsPK, KshmtWtFleBrFlWekTs> mapEntity = this.entity.getKshmtWtFleBrFlWekTss().stream()
				.collect(Collectors.toMap(entity -> ((KshmtWtFleBrFlWekTs) entity).getKshmtWtFleBrFlWekTsPK(),
						Function.identity()));
		
		String companyId = this.entity.getKshmtWtFleBrFlWekPK().getCid();
		String workTimeCd = this.entity.getKshmtWtFleBrFlWekPK().getWorktimeCd();
		Integer amPmAtr = this.entity.getKshmtWtFleBrFlWekPK().getAmPmAtr();
		
		// set list entity
		this.entity.setKshmtWtFleBrFlWekTss(lstFlowRestSet.stream().map(domain -> {
			
			// newPk
			KshmtWtFleBrFlWekTsPK pk = new KshmtWtFleBrFlWekTsPK();
			pk.setCid(companyId);
			pk.setWorktimeCd(workTimeCd);
			pk.setAmPmAtr(amPmAtr);
			pk.setPeriodNo(periodNo);
			
			// find entity existed if not have, new entity
			KshmtWtFleBrFlWekTs entity = mapEntity.get(pk);
			if (entity == null) {
				entity = new KshmtWtFleBrFlWekTs(pk);
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
