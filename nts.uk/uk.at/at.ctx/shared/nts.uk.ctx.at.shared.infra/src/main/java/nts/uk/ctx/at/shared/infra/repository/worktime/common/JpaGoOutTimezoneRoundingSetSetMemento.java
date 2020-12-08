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
import nts.uk.ctx.at.shared.infra.entity.worktime.common.KshmtSpecialRoundOutPK;
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
		if (CollectionUtil.isEmpty(this.entity.getKshmtSpecialRoundOuts())) {
			this.entity.setKshmtSpecialRoundOuts(new ArrayList<>());
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
		
		Map<KshmtSpecialRoundOutPK, KshmtWtComGooutRound> currentSets = this.entity.getKshmtSpecialRoundOuts().stream()
				.collect(Collectors.toMap(KshmtWtComGooutRound::getKshmtSpecialRoundOutPK, Function.identity()));

		KshmtSpecialRoundOutPK pk = new KshmtSpecialRoundOutPK(
				this.entity.getKshmtWorktimeCommonSetPK().getCid(), 
				this.entity.getKshmtWorktimeCommonSetPK().getWorktimeCd(), 
				this.entity.getKshmtWorktimeCommonSetPK().getWorkFormAtr(),
				this.entity.getKshmtWorktimeCommonSetPK().getWorktimeSetMethod(), 
				RoundingTimeType.PUB_HOL_WORK_TIMEZONE.value);
		KshmtWtComGooutRound entity = currentSets.get(pk);
		if (entity == null) {
			entity = new KshmtWtComGooutRound();
			entity.setKshmtSpecialRoundOutPK(pk);
		} 	
		
		this.updateDataEntity(entity, pubHolWorkTimezone);
		
		currentSets.put(pk, entity);
		this.entity.setKshmtSpecialRoundOuts(currentSets.values().stream()
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
		
		Map<KshmtSpecialRoundOutPK, KshmtWtComGooutRound> currentSets = this.entity.getKshmtSpecialRoundOuts().stream()
				.collect(Collectors.toMap(KshmtWtComGooutRound::getKshmtSpecialRoundOutPK, Function.identity()));

		KshmtSpecialRoundOutPK pk = new KshmtSpecialRoundOutPK(
				this.entity.getKshmtWorktimeCommonSetPK().getCid(), 
				this.entity.getKshmtWorktimeCommonSetPK().getWorktimeCd(), 
				this.entity.getKshmtWorktimeCommonSetPK().getWorkFormAtr(),
				this.entity.getKshmtWorktimeCommonSetPK().getWorktimeSetMethod(),
				RoundingTimeType.WORK_TIMEZONE.value);
		KshmtWtComGooutRound entity = currentSets.get(pk);
		if (entity == null) {
			entity = new KshmtWtComGooutRound();
			entity.setKshmtSpecialRoundOutPK(pk);
		} 	
		
		this.updateDataEntity(entity, workTimezone);
		
		currentSets.put(pk, entity);
		this.entity.setKshmtSpecialRoundOuts(currentSets.values().stream()
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
		
		Map<KshmtSpecialRoundOutPK, KshmtWtComGooutRound> currentSets = this.entity.getKshmtSpecialRoundOuts().stream()
				.collect(Collectors.toMap(KshmtWtComGooutRound::getKshmtSpecialRoundOutPK, Function.identity()));

		KshmtSpecialRoundOutPK pk = new KshmtSpecialRoundOutPK(
				this.entity.getKshmtWorktimeCommonSetPK().getCid(), 
				this.entity.getKshmtWorktimeCommonSetPK().getWorktimeCd(), 
				this.entity.getKshmtWorktimeCommonSetPK().getWorkFormAtr(),
				this.entity.getKshmtWorktimeCommonSetPK().getWorktimeSetMethod(),
				RoundingTimeType.OT_TIMEZONE.value);
		KshmtWtComGooutRound entity = currentSets.get(pk);
		if (entity == null) {
			entity = new KshmtWtComGooutRound();
			entity.setKshmtSpecialRoundOutPK(pk);
		} 	
		
		this.updateDataEntity(entity, ottimezone);
		
		currentSets.put(pk, entity);
		this.entity.setKshmtSpecialRoundOuts(currentSets.values().stream()
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
