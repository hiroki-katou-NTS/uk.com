/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.worktime.fixedset;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.shared.dom.worktime.common.EmTimeZoneSet;
import nts.uk.ctx.at.shared.dom.worktime.common.FixedWorkTimezoneSetSetMemento;
import nts.uk.ctx.at.shared.dom.worktime.common.OverTimeOfTimeZoneSet;
import nts.uk.ctx.at.shared.infra.entity.worktime.fixedset.KshmtWtFixOverTs;
import nts.uk.ctx.at.shared.infra.entity.worktime.fixedset.KshmtFixedOtTimeSetPK;
import nts.uk.ctx.at.shared.infra.entity.worktime.fixedset.KshmtWtFix;
import nts.uk.ctx.at.shared.infra.entity.worktime.fixedset.KshmtWtFixWorkTs;
import nts.uk.ctx.at.shared.infra.entity.worktime.fixedset.KshmtFixedWorkTimeSetPK;

/**
 * The Class JpaFixedWorkTimezoneSetSetMemento.
 */
public class JpaFixedWorkTimezoneSetSetMemento implements FixedWorkTimezoneSetSetMemento {

	/** The parent entitjy. */
	private KshmtWtFix parentEntity;
	
	/** The cid. */
	private String cid;

	/** The worktime cd. */
	private String worktimeCd;

	/** The type. */
	private int type;

	/**
	 * Instantiates a new jpa fixed work timezone set set memento.
	 *
	 * @param parentEntity the parent entitjy
	 */
	public JpaFixedWorkTimezoneSetSetMemento(KshmtWtFix parentEntity, int type) {
		super();
		this.parentEntity = parentEntity;
		this.type = type;
		
		// initial entity
		this.initialData();
	}

	/**
	 * Initial data.
	 */
	private void initialData() {
		this.cid = this.parentEntity.getKshmtFixedWorkSetPK().getCid();
		this.worktimeCd = this.parentEntity.getKshmtFixedWorkSetPK().getWorktimeCd();
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
		
		if (CollectionUtil.isEmpty(this.parentEntity.getKshmtFixedWorkTimeSets())) {
			this.parentEntity.setKshmtFixedWorkTimeSets(new ArrayList<>());
		}
		
		List<KshmtWtFixWorkTs> otherList = this.parentEntity.getKshmtFixedWorkTimeSets().stream()
				.filter(entity -> entity.getKshmtFixedWorkTimeSetPK().getAmPmAtr() != this.type)
				.collect(Collectors.toList());
		
		// KSHMT_WT_FIX_WORK_TS
		if (CollectionUtil.isEmpty(lstWorkingTimezone)) {
			this.parentEntity.setKshmtFixedWorkTimeSets(otherList);
			return;
		}
		
		// get list entity
		List<KshmtWtFixWorkTs> lstEntity = this.parentEntity.getKshmtFixedWorkTimeSets().stream()
				.filter(entity -> entity.getKshmtFixedWorkTimeSetPK().getAmPmAtr() == this.type)
				.collect(Collectors.toList());
		if (CollectionUtil.isEmpty(lstEntity)) {
			lstEntity = new ArrayList<>();
		}
		
		List<KshmtWtFixWorkTs> newListEntity = new ArrayList<>();
		
		// sort asc start time
		lstWorkingTimezone.stream()
				.sorted((item1, item2) -> item1.getTimezone().getStart().v() - item2.getTimezone().getStart().v())
				.collect(Collectors.toList());
		
		for (EmTimeZoneSet domain : lstWorkingTimezone) {
			
			KshmtFixedWorkTimeSetPK pk = new KshmtFixedWorkTimeSetPK(this.cid, this.worktimeCd, this.type,
					domain.getEmploymentTimeFrameNo().v());
			
			// get entity existed
			KshmtWtFixWorkTs entity = lstEntity.stream()
					.filter(item -> item.getKshmtFixedWorkTimeSetPK().equals(pk))
					.findFirst()
					.orElse(new KshmtWtFixWorkTs(pk));
			
			// set data
			entity.setUnit(domain.getTimezone().getRounding().getRoundingTime().value);
			entity.setRounding(domain.getTimezone().getRounding().getRounding().value);
			entity.setTimeStr(domain.getTimezone().getStart().valueAsMinutes());
			entity.setTimeEnd(domain.getTimezone().getEnd().valueAsMinutes());
			
			// add list
			newListEntity.add(entity);
		}
		
		newListEntity.addAll(otherList);
		
		this.parentEntity.setKshmtFixedWorkTimeSets(newListEntity);
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
		
		if (CollectionUtil.isEmpty(this.parentEntity.getKshmtFixedOtTimeSets())) {
			this.parentEntity.setKshmtFixedOtTimeSets(new ArrayList<>());
		}
		
		List<KshmtWtFixOverTs> otherList = this.parentEntity.getKshmtFixedOtTimeSets().stream()
				.filter(entity -> entity.getKshmtFixedOtTimeSetPK().getAmPmAtr() != this.type)
				.collect(Collectors.toList());
		
		// KSHMT_WT_FIX_OVER_TS
		if (CollectionUtil.isEmpty(lstOTTimezone)) {
			this.parentEntity.setKshmtFixedOtTimeSets(otherList);
			return;
		}
		
		// get list entity
		List<KshmtWtFixOverTs> lstEntity = this.parentEntity.getKshmtFixedOtTimeSets().stream()
				.filter(entity -> entity.getKshmtFixedOtTimeSetPK().getAmPmAtr() == this.type)
				.collect(Collectors.toList());
		if (CollectionUtil.isEmpty(lstEntity)) {
			lstEntity = new ArrayList<>();
		}
		
		List<KshmtWtFixOverTs> newListEntity = new ArrayList<>();
		
		// sort asc start time
		lstOTTimezone.stream()
				.sorted((item1, item2) -> item1.getTimezone().getStart().v() - item2.getTimezone().getStart().v())
				.collect(Collectors.toList());
		
		for (OverTimeOfTimeZoneSet domain : lstOTTimezone) {
			
			KshmtFixedOtTimeSetPK pk = new KshmtFixedOtTimeSetPK(this.cid, this.worktimeCd, this.type,
					domain.getWorkTimezoneNo().v());
			
			// get entity existed
			KshmtWtFixOverTs entity = lstEntity.stream()
					.filter(item -> item.getKshmtFixedOtTimeSetPK().equals(pk))
					.findFirst()
					.orElse(new KshmtWtFixOverTs(pk));
			
			// set data
			domain.saveToMemento(new JpaFixOverTimeOfTimeZoneSetSetMemento(entity));
			
			// add list
			newListEntity.add(entity);
		}
		
		newListEntity.addAll(otherList);
		
		this.parentEntity.setKshmtFixedOtTimeSets(newListEntity);
	}

}
