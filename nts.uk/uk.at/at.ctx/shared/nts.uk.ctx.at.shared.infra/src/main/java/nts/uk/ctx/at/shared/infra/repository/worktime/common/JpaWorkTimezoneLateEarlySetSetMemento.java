/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.worktime.common;

import java.util.ArrayList;
import java.util.List;

import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.shared.dom.worktime.common.BooleanGetAtr;
import nts.uk.ctx.at.shared.dom.worktime.common.EmTimezoneLateEarlyCommonSet;
import nts.uk.ctx.at.shared.dom.worktime.common.OtherEmTimezoneLateEarlySet;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimezoneLateEarlySetSetMemento;
import nts.uk.ctx.at.shared.infra.entity.worktime.common.KshmtLateEarlySet;
import nts.uk.ctx.at.shared.infra.entity.worktime.common.KshmtLateEarlySetPK;
import nts.uk.ctx.at.shared.infra.entity.worktime.common.KshmtOtherLateEarly;
import nts.uk.ctx.at.shared.infra.entity.worktime.common.KshmtOtherLateEarlyPK;
import nts.uk.ctx.at.shared.infra.entity.worktime.common.KshmtWorktimeCommonSet;

/**
 * The Class JpaWorkTimezoneLateEarlySetSetMemento.
 */
public class JpaWorkTimezoneLateEarlySetSetMemento implements WorkTimezoneLateEarlySetSetMemento {

	/** The parent entity. */
	private KshmtWorktimeCommonSet parentEntity;

	/**
	 * Instantiates a new jpa work timezone late early set set memento.
	 *
	 * @param parentEntity
	 *            the parent entity
	 */
	public JpaWorkTimezoneLateEarlySetSetMemento(KshmtWorktimeCommonSet parentEntity) {
		super();
		this.parentEntity = parentEntity;
		// initial entity
		this.initialEntity(parentEntity);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.common.
	 * WorkTimezoneLateEarlySetSetMemento#setCommonSet(nts.uk.ctx.at.shared.dom.
	 * worktime.common.EmTimezoneLateEarlyCommonSet)
	 */
	@Override
	public void setCommonSet(EmTimezoneLateEarlyCommonSet set) {		
		this.parentEntity.getKshmtLateEarlySet().setIsDeducteFromTime(BooleanGetAtr.getAtrByBoolean(set.isDelFromEmTime()));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.common.
	 * WorkTimezoneLateEarlySetSetMemento#setOtherClassSet(java.util.List)
	 */
	@Override
	public void setOtherClassSet(List<OtherEmTimezoneLateEarlySet> list) {
		List<KshmtOtherLateEarly> lstEntity = this.parentEntity.getKshmtOtherLateEarlies();
		if (CollectionUtil.isEmpty(lstEntity)) {
			lstEntity = new ArrayList<>();
		}
		
		List<KshmtOtherLateEarly> newLstEntity = new ArrayList<>();
		
		for (OtherEmTimezoneLateEarlySet emTimezone : list) {
			
			// new pk
			KshmtOtherLateEarlyPK newPK = this.setPrimaryKey(parentEntity, new KshmtOtherLateEarlyPK());
			
			// get entity existed
			KshmtOtherLateEarly entity = lstEntity.stream().filter(item -> {
					KshmtOtherLateEarlyPK pk = item.getKshmtOtherLateEarlyPK();
					
					return pk.getCid().equals(newPK.getCid()) && pk.getWorktimeCd().equals(newPK.getWorktimeCd())
							&& pk.getWorkFormAtr() == newPK.getWorkFormAtr()
							&& pk.getWorktimeSetMethod() == newPK.getWorktimeSetMethod()
							&& pk.getLateEarlyAtr() == emTimezone.getLateEarlyAtr().value;
					}).findFirst()
					.orElse(new KshmtOtherLateEarly(newPK));
			
			// save to memento
			emTimezone.saveToMemento(new JpaOtherEmTimezoneLateEarlySetSetMemento(entity));
			
			// add entity
			newLstEntity.add(entity);
		}
		// update entity
		this.parentEntity.setKshmtOtherLateEarlies(newLstEntity);
	}

	/**
	 * Initial entity.
	 *
	 * @param parentEntity
	 *            the parent entity
	 */
	private void initialEntity(KshmtWorktimeCommonSet parentEntity) {
		KshmtLateEarlySet entity = parentEntity.getKshmtLateEarlySet();

		if(entity == null){
			entity = new KshmtLateEarlySet();
		}
		// check existed key
		if (entity.getKshmtLateEarlySetPK() == null) {

			// set primary key
			KshmtLateEarlySetPK pk = this.setPrimaryKey(parentEntity, new KshmtLateEarlySetPK());
			entity.setKshmtLateEarlySetPK(pk);
		}

		// set entity
		parentEntity.setKshmtLateEarlySet(entity);
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
		
		// KshmtLateEarlySetPK
		if (primaryKey instanceof KshmtLateEarlySetPK) {
			KshmtLateEarlySetPK pk = (KshmtLateEarlySetPK) primaryKey;
			
			pk.setCid(parentEntity.getKshmtWorktimeCommonSetPK().getCid());
			pk.setWorktimeCd(parentEntity.getKshmtWorktimeCommonSetPK().getWorktimeCd());
			pk.setWorkFormAtr(parentEntity.getKshmtWorktimeCommonSetPK().getWorkFormAtr());
			pk.setWorktimeSetMethod(parentEntity.getKshmtWorktimeCommonSetPK().getWorktimeSetMethod());
		}
		
		// KshmtOtherLateEarlyPK
		if (primaryKey instanceof KshmtOtherLateEarlyPK) {
			KshmtOtherLateEarlyPK pk = (KshmtOtherLateEarlyPK) primaryKey;
			
			pk.setCid(parentEntity.getKshmtWorktimeCommonSetPK().getCid());
			pk.setWorktimeCd(parentEntity.getKshmtWorktimeCommonSetPK().getWorktimeCd());
			pk.setWorkFormAtr(parentEntity.getKshmtWorktimeCommonSetPK().getWorkFormAtr());
			pk.setWorktimeSetMethod(parentEntity.getKshmtWorktimeCommonSetPK().getWorktimeSetMethod());
		}
		return primaryKey;
	}
}
