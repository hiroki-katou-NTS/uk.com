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
import nts.uk.ctx.at.shared.infra.entity.worktime.common.KshmtSpecialRoundOut;
import nts.uk.ctx.at.shared.infra.entity.worktime.common.KshmtSpecialRoundOutPK;
import nts.uk.ctx.at.shared.infra.entity.worktime.common.KshmtWorktimeCommonSet;

/**
 * The Class JpaGoOutTimezoneRoundingSetSetMemento.
 */
public class JpaGoOutTimezoneRoundingSetSetMemento implements GoOutTimezoneRoundingSetSetMemento {

	/** The entity sets. */
	private KshmtWorktimeCommonSet entity;

	/**
	 * Instantiates a new jpa go out timezone rounding set set memento.
	 *
	 * @param entity
	 *            the entity
	 */
	public JpaGoOutTimezoneRoundingSetSetMemento(KshmtWorktimeCommonSet entity) {
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
		
		Map<KshmtSpecialRoundOutPK, KshmtSpecialRoundOut> currentSets = this.entity.getKshmtSpecialRoundOuts().stream()
				.map(KshmtSpecialRoundOut.class::cast)
				.collect(Collectors.toMap(entity -> entity.getKshmtSpecialRoundOutPK(), Function.identity()));

		KshmtSpecialRoundOutPK pk = new KshmtSpecialRoundOutPK(
				this.entity.getKshmtWorktimeCommonSetPK().getCid(), 
				this.entity.getKshmtWorktimeCommonSetPK().getWorktimeCd(), 
				this.entity.getKshmtWorktimeCommonSetPK().getWorkFormAtr(),
				this.entity.getKshmtWorktimeCommonSetPK().getWorktimeSetMethod(), 
				RoundingTimeType.PUB_HOL_WORK_TIMEZONE.value);
		KshmtSpecialRoundOut entity = currentSets.get(pk);
		if (entity == null) {
			entity = new KshmtSpecialRoundOut();
			entity.setKshmtSpecialRoundOutPK(pk);
		} 	
		
		GoOutTimeRoundingSetting domainSetting = pubHolWorkTimezone.getOfficalUseCompenGoOut().getApproTimeRoundingSetting();
		entity.setPubRoundingMethod(domainSetting.getRoundingMethod().value);
		entity.setPubRounding(domainSetting.getRoundingSetting().getRounding().value);
		entity.setPubRoundingUnit(domainSetting.getRoundingSetting().getRoundingTime().value);
		
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
		
		Map<KshmtSpecialRoundOutPK, KshmtSpecialRoundOut> currentSets = this.entity.getKshmtSpecialRoundOuts().stream()
				.map(KshmtSpecialRoundOut.class::cast)
				.collect(Collectors.toMap(entity -> entity.getKshmtSpecialRoundOutPK(), Function.identity()));

		KshmtSpecialRoundOutPK pk = new KshmtSpecialRoundOutPK(
				this.entity.getKshmtWorktimeCommonSetPK().getCid(), 
				this.entity.getKshmtWorktimeCommonSetPK().getWorktimeCd(), 
				this.entity.getKshmtWorktimeCommonSetPK().getWorkFormAtr(),
				this.entity.getKshmtWorktimeCommonSetPK().getWorktimeSetMethod(),
				RoundingTimeType.WORK_TIMEZONE.value);
		KshmtSpecialRoundOut entity = currentSets.get(pk);
		if (entity == null) {
			entity = new KshmtSpecialRoundOut();
			entity.setKshmtSpecialRoundOutPK(pk);
		} 	
		
		GoOutTimeRoundingSetting domainSetting = workTimezone.getPrivateUnionGoOut().getApproTimeRoundingSetting();
		entity.setPubRoundingMethod(domainSetting.getRoundingMethod().value);
		entity.setPubRounding(domainSetting.getRoundingSetting().getRounding().value);
		entity.setPubRoundingUnit(domainSetting.getRoundingSetting().getRoundingTime().value);
		
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
		
		Map<KshmtSpecialRoundOutPK, KshmtSpecialRoundOut> currentSets = this.entity.getKshmtSpecialRoundOuts().stream()
				.map(KshmtSpecialRoundOut.class::cast)
				.collect(Collectors.toMap(entity -> entity.getKshmtSpecialRoundOutPK(), Function.identity()));

		KshmtSpecialRoundOutPK pk = new KshmtSpecialRoundOutPK(
				this.entity.getKshmtWorktimeCommonSetPK().getCid(), 
				this.entity.getKshmtWorktimeCommonSetPK().getWorktimeCd(), 
				this.entity.getKshmtWorktimeCommonSetPK().getWorkFormAtr(),
				this.entity.getKshmtWorktimeCommonSetPK().getWorktimeSetMethod(),
				RoundingTimeType.WORK_TIMEZONE.value);
		KshmtSpecialRoundOut entity = currentSets.get(pk);
		if (entity == null) {
			entity = new KshmtSpecialRoundOut();
			entity.setKshmtSpecialRoundOutPK(pk);
		} 	
		
		GoOutTimeRoundingSetting domainSetting = ottimezone.getPrivateUnionGoOut().getDeductTimeRoundingSetting();
		entity.setPubRoundingMethod(domainSetting.getRoundingMethod().value);
		entity.setPubRounding(domainSetting.getRoundingSetting().getRounding().value);
		entity.setPubRoundingUnit(domainSetting.getRoundingSetting().getRoundingTime().value);
		
		currentSets.put(pk, entity);
		this.entity.setKshmtSpecialRoundOuts(currentSets.values().stream()
				.collect(Collectors.toList()));
	}

}
