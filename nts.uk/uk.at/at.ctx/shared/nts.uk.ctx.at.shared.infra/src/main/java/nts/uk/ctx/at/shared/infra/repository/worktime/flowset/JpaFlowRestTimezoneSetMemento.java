/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.worktime.flowset;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.shared.dom.worktime.common.BooleanGetAtr;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlowRestSetting;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlowRestTimezoneSetMemento;
import nts.uk.ctx.at.shared.infra.entity.worktime.flowset.KshmtWtFloBrFlAllTs;
import nts.uk.ctx.at.shared.infra.entity.worktime.flowset.KshmtWtFloBrFlAllTsPK;
import nts.uk.ctx.at.shared.infra.entity.worktime.flowset.KshmtWtFloBrFl;

/**
 * The Class JpaFlowRestTimezoneSetMemento.
 */
public class JpaFlowRestTimezoneSetMemento implements FlowRestTimezoneSetMemento {

	/** The entity. */
	private KshmtWtFloBrFl entity;
	
	/** The company id. */
	private String companyId;
	
	/** The work time cd. */
	private String workTimeCd;
	
	/** The work time cd. */
	private int resttimeAtr;	
	
	/**
	 * Instantiates a new jpa flow rest timezone set memento.
	 *
	 * @param entity the entity
	 */
	public JpaFlowRestTimezoneSetMemento(KshmtWtFloBrFl entity) {
		super();
		this.entity = entity;
		if (CollectionUtil.isEmpty(this.entity.getLstKshmtWtFloBrFlAllTs())) {
			this.entity.setLstKshmtWtFloBrFlAllTs(new ArrayList<>());
		}
		this.companyId = this.entity.getKshmtWtFloBrFlPK().getCid();
		this.workTimeCd = this.entity.getKshmtWtFloBrFlPK().getWorktimeCd();
		this.resttimeAtr = this.entity.getKshmtWtFloBrFlPK().getResttimeAtr();
	}
	
	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.worktime.common.FlowRestTimezoneSetMemento#setFlowRestSet(java.util.List)
	 */
	@Override
	public void setFlowRestSet(List<FlowRestSetting> set) {
		if (CollectionUtil.isEmpty(set)) {
			this.entity.setLstKshmtWtFloBrFlAllTs(new ArrayList<>());
			return;
		}		
		
		List<KshmtWtFloBrFlAllTs> lstEntity = this.entity.getLstKshmtWtFloBrFlAllTs();
        if (CollectionUtil.isEmpty(lstEntity)) {
            lstEntity = new ArrayList<>();
        }
		
        // convert map entity
     	Map<KshmtWtFloBrFlAllTsPK, KshmtWtFloBrFlAllTs> mapEntity = lstEntity.stream()
     			.collect(Collectors.toMap(KshmtWtFloBrFlAllTs::getKshmtWtFloBrFlAllTsPK, Function.identity()));
        
        // set list entity
        List<KshmtWtFloBrFlAllTs> newListEntity = new ArrayList<>();
        int periodNo = 1;
        for (FlowRestSetting domain : set) {
        	KshmtWtFloBrFlAllTsPK pk = new KshmtWtFloBrFlAllTsPK();
            pk.setCid(companyId);
            pk.setWorktimeCd(workTimeCd);
            pk.setResttimeAtr(resttimeAtr);
            pk.setPeriodNo(periodNo);
            
            // find entity if existed, else new entity
            KshmtWtFloBrFlAllTs entity = mapEntity.get(pk);
 			if (entity == null) {
 				entity = new KshmtWtFloBrFlAllTs();
 				entity.setKshmtWtFloBrFlAllTsPK(pk);
 			}
            
            // save to memento
            domain.saveToMemento(new JpaFlowRestSettingSetMemento(entity));
            periodNo++;
            newListEntity.add(entity);
        }
        this.entity.setLstKshmtWtFloBrFlAllTs(newListEntity);
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.worktime.common.FlowRestTimezoneSetMemento#setUseHereAfterRestSet(boolean)
	 */
	@Override
	public void setUseHereAfterRestSet(boolean val) {
		this.entity.setUseRestAfterSet(BooleanGetAtr.getAtrByBoolean(val));
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.worktime.common.FlowRestTimezoneSetMemento#setHereAfterRestSet(nts.uk.ctx.at.shared.dom.worktime.common.FlowRestSetting)
	 */
	@Override
	public void setHereAfterRestSet(FlowRestSetting set) {
		this.entity.setAfterRestTime(set.getFlowRestTime().valueAsMinutes());
		this.entity.setAfterPassageTime(set.getFlowPassageTime().valueAsMinutes());
	}

}
