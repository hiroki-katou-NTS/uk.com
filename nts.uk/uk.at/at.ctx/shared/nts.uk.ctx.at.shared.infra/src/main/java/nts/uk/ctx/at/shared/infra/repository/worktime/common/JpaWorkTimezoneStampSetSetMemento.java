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
import nts.uk.ctx.at.shared.dom.worktime.common.PrioritySetting;
import nts.uk.ctx.at.shared.dom.worktime.common.RoundingSet;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimezoneStampSetSetMemento;
import nts.uk.ctx.at.shared.infra.entity.worktime.common.KshmtPioritySet;
import nts.uk.ctx.at.shared.infra.entity.worktime.common.KshmtPioritySetPK;
import nts.uk.ctx.at.shared.infra.entity.worktime.common.KshmtRoundingSet;
import nts.uk.ctx.at.shared.infra.entity.worktime.common.KshmtRoundingSetPK;
import nts.uk.ctx.at.shared.infra.entity.worktime.common.KshmtWtCom;

/**
 * The Class JpaWorkTimezoneStampSetSetMemento.
 */
public class JpaWorkTimezoneStampSetSetMemento implements WorkTimezoneStampSetSetMemento {

	/** The parent entity. */
	private KshmtWtCom parentEntity;
	
	/**
	 * Instantiates a new jpa work timezone stamp set set memento.
	 *
	 * @param parentEntity the parent entity
	 */
	public JpaWorkTimezoneStampSetSetMemento(KshmtWtCom parentEntity) {
		super();
		this.parentEntity = parentEntity;
	}
	
	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.worktime.common.WorkTimezoneStampSetSetMemento#setRoundingSet(java.util.List)
	 */
	@Override
	public void setRoundingSet(List<RoundingSet> rdSet) {
		List<KshmtRoundingSet> lstEntity = this.parentEntity.getKshmtRoundingSets();
		if (CollectionUtil.isEmpty(lstEntity)) {
			lstEntity = new ArrayList<>();
		}
		
		// convert list entity to map
		Map<KshmtRoundingSetPK, KshmtRoundingSet> mapEntity = lstEntity.stream()
				.collect(Collectors.toMap(item -> ((KshmtRoundingSet) item).getKshmtRoundingSetPK(),
						Function.identity()));
		
		List<KshmtRoundingSet> newLstEntity = new ArrayList<>();
		
		for (RoundingSet rounding : rdSet) {
			
			// new pk
			KshmtRoundingSetPK newPK = new KshmtRoundingSetPK();
			newPK.setCid(parentEntity.getKshmtWtComPK().getCid());
			newPK.setWorktimeCd(parentEntity.getKshmtWtComPK().getWorktimeCd());
			newPK.setWorkFormAtr(parentEntity.getKshmtWtComPK().getWorkFormAtr());
			newPK.setWorkTimeSetMethod(parentEntity.getKshmtWtComPK().getWorktimeSetMethod());
			newPK.setAtr(rounding.getSection().value);
			
			// get entity existed
			KshmtRoundingSet entity = mapEntity.get(newPK) == null ? new KshmtRoundingSet(newPK) : mapEntity.get(newPK);
			
			// save to memento
			rounding.saveToMemento(new JpaRoundingSetSetMemento(entity));
			
			// add entity
			newLstEntity.add(entity);
		}
		// update entity
		this.parentEntity.setKshmtRoundingSets(newLstEntity);
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.worktime.common.WorkTimezoneStampSetSetMemento#setPrioritySet(java.util.List)
	 */
	@Override
	public void setPrioritySet(List<PrioritySetting> prSet) {
		List<KshmtPioritySet> lstEntity = this.parentEntity.getKshmtPioritySets();
		if (CollectionUtil.isEmpty(lstEntity)) {
			lstEntity = new ArrayList<>();
		}
		
		// convert list entity to map
		Map<KshmtPioritySetPK, KshmtPioritySet> mapEntity = lstEntity.stream()
				.collect(Collectors.toMap(item -> ((KshmtPioritySet) item).getKshmtPioritySetPK(),
						Function.identity()));
		
		List<KshmtPioritySet> newLstEntity = new ArrayList<>();
		
		for (PrioritySetting pioritySet : prSet) {
			
			// new pk
			KshmtPioritySetPK newPK = new KshmtPioritySetPK();
			newPK.setCid(parentEntity.getKshmtWtComPK().getCid());
			newPK.setWorktimeCd(parentEntity.getKshmtWtComPK().getWorktimeCd());
			newPK.setWorkFormAtr(parentEntity.getKshmtWtComPK().getWorkFormAtr());
			newPK.setWorkTimeSetMethod(parentEntity.getKshmtWtComPK().getWorktimeSetMethod());
			newPK.setPiorityAtr(pioritySet.getPriorityAtr().value);
			newPK.setStampAtr(pioritySet.getStampAtr().value);
			
			// get entity existed
			KshmtPioritySet entity = mapEntity.get(newPK) == null ? new KshmtPioritySet(newPK) : mapEntity.get(newPK);
			
			// save to memento
			pioritySet.saveToMemento(new JpaPrioritySettingSetMemento(entity));
			
			// add entity
			newLstEntity.add(entity);
		}
		// update entity
		this.parentEntity.setKshmtPioritySets(newLstEntity);
	}
}
