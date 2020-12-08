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
import nts.uk.ctx.at.shared.infra.entity.worktime.flexset.KshmtFlexOtTimeSetPK;
import nts.uk.ctx.at.shared.infra.entity.worktime.flexset.KshmtWtFleWorkTs;
import nts.uk.ctx.at.shared.infra.entity.worktime.flexset.KshmtFlexWorkTimeSetPK;

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
		if (CollectionUtil.isEmpty(this.entity.getKshmtFlexWorkTimeSets())) {
			this.entity.setKshmtFlexWorkTimeSets(new ArrayList<>());
		}
		
		Integer amPmAtr = this.entity.getKshmtFlexHaRtSetPK().getAmPmAtr();
		
		// get other list entity has type # amPmAtr
		List<KshmtWtFleWorkTs> lstOtherAmPmEntity = this.entity.getKshmtFlexWorkTimeSets().stream()
				.filter(item -> item.getKshmtFlexWorkTimeSetPK().getAmPmAtr() != amPmAtr)
				.collect(Collectors.toList());
		
		// check input empty
		if (CollectionUtil.isEmpty(lstWorkingTimezone)) {
			this.entity.setKshmtFlexWorkTimeSets(lstOtherAmPmEntity);
			return;
		}
		
		// convert map entity
		Map<KshmtFlexWorkTimeSetPK, KshmtWtFleWorkTs> mapEntity = this.entity.getKshmtFlexWorkTimeSets().stream()
				.collect(Collectors.toMap(entity -> ((KshmtWtFleWorkTs) entity).getKshmtFlexWorkTimeSetPK(),
						Function.identity()));
		
		String companyId = this.entity.getKshmtFlexHaRtSetPK().getCid();
		String workTimeCd = this.entity.getKshmtFlexHaRtSetPK().getWorktimeCd();
		
		// add other list AmPm entity
		List<KshmtWtFleWorkTs> newListEntity = new ArrayList<>();
		newListEntity.addAll(lstOtherAmPmEntity);
		
		// set data domain
		newListEntity.addAll(lstWorkingTimezone.stream().map(domain -> {
			
			// newPk
			KshmtFlexWorkTimeSetPK pk = new KshmtFlexWorkTimeSetPK();
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
		this.entity.setKshmtFlexWorkTimeSets(newListEntity);
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
		if (CollectionUtil.isEmpty(this.entity.getKshmtFlexOtTimeSets())) {
			this.entity.setKshmtFlexOtTimeSets(new ArrayList<>());
		}
		
		Integer amPmAtr = this.entity.getKshmtFlexHaRtSetPK().getAmPmAtr();
		
		// get other list entity has type # amPmAtr
		List<KshmtWtFleOverTs> lstOtherAmPmEntity = this.entity.getKshmtFlexOtTimeSets().stream()
				.filter(item -> item.getKshmtFlexOtTimeSetPK().getAmPmAtr() != amPmAtr)
				.collect(Collectors.toList());
		
		// check input empty
		if (CollectionUtil.isEmpty(lstOTTimezone)) {
			this.entity.setKshmtFlexOtTimeSets(lstOtherAmPmEntity);
			return;
		}
		
		// convert map entity
		Map<KshmtFlexOtTimeSetPK, KshmtWtFleOverTs> mapEntity = this.entity.getKshmtFlexOtTimeSets().stream()
				.collect(Collectors.toMap(entity -> ((KshmtWtFleOverTs) entity).getKshmtFlexOtTimeSetPK(),
						Function.identity()));
		
		String companyId = this.entity.getKshmtFlexHaRtSetPK().getCid();
		String workTimeCd = this.entity.getKshmtFlexHaRtSetPK().getWorktimeCd();
		
		// add other list AmPm entity
		List<KshmtWtFleOverTs> newListEntity = new ArrayList<>();
		newListEntity.addAll(lstOtherAmPmEntity);
		
		// set data domain
		newListEntity.addAll(lstOTTimezone.stream().map(domain -> {
			
			// newPk
			KshmtFlexOtTimeSetPK pk = new KshmtFlexOtTimeSetPK();
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
		this.entity.setKshmtFlexOtTimeSets(newListEntity);
	}

}
