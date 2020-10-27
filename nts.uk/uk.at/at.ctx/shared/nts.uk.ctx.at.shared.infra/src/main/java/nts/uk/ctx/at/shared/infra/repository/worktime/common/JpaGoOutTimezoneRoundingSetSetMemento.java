/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.worktime.common;

import java.util.ArrayList;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.shared.dom.worktime.common.GoOutTimeRoundingSetting;
import nts.uk.ctx.at.shared.dom.worktime.common.GoOutTimezoneRoundingSetSetMemento;
import nts.uk.ctx.at.shared.dom.worktime.common.GoOutTypeRoundingSet;
import nts.uk.ctx.at.shared.infra.entity.worktime.common.KshmtWtComGooutRound;
import nts.uk.ctx.at.shared.infra.entity.worktime.common.KshmtWtComGooutRoundPK;
import nts.uk.ctx.at.shared.infra.entity.worktime.common.KshmtWtCom;

/**
 * The Class JpaGoOutTimezoneRoundingSetSetMemento.
 */
public class JpaGoOutTimezoneRoundingSetSetMemento implements GoOutTimezoneRoundingSetSetMemento {

	/** The entity sets. */
	private KshmtWtCom entity;

	/**
	 * Instantiates a new jpa go out timezone rounding set set memento.
	 *
	 * @param entity
	 *            the entity
	 */
	public JpaGoOutTimezoneRoundingSetSetMemento(KshmtWtCom entity) {
		this.entity = entity;		
		if (CollectionUtil.isEmpty(this.entity.getKshmtWtComGooutRounds())) {
			this.entity.setKshmtWtComGooutRounds(new ArrayList<>());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.common.
	 * GoOutTimezoneRoundingSetSetMemento#setPubHolWorkTimezone(nts.uk.ctx.at.
	 * shared.dom.worktime.common.GoOutTypeRoundingSet)
	 */
	@Override
	public void setPubHolWorkTimezone(GoOutTypeRoundingSet pubHolWorkTimezone) {	
		if (pubHolWorkTimezone == null) {
			return;
		}
		
		Map<KshmtWtComGooutRoundPK, KshmtWtComGooutRound> currentSets = this.entity.getKshmtWtComGooutRounds().stream()
				.collect(Collectors.toMap(KshmtWtComGooutRound::getKshmtWtComGooutRoundPK, Function.identity()));

		KshmtWtComGooutRoundPK pk = new KshmtWtComGooutRoundPK(
				this.entity.getKshmtWtComPK().getCid(), 
				this.entity.getKshmtWtComPK().getWorktimeCd(), 
				this.entity.getKshmtWtComPK().getWorkFormAtr(),
				this.entity.getKshmtWtComPK().getWorktimeSetMethod(), 
				RoundingTimeType.PUB_HOL_WORK_TIMEZONE.value);
		KshmtWtComGooutRound entity = currentSets.get(pk);
		if (entity == null) {
			entity = new KshmtWtComGooutRound();
			entity.setKshmtWtComGooutRoundPK(pk);
		} 	
		
		this.updateDataEntity(entity, pubHolWorkTimezone);
		
		currentSets.put(pk, entity);
		this.entity.setKshmtWtComGooutRounds(currentSets.values().stream()
				.collect(Collectors.toList()));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.common.
	 * GoOutTimezoneRoundingSetSetMemento#setWorkTimezone(nts.uk.ctx.at.shared.
	 * dom.worktime.common.GoOutTypeRoundingSet)
	 */
	@Override
	public void setWorkTimezone(GoOutTypeRoundingSet workTimezone) {
		if (workTimezone == null) {
			return;
		}
		
		Map<KshmtWtComGooutRoundPK, KshmtWtComGooutRound> currentSets = this.entity.getKshmtWtComGooutRounds().stream()
				.collect(Collectors.toMap(KshmtWtComGooutRound::getKshmtWtComGooutRoundPK, Function.identity()));

		KshmtWtComGooutRoundPK pk = new KshmtWtComGooutRoundPK(
				this.entity.getKshmtWtComPK().getCid(), 
				this.entity.getKshmtWtComPK().getWorktimeCd(), 
				this.entity.getKshmtWtComPK().getWorkFormAtr(),
				this.entity.getKshmtWtComPK().getWorktimeSetMethod(),
				RoundingTimeType.WORK_TIMEZONE.value);
		KshmtWtComGooutRound entity = currentSets.get(pk);
		if (entity == null) {
			entity = new KshmtWtComGooutRound();
			entity.setKshmtWtComGooutRoundPK(pk);
		} 	
		
		this.updateDataEntity(entity, workTimezone);
		
		currentSets.put(pk, entity);
		this.entity.setKshmtWtComGooutRounds(currentSets.values().stream()
				.collect(Collectors.toList()));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.common.
	 * GoOutTimezoneRoundingSetSetMemento#setOttimezone(nts.uk.ctx.at.shared.dom
	 * .worktime.common.GoOutTypeRoundingSet)
	 */
	@Override
	public void setOttimezone(GoOutTypeRoundingSet ottimezone) {
		if (ottimezone == null) {
			return;
		}
		
		Map<KshmtWtComGooutRoundPK, KshmtWtComGooutRound> currentSets = this.entity.getKshmtWtComGooutRounds().stream()
				.collect(Collectors.toMap(KshmtWtComGooutRound::getKshmtWtComGooutRoundPK, Function.identity()));

		KshmtWtComGooutRoundPK pk = new KshmtWtComGooutRoundPK(
				this.entity.getKshmtWtComPK().getCid(), 
				this.entity.getKshmtWtComPK().getWorktimeCd(), 
				this.entity.getKshmtWtComPK().getWorkFormAtr(),
				this.entity.getKshmtWtComPK().getWorktimeSetMethod(),
				RoundingTimeType.OT_TIMEZONE.value);
		KshmtWtComGooutRound entity = currentSets.get(pk);
		if (entity == null) {
			entity = new KshmtWtComGooutRound();
			entity.setKshmtWtComGooutRoundPK(pk);
		} 	
		
		this.updateDataEntity(entity, ottimezone);
		
		currentSets.put(pk, entity);
		this.entity.setKshmtWtComGooutRounds(currentSets.values().stream()
				.collect(Collectors.toList()));
	}
	
	/**
	 * Update data entity.
	 *
	 * @param entity the entity
	 * @param typeRoundingSet the type rounding set
	 */
	private void updateDataEntity (KshmtWtComGooutRound entity, GoOutTypeRoundingSet typeRoundingSet) {
		GoOutTimeRoundingSetting pubDomainSetting = typeRoundingSet.getOfficalUseCompenGoOut().getApproTimeRoundingSetting();
		entity.setPubRoundingMethod(pubDomainSetting.getRoundingMethod().value);
		entity.setPubRounding(pubDomainSetting.getRoundingSetting().getRounding().value);
		entity.setPubRoundingUnit(pubDomainSetting.getRoundingSetting().getRoundingTime().value);				
			
		GoOutTimeRoundingSetting workDomainSetting = typeRoundingSet.getPrivateUnionGoOut().getApproTimeRoundingSetting();
		entity.setPersonalRoundingMethod(workDomainSetting.getRoundingMethod().value);
		entity.setPersonalRounding(workDomainSetting.getRoundingSetting().getRounding().value);
		entity.setPersonalRoundingUnit(workDomainSetting.getRoundingSetting().getRoundingTime().value);
		
		GoOutTimeRoundingSetting otDomainSetting = typeRoundingSet.getPrivateUnionGoOut().getDeductTimeRoundingSetting();
		entity.setPersonalDeductMethod(otDomainSetting.getRoundingMethod().value);
		entity.setPersonalDeductRounding(otDomainSetting.getRoundingSetting().getRounding().value);
		entity.setPersonalDeductUnit(otDomainSetting.getRoundingSetting().getRoundingTime().value);
	}

}
