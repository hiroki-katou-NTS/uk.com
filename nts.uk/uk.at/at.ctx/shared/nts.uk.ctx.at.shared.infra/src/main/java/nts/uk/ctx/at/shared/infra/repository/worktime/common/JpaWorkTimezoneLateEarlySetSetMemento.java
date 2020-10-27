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
import nts.uk.ctx.at.shared.infra.entity.worktime.common.KshmtWtComLatetimeMng;
import nts.uk.ctx.at.shared.infra.entity.worktime.common.KshmtWtComLatetimeMngPK;
import nts.uk.ctx.at.shared.infra.entity.worktime.common.KshmtWtComLatetime;
import nts.uk.ctx.at.shared.infra.entity.worktime.common.KshmtWtComLatetimePK;
import nts.uk.ctx.at.shared.infra.entity.worktime.common.KshmtWtCom;

/**
 * The Class JpaWorkTimezoneLateEarlySetSetMemento.
 */
public class JpaWorkTimezoneLateEarlySetSetMemento implements WorkTimezoneLateEarlySetSetMemento {

	/** The parent entity. */
	private KshmtWtCom parentEntity;

	/**
	 * Instantiates a new jpa work timezone late early set set memento.
	 *
	 * @param parentEntity
	 *            the parent entity
	 */
	public JpaWorkTimezoneLateEarlySetSetMemento(KshmtWtCom parentEntity) {
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
		this.parentEntity.getKshmtWtComLatetimeMng().setIsDeducteFromTime(BooleanGetAtr.getAtrByBoolean(set.isDelFromEmTime()));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.common.
	 * WorkTimezoneLateEarlySetSetMemento#setOtherClassSet(java.util.List)
	 */
	@Override
	public void setOtherClassSet(List<OtherEmTimezoneLateEarlySet> list) {
		List<KshmtWtComLatetime> lstEntity = this.parentEntity.getKshmtOtherLateEarlies();
		if (CollectionUtil.isEmpty(lstEntity)) {
			lstEntity = new ArrayList<>();
		}
		
		List<KshmtWtComLatetime> newLstEntity = new ArrayList<>();
		
		for (OtherEmTimezoneLateEarlySet emTimezone : list) {
			
			// new pk
			KshmtWtComLatetimePK newPK = this.setPrimaryKey(parentEntity, new KshmtWtComLatetimePK());
			
			// get entity existed
			KshmtWtComLatetime entity = lstEntity.stream().filter(item -> {
					KshmtWtComLatetimePK pk = item.getKshmtWtComLatetimePK();
					
					return pk.getCid().equals(newPK.getCid()) && pk.getWorktimeCd().equals(newPK.getWorktimeCd())
							&& pk.getWorkFormAtr() == newPK.getWorkFormAtr()
							&& pk.getWorktimeSetMethod() == newPK.getWorktimeSetMethod()
							&& pk.getLateEarlyAtr() == emTimezone.getLateEarlyAtr().value;
					}).findFirst()
					.orElse(new KshmtWtComLatetime(newPK));
			
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
	private void initialEntity(KshmtWtCom parentEntity) {
		KshmtWtComLatetimeMng entity = parentEntity.getKshmtWtComLatetimeMng();

		if(entity == null){
			entity = new KshmtWtComLatetimeMng();
		}
		// check existed key
		if (entity.getKshmtWtComLatetimeMngPK() == null) {

			// set primary key
			KshmtWtComLatetimeMngPK pk = this.setPrimaryKey(parentEntity, new KshmtWtComLatetimeMngPK());
			entity.setKshmtWtComLatetimeMngPK(pk);
		}

		// set entity
		parentEntity.setKshmtWtComLatetimeMng(entity);
	}

	/**
	 * Sets the primary key.
	 *
	 * @param <T> the generic type
	 * @param parentEntity the parent entity
	 * @param primaryKey the primary key
	 * @return the t
	 */
	private <T> T setPrimaryKey(KshmtWtCom parentEntity, T primaryKey) {
		
		// KshmtWtComLatetimeMngPK
		if (primaryKey instanceof KshmtWtComLatetimeMngPK) {
			KshmtWtComLatetimeMngPK pk = (KshmtWtComLatetimeMngPK) primaryKey;
			
			pk.setCid(parentEntity.getKshmtWtComPK().getCid());
			pk.setWorktimeCd(parentEntity.getKshmtWtComPK().getWorktimeCd());
			pk.setWorkFormAtr(parentEntity.getKshmtWtComPK().getWorkFormAtr());
			pk.setWorktimeSetMethod(parentEntity.getKshmtWtComPK().getWorktimeSetMethod());
		}
		
		// KshmtWtComLatetimePK
		if (primaryKey instanceof KshmtWtComLatetimePK) {
			KshmtWtComLatetimePK pk = (KshmtWtComLatetimePK) primaryKey;
			
			pk.setCid(parentEntity.getKshmtWtComPK().getCid());
			pk.setWorktimeCd(parentEntity.getKshmtWtComPK().getWorktimeCd());
			pk.setWorkFormAtr(parentEntity.getKshmtWtComPK().getWorkFormAtr());
			pk.setWorktimeSetMethod(parentEntity.getKshmtWtComPK().getWorktimeSetMethod());
		}
		return primaryKey;
	}
}
