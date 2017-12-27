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
	private List<KshmtSpecialRoundOut> entitySets;

	/** The cid. */
	private String cid;

	/** The worktime cd. */
	private String worktimeCd;

	/** The work form atr. */
	private int workFormAtr;

	/** The worktime set method. */
	private int worktimeSetMethod;

	/**
	 * Instantiates a new jpa go out timezone rounding set set memento.
	 *
	 * @param entity
	 *            the entity
	 */
	public JpaGoOutTimezoneRoundingSetSetMemento(KshmtWorktimeCommonSet entity) {
		this.entitySets = entity.getKshmtSpecialRoundOuts();
		if (CollectionUtil.isEmpty(this.entitySets)) {
			this.entitySets = new ArrayList<>();
		}
		this.cid = entity.getKshmtWorktimeCommonSetPK().getCid();
		this.worktimeCd = entity.getKshmtWorktimeCommonSetPK().getWorktimeCd();
		this.workFormAtr = entity.getKshmtWorktimeCommonSetPK().getWorkFormAtr();
		this.worktimeSetMethod = entity.getKshmtWorktimeCommonSetPK().getWorktimeSetMethod();
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
		
		Map<KshmtSpecialRoundOutPK, KshmtSpecialRoundOut> currentSets = this.entitySets.stream()
				.map(KshmtSpecialRoundOut.class::cast)
				.collect(Collectors.toMap(entity -> entity.getKshmtSpecialRoundOutPK(), Function.identity()));

		KshmtSpecialRoundOutPK pk = new KshmtSpecialRoundOutPK(this.cid, this.worktimeCd, this.workFormAtr,
				this.worktimeSetMethod, RoundingTimeType.PUB_HOL_WORK_TIMEZONE.value);
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
		this.entitySets = currentSets.values().stream()
				.collect(Collectors.toList());
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
		
		Map<KshmtSpecialRoundOutPK, KshmtSpecialRoundOut> currentSets = this.entitySets.stream()
				.map(KshmtSpecialRoundOut.class::cast)
				.collect(Collectors.toMap(entity -> entity.getKshmtSpecialRoundOutPK(), Function.identity()));

		KshmtSpecialRoundOutPK pk = new KshmtSpecialRoundOutPK(this.cid, this.worktimeCd, this.workFormAtr,
				this.worktimeSetMethod, RoundingTimeType.WORK_TIMEZONE.value);
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
		this.entitySets = currentSets.values().stream()
				.collect(Collectors.toList());
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
		
		Map<KshmtSpecialRoundOutPK, KshmtSpecialRoundOut> currentSets = this.entitySets.stream()
				.map(KshmtSpecialRoundOut.class::cast)
				.collect(Collectors.toMap(entity -> entity.getKshmtSpecialRoundOutPK(), Function.identity()));

		KshmtSpecialRoundOutPK pk = new KshmtSpecialRoundOutPK(this.cid, this.worktimeCd, this.workFormAtr,
				this.worktimeSetMethod, RoundingTimeType.WORK_TIMEZONE.value);
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
		this.entitySets = currentSets.values().stream()
				.collect(Collectors.toList());
	}

}
