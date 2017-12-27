/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.worktime.common;

import java.util.ArrayList;
import java.util.List;

import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.shared.dom.worktime.common.PrioritySetting;
import nts.uk.ctx.at.shared.dom.worktime.common.RoundingSet;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimezoneStampSetSetMemento;
import nts.uk.ctx.at.shared.infra.entity.worktime.common.KshmtPioritySet;
import nts.uk.ctx.at.shared.infra.entity.worktime.common.KshmtPioritySetPK;
import nts.uk.ctx.at.shared.infra.entity.worktime.common.KshmtRoundingSet;
import nts.uk.ctx.at.shared.infra.entity.worktime.common.KshmtRoundingSetPK;
import nts.uk.ctx.at.shared.infra.entity.worktime.common.KshmtWorktimeCommonSet;

/**
 * The Class JpaWorkTimezoneStampSetSetMemento.
 */
public class JpaWorkTimezoneStampSetSetMemento implements WorkTimezoneStampSetSetMemento {

	/** The parent entity. */
	private KshmtWorktimeCommonSet parentEntity;
	
	/**
	 * Instantiates a new jpa work timezone stamp set set memento.
	 *
	 * @param parentEntity the parent entity
	 */
	public JpaWorkTimezoneStampSetSetMemento(KshmtWorktimeCommonSet parentEntity) {
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
		
		List<KshmtRoundingSet> newLstEntity = new ArrayList<>();
		
		for (RoundingSet rounding : rdSet) {
			
			// new pk
			KshmtRoundingSetPK newPK = this.setPrimaryKey(parentEntity, new KshmtRoundingSetPK());
			
			// get entity existed
			KshmtRoundingSet entity = lstEntity.stream().filter(item -> {
				KshmtRoundingSetPK pk = item.getKshmtRoundingSetPK();
					
					return pk.getCid().equals(newPK.getCid()) && pk.getWorktimeCd().equals(newPK.getWorktimeCd())
							&& pk.getWorkFormAtr() == newPK.getWorkFormAtr()
							&& pk.getWorkTimeSetMethod() == newPK.getWorkTimeSetMethod()
							&& pk.getAtr() == rounding.getSection().value;
					}).findFirst()
					.orElse(new KshmtRoundingSet(newPK));
			
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
		
		List<KshmtPioritySet> newLstEntity = new ArrayList<>();
		
		for (PrioritySetting pioritySet : prSet) {
			
			// new pk
			KshmtPioritySetPK newPK = this.setPrimaryKey(parentEntity, new KshmtPioritySetPK());
			
			// get entity existed
			KshmtPioritySet entity = lstEntity.stream().filter(item -> {
				KshmtPioritySetPK pk = item.getKshmtPioritySetPK();
					
					return pk.getCid().equals(newPK.getCid()) && pk.getWorktimeCd().equals(newPK.getWorktimeCd())
							&& pk.getWorkFormAtr() == newPK.getWorkFormAtr()
							&& pk.getWorkTimeSetMethod() == newPK.getWorkTimeSetMethod()
							&& pk.getStampAtr() == pioritySet.getStampAtr().value;
					}).findFirst()
					.orElse(new KshmtPioritySet(newPK));
			
			// save to memento
			pioritySet.saveToMemento(new JpaPrioritySettingSetMemento(entity));
			
			// add entity
			newLstEntity.add(entity);
		}
		// update entity
		this.parentEntity.setKshmtPioritySets(newLstEntity);
	}

	/**
	 * Sets the primary key.
	 *
	 * @param <T> the generic type
	 * @param parentEntity the parent entity
	 * @param primaryKey the primary key
	 * @return the t
	 */
	private <T> T setPrimaryKey(KshmtWorktimeCommonSet parentEntity, T primaryKey) {
		
		// KshmtRoundingSetPK
		if (primaryKey instanceof KshmtRoundingSetPK) {
			KshmtRoundingSetPK pk = (KshmtRoundingSetPK) primaryKey;
			
			pk.setCid(parentEntity.getKshmtWorktimeCommonSetPK().getCid());
			pk.setWorktimeCd(parentEntity.getKshmtWorktimeCommonSetPK().getWorktimeCd());
			pk.setWorkFormAtr(parentEntity.getKshmtWorktimeCommonSetPK().getWorkFormAtr());
			pk.setWorkTimeSetMethod(parentEntity.getKshmtWorktimeCommonSetPK().getWorktimeSetMethod());
		}
		
		// KshmtPioritySetPK
		if (primaryKey instanceof KshmtPioritySetPK) {
			KshmtPioritySetPK pk = (KshmtPioritySetPK) primaryKey;
			
			pk.setCid(parentEntity.getKshmtWorktimeCommonSetPK().getCid());
			pk.setWorktimeCd(parentEntity.getKshmtWorktimeCommonSetPK().getWorktimeCd());
			pk.setWorkFormAtr(parentEntity.getKshmtWorktimeCommonSetPK().getWorkFormAtr());
			pk.setWorkTimeSetMethod(parentEntity.getKshmtWorktimeCommonSetPK().getWorktimeSetMethod());
		}
		return primaryKey;
	}
}
