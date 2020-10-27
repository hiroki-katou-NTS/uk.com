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
import nts.uk.ctx.at.shared.dom.worktime.common.EmTimeZoneSet;
import nts.uk.ctx.at.shared.dom.worktime.common.FixedWorkTimezoneSetSetMemento;
import nts.uk.ctx.at.shared.dom.worktime.common.OverTimeOfTimeZoneSet;
import nts.uk.ctx.at.shared.infra.entity.worktime.flexset.KshmtWtFleBrFlWek;
import nts.uk.ctx.at.shared.infra.entity.worktime.flexset.KshmtWtFleOverTs;
import nts.uk.ctx.at.shared.infra.entity.worktime.flexset.KshmtWtFleOverTsPK;
import nts.uk.ctx.at.shared.infra.entity.worktime.flexset.KshmtWtFleWorkTs;
import nts.uk.ctx.at.shared.infra.entity.worktime.flexset.KshmtWtFleWorkTsPK;

/**
 * The Class JpaFlexFixedWorkTimezoneSetSetMemento.
 */
public class JpaFlexFixedWorkTimezoneSetSetMemento implements FixedWorkTimezoneSetSetMemento {

	/** The entity. */
	private KshmtWtFleBrFlWek entity;

	/**
	 * Instantiates a new jpa flex fixed work timezone set set memento.
	 *
	 * @param entity
	 *            the entity
	 */
	public JpaFlexFixedWorkTimezoneSetSetMemento(KshmtWtFleBrFlWek entity) {
		super();
		this.entity = entity;
	}


	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.common.FixedWorkTimezoneSetSetMemento#
	 * setLstWorkingTimezone(java.util.List)
	 */
	@Override
	public void setLstWorkingTimezone(List<EmTimeZoneSet> lstWorkingTimezone) {
		
		// check list entity get empty
		if (CollectionUtil.isEmpty(this.entity.getKshmtWtFleWorkTss())) {
			this.entity.setKshmtWtFleWorkTss(new ArrayList<>());
		}
		
		Integer amPmAtr = this.entity.getKshmtWtFleBrFlWekPK().getAmPmAtr();
		
		// get other list entity has type # amPmAtr
		List<KshmtWtFleWorkTs> lstOtherAmPmEntity = this.entity.getKshmtWtFleWorkTss().stream()
				.filter(item -> item.getKshmtWtFleWorkTsPK().getAmPmAtr() != amPmAtr)
				.collect(Collectors.toList());
		
		// check input empty
		if (CollectionUtil.isEmpty(lstWorkingTimezone)) {
			this.entity.setKshmtWtFleWorkTss(lstOtherAmPmEntity);
			return;
		}
		
		// convert map entity
		Map<KshmtWtFleWorkTsPK, KshmtWtFleWorkTs> mapEntity = this.entity.getKshmtWtFleWorkTss().stream()
				.collect(Collectors.toMap(entity -> ((KshmtWtFleWorkTs) entity).getKshmtWtFleWorkTsPK(),
						Function.identity()));
		
		String companyId = this.entity.getKshmtWtFleBrFlWekPK().getCid();
		String workTimeCd = this.entity.getKshmtWtFleBrFlWekPK().getWorktimeCd();
		
		// add other list AmPm entity
		List<KshmtWtFleWorkTs> newListEntity = new ArrayList<>();
		newListEntity.addAll(lstOtherAmPmEntity);
		
		// set data domain
		newListEntity.addAll(lstWorkingTimezone.stream().map(domain -> {
			
			// newPk
			KshmtWtFleWorkTsPK pk = new KshmtWtFleWorkTsPK();
			pk.setCid(companyId);
			pk.setWorktimeCd(workTimeCd);
			pk.setAmPmAtr(amPmAtr);
			pk.setTimeFrameNo(domain.getEmploymentTimeFrameNo().v());
			
			// find entity if existed, else new entity
			KshmtWtFleWorkTs entity = mapEntity.get(pk);
			if (entity == null) {
				entity = new KshmtWtFleWorkTs(pk);
			}
			
			// save to memento
			domain.saveToMemento(new JpaFlexEmTimeZoneSetSetMemento(entity));
			
			return entity;
		}).collect(Collectors.toList()));
		
		// set list entity
		this.entity.setKshmtWtFleWorkTss(newListEntity);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.common.FixedWorkTimezoneSetSetMemento#
	 * setLstOTTimezone(java.util.List)
	 */
	@Override
	public void setLstOTTimezone(List<OverTimeOfTimeZoneSet> lstOTTimezone) {
		// check list entity get empty
		if (CollectionUtil.isEmpty(this.entity.getKshmtWtFleOverTss())) {
			this.entity.setKshmtWtFleOverTss(new ArrayList<>());
		}
		
		Integer amPmAtr = this.entity.getKshmtWtFleBrFlWekPK().getAmPmAtr();
		
		// get other list entity has type # amPmAtr
		List<KshmtWtFleOverTs> lstOtherAmPmEntity = this.entity.getKshmtWtFleOverTss().stream()
				.filter(item -> item.getKshmtWtFleOverTsPK().getAmPmAtr() != amPmAtr)
				.collect(Collectors.toList());
		
		// check input empty
		if (CollectionUtil.isEmpty(lstOTTimezone)) {
			this.entity.setKshmtWtFleOverTss(lstOtherAmPmEntity);
			return;
		}
		
		// convert map entity
		Map<KshmtWtFleOverTsPK, KshmtWtFleOverTs> mapEntity = this.entity.getKshmtWtFleOverTss().stream()
				.collect(Collectors.toMap(entity -> ((KshmtWtFleOverTs) entity).getKshmtWtFleOverTsPK(),
						Function.identity()));
		
		String companyId = this.entity.getKshmtWtFleBrFlWekPK().getCid();
		String workTimeCd = this.entity.getKshmtWtFleBrFlWekPK().getWorktimeCd();
		
		// add other list AmPm entity
		List<KshmtWtFleOverTs> newListEntity = new ArrayList<>();
		newListEntity.addAll(lstOtherAmPmEntity);
		
		// set data domain
		newListEntity.addAll(lstOTTimezone.stream().map(domain -> {
			
			// newPk
			KshmtWtFleOverTsPK pk = new KshmtWtFleOverTsPK();
			pk.setCid(companyId);
			pk.setWorktimeCd(workTimeCd);
			pk.setAmPmAtr(amPmAtr);
			pk.setWorktimeNo(domain.getWorkTimezoneNo().v());
			
			// find entity if existed, else new entity
			KshmtWtFleOverTs entity = mapEntity.get(pk);
			if (entity == null) {
				entity = new KshmtWtFleOverTs(pk);
			}
			
			// save to memento
			domain.saveToMemento(new JpaFlexOverTimeOfTimeZoneSetSetMemento(entity));
			
			return entity;
		}).collect(Collectors.toList()));
		
		// set list entity
		this.entity.setKshmtWtFleOverTss(newListEntity);
	}

}
